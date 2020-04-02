package V2;
import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection{

	private String ip;
	private int port;
	private String name;
	
	
	public Client(String ip, int port, String name, Consumer<Serializable> onReceiveCallback) {
		super(onReceiveCallback);
		this.ip = ip;
		this.port = port;
		this.name = name;
	}

	@Override
	protected boolean isServer() {
		return false;
	}

	@Override
	protected String getIP() {
		// TODO Auto-generated method stub
		return this.ip;
	}

	@Override
	protected int getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}
	
	

}
