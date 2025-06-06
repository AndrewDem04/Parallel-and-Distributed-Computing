import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;

public class CalculatorClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099	
	
	public static void main(String[] args) throws IOException {
		Data data = null;
		String request;

		Boolean done = false;
		while (!done){	
			data = promptUser();
			done = checkData(data);
		}
		request = buildRequestMessage(data);

		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "Calculator";
			Calculator ref = (Calculator)registry.lookup(rmiObjectName);
			
			String result = ref.calc(request);
			processReply(result);
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
				
	}

	public static Data promptUser() throws IOException {
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the first number: ");
		int a = Integer.parseInt(user.readLine());
		//System.out.println("a: " +a);
		System.out.print("Enter the second number: ");
		int b = Integer.parseInt(user.readLine());
		//System.out.println("b: " +b);
		System.out.print("Enter the operator (+, -, *, /): ");
		char op = user.readLine().charAt(0);
		return new Data(a, b, op);
	}

	public static boolean checkData(Data data) {
		switch (data.op) {
			case '+':
			case '-':
			case '*':
			  return true;
			case '/':
				if(data.b == 0) {
					System.out.println("Error: Division by zero is not allowed.");
					return false;
				}
				else {
					return true;
				}
			case '!':
			    return true;
			default:
				System.out.println("Invalid operator. Please enter +, -, *, / or ! if you want to exit.");
				return false;
			}
	}

	public static String buildRequestMessage(Data data) throws IOException {
		//System.out.println("REQUEST|" + data.a + "|" + data.b + "|" + data.op);
		return "REQUEST|" + data.a + "|" + data.b + "|" + data.op;
	}

	public static void processReply(String theInput) throws IOException {
    	String[] parts = theInput.split("\\|");
    
    	if (parts[0].charAt(0) == '!') {
        	System.out.println("Exiting... Goodbye!");
			System.exit(0);
    	}

    	// Extract the result if not exit
    	char responseType = parts[0].charAt(0);
    	if (responseType == 'R') {
        	int result = Integer.parseInt(parts[1]);
        	System.out.println("Result: " + result);
    	} else {
        	System.out.println("Invalid response: " + theInput);
    	}
	}
}

