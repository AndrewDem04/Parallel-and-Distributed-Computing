import java.io.*;
import java.net.*;
import java.util.List;

class ServerThread extends Thread
{
	private Socket myDataSocket;
	List<Socket> clients;
	private InputStream is;
   	private BufferedReader in;
	private static final String EXIT = "CLOSE";

   	public ServerThread(Socket socket1, List<Socket> clients)
   	{
      		myDataSocket = socket1;
			this.clients = clients;
      		try {
			is = myDataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}

	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			inmsg = in.readLine();
			ServerProtocol app = new ServerProtocol();
			outmsg = app.processRequest(inmsg);
			while(!outmsg.equals(EXIT)) {
				broadcast(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg);
			}		

			myDataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	

	private void broadcast(String message) {
        synchronized (clients) {
            for (Socket s : clients) {
                if (s != myDataSocket) {  
                    try {
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        out.println(message);
                    } catch (IOException e) {
                        System.out.println("Error sending message: " + e);
                    }
                }
            }
        }
    }
}	
			
		
