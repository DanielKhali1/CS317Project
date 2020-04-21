package Game;
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
		this.setName(name);
	}

	@Override
	protected boolean isServer() 
	{
		return false;
	}

	@Override
	protected String getIP() 
	{
		return this.ip;
	}

	@Override
	protected int getPort() 
	{
		return this.port;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	

}
