package com.example.kafkaproducer.config;

import com.example.kafkaproducer.util.PurchaseLogOneProductSerializer;
import com.example.kafkaproducer.util.PurchaseLogSerializer;
import com.example.kafkaproducer.util.WatchingAdLogSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration myKStreamConfig() {
        Map<String, Object> myKStreamConfig = new HashMap<>();
        myKStreamConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "lecture-6");
        myKStreamConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "3.39.223.252:9092,43.203.201.212:9092,3.36.122.61:9092");
        myKStreamConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        myKStreamConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreamsConfiguration(myKStreamConfig);
    }

    @Bean
    public KafkaTemplate<String, Object> KafkaTemplateForGeneral() {
        return new KafkaTemplate<String, Object>(ProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> ProducerFactory() {
        Map<String, Object> myConfig = new HashMap<>();
        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "3.39.223.252:9092,43.203.201.212:9092,3.36.122.61:9092");
        myConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        myConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PurchaseLogOneProductSerializer.class);

        return new DefaultKafkaProducerFactory<>(myConfig);
    }

    @Bean
    public KafkaTemplate<String, Object> KafkaTemplateForWatchingAdLog() {
        return new KafkaTemplate<String, Object>(ProducerFactoryForWatchingAdLog());
    }

    @Bean
    public ProducerFactory<String, Object> ProducerFactoryForWatchingAdLog() {
        Map<String, Object> myConfig = new HashMap<>();

        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "3.39.223.252:9092,43.203.201.212:9092,3.36.122.61:9092");
        myConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        myConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, WatchingAdLogSerializer.class);

        return new DefaultKafkaProducerFactory<>(myConfig);
    }

    @Bean
    public KafkaTemplate<String, Object> KafkaTemplateForPurchaseLog() {
        return new KafkaTemplate<String, Object>(ProducerFactoryForPurchaseLog());
    }
    @Bean
    public ProducerFactory<String, Object> ProducerFactoryForPurchaseLog() {
        Map<String, Object> myConfig = new HashMap<>();

        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "3.39.223.252:9092,43.203.201.212:9092,3.36.122.61:9092");
        myConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        myConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PurchaseLogSerializer.class);

        return new DefaultKafkaProducerFactory<>(myConfig);
    }

//
//    @Bean
//    public ConsumerFactory<String, Object> ConsumerFactory() {
//        Map<String, Object> myConfig = new HashMap<>();
//        myConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "13.124.237.39:9092,3.38.181.75:9092,3.35.220.144:9092");
//        myConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        myConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        return new DefaultKafkaConsumerFactory<>(myConfig);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> myfactory = new ConcurrentKafkaListenerContainerFactory<>();
//        myfactory.setConsumerFactory(ConsumerFactory());
//        return myfactory;
//    }


}
