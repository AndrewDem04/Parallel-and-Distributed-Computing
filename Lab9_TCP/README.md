# Lab 9: TCP Client-Server Programs

This repository contains several Java programs demonstrating TCP client-server communication, including both single-client and multi-client (multithreaded) architectures. The programs cover two main functionalities: a simple calculator and a string encoder/decoder using Caesar cipher, as well as string case conversion.


## Overview

The provided Java programs implement TCP-based client-server applications for:
- Performing arithmetic calculations (+, -, *, /) between two numbers.
- Encoding/decoding strings using Caesar cipher and converting string case (upper/lower).

Both single-client and multi-client (multithreaded server) versions are included for each functionality.

## Programs

### SingleClientCalc

- **Purpose:** Simple calculator over TCP for a single client.
- **Files:**
    - `EchoClientTCP.java` / `SimpleClientTCP.java`: Client application.
    - `EchoServerTCP.java` / `SimpleServerTCP.java`: Server application.
    - `ClientProtocol.java`: Handles user input and request formatting on the client side.
    - `ServerProtocol.java`: Processes requests and computes results on the server side.
    - `Data.java`, `Result.java`: Helper classes for data encapsulation.

**Usage:**  
Client prompts user for two numbers and an operator, sends the request to the server, and displays the result.

---

### MultiClientCalc

- **Purpose:** Calculator server supporting multiple concurrent clients using threads.
- **Files:**
    - `EchoClientTCP.java`: Client application.
    - `MultithreadedEchoServerTCP.java`: Multithreaded server.
    - `ServerThread.java`: Handles each client connection in a separate thread.
    - `ClientProtocol.java`, `ServerProtocol.java`, `Data.java`, `Result.java`: As above.

**Usage:**  
Multiple clients can connect and perform calculations simultaneously.

---

### SingleClientEncoder

- **Purpose:** String encoder/decoder and case converter for a single client.
- **Files:**
    - `SimpleClientTCP.java`: Client application.
    - `SimpleServerTCP.java`: Server application.
    - `ClientProtocol.java`: Handles user input and request formatting.
    - `ServerProtocol.java`: Processes string manipulation requests.

**Supported Commands:**
- `LOWER|message` — Convert message to lowercase.
- `UPPER|message` — Convert message to uppercase.
- `ENCODE|offset|message` — Caesar cipher encode with given offset.
- `DECODE|offset|message` — Caesar cipher decode with given offset.

---

### MultiClientEncoder

- **Purpose:** Multithreaded server for string encoding/decoding and case conversion.
- **Files:**
    - `EchoClientTCP.java`: Client application.
    - `MultithreadedEchoServerTCP.java`: Multithreaded server.
    - `ServerThread.java`: Handles each client connection.
    - `ClientProtocol.java`, `ServerProtocol.java`: As above.

**Usage:**  
Multiple clients can connect and send string manipulation requests concurrently.

