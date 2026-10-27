# Lab 20: End-to-End Integration with Apache Camel Routes

![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.2-DX3B2F?style=for-the-badge&logo=apachecamel&logoColor=white)
![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-20.04_LTS-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)

---

## Executive Summary

This project implements an enterprise-grade **End-to-End Integration Pipeline** using **Apache Camel 3.20.2** running on Java 11. The integration engine automates CSV order ingestion, enriches transaction records against a PostgreSQL relational database using custom processors and HikariCP connection pooling, dynamically routes orders based on transaction values using Content-Based Routing (EIP), stores updated state back to PostgreSQL, generates confirmation reports, and handles failures via a Dead Letter Channel (DLC) retry strategy.

---

## Architectural Overview & EIP Patterns

The enterprise integration route incorporates the following Enterprise Integration Patterns (EIP):

1. **File Component Poller (`file:data/input`):** Continuously monitors the directory for batch order uploads (`.csv`).
2. **CSV Unmarshaling:** Converts CSV payloads into Java object models using Apache Camel's native CSV data formatting.
3. **Content Enricher Pattern (`enrich`):** Queries PostgreSQL via JDBC to dynamically retrieve customer profile data (`customer_name`, `email`) based on `customer_code`.
4. **Exchange Property Preservation:** Preserves stateful `Order` domain models across destructive payload transformations (JDBC query execution and report text formatting).
5. **Content-Based Router (`choice/when/otherwise`):** Evaluates total order amounts to categorize transactions into business tiers (`HIGH`, `MEDIUM`, `STANDARD`).
6. **Dead Letter Channel (`deadLetterChannel`):** Implements automated error handling with up to 3 retry attempts at 2-second intervals, quarantining unprocessable messages into `data/error/`.

```
[CSV Order Input] ──► [Unmarshal] ──► [Customer Enrichment via JDBC]
                                                │
                                                ▼
[DLQ Quarantine] ◄── [3x Retries] ◄── [Content-Based Router]
  (data/error)                                  │
                                ┌───────────────┴───────────────┐
                                ▼                               ▼
                     [PostgreSQL Save]             [File Confirmation Report]
```

---

## Project Structure

```text
18-integration-with-camel-routes/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/alnafi/integration/
│       │       ├── IntegrationApplication.java
│       │       ├── model/
│       │       │   └── Order.java
│       │       ├── processor/
│       │       │   ├── CsvOrderProcessor.java
│       │       │   └── CustomerEnrichmentProcessor.java
│       │       └── route/
│       │           └── OrderProcessingRoute.java
│       └── resources/
│           └── database.properties
└── data/
    ├── input/
    ├── output/
    ├── processed/
    └── error/
```

---

## Technical Implementation Details

### Database Configuration (`database.properties`)
Configures HikariCP high-performance connection pooling for PostgreSQL connectivity:
```properties
db.url=jdbc:postgresql://localhost:5432/integration_lab
db.username=camel_user
db.password=camel_pass
db.driver=org.postgresql.Driver

db.pool.maxPoolSize=10
db.pool.minIdle=2
db.pool.connectionTimeout=30000
```

### Main Camel Route Definition (`OrderProcessingRoute.java`)
Key snippet demonstrating error handling, property retention, content enrichment, routing, and file output:

```java
// Dead Letter Channel Error Handler
errorHandler(deadLetterChannel("file:data/error")
        .maximumRedeliveries(3)
        .redeliveryDelay(2000)
        .retryAttemptedLogLevel(LoggingLevel.WARN));

// File Consumer Route
from("file:data/input?move=../processed&moveFailed=../error&noop=false")
        .routeId("order-file-processor")
        .log("Processing order file: ${header.CamelFileName}")
        .unmarshal(csvFormat)
        .process(new CsvOrderProcessor())
        .to("direct:enrich-customer")
        .to("direct:route-order");

// Preserve Domain Model on Exchange Property prior to routing/output
from("direct:route-order")
        .routeId("order-routing")
        .choice()
            .when(simple("${body.totalAmount} > 1000"))
                .setHeader("orderTier", constant("HIGH"))
            .when(simple("${body.totalAmount} > 500"))
                .setHeader("orderTier", constant("MEDIUM"))
            .otherwise()
                .setHeader("orderTier", constant("STANDARD"))
        .end()
        .setProperty("currentOrder", body())
        .to("direct:generate-report")
        .to("direct:save-order");
```

---

## Verification & Execution Proof

### 1. File Ingestion, Content Enrichment, and Pipeline Execution Logs
When dropping a sample CSV file (`order1.csv`) containing `ORD-1003,CUST001,1250.00`, Camel picks up the file, parses the data, enriches customer metadata, assigns `HIGH` tier status, and handles output destinations:

![Pipeline Execution Logs](./screenshots/Screenshot_602.png)

*Figure 1: Terminal output displaying route startup, CSV parsing, JDBC lookup result, enrichment confirmation, and Dead Letter Channel retry attempts on handled exceptions.*

---

### 2. Output File Generation & Database Persistence Verification
Verification of generated confirmation report text files, file movements (`data/processed/`), and SQL record persistence in PostgreSQL:

![Output Verification and DB Persistence](./screenshots/Screenshot_603.png)

*Figure 2: Execution output confirming generated order confirmation file (`order_ORD-1003_confirmation.txt`), file relocation to `data/processed/`, and successful row insertion into the `orders` PostgreSQL table (`PROCESSED` status).*

#### Output Confirmation Report Sample (`data/output/order_ORD-1003_confirmation.txt`):
```text
ORDER CONFIRMATION
==================
Order Number: ORD-1003
Customer: Tech Solutions Inc
Email: orders@techsolutions.com
Total Amount: $1250.00
Tier: HIGH
Status: PROCESSED
```

---

## Running the Application Locally

1. **Ensure PostgreSQL is running and schema is initialized:**
   ```bash
   PGPASSWORD=camel_pass psql -h localhost -U camel_user -d integration_lab -c "\dt"
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Start the Apache Camel Integration Application:**
   ```bash
   mvn exec:java
   ```

4. **Submit a test order:**
   ```bash
   cat > data/input/order_test.csv << 'EOF'
   order_number,customer_code,total_amount
   ORD-2001,CUST001,1500.00
   EOF
   ```

---

## Conclusion

This project successfully demonstrates an enterprise integration pattern implementation with **Apache Camel 3.20.2**. By leveraging stateful Exchange properties, developers can seamlessly chain multiple consumers and components (such as JDBC database writers and File outputs) without payload collision. The integration pipeline provides high availability, fault tolerance via Dead Letter Channel retries, dynamic routing, and automated data enrichment required in production-grade enterprise integration scenarios.