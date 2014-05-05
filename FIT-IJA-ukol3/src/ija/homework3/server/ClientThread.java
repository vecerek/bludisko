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
	
	private Socket socket;
	
	public ClientThread(Socket s){
		super();
		this.socket = s;
	}
	
	/**
	 * A simple method reading messages from the client.
	 * Those messages are interpreted into actual moves
	 * in the game and executed afterwards.
	 * @return client's message(command)
	 * @throws IOException
	 */
    public String readResponse() throws IOException
    {
        String toDo = "";
        String tmp;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        System.out.println("Response from client:");
        
        while ((tmp = stdIn.readLine()) != null)
        {
            System.out.println(tmp);
            toDo += tmp;
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
    		PrintStream ps = new PrintStream(outStream, true); // Second param: auto-flush on write = true
    		ps.println(state);
    	} catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
	
    /**
     * Starts the communication with the client.
     */
	public void start()
	{
		try {
			// obsluha klienta
			this.socket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
