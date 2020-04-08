package V5;

public class Player 
{
	private Vector pos;
	private Vector vel;
	private Vector acc;
	
	private double width, height;
	private double gravity;
	
	private double health;
	
	public Player(Vector pos, double w, double h, double gravity)
	{
		this.setPos(pos.clone());
		setVel(new Vector());
		setAcc(new Vector());
		
		this.setWidth(w);
		this.setHeight(h);
		this.setGravity(gravity);
	}
	
	public void move(Vector v)
	{
		getPos().add(v);
	}
	
	public void addForce(Vector v)
	{
		getVel().add(v);
	}
	
	public void update()
	{
		//gravity.
		setAcc(new Vector(0, getGravity()));
		getVel().add(getAcc());
		getPos().add(getVel());
	}

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public Vector getVel() {
		return vel;
	}

	public void setVel(Vector vel) {
		this.vel = vel;
	}

	public Vector getAcc() {
		return acc;
	}

	public void setAcc(Vector acc) {
		this.acc = acc;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	
	

}
