package com.tech2java.kafka.customconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.tech2java.kafka.customconsumer.model.Location;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "location-topic", groupId = "location-group-id", concurrency = "3") //note: concurrency is not applicable when we will use cloud or K8S as it will handle concurrency
    public void listen(ConsumerRecord<String,Location> message) {
        log.info("Key: {} | Value: {}", message.key(), message.value());
        log.info("Partition: {} | Offset: {}", message.partition(), message.offset());
    }
}
