# Lab 18: Apache Camel File Handling & Integration Pipelines

![Apache Camel](https://img.shields.io/badge/Apache%20Camel-3.20.0-orange?style=for-the-badge&logo=apachecamel&logoColor=white)
![Java](https://img.shields.io/badge/Java-11-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-Ubuntu%2024.04-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![FTP/SFTP](https://img.shields.io/badge/Protocol-FTP%20%7C%20SFTP-00599C?style=for-the-badge&logo=ssh&logoColor=white)

## Project Overview

This project implements an enterprise-grade file integration solution using **Apache Camel**, **Java 11**, and **Maven**. It demonstrates automated file consumption, transformation, remote server operations over FTP/SFTP, multi-destination message multicast, content/size/extension-based routing, and fault-tolerant error handling.

---

## Project Structure

The project was organized into a dedicated workspace (`camel-file-handling-lab`) containing source logic, configuration, and execution directories:

```text
camel-file-handling-lab/
├── pom.xml
├── src/
│   └── main/java/com/example/camel/
│       ├── FileHandlingApplication.java
│       ├── FileProcessingRoute.java
│       ├── FtpSftpRoute.java
│       └── AdvancedFileRoute.java
├── input/
├── output/
├── processed/
├── ftp-upload/
├── ftp-downloaded/
├── sftp-upload/
├── sftp-downloaded/
├── sync-source/
├── sync-completed/
├── advanced-input/
├── urgent-output/
├── archive-output/
├── regular-output/
├── size-input/
├── large-files/
├── small-files/
├── extension-input/
├── text-files/
├── csv-files/
├── xml-files/
└── error/
```

### Clean Workspace Verification
![Workspace Directory Structure](./Screenshot%20\(588\).jpg)

---

## Technical Architecture & Routes

### 1. Basic File Processing Route (`FileProcessingRoute.java`)
- Consumes incoming files from `input/`.
- Converts content to uppercase via custom `Processor` logic.
- Adds metadata headers (`ProcessedTimestamp`).
- Routes output to `output/` and archives original files in `processed/`.

### 2. FTP & SFTP Integration Route (`FtpSftpRoute.java`)
- **FTP Upload/Download:** Connects to local `vsftpd` server instance over port `21`.
- **SFTP Upload/Download:** Establishes secure SSH file transfer channel over port `22`.
- **Multicast Synchronization:** Reads files from `sync-source/` and broadcasts them simultaneously to FTP, SFTP, and local backup locations (`sync-completed/`).

### 3. Advanced Routing & Error Handling (`AdvancedFileRoute.java`)
- **Content-Based Routing:** Uses `.convertBodyTo(String.class)` to inspect file text and split streams (`URGENT`, `ARCHIVE`, or regular).
- **Size-Based Routing:** Filters files based on `CamelFileLength` header (> 1000 bytes).
- **Extension-Based Routing:** Sorts `.txt`, `.csv`, `.xml`, and generic files into dedicated storage.
- **Error Handling:** Declares `onException(Exception.class)` blocks before routes to trap runtime failures and direct failing payloads to `error/`.

---

## Verification & Proof of Execution

### Execution Outputs & Logs
The following captured outputs confirm full route operation across basic file processing, remote network protocols, content-based routing rules, and error trapping:

![Basic, FTP, SFTP, and Advanced Outputs](./Screenshot%20\(589\).jpg)

![Advanced Routing & Error Folder Verification](./Screenshot%20\(590\).jpg)

![Size and Extension Routing Verification](./Screenshot%20\(591\).jpg)

---

## How to Run

### Prerequisites
- Java JDK 11+
- Apache Maven 3.8+
- Active `vsftpd` and OpenSSH SFTP server instances configured on `localhost`

### Building & Launching
1. Clone the repository and enter project root:
   ```bash
   cd camel-file-handling-lab
   ```
2. Compile and run the main application:
   ```bash
   mvn clean compile exec:java -Dexec.mainClass="com.example.camel.FileHandlingApplication"
   ```

---

## Conclusion

Lab 18 successfully validates Apache Camel's capability to orchestrate complex enterprise integration patterns (EIP) with minimal boilerplate. Key achievements include setting up multi-protocol file routes (FTP/SFTP), implementing dynamic content- and attribute-based enterprise routing, and establishing resilient exception handling workflows.