# 🐫 Apache Camel Integration Projects

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-4.x-red?style=for-the-badge&logo=apachecamel&logoColor=white)](https://camel.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![ActiveMQ](https://img.shields.io/badge/ActiveMQ-Messaging-blue?style=for-the-badge&logo=apache&logoColor=white)](https://activemq.apache.org/)
[![Podman](https://img.shields.io/badge/Podman-Containerization-purple?style=for-the-badge&logo=podman&logoColor=white)](https://podman.io/)
[![JUnit](https://img.shields.io/badge/JUnit-Testing-green?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)

A comprehensive collection of hands-on Apache Camel integration labs and enterprise messaging projects. This repository demonstrates practical implementations of **Enterprise Integration Patterns (EIPs)**, Spring Boot microservices, asynchronous messaging pipelines, error handling strategies, and containerized deployments.

---

## 📂 Lab Modules Directory

| Module | Lab Name | Key Technologies & Concepts |
| :--- | :--- | :--- |
| **01** | [`01-apache-camel-xml-dsl-eips`](./01-apache-camel-xml-dsl-eips) | XML DSL Routing, Content-Based Router, EIPs |
| **02** | [`02-apache-camel-custom-processor`](./02-apache-camel-custom-processor) | Custom Java Processors, Exchange Manipulation |
| **03** | [`03-implementing-eips-using-camel-and-spring`](./03-implementing-eips-using-camel-and-spring) | Spring Boot Integration, Java DSL, Bean Binding |
| **04** | [`04-testing-camel-routes-with-junit`](./04-testing-camel-routes-with-junit) | Unit Testing, Camel Test Support, JUnit 5 |
| **05** | [`05-error-handling-and-dlq-with-apache-camel`](./05-error-handling-and-dlq-with-apache-camel) | Dead Letter Channel, Exception Handling, Redelivery |
| **06** | [`06-integrating-apache-camel-with-activemq`](./06-integrating-apache-camel-with-activemq) | JMS Messaging, Apache ActiveMQ, Queue/Topic routing |
| **07** | [`07-asynchronous-messaging-with-apache-camel`](./07-asynchronous-messaging-with-apache-camel) | Async Routing, Multicasting, Concurrent Processing |
| **08** | [`08-apache-camel-distributed-transactions`](./08-apache-camel-distributed-transactions) | XA Transactions, Rollback Handling, Database Consistency |
| **09** | [`09-exposing-restful-apis-with-camel`](./09-exposing-restful-apis-with-camel) | REST DSL, OpenAPI/Swagger, JSON/XML Marshalling |
| **10** | [`10-consuming-external-rest-api-with-camel`](./10-consuming-external-rest-api-with-camel) | HTTP Client, External REST Integration, Error Mapping |
| **11** | [`11-deploying-apache-camel-routes-with-podman`](./11-deploying-apache-camel-routes-with-podman) | Containerization, Podman Images, Dockerfiles |
| **13** | [`13-enterprise-api-gateway-with-camel-and-spring`](./13-enterprise-api-gateway-with-camel-and-spring) | API Gateway Pattern, Routing Logic, Load Balancing |
| **14** | [`14-dynamic-routing-and-performance-testing-with-apache-camel`](./14-dynamic-routing-and-performance-testing-with-apache-camel) | Dynamic Router EIP, Performance Benchmarking, Logging |

---

## 🛠️ Prerequisites & Tech Stack

To run and test these integration labs locally, ensure you have the following installed:
* **Java Development Kit (JDK):** Version 17 or higher
* **Apache Maven:** 3.8+ for project building and dependency management
* **Container Tool:** Podman (or Docker) for containerized labs
* **Message Broker:** Apache ActiveMQ (for JMS messaging modules)

---

## 🚀 Getting Started

1. **Clone the repository:**
```bash
   git clone [https://github.com/SecuredByRup/apache-camel-integration-projects.git](https://github.com/SecuredByRup/apache-camel-integration-projects.git)
   cd apache-camel-integration-projects
```
2. **Navigate into any lab module:**
```bash
    cd 03-implementing-eips-using-camel-and-spring
```

3. **Build and run with Maven:**
```bash
    mvn clean spring-boot:run
```

👨‍💻 Author: SecuredByRup
GitHub: @SecuredByRup
