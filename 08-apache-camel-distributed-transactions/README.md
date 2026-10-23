# Apache Camel Distributed Transactions (Lab 10)

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.8-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Apache Camel](https://img.shields.io/badge/Apache_Camel-3.20.2-red?style=for-the-badge&logo=apachecamel)](https://camel.apache.org/)
[![ActiveMQ](https://img.shields.io/badge/ActiveMQ-5.16.5-blue?style=for-the-badge&logo=apache)](https://activemq.apache.org/)
[![Atomikos](https://img.shields.io/badge/Atomikos-Transactions-yellow?style=for-the-badge)](https://www.atomikos.com/)

---

## 📖 Overview
This project demonstrates the implementation of **Distributed Transactions (XA)** using **Apache Camel**, **Spring Boot**, **Atomikos Transaction Manager**, **ActiveMQ XA**, and an embedded **H2 XA DataSource**. 

In enterprise integration, ensuring data consistency across multiple heterogeneous resource managers (such as relational databases and message brokers) is critical. This lab implements a robust order processing workflow that guarantees atomic commits and automatic rollbacks across both JDBC and JMS endpoints in the event of processing or inventory failures.

---

## 🛠️ Architecture & Core Components

1. **Transaction Management:** Powered by **Atomikos (`UserTransactionManager`)**, providing JTA-compliant distributed transaction coordination.
2. **Messaging (JMS):** **ActiveMQ XA Connection Factory** wrapped with `AtomikosConnectionFactoryBean` ensuring JMS sessions are automatically enlisted in active distributed transactions.
3. **Database (JDBC):** **H2 In-Memory Database** configured with `AtomikosDataSourceBean` (`h2XADataSource`) supporting XA transactions.
4. **Integration Routes:** **Apache Camel** routing engine orchestrating validation, database persistence, inventory reservation, and error handling via transacted policies (`atomikosTransactionPolicy`).

---

## 🚀 Getting Started & Execution

### Prerequisites
* Java 21 / JDK 11+
* Maven 3.8+

### Running the Application
Clone the repository and start the Spring Boot application using Maven:
```bash
mvn clean spring-boot:run
```
*Verification:* Upon successful startup, Atomikos initializes the XA transaction manager, embeds the ActiveMQ broker, and seeds the default inventory.

---

## 📸 Verification & Screenshots

### 1. Application Startup & Initialization
The application successfully boots up, starts the embedded ActiveMQ XA broker, and initializes the Atomikos transaction coordinator.

![Application Startup](Screenshot%20(568).png)

### 2. Successful Transaction Commit (Valid Order)
When a valid order is submitted (`/api/test/send-valid-order`), the transaction commits successfully across both resources: the order status is marked as `CONFIRMED`, and the product inventory (`Laptop`) is atomically decremented.

![Valid Order Commit & Inventory Update](Screenshot%20(567).jpg)

### 3. Automatic Transaction Rollback (Insufficient Inventory)
When an order exceeding available stock (`/api/test/send-insufficient-inventory-order`) is processed, the route catches the exception, triggers the error handler, and Atomikos safely rolls back all operations across the database and messaging queues, leaving inventory counts untouched.

![Transaction Rollback on Insufficient Inventory](Screenshot%20(569).png)

---

## 🧪 Testing Endpoints

| Endpoint | Method | Description |
| :--- | :---: | :--- |
| `/api/test/send-valid-order` | `POST` | Submits a valid order to commit successfully |
| `/api/test/send-insufficient-inventory-order` | `POST` | Triggers insufficient inventory error and transaction rollback |
| `/api/test/orders` | `GET` | Retrieves all saved orders |
| `/api/test/inventory` | `GET` | Views current product inventory levels |
| `/api/test/audit-log` | `GET` | Displays transaction audit and rollback logs |
| `/api/test/reset-data` | `POST` | Resets database inventory and logs |

---

## 💡 Conclusion
This lab successfully showcases enterprise-grade transaction handling in Apache Camel. By integrating Atomikos with XA-compliant datasources and connection factories, we eliminate the risk of partial failures (such as a database write succeeding while a message broker update fails), guaranteeing true ACID compliance across distributed systems.