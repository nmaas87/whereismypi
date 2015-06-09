/* 
discoverySystem
WMPServerThreadSocket
enthaelt das Server System: Tritt mit seinem Socket einer Multicast
Gruppe bei und ubertraget seine Daten in diese Gruppe.
Die Daten werden als Object WMPNodeBeacon uebertragen
WICHTIG: Der Socket brauch seinen eigenen Port PORT_MULTICAST_SERVER
und sendet von diesem Port an den PORT_MULTICAST_CLIENT in die Multicast Gruppe
ADDR_MULTICAST 
*/
import java.io.*;
import java.net.*;

public class WMPServerThreadSocket extends Thread {
// Variable Declaration
private DatagramSocket socket = null;
private long timeMulticast;
private int Mode = 0;

public WMPServerThreadSocket() throws IOException {
  this("WMPServerThreadSocket");
}

public WMPServerThreadSocket(String name) throws IOException {
  super(name);
  socket = new DatagramSocket(WMP.PORT_MULTICAST_SERVER);
}

public void run() {
  while (true) { 
    if (((WMP.getTime()-timeMulticast)>=WMP.WAIT_TIME_MULTICAST) || (WMP.shutdown))
    {
      try
      {
        // If Shutdown Sequence is initialized, set Mode to 1, which means "Goodbye all my friend nodes, please forget about me!"
    	// else, Mode 0 - which is the new / update Mode
    	if (WMP.shutdown)
    	{ 
    		Mode=1; 
    	} 
    	else 
    	{
    		Mode=0; 
    	}
    	// Create Object        
       	WMPNodeBeacon Beacon = new WMPNodeBeacon(Mode,WMP.getLocalIP(),WMP.getLocalMAC(),WMP.getLocalName(),WMP.DEFAULT_AGE);          	
    	// Serialize to a byte array
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream); 
        oo.writeObject(Beacon);
        oo.close();
        byte[] serializedMessage = bStream.toByteArray();       
	    //System.out.println("LENGTH:" + serializedMessage.length);
        // Send it
        InetAddress group = InetAddress.getByName(WMP.ADDR_MULTICAST);
        DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, group, WMP.PORT_MULTICAST_CLIENT);
        socket.send(packet);        
        System.out.println("WMP Server Socket System: Sent " + Beacon.getIP() + " / " + Beacon.getMAC() + " / " + Beacon.getNodeName() + " to Multicast Group " + group);
      } 
      catch (IOException e) 
      {
        e.printStackTrace();
      }           
      //socket.close();
      timeMulticast=WMP.getTime(); 
    }
    else
    {   
      try 
      {
        Thread.sleep(250);
      } 
      catch (InterruptedException e) 
      { 
        // well - do something?!
      }   
    }
  }
}





}
