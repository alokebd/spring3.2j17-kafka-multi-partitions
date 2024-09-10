package com.tech2java.kafka.customproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tech2java.kafka.customproducer.model.Location;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProducerService {
    private static AtomicInteger idCounter = new AtomicInteger();
    private static final String TOPIC_NAME ="location-topic";
    @Autowired
    private KafkaTemplate<String, Location> locationKafkaTemplate;


    @Scheduled(fixedRate = 2000)
    public void sendLocationsAsynchronously(){
        Location location = setMessageMetaData();
        log.info("Sending location information {}",location);
        locationKafkaTemplate.send(TOPIC_NAME,generateTransactionKey(),location)
                .whenComplete(((sendResult, throwable) -> {
                    if(throwable!=null){
                        onFailure(throwable);
                    }else {
                        onSuccess(sendResult);
                    }
                }));
    }

    private void onSuccess(SendResult<String, Location> sendResult) {
        log.info("Received new metadata. \n" +
                        "Topic: {}, Partition: {}, Offset: {}, Timestamp: {}",
                sendResult.getRecordMetadata().topic(),
                sendResult.getRecordMetadata().partition(),
                sendResult.getRecordMetadata().offset(),
                sendResult.getRecordMetadata().timestamp());
    }

    private void onFailure(Throwable throwable) {
        log.info("Error occurred while producing the message {}", throwable);
    }

    private Location setMessageMetaData() {
        Location location = new Location();
        location.setLocation(generateRandomLocation());
        location.setId(createID());
        location.setMessageId(UUID.randomUUID().toString());
        location.setSourceOriginator("Location Service");
        location.setTimestamp(new Date());
        return location;
    }

    public String generateTransactionKey(){
        return UUID.randomUUID().toString();
    }

    private static final String[] cities = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
            "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
            "Austin", "Jacksonville", "San Francisco", "Indianapolis", "Columbus",
            "Fort Worth", "Charlotte", "Seattle", "Denver", "Washington"
    };

    public static String generateRandomLocation() {
        int index = ThreadLocalRandom.current().nextInt(cities.length);
        return cities[index];
    }


    public static int createID(){
        return idCounter.getAndIncrement();
    }
}
