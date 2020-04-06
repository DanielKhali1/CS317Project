package V4;

public class Main 
{
	
	
	
	Server s;
	Client c, c1,c2, c3;
	
	public static void main(String[] args) 
	{
		Main m = new Main();
		m.s = new Server();
//		m.c = new Client();
//		m.c1 = new Client();
//		m.c2 = new Client();
//		m.c3 = new Client();

		
		m.s.start();
		m.c.start();
		m.c1.start();
		m.c2.start();
		m.c3.start();
	
		
		
		try
		{
			m.c1.send("heck");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}

}
