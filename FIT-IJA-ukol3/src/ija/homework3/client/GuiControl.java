package ija.homework3.client;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ija.homework3.gui.*;

public class GuiControl extends Thread {

	private GamePlay GUI;
	private MainMenu main;
		
	private PrintStream out;
		
	public GuiControl(PrintStream out){
		
		this.main = new MainMenu(this);
		this.main.setVisible(true);

		this.out = out;
	}
	
	public void closeWindow()
	{
		this.GUI.setVisible(false);
		this.main.setVisible(true);
	}
		
	public void bindGUI(GamePlay GUI)
	{
		this.GUI = GUI;
	}
	
	public void request(String msg)
	{
		this.out.println(msg);
	}
	
	public void sendMaps(String maps)
	{
		maps = maps.substring("list maps:".length());
		int size = this.getNumberOf(maps);
        String[] strListMap = new String[size];
        strListMap = this.getArrayOf(maps);
        
        this.main.setMaps(strListMap);
	}
	
	public void sendGames(String games)
	{
		games = games.substring("list games:".length());
		int size = this.getNumberOf(games);
        String[] strListGame = new String[size];
        strListGame = this.getArrayOf(games);
        
        this.main.setGames(strListGame);
	}
	
	public void InitMap(String map)
	{
		map = map.substring("map:".length());
		this.main.InitMap(map);
	}
	
	public void refreshMap(String map)
	{
		map = map.substring("refresh:".length());
		this.GUI.rePaintField(map);
	}
	
	public void InitSizes(String sizes, boolean full)
	{
		if(!full)
		{
			sizes = sizes.substring("sizes:".length());
			int size = this.getNumberOf(sizes);
			String[] mapSizes = new String[size];
			mapSizes = this.getArrayOf(sizes);
			this.main.setSizes(mapSizes);
		}
		else
		{
			String[] mapSizes = new String[2];
			mapSizes[0] = mapSizes[1] = "0";
			this.main.setSizes(mapSizes);
		}
	}
	
	public void addHistory(String msg)
	{
		this.GUI.historyAddLine(msg);
	}
	

    private int getNumberOf(String maps)
    {
		Pattern p = Pattern.compile(",");
		Matcher m = p.matcher(maps);
		
		int count = 0;
			
		while (m.find()){
			count +=1;
		}
			
		return count + 1;
    }
    
    private String[] getArrayOf(String anything)
    {
    	int size = this.getNumberOf(anything);
    	String[] tmp = new String[size];

    	tmp = anything.split(",");
    	return tmp;
    }
}
