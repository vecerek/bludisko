package ija.homework3.objects;

public class Wall extends ija.homework2.tape.TapeObject {
	public Wall(String name)
	{
		super(name);
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
}
