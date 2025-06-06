# Lab12 RMI Calculator

This project implements a simple calculator using Java RMI (Remote Method Invocation) for parallel and distributed computing.

## Structure

- `Calculator.java`: RMI interface defining calculator operations.
- `CalculatorImpl.java`: Implementation of the calculator interface.
- `CalculatorServer.java`: Starts the RMI server.
- `CalculatorClient.java`: Client to interact with the calculator remotely.

## Getting Started

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## How to Run

1. Compile all Java files:
    ```
    javac src/*.java
    ```
2. Start the RMI registry:
    ```
    start rmiregistry
    ```
3. Run the server:
    ```
    java -cp src CalculatorServer
    ```
4. Run the client:
    ```
    java -cp src CalculatorClient
    ```

## Notes

- Ensure the RMI registry is running before starting the server.
- Client and server can run on different machines if networked properly.
