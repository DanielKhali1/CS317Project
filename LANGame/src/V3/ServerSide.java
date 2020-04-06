package V3;

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList; 
  
public class ServerSide 
{
	ArrayList<Clients> clients = new ArrayList<Clients>();
	
    public static void main(String[] args) throws IOException 
    { 
    	ServerSide s = new ServerSide();
        // Step 1 : Create a socket to listen at port 1234 
        DatagramSocket ds = new DatagramSocket(1234); 
        byte[] receive = new byte[65535]; 
  
        DatagramPacket DpReceive = null; 
        
        
        while (true) 
        { 
  
            // Step 2 : create a DatgramPacket to receive the data. 
            DpReceive = new DatagramPacket(receive, receive.length); 
  
            // Step 3 : revieve the data in byte buffer. 
            ds.receive(DpReceive);
            
            if(s.clients.size() < 1 && data(receive).toString().split(" ")[1].equals("connected"))
            {
            	s.clients.add(s.new Clients(data(receive).toString().split(" ")[0], ds.getLocalSocketAddress()));
            }

            
            
  
            System.out.println(data(receive) + " " +  DpReceive.getAddress());
            DatagramPacket DpSend = new DatagramPacket(data(receive).toString().getBytes(), data(receive).toString().getBytes().length, DpReceive.getAddress(), 1234); 
            ds.send(DpSend);
  
            // Exit the server if the client sends "bye" 
            if (data(receive).toString().equals("bye")) 
            { 
                System.out.println("Client sent bye.....EXITING"); 
                break; 
            } 
  
            // Clear the buffer after every message. 
            receive = new byte[65535]; 
        } 
    } 
  
    // A utility method to convert the byte array 
    // data into a string representation. 
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    } 
    
    public class Clients
    {
    	String name;
    	SocketAddress address;
    	
    	public Clients(String name, SocketAddress address)
    	{
    		this.name = name;
    		this.address = address;
    	}
    }
} 