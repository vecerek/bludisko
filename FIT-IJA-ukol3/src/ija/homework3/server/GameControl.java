package ija.homework3.server;

import java.util.*;
import java.io.*;

import ija.homework3.game.*;
import ija.homework3.tape.*;

public class GameControl {

    private static GameControl instance = null;
    
    private ArrayList<Game> games = new ArrayList<Game>();
    private ArrayList<ArrayList<PrintStream>> clientsInGames = new ArrayList<ArrayList<PrintStream>>();
    private static final int MAX_PLAYERS = 4;
    
    private GameControl(){}
    
	public static GameControl getInstance()
	{
		if(instance == null)
			instance = new GameControl();
		return instance;
	}
	
	public String listGames()
	{
		String list = "list games:";
		int i = 1;
		
		for(Game game : this.games)
		{
			if(i == 1)
				list += "game" + Integer.toString(i);
			else
				list += ",game" + Integer.toString(i);
			
			i++;
		}
		
		return list;
	}
	
	public TapeHead createNewGame(ClientThread thread, PrintStream out, String map, int speed)
	{
		Game game = new Game(speed);
		this.games.add(game);
		int gameID = this.games.size() + 1;
		
		ArrayList<PrintStream> players = new ArrayList<PrintStream>();
		players.add(out);
		this.clientsInGames.add(players);
		
		TapeHead player = game.startGame(map);
		player.bindControl(this);
		player.bindGameID(gameID - 1);
		out.println("sizes:" +game.getSizes());
		out.println("map:" + game.getLabyrinthState());
		thread.bindGameID(gameID - 1);
		
		return player;
	}
	
	public TapeHead joinGame(ClientThread thread, PrintStream out, String gameName)
	{
		String tmp = gameName.substring("game".length());
		int position = Integer.parseInt(tmp) - 1;
		
		Game game = this.games.get(position);
		int size = (this.clientsInGames.get(position)).size();
		
		if(size < MAX_PLAYERS)
		{
			TapeHead player = game.addPlayer(size + 1);
			player.bindControl(this);
			player.bindGameID(position + 1);
			(this.clientsInGames.get(position)).add(out);
			
			out.println("sizes:" + game.getSizes());
			out.println("map:" + game.getLabyrinthState());
			
			thread.bindGameID(position + 1);
			thread.setSpeed(game.speed);
			this.refreshMap(position + 1, true);
			
			return player;
		}
		else
		{
			out.println("Full.");
			return null;
		}
	}
	
	public void refreshMap(int gameID, boolean joined)
	{
		ArrayList<PrintStream> players = new ArrayList<PrintStream>();
		players = this.clientsInGames.get(gameID - 1);
		Game game = this.games.get(gameID - 1);
		String newMap = game.getLabyrinthState();
		
		int size = players.size();
		int i = 1;
		
		for(PrintStream out : players)
		{
			if(joined)
			{
				if(i != size)
					out.println("refresh:" + newMap);
				i++;
			}
			else
				out.println("refresh:" + newMap);
		}
	}
	
	public void notifyAll(int gameID, int ID, String msg)
	{
		ArrayList<PrintStream> players = new ArrayList<PrintStream>();
		players = this.clientsInGames.get(gameID - 1);
		
		String hero = "";
		switch(ID)
		{
			case 1:
				hero = "Iron-Man";
				break;
			case 2:
				hero = "Captain America";
				break;
			case 3:
				hero= "Hulk";
				break;
			case 4:
				hero= "Thor";
				break;
			default:
				break;
		}
		
		for(PrintStream out : players)
		{
			out.println(hero + " " + msg);
		}
	}
	
}
