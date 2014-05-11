package ija.homework3.client;

import java.io.*;
import java.net.*;

import ija.homework3.gui.*;

public class GameThread extends Thread {

	private GamePlay GUI;
	//private MainMenu main;
	private Client client;
	
	private BufferedReader is;
	private PrintStream os;
	
	public GameThread(Client client, BufferedReader is, PrintStream os){
		this.client = client;
		
        MainMenu main = new MainMenu(this);
        main.setVisible(true);
        
        this.is = is;
        this.os = os;
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
	    
	
	    System.out.println("GameThread - Response from server:");
	        
	    while((tmp = this.is.readLine()) != null)
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
    	System.out.println(toDo);
    	try {
    		this.os.println(toDo);
    	} catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public String request(String what)
    {
    	synchronized(this) {
	    	System.out.println("GameThread - request.");
	    	try {
		    	this.client.send(what);
		    	String answer = this.client.readResponse();
		    	System.out.println("Thread: " + answer);
		    	return answer;
	    	} catch (Exception e) {
	    		System.out.println("Excepton1.");
	    		System.err.println(e.getMessage());
	    		return "";
	    	}
    	}
    }
    
    public void refreshMap(String map)
    {
    	this.GUI.rePaintField(map);
    }
}
