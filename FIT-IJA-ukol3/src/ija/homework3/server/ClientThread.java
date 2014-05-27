package ija.homework3.server;

import java.io.*;
import java.net.*;

import ija.homework3.tape.TapeHead;
import ija.homework3.game.*;

/**
 * A thread created by the server to communicate
 * with the client.
 * @author xvecer17
 * 
 */
public class ClientThread extends Thread {

	private int ID;
	private Socket connectionSocket;
	private BufferedReader in;
	private PrintStream out;
	protected GameControl control;
	protected Game game;
	protected String map;
	protected int speed;
	protected TapeHead player;
	protected int gameID;
	
	public ClientThread(Socket connectionSocket, int ID) {
		this.connectionSocket = connectionSocket;
		this.ID = ID;
	}
	
	public void run()
	{
		this.control = GameControl.getInstance();
		
		try {
			
			this.in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			this.out = new PrintStream(connectionSocket.getOutputStream());
			
			String clientSaid;
			
			while(true)
			{
				clientSaid = this.in.readLine();
				
				if(clientSaid.equals("list maps"))
					this.out.println(this.listFiles());
				
				else if(clientSaid.equals("list games"))
					this.out.println(control.listGames());
				
				else if(clientSaid.startsWith("create:"))
					this.createGame(clientSaid);
				
				else if(clientSaid.startsWith("join:"))
					this.joinGame(clientSaid);
				
				else if(clientSaid.equals("go"))
					this.processGoCommand();
				else
					this.executeCommand(clientSaid, false);
				
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Lists all possible labyrinths to select from
	 */
	public String listFiles()
	{
		System.out.println("Listing files:");
		File[] files = new File("examples/").listFiles();
		
		int i = 0;
		String listOfFiles = "list maps:";
		
		for(File file : files)
		{
			if(file.isFile())
			{
				if(i != 0)
					listOfFiles += "," + file.getName();
				else
					listOfFiles += file.getName();
				i++;
			}
		}
		    	
		return listOfFiles;
	}
	
	public void bindGameID(int ID)
	{
		this.gameID = ID;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	private void createGame(String create)
	{
		String map = "";
		int endIndexOfMap = create.indexOf(",speed:");
		map = create.substring("create:".length(), endIndexOfMap);
		String speedStr = create.substring(endIndexOfMap + ",speed:".length());
		this.speed = Integer.parseInt(speedStr);
		
		this.player = this.control.createNewGame(this, this.out, map, this.speed);
	}
	
	private void joinGame(String join)
	{
		String game = join.substring("join:".length());
		this.player = this.control.joinGame(this, this.out, game);
	}
	
	private void executeCommand(String command, boolean go)
	{
		System.out.println("Executing: " + command);
		try {					
	    	switch(command)
	       	{
	       		case "take":
	       			if(this.player.take())
	       				this.out.println("take - OK.");
	       			else
	       				this.out.println("take - NOT OK.");
	       			break;
	       		case "open":
	       			if(this.player.open())
	       				this.out.println("open - OK.");
	       			else
	       				this.out.println("open - NOT OK.");
	       			break;
	       		case "left":
	       			this.player.left();
	       			this.out.println("left - OK.");
	       			break;
	       		case "right":
	       			this.player.right();
	       			this.out.println("right - OK");
	       			break;
	       		case "step":
	       			if(this.player.step())
	       			{
	       				//if player's won the game
	       				if(this.player.finished());
	       					//TODO: notify all clients in the game
	       				if(!go)
	       					this.out.println("step - OK");
	       			}
	       			else
	       				this.out.println("step - NOT OK");
	       			break;
	       		default:
	       			this.out.println(command + " - NOT OK");
	       			break;
	      	}
		}
		finally {
			this.control.refreshMap(this.gameID, false);
		}
	}
	
	private void processGoCommand()
	{
		String command;
		
		if(this.player.step())
		{
			try {
				this.control.refreshMap(this.gameID, false);
				Thread.sleep(this.speed * 100);
			
				while(true)
				{
					command = this.in.readLine();
					if(command.equals("stop"))
						break;
					this.player.step();
					this.control.refreshMap(this.gameID, false);
					Thread.sleep(this.speed * 100);
				}
				
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
			
			this.out.println("go - OK");
		}
		else
			this.out.println("go - NOT OK");
	}
	
}
