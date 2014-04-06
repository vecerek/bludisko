package ija.homework3.objects;

public class Finish extends ija.homework3.tape.TapeObject {
	
	public Finish(String name) {
		super(name);
	}
	
	@Override
	public boolean canSeize() {
		return true;
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
		return true;
	}
}
