package V4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
	/**
	 * The server listens on a single port to establish client connections.
	 * 
	 */	
	ServerSocket s;
	Socket socket;
	
	ArrayList<Worker> workers = new ArrayList<Worker>();
	
	public Server()
	{
		//start the listener thread.
		try 
		{
			//set port on 2525
			s = new ServerSocket(2525);
			
		} 
		catch (IOException e) 
		{
			System.out.println("code died on the server");
			try 
			{
				s.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			try 
			{
				//adding workers
				workers.add(new Worker(s.accept()));
				workers.get(workers.size()-1).start();
			}
			catch (Exception e) 
			{
				System.out.println("no connection");
			}
		}
	}
	
	public void send(Serializable data) throws Exception
	{
		for(int i = 0; i < workers.size(); i++)
		{
			workers.get(i).out.writeObject(data);
		}
	}
	
	public class Worker extends Thread
	{
		Socket socket;

		ObjectOutputStream out;
		
		public Worker(Socket socket)
		{
			System.out.println("spawned new connection");
			this.socket = socket;
			
		}
		
		@Override
		public void run() {
			
			try
			{
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
				this.out = out;
				socket.setTcpNoDelay(true);
				
				
				while(true)
				{
					//blocking call 
					Serializable data = (Serializable) in.readObject();
					System.out.println("Server received " + data);
					send(data);
				}

			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	

}
