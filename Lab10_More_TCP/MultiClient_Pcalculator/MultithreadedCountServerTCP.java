import java.net.*;
import java.io.*;

public class MultithreadedCountServerTCP {
	private static final int PORT = 1234;
	public static CalcP pCalculator = new CalcP();

	public static void main(String args[]) throws IOException {


		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		while (true) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			ServerThread sthread = new ServerThread(dataSocket, pCalculator);
			sthread.start();
		}
	}
}


