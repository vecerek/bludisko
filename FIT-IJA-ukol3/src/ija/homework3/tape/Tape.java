package ija.homework3.tape;


public class Tape {
	TapeField [] fieldArray;
	TapeHead [] headArray;
	
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
}
