package Game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet 
{
	private Vector pos;
	private double speed;
	private boolean direction;
	private Circle display;
	private double id;
	
	public Bullet(Vector pos, double speed, boolean direction)
	{
		
		this.setId(Math.random());
		setDisplay(new Circle(10));
		getDisplay().setFill(Color.YELLOW);
		getDisplay().setStroke(Color.ORANGE);
		this.setPos(pos);
		this.setSpeed(speed);
		this.setDirection(direction);
		
		getDisplay().setLayoutX(this.getPos().x);
		getDisplay().setLayoutY(this.getPos().y);
	}
	
	public Bullet(Vector pos, double speed, boolean direction, double id)
	{
		
		this.setId(id);
		setDisplay(new Circle(10));
		getDisplay().setFill(Color.YELLOW);
		getDisplay().setStroke(Color.ORANGE);
		this.setPos(pos);
		this.setSpeed(speed);
		this.setDirection(direction);
		
		getDisplay().setLayoutX(this.getPos().x);
		getDisplay().setLayoutY(this.getPos().y);
	}
	
	public void update()
	{
		//direction true = right
		//direction false = left
		if(isDirection())
			this.getPos().x += getSpeed();
		else
			this.getPos().x -= getSpeed();
		
		
		getDisplay().setLayoutX(this.getPos().x);
		getDisplay().setLayoutY(this.getPos().y);
	}

	public Circle getDisplay() {
		return display;
	}

	public void setDisplay(Circle display) {
		this.display = display;
	}

	public boolean isDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

}
