# Module 10: Consuming External REST APIs with Camel

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.2-blue.svg)](https://camel.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-3.10.1-C71A36.svg)](https://maven.apache.org/)
[![SLF4J](https://img.shields.io/badge/Logging-SLF4J--Simple-informational.svg)](http://www.slf4j.org/)
[![REST API](https://img.shields.io/badge/API-JSONPlaceholder-green.svg)](https://jsonplaceholder.typicode.com/)

---

## Overview

**Consuming External REST APIs with Camel** demonstrates how to integrate external HTTP services into a microservices architecture using **Apache Camel**, the **Camel HTTP Component**, and **Jackson** for JSON data processing. This lab covers polling external REST endpoints via timer components, transforming and enriching JSON payloads using custom Java processors, handling transport-level exceptions and non-200 HTTP error codes, and implementing automated health monitoring.

---

## Architecture & Core Components

```
 [ Timer Trigger ] ──► [ Apache Camel HTTP Route ] ──► [ External REST API (JSONPlaceholder) ]
                               │
                               ▼
 [ DataTransformProcessor ] (Jackson JSON Node Parsing, Title Uppercase, Word Count, Category Tagging)
                               │
                               ▼
 [ Validation & Filtering ] ──► [ Simulated Database Storage / Logging ]
```

* **HTTP Consumer Component (`camel-http`):** Initiates outbound HTTP GET requests to external REST services (`https://jsonplaceholder.typicode.com`) with `bridgeEndpoint=true` for dynamic URI routing.
* **Custom Transformation Processor (`DataTransformProcessor`):** Utilizes Jackson (`ObjectMapper` and `JsonNode`) to parse JSON arrays and objects, appending timestamps (`processedAt`), source tags, uppercase conversions, word count metrics, and conditional category tags (`PRIORITY` vs `STANDARD`).
* **Multi-Route Pipeline (`EnhancedRestConsumerRoute`):** Decouples fetching, transformation, validation, and storage into separate `direct:` endpoints for modularity and maintainability.
* **Layered Error Handling:** Distinguishes between transport-level exceptions (timeouts, connection errors handled via global `onException`) and application-level HTTP error codes (`404`, `429` rate-limiting handled via choice routing).
* **API Monitoring Route (`MonitoringRoute`):** Performs periodic health checks against the target API to verify availability and log health status.

---

## Project Structure & Implementation

```
camel-rest-consumer/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── camel/
    │   │               ├── RestApiConsumerRoute.java
    │   │               ├── DataTransformProcessor.java
    │   │               ├── EnhancedRestConsumerRoute.java
    │   │               ├── MonitoringRoute.java
    │   │               ├── RestConsumerApplication.java
    │   │               └── CompleteTestApplication.java
    │   └── resources/
    │       └── simplelogger.properties
```

---

## Verification & Execution Results

### 1. Build and Execution
Compile the Maven project and execute the complete integration pipeline containing enhanced consumption, data transformation, validation, and health monitoring:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.camel.CompleteTestApplication"
```

**Console Output & Execution Verification:**
![Maven Build & Execution Success](images/Screenshot%20(574).png)

### 2. Manual API Verification with `curl`
Independent validation of the REST endpoints and error-handling logic was performed via `curl`:
```bash
# Test 1: Fetch posts batch (HTTP 200)
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" "https://jsonplaceholder.typicode.com/posts?_limit=3"

# Test 2: Retrieve single post JSON object
curl -s "https://jsonplaceholder.typicode.com/posts/1"

# Test 3: Test non-existent resource (HTTP 404 error handling)
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" "https://jsonplaceholder.typicode.com/posts/99999"
```

**Curl Verification Output:**
![Curl Verification Output](images/Screenshot%20(575).png)

---

## Conclusion

In this project, we successfully designed and implemented an enterprise-grade REST consumer integration pipeline using Apache Camel. Key accomplishments include:
* **Robust HTTP Integration:** Configured Camel routes to consume external REST endpoints with appropriate headers, methods, and bridge parameters.
* **Advanced JSON Processing:** Reshaped and enhanced raw JSON data structures using custom Jackson processors and split-jsonpath expressions.
* **Resilient Error Management:** Implemented dual-tier error handling for transport exceptions and non-200 HTTP responses (such as 404 missing resources and 429 rate limits).
* **Operational Readiness:** Established proactive health-check routes and structured SLF4J logging configurations for production monitoring.
