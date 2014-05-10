package ija.homework3.server;

import java.io.*;
import java.net.*;

/**
 * A thread created by the server to communicate
 * with the client.
 * @author xvecer17
 * 
 */
public class ClientThread extends Thread {
	
	private Server server;
	private Socket socket;
	private String name;
	private String gameID;
	private int gameSpeed;
	
	public ClientThread(Server server, Socket s, String name){
		super();
		this.server = server;
		this.socket = s;
		this.name = name;
		this.gameID = "";
		this.gameSpeed = 5;
	}
	
	/**
	 * A simple method reading messages from the client.
	 * Those messages are interpreted into actual moves
	 * in the game and executed afterwards.
	 * @return client's message(command)
	 * @throws IOException
	 */
    public String readResponse()
    {
        String toDo = "";
        String tmp;
        try {
        	BufferedReader stdIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	
        	System.out.println("Response from client:");
	        
        	while ((tmp = stdIn.readLine()) != null)
        	{
        		System.out.println(tmp);
        		toDo += tmp;
        	}
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
        
        return toDo;
    }
    
    /**
     * Send the actual state of the Tape to the client. 
     * @param state the game field
     */
    public void send(String state)
    {
    	try {
    		OutputStream outStream = this.socket.getOutputStream();
    		// Second param: auto-flush on write = true
    		PrintStream ps = new PrintStream(outStream, true);
    		ps.println(state);
    	} catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    /**
     * Sends the client the message, that he should repaint the scene.
     * Waits for the agreement and sends the labyrinth's new state.
     * 
     * @param gameField		the labyrinth
     */
    public void sendGameField(String gameField)
    {
    	this.send("refresh");
    	while(this.readResponse() != "OK");
    	this.send(gameField);
    }
    
    /**
     * The client joins the a new game with set game speed. 
     * 
     * @param gameID	points to the game the client is playing
     * @param speed		time putting asleep the player after making a step
     */
    public void joinGame(String gameID, int speed)
    {
    	this.gameID = gameID;
    	this.gameSpeed = speed;
    }
	
    /**
     * Starts the communication with the client. Checks if the client's
     * action has ended successfully or not.
     */
	public void start()
	{
		try {
			//Communicating with client
			String clientSaid = "";
			boolean success;
			
			while((clientSaid = readResponse()) != "exit")
			{
				if(clientSaid != "go" || clientSaid != "stop")
				{
					synchronized(this) {
						success = this.server.execCommand(this.name, this.gameID, clientSaid);
					}
					
					this.server.changeStat(this.name, this.gameID, true);
					
					if(success)
					{
						this.send("OK");
						if(clientSaid == "step")
							try {
								Thread.sleep(this.gameSpeed * 100);
							}
							catch (InterruptedException e) {
								//Do nothing
							}
					}
					else
						this.send("NOT OK");
					
				}
				else if(clientSaid == "go")
				{
					clientSaid = "";
					success = true;
					boolean madeStep = false;
					
					while(!(clientSaid == "stop" || success == false))
					{
						madeStep = true;
						
						synchronized(this) {
							success = this.server.execCommand(this.name, this.gameID, "step");
						}
						
						try {
							Thread.sleep(this.gameSpeed * 100);
						}
						catch (InterruptedException e) {
							//Do nothing
						}
					}
					
					this.server.changeStat(this.name, this.gameID, madeStep);
				}
			}
			
			this.socket.close();
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
