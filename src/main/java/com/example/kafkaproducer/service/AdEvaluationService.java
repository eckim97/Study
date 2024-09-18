package com.example.kafkaproducer.service;

import com.example.kafkaproducer.vo.EffectOrNot;
import com.example.kafkaproducer.vo.PurchaseLog;
import com.example.kafkaproducer.vo.PurchaseLogOneProduct;
import com.example.kafkaproducer.vo.WatchingAdLog;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdEvaluationService {
    // 광고 데이터는 중복 Join될 필요없다. --> Table
    // 광고 이력이 먼저 들어온다.
    // 구매 이력은 상품별로 들어오지 않는다. (복수개의 상품 존재) --> contain
    // 광고에 머문 시간이 10초 이상되어야만 join 대상
    // 특정가격 이상의 상품은 join 대상에서 제외 (100만원)
    // 광고이력 : KTable(AdLog), 구매이력 : KTable(PurchaseLogOneProduct)
    // filtering, 형 변환,
    // EffectOrNot --> Json 형태로 Topic : AdEvaluationComplete

    @Autowired
    Producer myprdc;

    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        JsonSerializer<EffectOrNot> effectSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLog> purchaseLogJsonSerializer = new JsonSerializer<>();
        JsonSerializer<WatchingAdLog> watchingLogJsonSerializer = new JsonSerializer<>();
        JsonSerializer<PurchaseLogOneProduct> purchaseLogOneProductJsonSerializer = new JsonSerializer<>();

        JsonDeserializer<EffectOrNot> effectDeserializer = new JsonDeserializer<>(EffectOrNot.class);
        JsonDeserializer<PurchaseLog> purchaseLogDeserializer = new JsonDeserializer<>(PurchaseLog.class);
        JsonDeserializer<WatchingAdLog> watchingLogDeserializer = new JsonDeserializer<>(WatchingAdLog.class);
        JsonDeserializer<PurchaseLogOneProduct> purchaseLogOneProductJsonDeserializer = new JsonDeserializer<>(PurchaseLogOneProduct.class);

        Serde<EffectOrNot> effectOrNotSerde = Serdes.serdeFrom(effectSerializer, effectDeserializer);
        Serde<PurchaseLog> purchaseLogSerde = Serdes.serdeFrom(purchaseLogJsonSerializer, purchaseLogDeserializer);
        Serde<WatchingAdLog> watchingAdLogSerde = Serdes.serdeFrom(watchingLogJsonSerializer, watchingLogDeserializer);
        Serde<PurchaseLogOneProduct> purchaseLogOneProductSerde = Serdes.serdeFrom(purchaseLogOneProductJsonSerializer, purchaseLogOneProductJsonDeserializer);

        // adLog stream --> table
        KTable<String, WatchingAdLog> adTable = sb.stream("adLog", Consumed.with(Serdes.String(), watchingAdLogSerde))
                .selectKey((k,v) -> v.getUserId() + "_" + v.getProductId())
                .toTable(Materialized.<String, WatchingAdLog, KeyValueStore<Bytes, byte[]>>as("adStore")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(watchingAdLogSerde)
                );

        KStream<String, PurchaseLog> purchaseLogKStream = sb.stream("purchaseLog", Consumed.with(Serdes.String(), purchaseLogSerde));

        purchaseLogKStream.foreach((k, v) -> {
            for (Map<String, String> prodInfo:v.getProductInfo()) {
                if (Integer.valueOf(prodInfo.get("price")) < 1000000) {
                    PurchaseLogOneProduct tempVo = new PurchaseLogOneProduct();
                    tempVo.setUserId(v.getUserId());
                    tempVo.setProductId(prodInfo.get("productId"));
                    tempVo.setOrderId(v.getOrderId());
                    tempVo.setPrice(prodInfo.get("price"));
                    tempVo.setPurchasedDt(v.getPurchasedDt());

                    myprdc.sendJoinedMsg("purchaseLogOneProduct", tempVo);
                }
            }
        });

        KTable<String, PurchaseLogOneProduct> purchaseLogOneProductKTable = sb.stream("purchaseLogOneProduct", Consumed.with(Serdes.String(), purchaseLogOneProductSerde))
                .selectKey((k, v) -> v.getUserId() + "_" + v.getProductId())
                .toTable(Materialized.<String, PurchaseLogOneProduct, KeyValueStore<Bytes, byte[]>>as("purchaseLogStore")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(purchaseLogOneProductSerde)
                );

        ValueJoiner<WatchingAdLog, PurchaseLogOneProduct, EffectOrNot> tableStreamJoiner = (leftValue, rightValue) -> {
            EffectOrNot returnValue = new EffectOrNot();
            returnValue.setUserId(leftValue.getUserId());
            returnValue.setAdId(leftValue.getAdId());
            returnValue.setOrderId(rightValue.getOrderId());
            Map<String, String> tempProdInfo = new HashMap<>();
            tempProdInfo.put("productId", rightValue.getProductId());
            tempProdInfo.put("price", rightValue.getPrice());
            returnValue.setProductInfo(tempProdInfo);
            return returnValue;
        };

        adTable.join(purchaseLogOneProductKTable, tableStreamJoiner)
                .toStream().to("AdEvaluationComplete", Produced.with(Serdes.String(), effectOrNotSerde));
    }
}
