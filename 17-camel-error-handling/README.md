# 🐪 Apache Camel: Handling Message Failures & Enterprise Error Routing Patterns

[![Java](https://img.shields.io/badge/Java-11-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.0-red?style=for-the-badge&logo=apachecamel&logoColor=white)](https://camel.apache.org/)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.6+-blue?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Linux](https://img.shields.io/badge/Linux-Ubuntu-FCC624?style=for-the-badge&logo=ubuntu&logoColor=black)](https://ubuntu.com/)
[![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/)

An enterprise-grade integration lab demonstrating robust error-handling strategies, fault tolerance, automated retry mechanisms, Dead Letter Queues (DLQ), and exception-specific routing using **Apache Camel 3.x**.

---

## 📐 Architecture & Key Patterns

This repository implements four core Enterprise Integration Patterns (EIPs) for failure recovery and error handling:

```
                          ┌──────────────────────────┐
                          │   Incoming Message File  │
                          └─────────────┬────────────┘
                                        │
             ┌──────────────────────────┼──────────────────────────┐
             ▼                          ▼                          ▼
   ┌───────────────────┐      ┌───────────────────┐      ┌───────────────────┐
   │ Retry Strategy    │      │ DLQ Strategy      │      │ Fallback Strategy │
   │ - Max 3 Attempts  │      │ - Max 2 Retries   │      │ - doTry / doCatch │
   │ - 2s Redelivery   │      │ - DeadLetterQueue │      │ - Alternate Route │
   └─────────┬─────────┘      └─────────┬─────────┘      └─────────┬─────────┘
             │                          │                          │
             ▼                          ▼                          ▼
   ┌───────────────────┐      ┌───────────────────┐      ┌───────────────────┐
   │ Success / Retry   │      │ DLQ Handler Queue │      │ Fallback Output   │
   └───────────────────┘      └───────────────────┘      └───────────────────┘
```

1. **Automated Redelivery & Retry Strategy (`RetryRouteBuilder`)**: Implements exponential/fixed redelivery policies for transient network or file system failures.
2. **Dead Letter Queue (`DeadLetterQueueRouteBuilder`)**: Isolates non-recoverable messages after 2 failed attempts, appending failure metadata (`FailureReason`, `FailureTimestamp`) for offline inspection.
3. **In-Route Fallback & Exception Handling (`FallbackRouteBuilder`)**: Demonstrates `doTry / doCatch` blocks alongside `onException` handlers to maintain business continuity when primary processors fail.
4. **Typed Exception Routing (`AdvancedErrorHandlingRouteBuilder`)**: Categorizes runtime errors by exception class (`IllegalArgumentException`, `RuntimeException`, general `Exception`) and dispatches them to specialized error queues.

---

## 🛠️ Build & Compilation

The project uses Apache Maven for dependency management and build automation.

```bash
# Clean and compile Java source files
mvn clean compile

# Build executable package skipping unit tests
mvn clean package -DskipTests
```

### 📸 Compilation Verification
![Maven Clean Compile & Package Screenshot](https://raw.githubusercontent.com/SecuredByRup/apache-camel-integration-projects/main/17-camel-error-handling/screenshots/Screenshot_595.png)

*Figure 1: Successful Maven compilation (`BUILD SUCCESS`) showing 7 source files compiled for `camel-error-handling-1.0.0.jar`.*

---

## 🧪 Execution & Real-Time Performance Load Testing

The application runs in a multi-threaded asynchronous environment processing batches of simulated messages with varied error conditions.

```bash
# Execute application in background with output logging
mvn exec:java > application.log 2>&1 &

# Execute load testing suite
./load-test.sh

# Analyze runtime performance metrics
./analyze-performance.sh
```

### 📸 Runtime Load Test Analysis
![Runtime Load Test Execution Screenshot](https://raw.githubusercontent.com/SecuredByRup/apache-camel-integration-projects/main/17-camel-error-handling/screenshots/Screenshot_596.png)

*Figure 2: Real-time analysis demonstrating 74 total messages processed across all error-handling routes with retry counts, DLQ isolations, and fallback executions.*

---

## 📊 Comprehensive Test Report & Metrics

Running the `./generate-test-report.sh` script produces an executive summary of performance across all integration patterns:

### 📸 Generated Test Report & Process Cleanup
![Final Test Report and Cleanup Screenshot](https://raw.githubusercontent.com/SecuredByRup/apache-camel-integration-projects/main/17-camel-error-handling/screenshots/Screenshot_597.png)
![Application Process Shutdown Screenshot](https://raw.githubusercontent.com/SecuredByRup/apache-camel-integration-projects/main/17-camel-error-handling/screenshots/Screenshot_598.png)

*Figures 3 & 4: Detailed test metrics report highlighting 77 retry attempts, 57 DLQ routing events, 64 fallback events, and graceful process termination via `pkill`.*

---

## 📁 Repository Structure

```text
17-camel-error-handling/
├── src/main/java/com/example/camel/
│   ├── AdvancedErrorHandlingRouteBuilder.java  # Exception-type router
│   ├── DeadLetterQueueRouteBuilder.java       # DLQ & failure metadata handler
│   ├── ErrorHandlingApplication.java           # Main Camel runner entrypoint
│   ├── FailureSimulatorProcessor.java         # Simulated exception thrower
│   ├── FallbackProcessor.java                 # Business fallback handler
│   ├── FallbackRouteBuilder.java              # doTry/doCatch implementation
│   └── RetryRouteBuilder.java                 # Redelivery policy route
├── load-test.sh                                # Automated message injector
├── analyze-performance.sh                      # Log analyzer and metrics calculator
├── generate-test-report.sh                     # Executive report generator
├── error-handling-test-report.txt              # Output performance report
└── pom.xml                                     # Apache Camel dependencies
```

---

## 💡 Production Recommendations

1. **Dead Letter Queue Management**: Establish automated alert triggers (e.g., Prometheus / Grafana) whenever messages enter the DLQ directory.
2. **Back-Off Policy Tuning**: Introduce exponential back-off (`useExponentialBackOff()`) with randomized jitter to prevent thundering herd problems on downstream services.
3. **Circuit Breaker Integration**: Combine Camel's `onException` clauses with Resilience4j circuit breakers for external REST/gRPC endpoints.

---

## 🎯 Conclusion

This lab successfully demonstrates the resilience and enterprise reliability of **Apache Camel 3.x**. By orchestrating automated retries, isolating terminal failures in Dead Letter Queues, and executing graceful fallback logic, the application maintains continuous operation and message visibility even under heavy simulated failure rates.
EOF
```

---

I have generated the document editor containing your formatted `README.md` file. You can download or copy it directly for your repository!

<FollowUp>
Would you like help with anything else on this repository or your next integration lab?
</FollowUp>

<ElicitationsGroup>
</ElicitationsGroup>