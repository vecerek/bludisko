package ija.homework3.tape;

//import ija.homework3.objects.*;

public class TapeField {
	protected int p;
	protected TapeObject obj;
	protected TapeHead head;
	protected Tape tape;
	
	public TapeField(int p)
	{
		this.p = p;
	}
	
	public TapeField(int p, TapeObject obj)
	{
		this.p = p;
		this.obj = obj;
	}
	
	public TapeField(Tape tape, int p, String type)
	{
		this.p = p;
		this.obj = TapeObject.create(type);
		this.tape = tape;
	}
	
	/*
	 * Returns the field's position.
	 */
	public int position()
	{
		return this.p;
	}
	
	/*
	 * Seizes field.
	 */
	public boolean seize(TapeHead head)
	{
		if(canSeize()) {
			this.head = head;
			return true;
		}
		else
			return false;
	}
	
	/*
	 * Makes the head leave it's field.
	 */
	public TapeHead leave()
	{
		if(this.obj != null || this.head != null)
		{
			TapeHead tmp = this.head;
			this.head = null;
			return tmp;
		}
		else
			return null;
	}
	
	/*
	 * Checks, if the field can be seized.
	 */
	public boolean canSeize()
	{
		if(this.obj == null && this.head == null)
			return true;
		else
		{
			if(this.head == null && this.obj.canSeize())
				return true;
			return false;
		}
	}
	
	public boolean canKill()
	{
		if(this.head != null)
			return true;
		else
			return false;
	}
	
	public void kill()
	{
		this.head.die();
		this.head = null;
	}
	
	public int lootKeys()
	{
		if(this.canKill())
			return this.head.numberOfKeys();
		else
			return 0;
	}
	
	public int getPlayerID()
	{
		return this.head.id();
	}
	
	/*
	 * Opens the closed gate.
	 */
	public boolean open()
	{
		if (this.obj != null && this.obj.open())
			return true;
		else
			return false;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TapeField)
		{
			TapeField c = (TapeField) o;
			if (this.hashCode() == c.hashCode() && this.obj == c.obj)
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
		return this.p;
	}
	
	/* Homework 2*/
	public TapeField rightField()
	{
		/* Homework 3 body */
		int rightPos = this.tape.isRight(this.p);
		if(rightPos != -1)
			return this.tape.fieldArray[rightPos];
		else
			return null;
		
	}
	/*
	 * Checks, if the field contains a closed gate.
	 */
	public boolean canBeOpen()
	{
		if(this.obj == null)
			return false;
		else if(this.obj.canBeOpen())
			return true;
		else
			return false;
	}
	
	/* Homework 3 */
	/*
	 * Checks, if the field contains a key, or not.
	 */
	public boolean canBeTaken()
	{
		if(this.obj == null)
			return false;
		else if(this.obj.canBeTaken())
			return true;
		else
			return false;
	}
	
	/*
	 * Returns the field to the left, if exists.
	 */
	public TapeField leftField()
	{
		int leftPos = this.tape.isLeft(this.p);
		if(leftPos != -1)
			return this.tape.fieldArray[leftPos];
		else
			return null;
	}
	/*
	 * Returns the field to the top, if exists.
	 */
	public TapeField topField()
	{
		int topPos = this.tape.isTop(this.p);
		if(topPos != -1)
			return this.tape.fieldArray[topPos];
		else
			return null;
	}
	
	/*
	 *  Returns the field to the bottom, if exists.
	 */
	public TapeField lowerField()
	{
		int lowPos = this.tape.isLower(this.p);
		if(lowPos != -1)
			return this.tape.fieldArray[lowPos];
		else
			return null;
	}
	
	/*
	 * Returns the type of the object on the field. 
	 */
	public String objType(){
		if(this.obj == null)
			return "p";
		else
			return obj.objType();
	}
	
	/*
	 * Returns the players's orientation on the field.
	 */
	public int headOrientation()
	{
		return this.head.state;
	}
}
