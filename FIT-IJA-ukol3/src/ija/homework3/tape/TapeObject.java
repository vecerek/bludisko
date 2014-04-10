package ija.homework3.tape;

import ija.homework3.objects.*;

public abstract class TapeObject
{
	protected String name;
	
	public TapeObject(String name)
	{
		this.name = name;
	}
	
	public abstract boolean canSeize();
	public abstract boolean open();
	public abstract boolean canBeOpen();
	public abstract boolean canBeTaken();
	public abstract String	objType();

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TapeObject)
		{
			TapeObject c = (TapeObject) o;
			return (this.hashCode() == c.hashCode() ? this.name.equals(c.name) : false);
		}
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.name.hashCode();
	}
	
	/* Homework2 */
	public static TapeObject create(String format)
	{
		switch(format)
		{
			case "w":
				return new Wall("w");
			case "g":
				return new Gate("g");
			case "k":
				return new Key("k");
			case "f":
				return new Finish("f");
			default:
				return null;
		}
	}
}
