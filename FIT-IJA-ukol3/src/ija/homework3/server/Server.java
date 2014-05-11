package ija.homework3.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ija.homework3.game.*;
import ija.homework3.tape.*;

/**
 * A simple socket server.
 * There can be created countless games and the number of
 * players in each game is limited up to 4. To handle so 
 * many games and clients, it's essential to track the 
 * belongings of each player to his/her game.
 * @author xvecer17
 * 
 */
public class Server {

	private ServerSocket ss;
	private int port;
	//Each client has its own ID.
	private int clientCounter;
	//Each game has its own ID.
	private int gameCounter;
	
	protected BufferedReader in;
	protected PrintStream out;
	
	//Pairs of gameIDs and Game objects.
	private Map<String, Game> games;
	//List of ClientThreads.
	private ArrayList<ClientThread> clients;
	
	/**
	 * The clientsInGames is that data structure, which
	 * maps the name of the game to an other map of client IDs
	 * and TapeHead(actual player) objects.
	 */
	private Map<String, Map<String, TapeHead>> clientsInGames;
	
	private static final int MAX_PLAYERS = 4;
	
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
	 * Starts the server. Establishes connection to every client.
	 * Tags the clients with unique ID. Forks a new thread to handle
	 * client-server communication. Adds the new thread to the list
	 * of ClientThreads.
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
	    		
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		PrintStream out = new PrintStream(connection.getOutputStream());
	    		
	    		this.in = in;
	    		this.out = out;
	    		
