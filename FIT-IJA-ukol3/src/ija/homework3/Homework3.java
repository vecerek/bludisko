
package ija.homework3;

import java.lang.reflect.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import ija.homework3.tape.*;
import ija.homework3.objects.*;
import ija.homework3.game.*;

/**
 * Homework2: uloha c. 2 z IJA
 * Trida testujici implementaci zadani 2. ukolu.
 */
@SuppressWarnings("unused")
public class Homework3 {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }
    
    @Test
    public void testWallsAndTakingKeys() {
    	Game game = new Game();
    	TapeHead player = game.startGame("bludisko1.txt");
    	
    	assertTrueFalse(game, player, "STEP", false);
    	assertTrueFalse(game, player, "OPEN", false);
    	assertTrueFalse(game, player, "TAKE", false);
    	
    	//point the head right to a key
    	assertLeftRight(game, player, "right");
    	//taking step to the key
    	assertTrueFalse(game, player, "STEP", true);
    	
    	assertTrueFalse(game, player, "STEP", false);
    	assertTrueFalse(game, player, "OPEN", false);
    	assertTrueFalse(game, player, "TAKE", true);
    	//key is taken, now it should be seizable
    	assertTrueFalse(game, player, "OPEN", false);
    	assertTrueFalse(game, player, "TAKE", false);
    	assertTrueFalse(game, player, "STEP", true);
    	//testing number of keys
    	assertKeyNumber(game, player, "KEYS", 1);    	
    }
    
    @Test
    public void testOpeningGates() {
    	Game game = new Game();
    	TapeHead player = game.startGame("bludisko1.txt");
    	//teleporting player next to a gate, orientation: north
    	game.teleportPlayer(78);
    	//orientation changed to east
    	assertLeftRight(game, player, "right");
    	
    	assertTrueFalse(game, player, "STEP", false);
    	assertTrueFalse(game, player, "OPEN", false);
    	assertTrueFalse(game, player, "TAKE", false);
    	
    	//orientation changed to south(facing the gate)
    	assertLeftRight(game, player, "right");
    	//no keys yet
    	assertTrueFalse(game, player, "STEP", false);
    	assertTrueFalse(game, player, "TAKE", false);
    	assertTrueFalse(game, player, "OPEN", false);
    	
    	player.addKeys(10);
    	
    	assertTrueFalse(game, player, "STEP", false);
    	assertTrueFalse(game, player, "TAKE", false);
    	assertTrueFalse(game, player, "OPEN", true);
    	
    	assertKeyNumber(game, player, "KEYS", 9);
    	//now it should pass the opened gate
    	assertTrueFalse(game, player, "TAKE", false);
    	assertTrueFalse(game, player, "STEP", true);
    }
    
    @Test
    public void testFinish() {
    	Game game = new Game();
    	TapeHead player = game.startGame("bludisko1.txt");
    	//teleporting player close to the finishing field, orientation: north
    	game.teleportPlayer(396);
    	//orientation changed to east
    	assertLeftRight(game, player, "right");
    	
    	assertTrueFalse(game, player, "STEP", true);
    	//orientation changed to south
    	assertLeftRight(game, player, "right");
    	//tests the fields boarders
    	assertTrueFalse(game, player, "STEP", false);

    	//orientation changed to east
    	assertLeftRight(game, player, "left");
    	assertTrueFalse(game, player, "STEP", true);
    	assertTrueFalse(game, player, "STEP", true);
    	assertTrueFalse(game, player, "FINISH", true);
    }
    
    /*
     * Tests the number of keys possessed by the player. 
     */
    public void assertKeyNumber(Game game,TapeHead player, String message, int keys)
    {
    	try {
    		assertEquals(message, keys, player.testKeys());
    	}
    	catch(AssertionError e) {
    		System.out.println(message + " - failed");
    		game.show();
    		throw e;
    	}
    }
    
    /*
     * Tests assertTrue or assertFalse based on the arbitrator.
     */
    public void assertTrueFalse(Game game, TapeHead player, String message, boolean arbitrator)
    {   
    	String extendedMessage;
    	if(arbitrator)
    		extendedMessage = "\n" + message + ": should be executed";
    	else
    		extendedMessage = "\n" + message + ": should not be executed";
    	
    	try {
    		if(arbitrator)
    			assertTrue(message, doCommand(game, player, message));
    		else
    			assertFalse(message, doCommand(game, player, message));
            System.out.print(extendedMessage + " - passed");
            game.show();
        }
    	catch(AssertionError e) {
    		System.out.print(extendedMessage + " - failed");
    		game.show();
    		throw e;
    	}
    }
    
    /*
     * Executes the appropriate command.
     */
    public boolean doCommand(Game game, TapeHead player, String message)
    {
    	switch(message)
    	{
    		case "STEP":
    			return player.step();
    		case "TAKE":
    			return player.take();
    		case "OPEN":
    			return player.open();
    		case "FINISH":
    			return player.finished();
    	}
    	
    	return false;
    }
    
    /*
     * "Tests" left() or right() based on the direction.
     */
    public void assertLeftRight(Game game, TapeHead player, String direction)
    {
    	if(direction == "right")
    	{
    		player.right();
    		System.out.print("\nRIGHT: should be always executed");
    	}
    	else if(direction == "left")
    	{
    		player.left();
    		System.out.print("\nLEFT: should be always executed");
    	}
    	
    	game.show();
    }

}