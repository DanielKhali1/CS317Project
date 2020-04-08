package V5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread
{
	/**
	 * The server listens on a single port to establish client connections.
	 * 
	 */	
	ServerSocket s;
	Socket socket;
	boolean listnerstop = false;
	
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
	
	
	
	public void disconnect()
	{
		listnerstop = true;
		try 
		{
			s.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		for(int i = 0; i < workers.size(); i++)
		{
			workers.get(i).disconnect();
		}
		System.out.println("Killing Server Thread");
	}
	@Override
	public void run() 
	{
		this.setName("Server Listener");

		while(!listnerstop)
		{
			System.out.println();
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
		
		boolean stop = false;
		
		public Worker(Socket socket)
		{
			System.out.println("spawned new connection");
			this.socket = socket;
			
		}
		public void disconnect()
		{
			stop = true;
		}
		@Override
		public void run() {
			this.setName("Server Worker");

			try
			{
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
				this.out = out;
				socket.setTcpNoDelay(true);
				
				
				while(!stop)
				{
					try
					{
						//blocking call 
						Serializable data = (Serializable) in.readObject();
						//System.out.println("Server received " + data);
						send(data);
					}
					catch(SocketException e)
					{
						System.out.println("worker thread has been terminated");
						disconnect();
					}
					catch(OptionalDataException e)
					{
						System.out.println("stream doing weird shit in client");
						disconnect();

					}
				}

			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	

}