	    		String clientName = "Client" + Integer.toString(this.clientCounter);
	    		ClientThread client = new ClientThread(this, connection, clientName);
	    		client.start();
	    		this.clients.add(client);
	    		if(this.clients.isEmpty())
	    			System.out.println("START: Empty.");
	    		System.out.println("Client connected.");
	    		this.clientCounter++;
	    		
	    	} catch (IOException e) {
	    		System.out.println(e);
	    	}
	    }
	}
	
	/**
     * Lists all possible labyrinths to select from
     */
    public String listFiles()
    {System.out.println("Listing files:");
    	File f = new File("examples/");
    	String[] fileNames = f.list();
    	String listOfFiles = "";
    	
    	Arrays.sort(fileNames);
    	int size = fileNames.length;
    	System.out.println("Size: " + size);
    	
    	for(int i=0; i < size; i++)
    	{System.out.println(fileNames[i]);
    		if(i != size - 1)
    			listOfFiles += fileNames[i] + ",";
    		else
    			listOfFiles += fileNames[i];
    	}
    	
    	return listOfFiles;
    }
    
    public String listGames()
    {
    	String key = "";
    	String listOfGames = "";
    	int count = 0;
    	Map.Entry<String, Game> pairs;
		
		Map<String, Game> gamesList = new HashMap<String, Game>();
		
		Iterator<Map.Entry<String, Game>> it = gamesList.entrySet().iterator();
	    while(it.hasNext())
	    {
	        pairs = (Map.Entry<String, Game>)it.next();
	        key = pairs.getKey();
	        
	        if(count == 0)
	        	listOfGames += key;
	        else
	        	listOfGames += "," + key;
	        
	        count++;
	    }
	    gamesList.clear();
	    
	    return listOfGames;
    }
	
	/**
	 * Creates new Game object. Tags it with a unique ID.
	 * Puts the pair of gameID and object into the map.
	 * Starts the game and creates the first player. Puts
	 * the pair of clientID and player object into a map.
	 * Puts that map into the clientsInGames map in pair
	 * with the gameID.
	 * 
	 * @param clientName	client's ID.
	 * @param map			chosen level of labyrinth.
	 * @return true/false   is it able to create a new game or not.
	 */
	public String createNewGame(String clientName, String map, int speed)
	{
		System.out.println("CreateNewGame1");
		try {			
			String client = clientName.substring("Client".length());
			int ID = Integer.parseInt(client);
			
			Game game = new Game(speed);
			String gameName;
		
			if(this.gameCounter < 10)
				gameName = "Game0" + Integer.toString(this.gameCounter);
			else
				gameName = "Game" + Integer.toString(this.gameCounter);
			this.gameCounter++;
		
			this.games.put(gameName, game);
			System.out.println("Lofasz2: " + ID);
    		if(this.clients.isEmpty())
    			System.out.println("START: Empty.");
			ClientThread thread = this.clients.get(ID - 1);
			thread.joinGame(gameName, game.speed);
		
			TapeHead player = game.startGame(map);
			
			Map<String, TapeHead> clientsPlayers = new HashMap<String, TapeHead>();
			clientsPlayers.put(clientName, player);
			this.clientsInGames.put(gameName, clientsPlayers);
			clientsPlayers.clear();
			System.out.println("CreateNewGame10");
			return game.getSizes();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the number of players in the game. If it's possible, 
	 * the client connects and a new player is placed into the game.
	 * 
	 * @param clientName	client's ID.
	 * @param gameName		games's ID.
	 * @return true/false	is the client able to connect to the game.
	 */
	public String connectToGame(String clientName, String gameName)
	{
		System.out.println("Connecting to a game.");
		Map<String, TapeHead> clientsPlayers = new HashMap<String, TapeHead>();
		clientsPlayers = this.clientsInGames.get(gameName);
		int size = clientsPlayers.size();
		
		if(size < MAX_PLAYERS)
		{
			Game game = this.games.get(gameName);
			TapeHead player = game.addPlayer(++size);
			clientsPlayers.put(clientName, player);
			this.clientsInGames.put(gameName, clientsPlayers);
			
			//clears the temporary map.
			clientsPlayers.clear();
			
			String client = clientName.substring("Client".length());
			int ID = Integer.parseInt(client);
			ClientThread thread = this.clients.get(--ID);
			thread.joinGame(gameName, game.speed);
			
			return game.getSizes();
		}
		else
			return "Full.";
	}
	
	public boolean execCommand(String clientName, String gameName, String command)
    {
		System.out.println("Executing: " + command);
		try {
			TapeHead player = this.getPlayer(clientName, gameName);
					
	    	switch(command)
	       	{
	       		case "take":
	       			if(player.take())
	       				return true;
	       			else
	       				return false;
	       		case "open":
	       			if(player.open())
	       				return true;
	       			else
	       				return false;
	       		case "left":
	       			player.left();
	       			return true;
	       		case "right":
	       			player.right();
	       			return true;
	       		case "step":
	       			if(player.step())
	       			{
	       				//if player's won the game
	       				if(player.finished())
	       					//TODO: notify all clients in the game
	       				return true;
	       			}
	       			else
	       				return false;
	       		default:
	       			return false;
	      	}
		}
		finally {
			this.refreshMap(gameName);
		}
    }
	
	public void refreshMap(String gameName)
	{
		System.out.println("Refresh started.");
		//String key = "";
		String client = "";
		int ID;
		List<ClientThread> InGamePlayers = new ArrayList<ClientThread>();
		//Map.Entry<String, TapeHead> pairs;
		
		Map<String, TapeHead> players = new HashMap<String, TapeHead>();
		players = this.clientsInGames.get(gameName);
		
		for (String key : players.keySet()) {
		    //TapeHead player = players.get(key);
		    //System.out.println("Key = " + key + ", Value = " + value);
		    client = key.substring("Client".length());
			ID = Integer.parseInt(client);
			
			InGamePlayers.add(this.clients.get(--ID));
		}
		
		
		
		/*Iterator<Map.Entry<String, TapeHead>> it = players.entrySet().iterator();
	    while(it.hasNext())
	    {
	        pairs = (Map.Entry<String, TapeHead>)it.next();
	        key = pairs.getKey();
	        
	        client = key.substring("Client".length());
			ID = Integer.parseInt(client);
			
			InGamePlayers.add(this.clients.get(--ID));
	    }*/
	    
	    ClientThread oneClient;
	    String gameField = this.getGameField(gameName);
	    
	    Iterator<ClientThread> CTit = InGamePlayers.listIterator();
		while(CTit.hasNext())
		{
			oneClient = CTit.next();
			oneClient.sendGameField(gameField);			
		}
	}
	
	public void changeStat(String clientName, String gameName, boolean success)
	{
		TapeHead player = this.getPlayer(clientName, gameName);
		player.increaseRate(success);
	}
	
	public String getGameField(String gameName)
	{
		Game game = this.games.get(gameName);
		return game.getLabyrinthState();
	}
	
	public int getKeys(String clientName, String gameName)
	{
		TapeHead player = this.getPlayer(clientName, gameName);
		return player.numberOfKeys();
	}
	
	public int getKills(String clientName, String gameName)
	{
		TapeHead player = this.getPlayer(clientName, gameName);
		return player.getKills();
	}
	
	public double getRate(String clientName, String gameName)
	{
		TapeHead player = this.getPlayer(clientName, gameName);
		return player.getRate();
	}
	
	private TapeHead getPlayer(String clientName, String gameName)
	{
		Map<String, TapeHead> clientsPlayers = new HashMap<String, TapeHead>();
		clientsPlayers = this.clientsInGames.get(gameName);
		
		try {
			return clientsPlayers.get(clientName);
		}
		finally {
			//clears the temporary map.
			clientsPlayers.clear();
		}
	}
    
    /**
    * Creates a SocketServer object and starts the server.
    * 
    * @param args
    */
    public static void main(String[] args) {
        // Setting a default port number.
        int portNumber = 9988;
        
        try {
            // initializing the Socket Server
            Server socketServer = new Server(portNumber);
            socketServer.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
}
