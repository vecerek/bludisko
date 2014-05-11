package ija.homework3.client;

import java.io.*;
import java.net.*;

import ija.homework3.gui.*;

public class GameThread extends Thread {

	private GamePlay GUI;
	//private MainMenu main;
	//private Client client;
	private Socket socket;
	
	public GameThread(Socket clientSocket){
		this.socket = clientSocket;
        MainMenu main = new MainMenu(this);
        main.setVisible(true);
	}
	
	public void bindGUI(GamePlay GUI)
    {
    	this.GUI = GUI;
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
	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	
	    System.out.println("Response from server:");
	        
	    while ((tmp = stdIn.readLine()) != null)
	    {
	        System.out.println(tmp);
	        answer += tmp;
	    }
	        
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
    	try {
    		OutputStream outStream = this.socket.getOutputStream();
    		PrintStream ps = new PrintStream(outStream, true); // Second param: auto-flush on write = true
    		ps.println(toDo);
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
    
    public void refreshMap(String map)
    {
    	this.GUI.rePaintField(map);
    }
}
