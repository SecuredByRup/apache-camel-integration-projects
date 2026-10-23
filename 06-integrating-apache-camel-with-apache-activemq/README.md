# Lab 8: Integrating Apache Camel with Apache ActiveMQ

<p align="center">
  <img src="https://img.shields.io/badge/Apache%20Camel-D00000?style=for-the-badge&logo=apachecamel&logoColor=white" alt="Apache Camel" />
  <img src="https://img.shields.io/badge/Apache%20ActiveMQ-CC0000?style=for-the-badge&logo=apache&logoColor=white" alt="Apache ActiveMQ" />
  <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Apache Maven" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
</p>

## Overview
This laboratory guide outlines the integration of **Apache Camel** enterprise integration patterns with **Apache ActiveMQ** message-oriented middleware. The project implements standalone message brokers, Maven dependency management, dynamic Camel route builders, and robust message routing workflows including transformation, content-based routing, and dead-letter error handling.

---

## Task 1: ActiveMQ Broker Setup & Verification
In this task, Apache ActiveMQ Classic (v5.18.3) was downloaded, configured, and initiated as a standalone messaging broker. OpenWire (`port 61616`) and the Web Console (`port 8161`) were verified to ensure proper client connectivity and administrative management.

* **Broker Status Check & Port Verification:**
  ```bash
  ~/activemq/bin/activemq status
  ss -tlnp 2>/dev/null | grep -E '61616|8161'
  curl -s -o /dev/null -w "HTTP status: %{http_code}\n" http://localhost:8161/admin/
  ```

![ActiveMQ Setup & Maven Build Verification](Screenshot%20(563).png)
*Figure 1: Verification of ActiveMQ broker status, active listening ports, HTTP 401 web console authentication prompt, and successful Maven compilation (`BUILD SUCCESS`).*

---

## Task 2: Build a Camel Route Integrated with ActiveMQ
A Maven project (`camel-activemq-integration`) was established with dependencies for `camel-core`, `camel-main`, `camel-jms`, `activemq-client`, and `activemq-pool`. Three core routes were implemented in `CamelRouteBuilder.java`:
1. **Simple Transformation Route:** Consumes raw text from `input.queue`, appends a timestamp, converts text to uppercase, and forwards to `output.queue`.
2. **Order Processing Route:** Evaluates order messages using content-based routing (`choice()`), routing approved orders to `fulfillment.queue` and pending orders to `review.queue`.
3. **Risky Processing Route:** Simulates random processing failures to demonstrate dead-letter error handling routing to `dead.letter.queue`.

![Camel Route Execution Logs](Screenshot%20(560).png)
*Figure 2: Camel application runtime logs displaying route initialization and message transformation execution.*

---

## Task 3: End-to-End Testing & Verification
Interactive `MessageSender` and `MessageReceiver` utilities were developed to validate end-to-end message flow across the ActiveMQ broker.

### Sending Messages
Using the `MessageSender` utility, test payloads such as `hello camel` were published to `input.queue`.

![Message Sender Utility](Screenshot%20(557).jpg)
*Figure 3: Interactive `MessageSender` console menu successfully pushing a test message to `input.queue`.*

### Receiving Transformed Messages
Using the `MessageReceiver` utility, messages were consumed from `output.queue` to inspect the transformed payload.

![Message Receiver Utility](Screenshot%20(558).jpg)
*Figure 4: Interactive `MessageReceiver` console capturing the processed message with timestamp and uppercase formatting.*

---

## Conclusion
Lab 8 successfully demonstrated how to seamlessly couple Apache Camel integration routes with Apache ActiveMQ as an enterprise messaging backbone. Through hands-on configuration, route development, and interactive testing utilities, we established a resilient, decoupled messaging architecture capable of handling message transformation, conditional content-based routing, and automated error management.