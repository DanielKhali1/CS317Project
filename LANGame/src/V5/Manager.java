package V5;
import java.util.ArrayList;

public class Manager 
{
	private final double PLAYERJUMPFORCE = 20;
	private final double GRAVITY = 1;
	private final double PLAYERMOVESPEED = 10;
	
	
	Player player;
	
	boolean jump = false;
	
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	public Manager(double width, double height)
	{
		player = new Player(new Vector(width/2, height/2), 50, 50, GRAVITY);
	}
	
	public void addPlatform(double x, double y, double w, double h)
	{
		platforms.add(new Platform(new Vector(x, y), new Vector(w, h)));
	}
	
	
	public void update()
	{
		player.update();
		
		
		if(player.getVel().y >= 0)
		{
			for(int i = 0; i < platforms.size(); i++)
			{
				if (		player.getPos().x < platforms.get(i).getPos().x + platforms.get(i).getSize().x &&
						player.getPos().x + player.getWidth() > platforms.get(i).getPos().x &&
						player.getPos().y < platforms.get(i).getPos().y + platforms.get(i).getSize().y &&
						player.getPos().y + player.getHeight() > platforms.get(i).getPos().y) 
				{
					jump = false;
					player.getAcc().mult(0);
					player.getVel().mult(0);
					player.setPos(new Vector(player.getPos().x, platforms.get(i).getPos().y - player.getHeight()));
				}
			}
		}
		else if(player.getVel().y < 0)
		{
			for(int i = 0; i < platforms.size(); i++)
			{
				if (		player.getPos().x < platforms.get(i).getPos().x + platforms.get(i).getSize().x &&
						player.getPos().x + player.getWidth() > platforms.get(i).getPos().x &&
						player.getPos().y < platforms.get(i).getPos().y + platforms.get(i).getSize().y &&
						player.getPos().y + player.getHeight() > platforms.get(i).getPos().y) 
				{
					player.getAcc().mult(0);
					player.getVel().mult(0);
					player.setPos(new Vector(player.getPos().x, platforms.get(i).getPos().y + player.getHeight()));
				}
			}
		}
	}
	
	public void playerJump()
	{
		if(!jump)
		{
			jump = true;
			player.setVel(new Vector());
			player.addForce(new Vector(0, -PLAYERJUMPFORCE));
		}
	}
	public void playerMoveRight()
	{
		player.move(new Vector(PLAYERMOVESPEED, 0));
		
		for(int i = 0; i < platforms.size(); i++)
		{
			if (		player.getPos().x < platforms.get(i).getPos().x + platforms.get(i).getSize().x &&
					player.getPos().x + player.getWidth() > platforms.get(i).getPos().x &&
					player.getPos().y < platforms.get(i).getPos().y + platforms.get(i).getSize().y &&
					player.getPos().y + player.getHeight() > platforms.get(i).getPos().y) 
				{
					player.setPos(new Vector(platforms.get(i).getPos().x - player.getWidth(), player.getPos().y));
				}
			}
	}
	public void playerMoveLeft()
	{
		player.move(new Vector(-PLAYERMOVESPEED, 0));
		
		for(int i = 0; i < platforms.size(); i++)
		{
			if (		player.getPos().x < platforms.get(i).getPos().x + platforms.get(i).getSize().x &&
					player.getPos().x + player.getWidth() > platforms.get(i).getPos().x &&
					player.getPos().y < platforms.get(i).getPos().y + platforms.get(i).getSize().y &&
					player.getPos().y + player.getHeight() > platforms.get(i).getPos().y) 
				{
					player.setPos(new Vector(platforms.get(i).getSize().x + platforms.get(i).getPos().x, player.getPos().y));
				}
			}
	}

}
