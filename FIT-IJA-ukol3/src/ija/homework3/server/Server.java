package ija.homework3.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ija.homework3.game.*;
import ija.homework3.tape.*;

/**
 * A socket server for handling up to 4 clients.
 * @author xvecer17
 * 
 */
public class Server {

	private ServerSocket ss;
	private int port;
	private int clientCounter;
	private int gameCounter;
	private Map<String, Game> games;
	private List<ClientThread> clients;
	private Map<String, Map<String, TapeHead>> clientsInGames;
	
	int MaxPlayerCount = 4;
	
	public Server(int p)
	{
		this.port = p;
		this.games = new HashMap<String, Game>();
		this.clients = new ArrayList<ClientThread>();
		this.clientsInGames = new HashMap<String, Map<String, TapeHead>>();
		this.clientCounter = 1;
		this.gameCounter = 1;
	}
	
	/**
	 * Starting server, waiting for up to 4 connections.
	 * A new thread is created by every connection, which
	 * represents the communication channel with the client.
	 * The client's messages are interpreted into the actual
	 * moves in the game, which are then executed and the
	 * new scene is sent back to the client.
	 * @throws IOException
	 */
	public void start() throws IOException
	{
		//Creating server socket on default portNumber.
		try {
			this.ss = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		/*
	     * Create a client socket for each connection and pass it to a new client
	     * thread.
	     */
	    while(true)
	    {
	    	try {
	    		Socket connection = this.ss.accept();
	    		String clientName = "Client" + Integer.toString(this.clientCounter);
	    		ClientThread client = new ClientThread(this, connection, clientName);
	    		client.start();
	    		this.clients.add(client);
	    		this.clientCounter++;
	    	} catch (IOException e) {
	    		System.out.println(e);
	    	}
	    }
	}
	
	public boolean createNewGame(String clientName, String map)
	{
		try {
			Game game = new Game();
			String gameName;
		
			if(this.gameCounter < 10)
				gameName = "Game0" + Integer.toString(this.gameCounter);
			else
				gameName = "Game" + Integer.toString(this.gameCounter);
			this.gameCounter++;
		
			this.games.put(gameName, game);
		
			TapeHead player = game.startGame(map);
			Map<String, TapeHead> clientsPlayers = new HashMap<String, TapeHead>();
			clientsPlayers.put(clientName, player);
			this.clientsInGames.put(gameName, clientsPlayers);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean connectToGame(String clientName, String gameName)
	{
		Game game = this.games.get(gameName);
		int size = this.clientsInGames.size();
		
		if(size < MaxPlayerCount)
		{
			TapeHead player = game.addPlayer();
			Map<String, TapeHead> clientsPlayers = new HashMap<String, TapeHead>();
			clientsPlayers = this.clientsInGames.get(gameName);
			clientsPlayers.put(clientName, player);
			this.clientsInGames.put(gameName, clientsPlayers);
			clientsPlayers.clear();
			
			return true;
		}
		else
			return false;
	}
    
    /**
    * Creates a SocketServer object and starts the server.
    * 
    * @param args
    */
    public static void main(String[] args) {
        // Setting a default port number.
        int portNumber = 9990;
        
        try {
            // initializing the Socket Server
            Server socketServer = new Server(portNumber);
            socketServer.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
}
