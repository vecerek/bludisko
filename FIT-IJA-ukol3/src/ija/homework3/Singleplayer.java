package ija.homework3;

import ija.homework3.game.*;
import ija.homework3.tape.*;
import ija.homework3.objects.*;


import java.lang.reflect.*;
import java.io.*;


public class Singleplayer {

    public static void main(String[] argv) {
    	String newLine = System.getProperty("line.separator");
    	System.out.println("Welcome to the labyrinth kid!"+newLine+
    	"To begin your journey choose one map by typing: game <labyrinth name>"+newLine+
    	"-where <labyrinth name> is the choosen map."+newLine+newLine+"-Map list:");
    	
    	File f = new File("../examples");
    	String[] names = f.list();
    	java.util.Arrays.sort(names);
    	for(int i = 0; i < names.length; i++) {
    		System.out.println(names[i]);
    	}
    	System.out.println();
    	
    	
    	
    	BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        String param = "";
        while(!line.equals("game"))try {
            line = buff.readLine();
            line = line.replaceAll("\\s+", "");
            if(line.length() > 3){
            	param = line.substring(4);
            	line = line.substring(0, 4);
       //     	System.out.println(param);
         //   	System.out.println(line);
            }
        } catch (IOException ioe) {
        	 //not sure what to do here
        	 System.out.println("IO error trying to read your name!");
            System.exit(1);
         }
        //System.out.println(line);
        Game game1 = new Game();
        game1.startGame(param);
        
    }//main ends here
}

