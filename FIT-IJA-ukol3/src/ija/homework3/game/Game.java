package ija.homework3.game;

import java.io.*;

import ija.homework3.tape.*;

public class Game {

    protected int R; 
    protected int C;
    protected Tape playGround;
    protected TapeHead player;
    public int speed;
    
    public Game(int speed)
	{
    	this.R = 0;
    	this.C = 0;
    	this.speed = 5;
    	this.playGround = null;
    	this.player = null;
	}
	
	public TapeHead startGame(String map)
	{
        File file = new File("examples/"+map);
        StringBuffer buff = new StringBuffer();
        String tmpString;
        int separatorPos;
        BufferedReader reader = null;
        
        try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
            tmpString = reader.readLine();
            separatorPos = tmpString.indexOf("x");
            
            this.R = Integer.parseInt(tmpString.substring(0, separatorPos));
            this.C = Integer.parseInt(tmpString.substring(separatorPos + 1, tmpString.length()));
            
            while ((tmpString = reader.readLine()) != null)
                buff.append(tmpString);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
        	if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        
        String content = (buff.toString()).replaceAll("\\s+", "");
        
        try {
        	//System.out.println(this.R + this.C + content);
        	if( (content.length() != (this.R * this.C)) ||
                (this.R < 20 || this.R > 50) ||
                (this.C < 20 || this.C > 50)
              )
                throw new TapeOutOfSize("The playground exceeds maximal size in either way.");
            else{

                this.playGround = new Tape(this.C, this.R, 1, content);
            }
        	this.player = this.playGround.createHead(1);
            return this.player;
        }
        catch(TapeOutOfSize e) {
            e.toString();
        }
        
        return null;
	}
	
	/**
	 * Adds a new player into the game, with a unique ID.
	 * 
	 * @param ID			the player's ID.
	 * @return TapeHead		new player in the game.
	 */
	public TapeHead addPlayer(int ID)
	{
		return this.playGround.createHead(ID);
	}

	/*
	 * Prints the tape's actual state.
	 */
	public void show()
	{
		this.playGround.printTape(this.R, this.C);
	}
	
	public String getLabyrinthState()
	{
		return this.playGround.sendTape(this.R, this.C);
	}
	
	/*
	 * Teleports the player to another field 
	 * for testing purposes.
	 */
	public void teleportPlayer(int position)
	{
		//leaves the actual field, all connections
		this.player = this.player.testLeaveField();
		//assigns the head to the field
		this.playGround.fieldArray[position].seize(this.player);
		//assigns the field to the head
		this.player.testSeizeField(this.playGround.fieldArray[position]);
	}
	
	/*
	 * Defined own exception, which occurs, when the tape's
	 * dimensions are out of the acceptable range.
	 */
    public class TapeOutOfSize extends Exception
    {
		private static final long serialVersionUID = 1L;

		public TapeOutOfSize(String msg)
        {
            super(msg);
        }
    }
}
