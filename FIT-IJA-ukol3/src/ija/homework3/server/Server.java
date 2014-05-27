package ija.homework3.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author xvecer17
 * 
 */
public class Server {

	// The server socket.
	private static ServerSocket serverSocket = null;
	// Maximum opened connections
	private static final int max = 100;
	
	public static void main(String args[]) {
	
		// The default port number.
		int portNumber = 9988;
		Socket connectionSocket;
		int numberOfClients = 0;
		ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
		
		/*
		 * Opens a server socket on the portNumber.
		 */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		/*
		 * Creates a connection socket for each connection. 
		 */
		while (true) {
			try {
				
				connectionSocket = serverSocket.accept();
				if(numberOfClients < max)
				{
					ClientThread client;
					(client = new ClientThread(connectionSocket, numberOfClients + 1)).start();
					clients.add(client);
				}
				else
				{
					PrintStream out = new PrintStream(connectionSocket.getOutputStream());
					out.println("Max number of clients reached.");
					out.close();
					connectionSocket.close();
				}
				
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
