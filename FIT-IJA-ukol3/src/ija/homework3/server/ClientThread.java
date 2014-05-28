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
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Lists all possible labyrinths to select from
	 */
	public String listFiles()
	{
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
		try {					
	    	switch(command)
	       	{
	       		case "keys":
	       			int keys = this.player.numberOfKeys();
	       			this.out.println("keys: " + Integer.toString(keys));
	       			break;
	       		case "take":
	       			if(this.player.take())
	       				this.out.println("take - OK");
	       			else
	       				this.out.println("take - NOT OK");
	       			break;
	       		case "open":
	       			if(this.player.open())
	       				this.out.println("open - OK");
	       			else
	       				this.out.println("open - NOT OK");
	       			break;
	       		case "left":
	       			this.player.left();
	       			this.out.println("left - OK");
	       			break;
	       		case "right":
	       			this.player.right();
	       			this.out.println("right - OK");
	       			break;
	       		case "step":
	       			if(this.player.step())
	       			{
	       				this.control.refreshMap(this.gameID, false);
	       				//if player's won the game
		       			if(this.player.finished())
		       				this.control.notifyAll(this.gameID, this.player.id(), "has WON!!!");
	       				
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
	
	private void recursiveStep()
	{
		try {
			this.player.step();
			this.control.refreshMap(this.gameID, false);
			//if player's won the game
			if(this.player.finished())
			{
				this.control.notifyAll(this.gameID, this.player.id(), "has WON!!!");
				this.connectionSocket.setSoTimeout(0);
				return;
			}
			
			Thread.sleep(this.speed * 100);
			String command = this.in.readLine();
			if(command.equals("stop"))
			{
				this.connectionSocket.setSoTimeout(0);
				return;
			}
		} catch(Exception e) {
			this.recursiveStep();
			return;
		}
	}
	
	private void processGoCommand()
	{
		try {
			this.connectionSocket.setSoTimeout(50);
			if(this.player.step())
			{
				this.control.refreshMap(this.gameID, false);
				//if player's won the game
				if(this.player.finished())
				{
					this.out.println("go - OK");
					this.control.notifyAll(this.gameID, this.player.id(), "has WON!!!");
					this.connectionSocket.setSoTimeout(0);
					return;
				}
				
				Thread.sleep(this.speed * 100);
				String command = this.in.readLine();
				if(command.equals("stop"))
					this.out.println("go - OK");
				this.connectionSocket.setSoTimeout(0);
			}
			else
				this.out.println("go - NOT OK");
			
		} catch(Exception e) {
			this.recursiveStep();
			this.out.println("go - OK");
			try {
				this.connectionSocket.setSoTimeout(0);
			} catch(Exception ex) {
				//Do nothing.
			}
		}
	}
	
}
