import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	public String prepareRequest() throws IOException {
	
		String theOutput = promptUser();
		if(theOutput.equals("!")) {
			System.out.println("Exiting... Goodbye!");
			System.exit(0);
		}
		return theOutput;
	}
        
	public String promptUser() throws IOException { 
		return user.readLine();
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Reply: " + theInput);
	}
}
