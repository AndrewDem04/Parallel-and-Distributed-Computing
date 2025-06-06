# Lab12_RMI_Mail

This project demonstrates a simple Java RMI (Remote Method Invocation) application for managing a shared email directory. Clients can remotely add, remove, and retrieve email addresses associated with names.

## Project Structure

- **ManagementEmails.java**: Remote interface defining available operations.
- **ManagementEmailsImpl.java**: Implementation of the remote interface.
- **ManagementEmailsServer.java**: RMI server that registers and binds the remote object.
- **ManagementEmailsClient.java**: Client application to interact with the RMI server.
- **MultiClientLauncher.java**: Utility to launch multiple client instances for concurrent operations.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Compilation

Open a terminal in the project directory and run:

```bash
javac *.java
```

### Running the Application

1. **Start the RMI Registry** (if not already running):

    ```bash
    start rmiregistry
    ```

2. **Start the Server**:

    ```bash
    java ManagementEmailsServer
    ```

3. **Run a Client**:

    ```bash
    java ManagementEmailsClient
    ```

4. **Launch Multiple Clients (Optional):**

    ```bash
    java MultiClientLauncher
    ```

## Usage

- Add, remove, or retrieve email addresses by following the client prompts.
- Multiple clients can interact with the server concurrently.

## Notes

- Ensure the RMI registry is running in the project directory.
- All `.class` files should be in the same directory as the source files.
