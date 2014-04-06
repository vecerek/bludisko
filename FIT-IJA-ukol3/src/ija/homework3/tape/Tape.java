package ija.homework3.tape;


public class Tape {
	public TapeField [] fieldArray;
	public TapeHead [] headArray;
	protected int w;
	protected int h;
	
	public Tape(int f, int h, String format)
	{
		this.fieldArray = new TapeField[f];
		this.headArray = new TapeHead[h];
		format = format.replaceAll("\\s", "");
		
		for(int i = 0; i < format.length(); i++)
		{
			String objectToPlace = Character.toString(format.charAt(i));
			this.fieldArray[i] = new TapeField(this, i, objectToPlace);
		}
	}

	/* Homework3 */
	public Tape(int width, int height, int h, String format)
	{
		this.w = width;
		this.h = height;
		this.fieldArray = new TapeField[width*height];
		this.headArray = new TapeHead[h];
		format = format.replaceAll("\\s", "");
		
		for(int i = 0; i < format.length(); i++)
		{
			String objectToPlace = Character.toString(format.charAt(i));
			this.fieldArray[i] = new TapeField(this, i, objectToPlace);
		}
	}
	
	public TapeField fieldAt(int i)
	{
		if(i < this.fieldArray.length)
			return this.fieldArray[i];
		else
			return null;
		
	}
	
	public TapeHead createHead(int i)
	{
		for(int j = 0; j < this.fieldArray.length; j++)
		{
			if(this.fieldArray[j].canSeize())
			{
				TapeHead head = new TapeHead(i, fieldArray[j]);
				fieldArray[j].seize(head);
				return head;
			}
		}
		return null;
	}
	
	/* Homework 3 */
	/*
	 * Returns the position to the top, if valid.
	 */
	public int isTop(int p)
	{
		int retPos = p - this.w;
		return retPos > -1 ? retPos:-1;
	}
	/*
	 * Returns the position to the bottom, if valid.
	 */
	public int isLower(int p)
	{
		int retPos = p + this.w;
		return retPos < (this.w*this.h) ? retPos:-1;
	}
	/*
	 * Returns the position to the right, if valid.
	 */
	public int isRight(int p)
	{
		int retPos = p + 1;
		if(retPos >= this.w*this.h || (retPos % this.w) == 0)
			return -1;
		else
			return retPos;
	}
	/*
	 * Returns the position to the left, if valid.
	 */
	public int isLeft(int p)
	{
		int retPos = p - 1;
		if(retPos < 0 || (retPos % this.w) == (this.w - 1))
			return -1;
		else
			return retPos;
	}
	/*
	 * Prints actual state of the labyrinth
	 */
	public void printTape()
	{
		
	}
}
