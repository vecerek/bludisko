package ija.homework3.tape;

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
	
	public int position()
	{
		return this.p;
	}
	
	public boolean seize(TapeHead head)
	{
		if(canSeize()) {
			this.head = head;
			return true;
		}
		else
			return false;
	}
	
	public TapeHead leave()
	{
		if(this.obj != null || this.head != null)
		{
			TapeHead tmp = head;
			this.head = null;
			return tmp;
		}
		else
			return null;
	}
	
	public boolean canSeize()
	{
		if (this.obj == null && this.head == null)
			return true;
		else
		{
			if (this.head == null && this.obj.canSeize())
				return true;
			return false;
		}
	}
	
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
		if((this.p+1) < this.tape.fieldArray.length)
			return this.tape.fieldArray[this.p + 1];
		else
			return null;
	}
	
	public boolean canBeOpen()
	{
		if(this.obj == null)
			return false;
		else if(this.obj.canBeOpen())
			return true;
		else
			return false;
	}
}
