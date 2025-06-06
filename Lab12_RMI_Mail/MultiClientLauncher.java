import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MultiClientLauncher {
    private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT;
    public static void main(String[] args) {
       
        int numberOfClients = 5;

        for (int i = 0; i < numberOfClients; i++) {
            String name = "User" + i;
            String email = "user" + i + "@example.com";

            EmailClientThread client = new EmailClientThread(name, email);
            client.setName("Client-" + i);
            client.start();
        }
    }

    public static class EmailClientThread extends Thread {
        private String name;
        private String email;
    
        public EmailClientThread(String name, String email) {
            this.name = name;
            this.email = email;
        }
    
        @Override
        public void run() {
            try {
			    Registry registry = LocateRegistry.getRegistry(HOST, PORT);
                ManagementEmails ref = (ManagementEmails) registry.lookup("ManagementEmails");
    
                ref.putEmail(name, email);
                System.out.println("[" + Thread.currentThread().getName() + "] Added: " + name + " -> " + email);
    
                Thread.sleep(100); 
    
                String result = ref.getEmail(name);
                System.out.println("[" + Thread.currentThread().getName() + "] Got: " + result);
    
            } catch (Exception e) {
                System.err.println("[" + Thread.currentThread().getName() + "] Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
