package Game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server extends NetworkConnection
{

	private int port;
	String name;
	
//	ListenerThread listenerThread;

	public Server(int port, String name, Consumer<Serializable> onReceiveCallback) {
		super(onReceiveCallback);
		this.port = port;
		this.name = name;
	}

	@Override
	protected boolean isServer() {
		return true;
	}

	@Override
	protected String getIP() {
		return null;
	}

	@Override
	protected int getPort() {
		return this.port;
	}
	
//	public class ListenerThread extends Thread
//	{
//		private Socket socket;
//		private ObjectOutputStream out;
//		
//		
//		@Override
//		public void run()
//		{
//			try(ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
//					Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
//					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//					ObjectInputStream in = new ObjectInputStream(socket.getInputStream()) )
//			{
//				this.socket = socket;
//				this.out = out;
//				socket.setTcpNoDelay(true);
//				
//				
//			// infinite while loop reading in objects
//				while(true) {
//					Serializable data = (Serializable) in.readObject();
//					onRecieveCallback.accept(data);
//				}
//				
//			}
//			catch(Exception e)
//			{
//				onRecieveCallback.accept("Connection closed");
//			}
//		}
//	}
	
	
}
