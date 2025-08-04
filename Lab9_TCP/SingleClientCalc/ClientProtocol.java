import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	int a,b,r;
	char op; // +, -, *, /
	int err_code;
	Data data;
	String request;

	public String prepareRequest() throws IOException {
	
		Boolean done = false;
		while (!done){
			promptUser();
			this.data = readUserInput();
			//System.out.println("data: " + this.data.a + " " + this.data.op + " " + this.data.b);
			done = checkData(this.data);
		}
		request = buildRequestMessage(data);
		return(request);
	}

	public void promptUser() throws IOException {
		System.out.print("Enter the first number: ");
		a = Integer.parseInt(user.readLine());
		//System.out.println("a: " +a);
		System.out.print("Enter the second number: ");
		b = Integer.parseInt(user.readLine());
		//System.out.println("b: " +b);
		System.out.print("Enter the operator (+, -, *, /) or ! to exit: ");
		op = user.readLine().charAt(0);
		//System.out.println("op: " +op);
	}

	public Data readUserInput() {
    	return new Data(a, b, op);
	}

	public boolean checkData(Data data) {
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

	public String buildRequestMessage(Data data) {
		//System.out.println("REQUEST|" + data.a + "|" + data.b + "|" + data.op);
		return "REQUEST|" + data.a + "|" + data.b + "|" + data.op;
	}

	public void processReply(String theInput) throws IOException {
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
