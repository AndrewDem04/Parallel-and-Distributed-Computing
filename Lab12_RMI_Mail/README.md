# ManagementEmails RMI Application

This Java RMI (Remote Method Invocation) application allows remote clients to interact with a shared email directory by adding, removing, or retrieving email addresses associated with names.

## Structure

- `ManagementEmails.java`: Remote interface defining the operations.
- `ManagementEmailsImpl.java`: Implementation of the remote object.
- `ManagementEmailsServer.java`: RMI server binding the object.
- `ManagementEmailsClient.java`: Client that connects to and invokes remote methods.

## How to Compile & Run

### Step 1: Compile all files

```bash
javac *.java
