package V2;

public class Vector 
{
	double x,y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public void add(Vector v)
	{
		this.x += v.x;
		this.y += v.y;
	}
	
	public void mult(double c)
	{
		this.x *= c;
		this.y *= c;
	}

	public void div(double c)
	{
		this.x /= c;
		this.y /= c;
	}
	
	public Vector clone()
	{
		return new Vector(this.x, this.y);
	}
	
	
	public void normalize()
	{
		double magnitude = Math.sqrt(x*x + y*y);
		
		div(magnitude);
	}

}
