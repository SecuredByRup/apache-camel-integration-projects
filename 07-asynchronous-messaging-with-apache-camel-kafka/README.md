# Module 7: Asynchronous Messaging with Apache Camel and Apache Kafka

[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-E31C25?style=for-the-badge&logo=apachecamel&logoColor=white)](https://camel.apache.org/)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)

## Overview

This repository contains the implementation for **Asynchronous Messaging with Apache Kafka**, focusing on building robust, scalable event-driven architectures using **Apache Camel** and **Apache Kafka**. The project demonstrates how to instantiate enterprise integration routes that produce structured order events, filter high-value transactions, handle partitioned consumer groups, and implement fault-tolerant messaging flows.

## Architecture & Components

The solution is structured around three primary layers:

1. **Producer Route (`OrderProducerRoute`)**: Generates periodic order transactions via a Camel timer component, serializes Java objects into JSON using Jackson, and dispatches them to the `order-events` Kafka topic. High-value transactions are simultaneously routed to a dedicated `notification-events` topic.

2. **Consumer Routes (`OrderConsumerRoute`)**: Consumes incoming order events across distinct consumer groups (`order-logger`, `customer-processor`, and `high-value-processor`), executing business rules such as VIP customer discounts (`CUST001`) and priority alerting.

3. **Kafka Broker & Zookeeper Ecosystem**: Manages topic partitioning, offset tracking, and consumer group coordination across distributed nodes.

## Implementation & Verification

### 1. Service Initialization and Project Build

Zookeeper and Kafka broker processes were initiated, followed by the creation of partitioned topics (`order-events` and `notification-events`). The Maven project was compiled successfully with the required Camel and Kafka dependencies.

![Service Initialization and Compilation](images/Screenshot%20(564).png)
*Figure 1: Verification of active Kafka and Zookeeper daemon processes (`jps`), topic listing, and successful Maven compilation (`BUILD SUCCESS`).*

### 2. Consumer Groups & Offset Management

To inspect the behavior of distributed event consumption, consumer groups were queried to verify offset tracking, lag metrics, and partition assignments.

![Consumer Groups and Offsets](images/Screenshot%20(565).png)
*Figure 2: Listing active Kafka consumer groups (`order-logger`, `high-value-processor`, `notification-processor`, `customer-processor`) and inspecting topic offsets and consumer lag.*

### 3. Runtime Execution & Event Processing

When executing the main application class (`KafkaCamelApplication`), the integration pipeline processes generated orders, applies VIP discounts, and triggers notifications in real time.

![Runtime Execution Logs](images/Screenshot%20(566).png)
*Figure 3: Application runtime logs illustrating order generation, JSON unmarshaling, VIP customer discount application, and high-value alert dispatch.*

## Conclusion

This project successfully demonstrates the power of combining Apache Camel's enterprise integration patterns with Apache Kafka's high-throughput distributed commit log. By implementing decoupled producers, partitioned consumers, and event-driven filters, this project establishes a resilient foundation for modern microservices and real-time streaming architectures.
