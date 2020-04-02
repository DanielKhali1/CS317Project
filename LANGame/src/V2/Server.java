package V2;
import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection
{

	private int port;
	String name;

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
	
	
}
