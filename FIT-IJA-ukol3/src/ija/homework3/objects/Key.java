package ija.homework3.objects;

public class Key extends ija.homework3.tape.TapeObject {
	
	public Key(String name) {
		super(name);
	}

	@Override
	public String	objType(){
		return "~";
	}

	@Override
	public boolean canSeize() {
		return false;
	}

	@Override
	public boolean open() {
		return false;
	}
	
	@Override
	public boolean canBeOpen() {
		return false;
	}
	
	@Override
	public boolean canBeTaken() {
		return true;
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
}
