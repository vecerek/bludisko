package ija.homework3.server;

import java.io.*;
import java.net.*;

/**
 * A socket server for handling up to 4 clients.
 * @author xvecer17
 * 
 */
public class Server {

	private ServerSocket ss;
	private int port;
	
	public Server(int p)
	{
		this.port = p;
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
		try {
			this.ss = new ServerSocket(this.port);
			
			for (int conns = 0; conns < 4; conns++)
			{
				Socket connection = ss.accept();
				(new ClientThread(connection)).start();
			}
			
			ss.close();
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
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
