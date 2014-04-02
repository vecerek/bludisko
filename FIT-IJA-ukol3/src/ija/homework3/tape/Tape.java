package ija.homework3.tape;


public class Tape {
	TapeField [] fieldArray;
	TapeHead [] headArray;
	TapeField [][] field2DArray;
	
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
		this.field2DArray = new TapeField[height][width];
		this.headArray = new TapeHead[h];
		format = format.replaceAll("\\s", "");
		int position = 0;

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				String objectToPlace = Character.toString(format.charAt(position));
				this.field2DArray[i][j] = new TapeField(this, position, objectToPlace);
				position++;
			}
		}
	}
	/*****************************************************/
	
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
