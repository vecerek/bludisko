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
	 * Checks, if the head can take a step.
	 * Returns true and takes the step, if the next field is seizable.
	 * Otherwise returns false.
	 */
	public boolean step()
	{
		TapeField fieldTmp = null;
		
		switch(this.state)
		{
			/* North orientation */
			case 0:
				fieldTmp = this.field.topField();
				break;
			/* East orientation */
			case 1:
				fieldTmp = this.field.rightField();
				break;
			/* South orientation */
			case 2:
				fieldTmp = this.field.lowerField();
				break;
			/* West orientation */
			case 3:
				fieldTmp = this.field.leftField();
				break;
		}
		
		return this.makeStep(fieldTmp);
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
			{
				return seizeField(fieldTmp);
			}
			else if(fieldTmp.canBeOpen() && (this.keys > 0))
			{
				fieldTmp.open();
				this.keys--;
				return seizeField(fieldTmp);
			}
			else if(fieldTmp.canBeTaken())
			{
				this.take(fieldTmp);
				return seizeField(fieldTmp);
			}
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
	public void take(TapeField fieldTmp)
	{
		this.addKeys(1);
		fieldTmp.obj = null;
		return;
	}
	
	/*
	 * Prints the actual amount of keys possessed.
	 */
	public void keys()
	{
		System.out.println(this.keys);
	}
}
