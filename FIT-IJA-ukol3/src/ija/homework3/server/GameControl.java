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
		if (instance == null)
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
	
	public TapeHead createNewGame(PrintStream out, String map, int speed)
	{
		Game game = new Game(speed);
		this.games.add(game);
		
		ArrayList<PrintStream> players = new ArrayList<PrintStream>();
		players.add(out);
		this.clientsInGames.add(players);
		
		TapeHead player = game.startGame(map);
		out.println("sizes:" +game.getSizes());
		out.println("map:" + game.getLabyrinthState());
		return player;
	}
	
	public TapeHead joinGame(PrintStream out, String gameName)
	{
		String tmp = gameName.substring("game".length());
		int position = Integer.parseInt(tmp) - 1;
		
		Game game = this.games.get(position);
		int size = (this.clientsInGames.get(position)).size();
		
		if(size < MAX_PLAYERS)
		{
			TapeHead player = game.addPlayer(size + 1);
			(this.clientsInGames.get(position)).add(out);
			out.println("sizes:" + game.getSizes());
			out.println("map:" + game.getLabyrinthState());
			return player;
		}
		else
		{
			out.println("Full.");
			return null;
		}
	}
	
}
