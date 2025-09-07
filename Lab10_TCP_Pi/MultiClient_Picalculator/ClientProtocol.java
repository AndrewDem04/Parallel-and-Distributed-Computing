import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	public String prepareRequest() throws IOException {
	
		String theOutput = promptUser();
		return theOutput;
	}
        
	public String promptUser() throws IOException { 
		return user.readLine();
	}

	public String prepareExit() throws IOException {
	
		String theOutput = "EXIT";
		return theOutput;
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Reply: " + theInput);
	}
}
