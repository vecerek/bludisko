package ija.homework3.tape;

public class TapeHead {
	protected int id;
	protected TapeField field;
	protected int keys = 0;
	
	public TapeHead(int id)
	{
		this.id = id;
	}
	
	public TapeHead(int id, TapeField f)
	{
		this.id = id;
		this.field = f;
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
	public void step()
	{
		return;
	}

	public void left()
	{
		return;
	}

	public void right()
	{
		return;
	}

	public void take()
	{
		return;
	}

	public int keys()
	{
		return 0;
	}
}
