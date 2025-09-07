import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		List<Socket> clients = Collections.synchronizedList(new ArrayList<>());


		while (true) {	

			System.out.println("Server is waiting first client in port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());
			clients.add(dataSocket);
			ServerThread sthread = new ServerThread(dataSocket, clients);
			sthread.start();
		}
	}
}


