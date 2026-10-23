# Lab 11: Exposing RESTful APIs with Camel

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.0-blue.svg)](https://camel.apache.org/)
[![Jetty](https://img.shields.io/badge/Jetty-9.4-red.svg)](https://www.eclipse.org/jetty/)
[![Jackson](https://img.shields.io/badge/Jackson-JSON-green.svg)](https://github.com/FasterXML/jackson)
[![Maven](https://img.shields.io/badge/Maven-3.8-yellow.svg)](https://maven.apache.org/)

---

## Overview

**Lab 11: Exposing RESTful APIs with Camel** demonstrates how to design, configure, and secure enterprise-grade RESTful web services using **Apache Camel REST DSL**, **Jetty HTTP Server**, and **Jackson** for automated JSON marshalling/unmarshalling. The lab emphasizes clean integration patterns, robust input request validation, and standard HTTP response status code handling across microservices.

---

## Architecture & Core Components

```
 [ REST Client / cURL ]
           │
           ▼
 [ Jetty HTTP Server (Port 8080) ]
           │
           ▼
 [ Apache Camel REST DSL & Jackson Data Binding ]
           │
           ├───────────────────────────────┐
           ▼                               ▼
 [ Direct Route Endpoints ]     [ In-Memory User Service ]
 (GET, POST, PUT, DELETE)       (CRUD Logic & Validation)
```

* **Apache Camel REST DSL:** Provides a clean, fluent API builder to define REST endpoints without requiring external servlet containers.
* **Jetty Component (`camel-jetty`):** Acts as the underlying lightweight HTTP server listening on port `8080`.
* **Jackson Data Format (`camel-jackson`):** Automatically handles JSON-to-POJO and POJO-to-JSON serialization.

---

## Execution & Verification Results

### 1. Application Startup & Initialization
The standalone Java application boots successfully via Maven execution, initializing the Apache Camel context, loading route builders, and launching the Jetty HTTP connector on port `8080`.

![Application Startup](Screenshot%20(570).png)

### 2. CRUD Operations & Successful Retrieval
Retrieving initial seed records and successfully creating a new user (`Alice Cooper`, ID `4`) via `cURL` requests verifies accurate routing and JSON serialization.

![CRUD Testing and User Creation](Screenshot%20(571).jpg)

### 3. Request Validation & Error Handling
Testing invalid payloads (such as omitting required fields like email) successfully triggers routing validation logic, returning an HTTP `400 Bad Request` with structured JSON error feedback.

![Request Validation Testing](Screenshot%20(572).jpg)

---

## Conclusion

In this lab, we successfully designed, implemented, and validated robust RESTful microservices using Apache Camel. Key achievements include:
* **Leveraging Camel REST DSL:** Cleanly mapping standard HTTP verbs (`GET`, `POST`, `PUT`, `DELETE`) to internal processing routes.
* **Implementing Data Binding & Validation:** Ensuring strict payload integrity using Jackson JSON marshalling and custom choice-based validation filters.
* **Managing Enterprise HTTP Responses:** Gracefully handling status codes (`200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`) and global exception policies.

---
*Developed as part of technical cloud integration training modules.*