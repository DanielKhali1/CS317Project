//package V3;

//Java program to illustrate Client side 
//Implementation using DatagramSocket 
import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.util.Scanner; 

public class ClientSide 
{ 
 public static void main(String args[]) throws IOException 
 { 
     Scanner sc = new Scanner(System.in); 
     String name;
     
     System.out.println("what is your name?\n:");
     name = sc.nextLine();
     // Step 1:Create the socket object for 
     // carrying the data. 
     DatagramSocket ds = new DatagramSocket(); 

     InetAddress ip = InetAddress.getLocalHost(); 
     
     byte[] receive = new byte[65535]; 
     
     DatagramPacket DpReceive = null;
     
     //initial connection request.
     byte init[] = (name + " connected").getBytes();
     //send a packet to the server telling ther server that you want to be a new client
     ds.send(new DatagramPacket(init, init.length, ip, 1234));
     

     byte buf[] = null; 
     

     // loop while user not enters "bye" 
     while (true) 
     { 
         String inp = name + ": " + sc.nextLine(); 

         // convert the String input into the byte array. 
         buf = inp.getBytes(); 

         // Step 2 : Create the datagramPacket for sending 
         // the data. 
         DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234); 

         // Step 3 : invoke the send call to actually send 
         // the data. 
         ds.send(DpSend); 
         
         DpReceive = new DatagramPacket(receive, receive.length); 
         System.out.println(data(receive).toString());


         // break the loop if user enters "bye" 
         if (inp.equals("bye")) 
             break; 
     } 
 }
 
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
} 
