package ija.homework3;

import ija.homework3.game.*;
import ija.homework3.tape.*;
import java.util.Arrays;

//import java.lang.reflect.*;
import java.io.*;


public class Singleplayer {
	
    public static void main(String[] argv) {
    	
    	welcomeMessage();
    	listFiles();
    	
    	Game game1 = new Game();
    	TapeHead player = start(game1);
        game1.show();
    	
    	boolean enough = false;
    	//reading commands until the close command
    	while(!enough)
    	{
    		enough = execCommand(game1, player);
    	}
    	
    	byeByeMessage();
    	
    }//main ends here
    
    /*
     * Lists all possible labyrinths to select from
     */
    public static void listFiles()
    {
    	File f = new File("examples/");
    	String[] fileNames = f.list();
    	
    	Arrays.sort(fileNames);
    	
    	for(int i=0; i < fileNames.length; i++)
    	{
    		System.out.println(fileNames[i]);
    	}
    	System.out.print("\n");
    }
    
    /*
     * Gives a warm welcome to the gamer.
     */
    public static void welcomeMessage()
    {
    	String newLine = System.getProperty("line.separator");
    	
    	System.out.println("Welcome to the labyrinth kid!"+newLine+
    	"To begin your journey choose one map by typing: game <labyrinth name>"+newLine+
    	"-where <labyrinth name> is the choosen map."+newLine+newLine+"-Map list:");
    }
    
    /*
     * Waits for 'game' command and starts the game with
     * the appropriate labyrinth.
     * Returns the created head(player).
     */
    public static TapeHead start(Game game1)
    {
    	BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        String param = "";
        while(!line.equals("game"))
        	try {
        		line = buff.readLine();
        		line = line.replaceAll("\\s+", "");
        		if(line.length() > 3)
        		{
        			param = line.substring(4);
        			line = line.substring(0, 4);
        		}
        	} catch(IOException ioe) {
        		//not sure what to do here
        		//It's okay, you did well :)
        		System.out.println("IO error trying to read your name!");
        		System.exit(1);
        	}

        return game1.startGame(param);
    }
    
    /*
     * Reads the command and executes it.
     */
    public static boolean execCommand(Game game1, TapeHead player)
    {
    	BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
    	
    	try {
    		String command = buff.readLine();
    		
    		switch(command)
        	{
        		case "take":
        			if(player.take())
        				System.out.println("OK.");
        			else
        				System.out.println("NOT OK.");
        			break;
        		case "open":
        			if(player.open())
        				System.out.println("OK.");
        			else
        				System.out.println("NOT OK.");
        			break;
        		case "left":
        			player.left();
        			System.out.println("OK.");
        			break;
        		case "right":
        			player.right();
        			System.out.println("OK.");
        			break;
        		case "step":
        			if(player.step())
        				System.out.println("OK.");
        			else
        				System.out.println("NOT OK.");
        			break;
        		case "keys":
        			player.keys();
        			break;
        		case "show":
        			game1.show();
        			break;
        		case "close":
        			return true;
        		default:
        			System.out.println("Unsupported command.");
        	}
    		
    	} catch(IOException ioe) {
    		System.out.println("IO error trying to read your name!");
    		System.exit(1);
    	}
    	
    	return false;
    }
    
    /*
     * Message after closing the game.
     */
    public static void byeByeMessage()
    {
    	System.out.println("The game has been closed.");
    }
}

