package ija.homework3.tape;

public class TapeHead {
	protected int id;
	protected TapeField field;
	protected int keys;
	protected int state;	//possible values: 0..3, 0=north 3=west orientation
	
	public TapeHead(int id)
	{
		this.id = id;
	}
	
	public TapeHead(int id, TapeField f)
	{
		this.id = id;
		this.field = f;
		this.keys = 0;
		this.state = 0;
	}
	
	public int id()
	{
		return this.id;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof TapeHead)
		{
			TapeHead c = (TapeHead) o;
			if (this.hashCode() == c.hashCode())
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
		return this.id;
	}
	
	/* Homework2 */
	public TapeField seizedField()
	{
		return this.field;
	}
	
	public void addKeys(int n)
	{
		this.keys += n;
	}
	
	public boolean moveRight()
	{
		TapeField fieldTmp = this.field;
		
		while((fieldTmp = fieldTmp.rightField()) != null)
		{
			if(fieldTmp.canSeize())
			{
				this.field.leave();
				this.field = fieldTmp;
				fieldTmp.seize(this);
				return true;
			}
			else if(fieldTmp.canBeOpen() && (this.keys > 0))
			{
				fieldTmp.open();
				this.keys--;
				this.field.leave();
				this.field = fieldTmp;
				fieldTmp.seize(this);
				return true;
			}
		}
		return false;
	}

	/* Homework3 */
	/*
	 * May take a step to the next field if possible.
	 */
	public boolean step()
	{		
		return this.makeStep(getNextField());
	}
	
	/*
	 * Depending on the head orientation, the method returns
	 * the correct next field in its way.
	 */
	private TapeField getNextField()
	{
		TapeField tmp = null;
		
		switch(this.state)
		{
			/* North orientation */
			case 0:
				tmp = this.field.topField();
				break;
			/* East orientation */
			case 1:
				tmp = this.field.rightField();
				break;
			/* South orientation */
			case 2:
				tmp = this.field.lowerField();
				break;
			/* West orientation */
			case 3:
				tmp = this.field.leftField();
				break;
		}
		
		return tmp;
	}
	
	/*
	 * Opens and seizes a gate or seizes an empty field
	 * or takes the key and seizes its field.
	 */
	private boolean makeStep(TapeField fieldTmp)
	{
		if(fieldTmp != null)
		{
			if(fieldTmp.canSeize())
				return seizeField(fieldTmp);
			/*else if(fieldTmp.canBeOpen() && (this.keys > 0))
				return false;
			else if(fieldTmp.canBeTaken())
				return false;*/
			else
				return false;
		}
		else
			return false;
	}
	
	/*
	 * The head moves to the new field by leaving
	 * the actual position and seizing the new field.
	 */
	public boolean seizeField(TapeField fieldTmp)
	{
		this.field.leave();
		this.field = fieldTmp;
		fieldTmp.seize(this);
		return true;
	}

	/*
	 * Rotates the head 90% left.
	 */
	public void left()
	{
		if(this.state == 0)
			this.state = 3;
		else
			this.state--;
	}
	
	/*
	 * Rotates the head 90% right.
	 */
	public void right()
	{
		if(this.state == 3)
			this.state = 0;
		else
			this.state++;
	}
	
	/*
	 * Adds a key and removes the object from the field.
	 */
	public boolean take()
	{
		TapeField tmp = getNextField();
		
		if(tmp.canBeTaken())
		{
			this.addKeys(1);
			tmp.obj = null;
			return true;
		}
		else
			return false;
	}
	
	/*
	 * Opens a gate.
	 */
	public boolean open()
	{
		TapeField fieldTmp = getNextField();
		
		if(fieldTmp.canBeOpen() && (this.keys > 0))
		{
			fieldTmp.open();
			this.keys--;
			return seizeField(fieldTmp);
		}
		else
			return false;
	}
	
	/*
	 * Prints the actual amount of keys possessed.
	 */
	public void keys()
	{
		System.out.println(this.keys);
	}
}
