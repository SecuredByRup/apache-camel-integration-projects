# Lab 17: Using EIPs for Scalable Integrations with Apache Camel

<p align="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Apache_Camel-8C1515?style=for-the-badge&logo=apachecamel&logoColor=white" alt="Apache Camel">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black" alt="Linux">
</p>

## Overview

This repository features **Lab 17: Using EIPs for Scalable Integrations**, an enterprise integration project demonstrating foundational Enterprise Integration Patterns (EIPs) using Apache Camel 3.20. The application orchestrates complex composite order processing workflows by implementing the **Splitter**, **Aggregator**, and **Recipient List** patterns to achieve scalable, loosely coupled, and resilient message processing.

---

## Architecture & Implementation Highlights

* **Splitter Pattern (`SplitterRoute.java`)**: Automatically breaks down composite `Order` payloads into individual `OrderItem` messages for granular processing while injecting unique correlation IDs (`originalOrderId`).
* **Aggregator Pattern (`AggregatorRoute.java` & `OrderAggregationStrategy.java`)**: Reassembles processed item messages back into a finalized `AggregatedOrder` based on correlation headers, governed by batch size thresholds (`completionSize(4)`) and inactivity timeouts (`completionTimeout(3000)`).
* **Recipient List Pattern (`RecipientListRoute.java` & `OrderRecipientListResolver.java`)**: Dynamically evaluates business metrics (e.g., total order value) at runtime to route finalized orders in parallel across specific downstream systems (`direct:orderProcessing`, `direct:shippingArrangement`, `direct:financeApproval`, `direct:customerNotification`).

---

## Verification & Execution

### 1. Build Verification & Compilation
The project model, data structures, dynamic resolvers, and Camel routes are compiled cleanly using Maven to verify build integrity across all packages.

```bash
mvn clean compile
```

![Build Verification](images/Screenshot%20%28586%29.png)

### 2. Complete End-to-End EIP Route Execution
The complete application initiates the Apache Camel context, triggering automated order generation, splitting, individual item processing, re-aggregation with tax calculations, and dynamic distribution to downstream recipient queues.

```bash
mvn clean compile exec:java -Dexec.mainClass="com.alnafi.camel.eip.CompleteEIPApplication"
```

![Complete EIP Execution](images/Screenshot%20%28587%29.png)

---

## Conclusion

Lab 17 demonstrates the core concepts of building scalable enterprise integration architectures using Apache Camel. By successfully combining the Splitter, Aggregator, and Recipient List patterns, this project proves production-ready capability in handling complex message flows, maintaining data consistency with correlation IDs, and dynamically fanning out data to decoupled microservices.
