package ija.homework3.client;

import java.io.*;
import java.net.*;

/**
 * A simple client communicating with the game
 * server.
 * @author xvecer17
 *
 */
public class Client {

	private String hostname;
	private int port;
	private Socket socket;
	//private GamePlay GUI;
	    
	private BufferedReader in;
	private PrintStream out;
	
	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	    
	/**
	* Connects to the server's socket.
	* @throws UnknownHostException
	* @throws IOException
	*/
	public void connect() throws UnknownHostException, IOException
	{
		System.out.println("Attempting to connect to "+hostname+":"+port);
		this.socket = new Socket(hostname, port);
		System.out.println("Connection Established");
	}
	
	/**
	 * Creates the client, opens connection and sends
	 * the commands caught in the GUI.
	 * @param arg
	 */
	public static void main(String arg[])
	{
		//Creating a SocketClient object
		Client client = new Client ("localhost", 9988);
		
		try {
			
			client.connect();
			client.in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
			client.out = new PrintStream(client.socket.getOutputStream());
			GuiControl control = new GuiControl(client.out);
			
			String serverSaid;
			String gameSizes = "";
			String map = "";
			
			while(true)
			{
				serverSaid = client.in.readLine();
				
				System.out.println(serverSaid);
				
				if(serverSaid.startsWith("list maps:"))
					control.sendMaps(serverSaid);
				
				else if(serverSaid.startsWith("list games:"))
					control.sendGames(serverSaid);
				
				else if(serverSaid.startsWith("sizes:"))
					control.InitSizes(serverSaid, false);
				
				else if(serverSaid.startsWith("map:"))
					control.InitMap(serverSaid);
				
				else if(serverSaid.equals("Full."))
					control.InitSizes(serverSaid, true);
			}
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
    
}
