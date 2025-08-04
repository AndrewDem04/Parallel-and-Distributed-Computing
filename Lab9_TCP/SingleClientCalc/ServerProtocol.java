import java.net.*;
import java.io.*;

public class ServerProtocol {

	String reply;
	Result result;
	Data data;

	public String processRequest(String request) {
		data = getRequestData(request);
		result = doServerComputation(data);
		reply = buildReplyMessage(result);
		return(reply);
	}

	public Data getRequestData(String request) {
		String[] parts = request.split("\\|");
		int a = Integer.parseInt(parts[1]);
		//System.out.println("a = " + a);
		int b = Integer.parseInt(parts[2]);
		//System.out.println("b = " + b);
		char op = parts[3].charAt(0);
		//System.out.println("op = " + op);
		return new Data(a, b, op);
	}

	public Result doServerComputation(Data data) {
		//System.out.println("doServerComputation: " + data.a + " " + data.op + " " + data.b);
		switch(data.op) {
			case '+': 
				return new Result('R' , data.a + data.b);
			case '-':
				return new Result('R' , data.a - data.b);
			case '*':
				return new Result('R' , data.a * data.b);
			case '/':
				return new Result('R' , data.a / data.b);		
			case '!':
				return new Result('!', 0); // exit command
			default:
				System.out.println("Invalid operator. Please enter +, -, *, / or ! if you want to exit.");
				return new Result(data.op , 0); // or some error code
		}
	}

	public String buildReplyMessage(Result result) {
		return result.op + "|" + result.r;
	}
}
