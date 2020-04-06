package V4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread
{
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	private Consumer<Serializable> onRecieveCallback;

	
	public Client(Consumer<Serializable> onRecieveCallback )
	{
		this.onRecieveCallback = onRecieveCallback;
		try 
		{
			socket = new Socket(InetAddress.getLocalHost(), 2525);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			this.out = out;

		} 
		catch (Exception e) 
		{
			System.out.println("I DONT WORK");
		}
	}
	
	public void send(Serializable data)
	{
		try 
		{
			System.out.println("Client sending " + data);
			out.writeObject(data);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() 
	{
		try
		{

			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			this.in = in;
			socket.setTcpNoDelay(true);
			
			
			while(true)
			{
				
				Serializable data = (Serializable) in.readObject();
				System.out.println("Client received " + data);
				onRecieveCallback.accept(data);
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	

}
