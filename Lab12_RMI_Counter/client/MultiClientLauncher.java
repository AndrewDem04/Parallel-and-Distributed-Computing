import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MultiClientLauncher {
    public static void main(String[] args) {
        int numberOfClients = 5;

        for (int i = 0; i < numberOfClients; i++) {
            CountClientThread client = new CountClientThread();
            client.setName("Client-" + i);
            client.start();
        }
    }

    public static class CountClientThread extends Thread {
        private static final String HOST = "localhost";
        private static final int PORT = Registry.REGISTRY_PORT;
        private static final String rmiObjName = "Counter";

        public CountClientThread() {
        }

        @Override
        public void run() {
            try {
                Registry registry = LocateRegistry.getRegistry(HOST, PORT);
                Interface iface = (Interface) registry.lookup(rmiObjName);

                iface.setCommVar();	

                for (int i = 0; i < 10000; i++) {
                    int value = iface.getCommVar();
                    System.out.println("[" + getName() + "] CommVar = " + value);
                    iface.updateCommVar(1);
                }

                System.out.println("[" + getName() + "] Finished updating.");
            } catch (RemoteException | NotBoundException e) {
                System.err.println("[" + getName() + "] Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}



