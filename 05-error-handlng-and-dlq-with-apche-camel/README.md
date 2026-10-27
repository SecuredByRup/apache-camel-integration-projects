# Module 5: Error Handling and Dead Letter Queues (DLQ) with Apache Camel

<p align="center">
  <img src="https://img.shields.io/badge/Apache%20Camel-3.20.2-D22128?style=for-the-badge&logo=apachecamel&logoColor=white" alt="Apache Camel" />
  <img src="https://img.shields.io/badge/Apache%20ActiveMQ-5.17.3-FC6A03?style=for-the-badge&logo=apache&logoColor=white" alt="Apache ActiveMQ" />
  <img src="https://img.shields.io/badge/Java-11-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 11" />
  <img src="https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Apache Maven" />
</p>

## Executive Summary

This repository contains the full implementation and verification suite for **Error Handling and Dead Letter Queues (DLQ) with Apache Camel**. 

In enterprise integration architectures, distinguishing between transient (recoverable) errors and permanent (non-recoverable) errors is critical. This project demonstrates how to construct resilient Camel integration routes using declarative `onException()` clauses, exponential backoff redelivery policies, and Dead Letter Queue (DLQ) patterns enriched with contextual failure metadata.

---

## Project Structure & Architecture

```text
lab7-error-handling/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/alnafi/camel/errorhandling/
                ├── ErrorSimulationService.java     # Simulates transient & permanent errors
                ├── ErrorHandlingRouteBuilder.java   # Retries & exception handling logic
                ├── RetryTestApplication.java       # Standalone execution for retry logic
                ├── DLQRouteBuilder.java             # Route configuration feeding DLQ destinations
                └── DLQTestApplication.java         # Driver & results analyzer for DLQ routing
```

### Key Architectural Concepts Implemented

1. **Transient Error Recovery**: `RuntimeException` occurrences trigger up to 3 redelivery attempts using exponential backoff (`redeliveryDelay=500ms`, `backOffMultiplier=2`).
2. **Immediate Failure Routing**: Non-retryable `IllegalArgumentException` validation failures skip retry attempts (`maximumRedeliveries(0)`) and route straight to a dedicated error handler.
3. **Dead Letter Queue (DLQ) Enrichment**: Messages routed to DLQ channels are enriched with `ErrorCategory` and `FailureTime` headers prior to persistence for post-mortem analysis.

---

## Execution Verification & Screenshots

### 1. Build Verification & Application Execution

Executing `mvn clean compile` compiles all 5 Java source files cleanly. Running both `RetryTestApplication` and `DLQTestApplication` demonstrates the retry loops and message routing under failure conditions.

![Compilation and Application Execution](images/Screenshot%20%28555%29.png)

* **Retry Execution**: Transient messages succeed after 2 retries on attempt #3, while validation errors fail immediately without retry overhead.
* **DLQ Execution Summary**: The batch processor produces **10 success files**, **6 runtime error DLQ files**, and **2 validation error DLQ files**.

---

### 2. Output File Structure & Error Metadata Analysis

Inspecting the output directories confirms that failure messages reaching the Dead Letter Queue destinations are properly formatted and enriched with precise failure timestamps and categorization headers.

![DLQ Output File Verification](images/Screenshot%20%28556%29.png)

* **DLQ Runtime Error Payload**: `RuntimeError at 2026-10-14 07:40:44: NETWORK_ERROR - This will fail and go to DLQ`
* **DLQ Validation Error Payload**: `ValidationError at 2026-10-14 07:40:43: VALIDATION_ERROR - Invalid format`

---

## Conclusion

This project successfully demonstrates how to build fault-tolerant enterprise integration pipelines with Apache Camel. By decoupling transient retry mechanisms from permanent validation failures and forwarding unrecoverable payloads to Dead Letter Queues enriched with failure context, system components remain isolated from cascading failures while providing complete observability for downstream auditing and manual remediation.
