package ija.homework3.tape;

import ija.homework3.server.GameControl;

public class TapeHead {
	
	protected int id;		//possible values: 1..4, 1=Ironman, 2=Captain America, 3=Hulk, 4=Thor
	protected TapeField field;
	protected int keys;
	protected int state;	//possible values: 0..3, 0=north 3=west orientation
	protected boolean alive;
	
	private int passed;
	private int failed;
	private int kills;
	private GameControl control;
	private int gameID;
	
	public TapeHead(int id)
	{
		this.id = id;
	}
	
	public TapeHead(int id, TapeField f)
	{
		this.id = id;
		this.field = f;
		this.keys = 0;
		this.state = 0;
		this.alive = true;
		
		this.passed = 0;
		this.failed = 0;
		this.kills = 0;
	}
	
	public int id()
	{
		return this.id;
	}
	
	public void bindControl(GameControl control)
	{
		this.control = control;
	}
	
	public void bindGameID(int ID)
	{
		this.gameID = ID;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TapeHead)
		{
			TapeHead c = (TapeHead) o;
			if (this.hashCode() == c.hashCode())
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() 
	{
		return this.id;
	}
	
	/* Homework2 */
	/*
	 * Returns the field seized by the head.
	 */
	public TapeField seizedField()
	{
		return this.field;
	}
	
	public void die()
	{
		this.alive = false;
		this.field = null;
		
		this.control.notifyAll(this.gameID, this.id, "has died.");
	}
	
	public boolean isAlive()
	{
		return this.alive;
	}
	
	/*
	 * Adds n keys into the backpack.
	 */
	public void addKeys(int n)
	{
		this.keys += n;
	}
	
	public boolean moveRight()
	{
		TapeField fieldTmp = this.field;
		
		while((fieldTmp = fieldTmp.rightField()) != null)
		{
			if(fieldTmp.canSeize())
			{
				this.field.leave();
				this.field = fieldTmp;
				fieldTmp.seize(this);
				return true;
			}
			else if(fieldTmp.canBeOpen() && (this.keys > 0))
			{
				fieldTmp.open();
				this.keys--;
				this.field.leave();
				this.field = fieldTmp;
				fieldTmp.seize(this);
				return true;
			}
		}
		return false;
	}

	/* Homework3 */
	/*
	 * May take a step to the next field if possible.
	 */
	public boolean step()
	{		
		return this.makeStep(getNextField());
	}
	
	/*
	 * Depending on the head orientation, the method returns
	 * the correct next field in its way.
	 */
	private TapeField getNextField()
	{
		TapeField tmp = null;
		
		switch(this.state)
		{
			/* North orientation */
			case 0:
				tmp = this.field.topField();
				break;
			/* East orientation */
			case 1:
				tmp = this.field.rightField();
				break;
			/* South orientation */
			case 2:
				tmp = this.field.lowerField();
				break;
			/* West orientation */
			case 3:
				tmp = this.field.leftField();
				break;
		}
		
		return tmp;
	}
	
	/*
	 * Opens and seizes a gate or seizes an empty field
	 * or takes the key and seizes its field.
	 */
	private boolean makeStep(TapeField fieldTmp)
	{
		if(fieldTmp != null)
		{
			if(fieldTmp.canSeize())
			{
				return seizeField(fieldTmp);
			}
			else if(fieldTmp.canKill())
			{
				fieldTmp.kill();
				this.kills++;
				return this.makeStep(fieldTmp);
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/*
	 * The head moves to the new field by leaving
	 * the actual position and seizing the new field.
	 */
	public boolean seizeField(TapeField fieldTmp)
	{
		this.field.leave();
		this.field = fieldTmp;
		fieldTmp.seize(this);
		return true;
	}

	/*
	 * Rotates the head 90% left.
	 */
	public void left()
	{
		if(this.state == 0)
			this.state = 3;
		else
			this.state--;
	}
	
	/*
	 * Rotates the head 90% right.
	 */
	public void right()
	{
		if(this.state == 3)
			this.state = 0;
		else
			this.state++;
	}
	
	/*
	 * Adds a key and removes the object from the field.
	 */
	public boolean take()
	{
		TapeField tmp = getNextField();
		
		if(tmp.canBeTaken())
		{
			this.addKeys(1);
			tmp.obj = null;
			return true;
		}
		else
			return false;
	}
	
	/*
	 * Opens a gate.
	 */
	public boolean open()
	{
		TapeField fieldTmp = getNextField();
		
		if(fieldTmp.canBeOpen() && (this.keys > 0))
		{
			fieldTmp.open();
			this.keys--;
			return true;
		}
		else
			return false;
	}
	
	/*
	 * Prints the actual amount of keys possessed.
	 */
	public void keys()
	{
		System.out.println(this.keys);
	}
	
	public int numberOfKeys()
	{
		this.passed++;
		return this.keys;
	}
	
	/*
	 * Asks the field, if it's the finishing one.
	 */
	public boolean finished()
	{
		if(this.field.objType().equals("f"))
			return true;
		else
			return false;
	}
	
	public void increaseRate(boolean success)
	{
		if(success)
			this.passed++;
		else
			this.failed++;
	}
	
	public double getRate()
	{
		return 100.0/(1 + (double)this.failed/this.passed);
	}
	
	public int getKills()
	{
		return this.kills;
	}
	
	/*
	 * Returns the number of keys for testing 
	 * purposes.
	 */
	public int testKeys()
	{
		return this.keys;
	}
	
	/*
	 * Leaves the actual field for testing 
	 * purposes.
	 */
	public TapeHead testLeaveField()
	{
		TapeHead tmp = this.field.leave();
		this.field = null;
		return tmp;
	}
	
	/*
	 * Saves it's new position.
	 * Used by teleport method.
	 */
	public void testSeizeField(TapeField field)
	{
		this.field = field;
	}
}
