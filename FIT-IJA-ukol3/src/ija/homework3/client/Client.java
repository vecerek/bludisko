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
    
	private BufferedReader is;
	private PrintStream os;

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
     * Reading server's response from the socket stream.
     * @return answer the state of the game
     * @throws IOException
     */
    public String readResponse() throws IOException
    {
	    String answer = "";
	    String tmp;
		
	    System.out.println("Client - Response from server:");
		        
	    tmp = this.is.readLine();
	    System.out.println(tmp);
	    answer += tmp;
		        
	    return answer;
    }
    
    /**
     * Sends the player's move in the game.
     * The server parses the message and executes
     * the command.
     * @param toDo the player's action in the game
     */
    public void send(String toDo)
    {
    	System.out.println(toDo);
    	try {
    		this.os.println(toDo);
    	} catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public String request(String what)
    {
    	try {
	    	this.send(what);
	    	return this.readResponse();
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    		return "";
    	}
    }
    
    /**
     * Creates the client, opens connection and sends
     * the commands caught in the GUI.
     * @param arg
     */
    public static void main(String arg[]) {
        //Creating a SocketClient object
        Client client = new Client ("localhost", 9988);
        
        GameThread gameControl = new GameThread(client, client.is, client.os);
        /*MainMenu main = new MainMenu(client);
        main.setVisible(true);*/
        
        //String serverToldMe;
        try {
            //trying to establish connection to the server
            client.connect();
    		
            client.is = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
    		OutputStream outStream = client.socket.getOutputStream();
    		client.os = new PrintStream(outStream, true); // Second param: auto-flush on write = true
            
    		//String serverToldMe = "";
            //if successful, read response from server
            /*while((serverToldMe = client.readResponse()) != "exit")
            {
            	//Tell the graphic user interface to paint the stuff.
            	//Tell server, what to do
            	if(serverToldMe.equals("refresh"))
            	{
            		client.send("OK");
            		serverToldMe = client.readResponse();
            		gameControl.refreshMap(serverToldMe);
            	}
            	else
            	{
            		System.out.println("Motherfucking main: " + serverToldMe);
            		//gameControl.handle(serverToldMe);
            	}
            }*/
    		while(true);

        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up.\n"+e.getMessage());
        }
    }
}
