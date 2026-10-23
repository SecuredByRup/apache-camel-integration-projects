# Lab 13: Deploying & Running Apache Camel Routes with Podman

An enterprise integration project demonstrating timer-based file processing, JSON message transformation, and REST API development using **Apache Camel**, **Maven**, and containerized with **Podman**.

---

## 🚀 Technologies Used
* **Apache Camel (4.4.0)** - Enterprise Integration Patterns (EIP) & Route Builder
* **Apache Maven** - Project build automation and fat-JAR shading via Maven Shade Plugin
* **Podman** - Container engine for local containerization
* **Java 17 (Eclipse Temurin)** - Runtime environment

---

## 🛠️ Project Structure
```text
local-camel-lab/
├── src/main/java/
│   ├── Application.java          # Main Camel runner class
│   ├── FileProcessor.java        # Timer-based logging route
│   ├── MessageTransformer.java   # JSON unmarshalling and transformation route
│   └── RestApiRoute.java         # Undertow-based REST API endpoint route
├── Containerfile                 # Podman container definition
└── pom.xml                       # Maven configuration & shade plugin
```

---

## ⚙️ How to Build and Run

### 1. Compile and Package
```bash
mvn clean package -DskipTests
```

### 2. Build Container Image
```bash
podman build -t localhost/local-camel-app:latest .
```

### 3. Run Container
```bash
podman run -d --name camel-container -p 8080:8080 localhost/local-camel-app:latest
```

---

## 🧪 Testing REST Endpoints
Test the containerized REST API routes using `curl`:

* **Hello Endpoint:**
  ```bash
  curl http://localhost:8080/api/hello
  ```
  *Response:*
  ```json
  {"message": "Hello from Local Camel REST API", "timestamp": "1776510400000"}
  ```

* **Status Endpoint:**
  ```bash
  curl http://localhost:8080/api/status
  ```
  *Response:*
  ```json
  {"status": "healthy", "service": "local-camel-rest-api"}
  ```

---

## 📊 Verification & Execution Evidence

The terminal execution below demonstrates a successful Maven build (`BUILD SUCCESS`) and verifies the creation of the local Podman image (`localhost/local-camel-app:latest`):

![Execution and Build Verification](Screenshot%20(577).png)

---

## 📝 Conclusion
In this lab, we successfully built, tested, and containerized enterprise integration routes using Apache Camel and Podman. By implementing timer-based file logging, JSON data transformations, and Undertow-based REST APIs, we demonstrated core cloud-native integration patterns. Packaging the application into an executable fat JAR and building a custom container image bridges traditional Java development with modern containerized microservices architectures, providing essential skills for enterprise deployment and DevOps workflows.