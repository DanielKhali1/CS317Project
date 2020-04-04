import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server extends NetworkConnection
{

	private int port;
	private ArrayList<String> messages = new ArrayList<String>();

	public Server(int port, Consumer<Serializable> onReceiveCallback) {
		super(onReceiveCallback);
		this.port = port;
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
	
	public void addToMessages(String message)
	{
		messages.add(message);
	}
	
	public String getMessages()
	{
		String history = "";
		for(int i = 0; i < messages.size(); i++)
		{
			history += messages.get(i) + "\n";
		}
		return history;
	}

}