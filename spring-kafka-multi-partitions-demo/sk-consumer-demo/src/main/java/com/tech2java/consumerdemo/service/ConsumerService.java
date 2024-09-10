package com.tech2java.consumerdemo.service;

import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    
    
    //@KafkaListener (topics = "payment-topic", groupId = "g,roup_id", topicPartitions = @TopicPartition(topic="payment-topic", partitions = {"1","2"}))
    @KafkaListener(topics = "payment-topic", groupId = "g,roup_id", containerFactory = "concurrentKafkaListenerContainerFactory") 
    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 3000, multiplier =2 ))
    public void consume(ConsumerRecord<String,String> message) {
            log.info("Key: {} | Value: {}", message.key(), message.value());
            log.info("Partition: {} | Offset: {}", message.partition(), message.offset());
            String value = message.value().substring(message.value().indexOf("Amount:$")+"Amount:$".length() , message.value().length());
            log.info("amount: {}" , value);
            try {
            	double isNum = Double.parseDouble(value);
            	if (isNum>500) {
            		  throw new RuntimeException("Over limit payment received !");
            	}
            }
            catch(NumberFormatException e) {
            	 e.printStackTrace();
            }
            
    }

   @DltHandler
    public void processFailureMessages(ConsumerRecord<String,String> message){
        log.info("Dead letter topic Key: {} | Value: {}", message.key(), message.value());
        //Send email notifications about failure message
    }

}
