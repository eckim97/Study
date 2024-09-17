package com.example.kafkaproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    String topicName = "kafkaProducer";

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void pub(String msg) {
        System.out.println("Sending message: " + msg + " to topic: " + topicName);
        try {
            kafkaTemplate.send(topicName, msg).get(); // .get()을 사용하여 동기적으로 결과를 기다림
            System.out.println("Message sent successfully");
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
