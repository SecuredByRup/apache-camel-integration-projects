# Enterprise API Gateway with Apache Camel & Spring Security

![Java](https://img.shields.io/badge/Java-11%20%7C%2017-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.6-brightgreen?style=flat-square&logo=springboot)
![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.0-red?style=flat-square&logo=apachecamel)
![Apache Maven](https://img.shields.io/badge/Maven-3.8+-blue?style=flat-square&logo=apachemaven)
![Security](https://img.shields.io/badge/Spring%20Security-Basic%20Auth-yellow?style=flat-square&logo=springsecurity)

---

## Overview

This project contains a comprehensive implementation of the **API Gateway Pattern** utilizing **Apache Camel**, **Spring Boot**, and **Spring Security**. The gateway acts as a secure, unified single entry point for a distributed microservices architecture, managing request routing, authentication, rate-limiting, fault tolerance, and multi-service response aggregation.

---

## Key Architecture & Features

* **API Mediation & Routing**: Centralizes external client requests and proxies them to backend mock microservices (`User Service`, `Product Service`, and `Order Service`).
* **Role-Based Access Control (RBAC)**: Enforces HTTP Basic Authentication using Spring Security, separating privileges between standard users (`USER`) and administrators (`ADMIN`).
* **Rate Limiting & Throttling**: Implements Camel’s throttling mechanisms to protect downstream services from traffic spikes and service degradation.
* **Fault-Tolerant Error Handling**: Utilizes `doTry / doCatch` blocks to gracefully handle backend service unavailability and return standardized HTTP `503` fallbacks.
* **Response Aggregation**: Composes a unified dashboard view by performing parallel multicast requests across multiple backend services.

---

## Project Structure

```text
camel-api-gateway/
├── src/main/java/com/example/gateway/
│   ├── ApiGatewayApplication.java
│   ├── ApiGatewayRoutes.java
│   ├── MockBackendRoutes.java
│   ├── AggregationRoutes.java
│   └── SecurityConfig.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

---

## Getting Started & Execution

### Prerequisites
* Java 11 or 17 OpenJDK
* Apache Maven 3.8+

### Build and Run Application
Compile and start the API Gateway along with its embedded mock backend services:

```bash
mvn clean compile
mvn spring-boot:run
```

#### Application Startup Verification
When started successfully, Spring Boot initializes the application context on port `8090` (for Actuator metrics), while Apache Camel and Jetty run the API Gateway on port `8080` alongside mock microservice instances on ports `8081`, `8082`, and `8083`.

![Application Startup and Initialization](images/Screenshot%20(578).png)

---

## Testing & Validation

### 1. Basic Functionality & Security Testing
Verify endpoint access and role-based access control using `cURL`:

```bash
# Test User Service (USER role required)
curl -u user:password http://localhost:8080/api/v1/users

# Test Order Service (ADMIN role required)
curl -u admin:admin http://localhost:8080/api/v1/orders
```

### 2. Rate Limiting / Throttling Verification
Execute the automated rate-limiting test script to observe throttling thresholds (e.g., 10 requests per minute for the User Service):

```bash
chmod +x test-rate-limiting.sh
./test-rate-limiting.sh
```

![Rate Limiting Script Execution](images/Screenshot%20(579).png)

### 3. Concurrent Performance Testing
Evaluate the throughput and latency of the API Gateway under concurrent load conditions:

```bash
chmod +x performance-test.sh
./performance-test.sh
```

![Performance and Concurrent Load Testing](images/Screenshot%20(581).png)

---

## Conclusion

The successful implementation of this project demonstrates how an enterprise-grade API Gateway can be constructed using lightweight integration frameworks like Apache Camel and Spring Boot. By centralizing core infrastructural concerns—such as security enforcement, rate-limiting, error handling, and parallel response aggregation—this architecture significantly reduces client-side complexity, enhances system resilience, and establishes a secure boundary for modern microservices ecosystems.
