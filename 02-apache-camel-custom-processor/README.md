# Apache Camel Custom Processors & Route Integration

[![Java](https://img.shields.io/badge/Java-11%2B-orange?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.0-e61c24?logo=apachecamel&logoColor=white)](https://camel.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-4.13-25A162?logo=junit5&logoColor=white)](https://junit.org/junit4/)
[![SLF4J](https://img.shields.io/badge/Logging-SLF4J-blue)](http://www.slf4j.org/)
[![Ubuntu](https://img.shields.io/badge/OS-Ubuntu-E95420?logo=ubuntu&logoColor=white)](https://ubuntu.com/)

---

## 📌 Overview

This project demonstrates advanced enterprise integration patterns using **Apache Camel (v3.20.0)**. Specifically, it explores the creation and execution of **Custom Processors** (implementing the `org.apache.camel.Processor` interface) to transform and enrich message payloads, manipulate Camel exchanges, handle custom headers, and integrate seamlessly with Camel route builders and mock testing frameworks.

---

## 🏗️ Key Components & Architecture

### 1. `MessageTransformProcessor`
* Intercepts incoming message payloads, converts string bodies to uppercase, and appends execution timestamps.
* Injects custom tracking metadata headers (`ProcessedBy`, `ProcessingTimestamp`) into the active Camel Exchange.

### 2. `MessageEnrichmentProcessor`
* Evaluates incoming message headers (e.g., `MessageType`) and message parameters (e.g., user IDs).
* Enriches payloads with simulated customer database lookups (e.g., customer tier, account details) alongside structured dynamic logging.

### 3. Routing & Integration
* Implements direct endpoints (`direct:transform`, `direct:enrich`, `direct:combined`) for granular message flow control.
* Incorporates a timer-triggered background route (`auto-test-route`) to showcase scheduled message generation and asynchronous routing.

---

## 📁 Project Directory Structure

The project follows a standard Maven layout, separating production source code from unit and integration test suites:

```text
custom-processor-lab/
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── com/alnafi/camel/lab4/
    │           ├── CustomProcessorApplication.java
    │           ├── processors/
    │           │   ├── MessageEnrichmentProcessor.java
    │           │   └── MessageTransformProcessor.java
    │           └── routes/
    │               └── CustomProcessorRouteBuilder.java
    └── test
        └── java
            └── com/alnafi/camel/lab4/
                └── routes/
                    └── CustomProcessorRouteTest.java
```

![Project Directory Structure](screenshots/project_directory_structure.png)
*Figure 1: Verification of project structure using the `tree` utility inside the lab instance.*

---

## 🛠️ Prerequisites & Build Instructions

Ensure you have the following installed on your development or lab environment:
* **Java JDK:** 11 or higher
* **Build System:** Apache Maven 3.6+

### Building the Project
Clone the repository and compile the project using Maven:
```bash
mvn clean compile
```

### Running the Application
To run the main application and observe Camel routes, timers, and processor logs in real time:
```bash
mvn exec:java -Dexec.mainClass="com.alnafi.camel.lab4.CustomProcessorApplication"
```

---

## 💻 Application Execution Logs

Below is a capture of the main application running, demonstrating timer-triggered messages flowing through custom processors, transforming payloads, adding custom headers, and stopping cleanly:

![Application Execution Logs](screenshots/application_execution_and_logging.png)
*Figure 2: Terminal execution log showing timer events, payload transformation (`HELLO FROM TIMER ROUTE`), custom header enrichment (`ProcessedBy=MessageTransformProcessor`), and graceful shutdown.*

---

## 🧪 Testing & Verification

The project includes unit and integration tests built using **JUnit 4** and Apache Camel's testing framework (`CamelTestSupport` and `MockEndpoint`).

### Running the Test Suite
Execute the automated test suite with:
```bash
mvn test
```

### Test Execution Results
All test cases execute successfully with zero failures or errors, validating route behavior, transformation logic, and message enrichment:

![Test Suite Verification](screenshots/test_suite_verification.png)
*Figure 3: JUnit test runner output confirming successful verification of `testEnrichmentRoute` and `testTransformRoute` using mock assertions.*

---

## 🏁 Conclusion

In this lab, we successfully designed and validated an enterprise-grade message integration pipeline using **Apache Camel 3.20.0**. By leveraging custom `Processor` implementations, we demonstrated full control over message exchange bodies and headers without tightly coupling business logic to external infrastructure. Combined with automated testing via `CamelTestSupport` and `MockEndpoint`, this project serves as a foundational blueprint for building extensible, testable integration microservices.