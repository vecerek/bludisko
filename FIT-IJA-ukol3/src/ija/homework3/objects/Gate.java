package ija.homework3.objects;

public class Gate extends ija.homework3.tape.TapeObject {
	protected boolean opened = false;
	
	public Gate(String name) {
		super(name);
	}

	@Override
	public String	objType(){
		if(!this.opened)
			return "G";
		else
			return "-";
	}

	@Override
	public boolean canSeize() {
		if(this.opened == false)
			return false;
		return true;
	}

	@Override
	public boolean open() {
		if(this.opened == false)
		{
			this.opened = true;
			return true;
		}
		return false;
	}

	public boolean equals(Object o)
	{
		if(o instanceof Gate)
		{
			Gate c = (Gate) o;
			if (this.hashCode() == c.hashCode() && this.opened == c.opened)
				return this.name.equals(c.name);
			else
				return false;
		}
		else
			return false;
	}
	
	public int hashCode() 
	{
		return this.name.hashCode();
	}
	
	/* Homework2 */
	@Override
	public boolean canBeOpen() {
		if(this.opened)
			return false;
		else
			return true;
	}
	
	/* Homework3 */
	@Override
	public boolean canBeTaken() {
		return false;
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
}
