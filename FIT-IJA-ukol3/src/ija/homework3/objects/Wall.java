package ija.homework3.objects;

public class Wall extends ija.homework3.tape.TapeObject {
	public Wall(String name)
	{
		super(name);
	}

	public String objType(){
		return "#";
	}
	
	@Override
	public boolean canSeize() {
		return false;
	}

	@Override
	public boolean open() {
		return false;
	}
	
	/* Homework2 */
	@Override
	public boolean canBeOpen() {
		return false;
	}
	
	/* Homework3 */
	@Override
	public boolean canBeTaken() {
		return false;
	}
}
