### Spring Kafka using multiple partitions with unique KEY.
- The demo projects with Spring boot and Apache Kafka for using multiple partitions.

## Prerequisites
- Spring Boot (3.2.1)
- Maven 
- Java (17)
- Libraries:
  * starter-web 
  * kafka
  * lombok/logback
  
## Projects 
- There are 2 prjects (producer and consumer). It is requied to work them together.
* sk-producer-demo & sk-consumer-demo for demostrate string messages 
* sk-producer-custom-messages & sk-consumer-custom-messages for custome message.

## Spring Kafka Document:
- Reference: https://docs.spring.io/spring-kafka/reference/kafka/receiving-messages/message-listener-container.html

- Discover how to create Kafka producers efficiently and send messages both synchronously and asynchronously. Learn the nuances of sending messages with and without keys, and explore strategies for efficient message routing and consumption.
- When testing synchronous, it is required to comment out asynchronous method and vice-versa.
- Explore advanced topics including custom message production and consumption, handling consumer partition rebalancing with multiple consumers, and optimizing offset commitments for reliability.
- Practice for error handling for deploying Kafka applications outside of cloud environments or Kubernetes.

- For Consume partition Reblance with multiple comsumers
* Create 2nd instance of Consumer (in IDE with diffrent port) run it.
* The partitons will be reblance (one consumer might have one and other will will have other 2 partitions).

- Offset Commit (please check 'Committing Offsets' in spring doc as embeded) 
* By default it is BATCH mode
* In applicaiton properties (sk-consumer-custom-messages) added 'spring.kafka.listener.ack-mode = RECORD' to enable commit per record.

- Consumer Concurrency
* The concurrency is not applicable when we will use cloud or K8S as it will handle concurrency.
* Example:     @KafkaListener(topics = "location-topic", groupId = "location-group-id", concurrency = "3")


## START THE KAFKA ENVIRONMENT:
- NOTE: Local environment must have Java 8+ installed.

- Kafka Servers Start (local: eg. C:\kafka_2.13-3.7.0) with zookeeper.
* 1) .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
* 2) .\bin\windows\kafka-server-start.bat .\config\server.properties

- Apache Kafka can be started using ZooKeeper or KRaft. To get started with either configuration follow one of the sections below but not both.

* Kafka with KRaft:
- Kafka can be run using KRaft mode using local scripts and downloaded files or the docker image. Follow one of the sections below but not both to start the kafka server.
- Generate a Cluster UUID
``` 
$ KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)" 

```
- Format Log Directories
``` 
$ bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties 
```

