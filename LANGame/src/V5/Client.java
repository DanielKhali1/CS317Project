package V5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class Client extends Thread
{
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;

	boolean stop = false;
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
	
	public void disconnect()
	{
		stop = true;
	}
	
	public void send(Serializable data)
	{
		try 
		{
			//System.out.println("Client sending " + data);
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
		this.setName("Client");
		try
		{

			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			this.in = in;
			socket.setTcpNoDelay(true);
			
			
			while(!stop)
			{
				try {
					
				Serializable data = (Serializable) in.readObject();
				//System.out.println("Client received " + data);
				onRecieveCallback.accept(data);
				}
				catch(SocketException e)
				{
					System.out.println("Client thread has been terminated");
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
