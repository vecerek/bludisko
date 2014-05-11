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
	
	private BufferedReader in;
	private PrintStream out;
	
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
    /*public String readResponse()
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
    }*/
    
    /**
     * Send the actual state of the Tape to the client. 
     * @param state the game field
     */
    public void send(String state)
    {
    	try {
    		this.out.println(state);
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
    	try {
			while(this.in.readLine() != "OK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
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
		System.out.println("New thread has started.");
		try {
		    //Communicating with client
			String clientSaid = "";
			boolean success;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    		PrintStream out = new PrintStream(this.socket.getOutputStream());
    		
    		this.in = in;
    		this.out = out;
			
			while((clientSaid = this.in.readLine()) != "exit")
			{
				System.out.println("The client said to: |" + clientSaid+"|");
				if(clientSaid.equals("") || clientSaid == null)
					continue;
				else if(clientSaid.equals("list maps"))
				{
					System.out.println("Lofasz");
					this.send(this.server.listFiles());
				}
				else if(clientSaid.equals("list games"))
				{
					this.send(this.server.listGames());
				}
				else if(clientSaid.contains("create:"))
				{
					String map = "";
					int endIndexOfMap = clientSaid.indexOf(",speed:");
					map = clientSaid.substring("create:".length(), endIndexOfMap);
					System.out.println("Here I am 2: " + map);
					String speedStr = clientSaid.substring(endIndexOfMap + ",speed:".length());
					System.out.println("Here I am: " + speedStr);
					int speed = Integer.parseInt(speedStr);
					
					String size = this.server.createNewGame(this.name, map, speed);
					this.send(size);
				}
				else if(clientSaid.contains("connect:"))
				{
					String gameName = clientSaid.substring("connect:".length());
					String size = this.server.connectToGame(this.name, gameName);
					this.send(size);
				}
				else if(!(clientSaid.equals("go") || clientSaid.equals("stop")))
				{
					synchronized(this) {
						success = this.server.execCommand(this.name, this.gameID, clientSaid);
					}
					
					this.server.changeStat(this.name, this.gameID, true);
					
					if(success)
					{
						this.send("OK");
						if(clientSaid.equals("step"))
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
				else if(clientSaid.equals("go"))
				{
					clientSaid = "";
					success = true;
					boolean madeStep = false;
					
					while(!(clientSaid.equals("stop") || success == false))
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
