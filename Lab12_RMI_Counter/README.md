# Lab12: RMI Counter

This project demonstrates a basic client-server counter using Java Remote Method Invocation (RMI). The client can increment, decrement, and retrieve the counter value remotely from the server.

## Project Structure

- `Counter.java` – Remote interface
- `CounterImpl.java` – Counter implementation
- `CounterServer.java` – Server application
- `CounterClient.java` – Client application

## Getting Started

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## Getting Started

1. **Compile all Java files:**
    ```sh
    javac *.java
    ```

2. **Start the RMI registry:**
    ```sh
    start rmiregistry
    ```

3. **Run the server:**
    ```sh
    java CounterServer
    ```

4. **Run the client:**
    ```sh
    java CounterClient
    ```

## Usage

The client connects to the server and can:
- Increment the counter
- Decrement the counter
- Retrieve the current counter value

## Notes

- Ensure the RMI registry is running in the project directory.
- All `.class` files should be in the same directory as the source files.