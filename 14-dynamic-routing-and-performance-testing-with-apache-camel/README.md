# Module 14: Dynamic Routing and Performance Testing with Apache Camel

<p align="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Apache_Camel-8C1515?style=for-the-badge&logo=apachecamel&logoColor=white" alt="Apache Camel">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/Jetty-000000?style=for-the-badge&logo=eclipsejetty&logoColor=white" alt="Jetty">
  <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black" alt="Linux">
</p>

## Overview

This repository features **Dynamic Routing with Apache Camel**, an enterprise integration project focusing on dynamic message routing, centralized rule engines, and concurrent performance testing. Built with Java, Apache Camel, and Maven, the application exposes HTTP endpoints via embedded Jetty to evaluate incoming JSON payloads dynamically and dispatch them to appropriate processing departments, priority queues, and regional handlers.

---

## Architecture & Implementation Highlights

* **Externalized Configuration Service (`ConfigurationService.java`)**: Centralizes routing rules, priority weights, and regional queue mappings, separating business rules from integration routes.
* **Category-Based Dynamic Routing (`AdvancedDynamicRouting.java`)**: Listens on `http://0.0.0.0:8080/products`, parses JSON payloads using Jackson, and routes messages using recipient lists.
* **Multi-Criteria Complex Routing (`/complex-orders`)**: Evaluates order amounts, priorities, and geographical regions using Camel choice predicates and custom headers.
* **Multithreaded Performance Testing (`PerformanceTest.java`)**: Simulates high-concurrency client requests using Java's modern HTTP client and thread pools to benchmark throughput and system reliability under load.

---

## Execution & Verification

### 1. Application Startup & Route Initialization
The application initializes the Apache Camel Main context, binding the embedded Jetty server to port `8080` and activating all category processors, priority handlers, and regional queues.

```bash
mvn exec:java -Dexec.mainClass="com.example.routing.AdvancedDynamicRouting"
```

![Application Startup](images/Screenshot%20%28585%29.png)

### 2. Functional & Performance Test Execution
Incoming HTTP POST requests are processed through automated test scripts to validate functional correctness (`Product order routed successfully` and `Complex order processed`), followed by a 50-request concurrent load test yielding zero failures.

```bash
# Run functional tests
./test-dynamic-routing.sh

# Run concurrency load test
java -cp target/classes com.example.routing.PerformanceTest
```

![Test Results and Performance Load Test](images/Screenshot%20%28584%29.png)

---

## Conclusion

Module 14 successfully demonstrates advanced enterprise integration patterns using Apache Camel. By combining external rule engines, dynamic recipient lists, and robust multithreaded performance benchmarking, this project establishes production-ready competencies in building scalable, loosely coupled microservices and message-driven architectures.
