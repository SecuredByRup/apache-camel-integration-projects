# Module 4: Testing Apache Camel Routes with JUnit 5

[![Java](https://img.shields.io/badge/Java-11%20%7C%2017%20%7C%2021-orange?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.2-red?style=flat-square&logo=apachecamel)](https://camel.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.9.2-green?style=flat-square&logo=junit5)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org/)

---

## Overview

This project contains the implementation and test suite for **Module 4: Testing Camel Routes with JUnit**. The project demonstrates enterprise integration patterns using **Apache Camel**, robust unit and integration testing with **JUnit 5** and `CamelTestSupport`, message validation via **XPath**, and message transformation workflows.

---

## Project Structure & Architecture

The project is organized into standard Maven source and test directories:
* **`src/main/java/com/example/camel/OrderProcessingRoute.java`**: Contains production routing logic, priority-based choices (`HIGH`, `MEDIUM`, `LOW`), payload validations (checking customer ID presence and positive amounts), and customer ID extraction/transformation.
* **`src/test/java/com/example/camel/OrderProcessingRouteTest.java`**: Comprehensive test suite leveraging `CamelTestSupport`, endpoint mocking and skipping (`isMockEndpointsAndSkip()`), and `MockEndpoint` assertions.

---

## Build & Execution Verification

### 1. Running the Test Suite (`mvn clean test`)
The test suite validates route creation, high/medium/low priority routing branches, XPath validations, exception handling for invalid payloads, and data transformation headers. Executing the test runner verifies that all components operate correctly within the Camel execution context.

![Test Suite Execution and Compilation](images/Screenshot%20(552).png)

As shown in the terminal output above, all **8 test cases** executed successfully with **zero failures or errors**, resulting in a successful build (`BUILD SUCCESS`).

---

### 2. Full Test Execution Log

Further verification of the parallel test lifecycle shows the cleanup, engine orchestration, and graceful context shutdown managed by JUnit 5 and Apache Camel.

![Full Test Execution Log](images/Screenshot%20(553).png)

---

### 3. Surefire Report Validation

Maven generates plain-text execution reports under `target/surefire-reports/` to document test results for continuous integration and auditing. Inspecting the report confirms the execution status:

```bash
cat target/surefire-reports/com.example.camel.OrderProcessingRouteTest.txt
```

![Surefire Report Verification](images/Screenshot%20(554).png)

---

## Conclusion

This project successfully bridges the gap between enterprise route definition and automated integration testing. By utilizing Apache Camel's testing framework (`CamelTestSupport`) alongside `isMockEndpointsAndSkip()`, we are able to isolate routing components, simulate external dependencies or missing endpoints, and perform precise assertions on message bodies and headers. This ensures high reliability, maintainability, and confidence when deploying complex integration pipelines in production environments.
