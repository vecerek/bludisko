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
	
	private void createGame(String create)
	{
		String map = "";
		int endIndexOfMap = create.indexOf(",speed:");
		map = create.substring("create:".length(), endIndexOfMap);
		String speedStr = create.substring(endIndexOfMap + ",speed:".length());
		
		this.player = this.control.createNewGame(this.out, map, Integer.parseInt(speedStr));
	}
	
	private void joinGame(String join)
	{
		String game = join.substring("join:".length());
		this.player = this.control.joinGame(this.out, game);
	}
	
}
