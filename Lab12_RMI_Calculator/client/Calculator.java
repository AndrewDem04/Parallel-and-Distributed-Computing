import java.rmi.*;

public interface Calculator extends Remote {
	
	public String calc(String request) throws RemoteException;
}
