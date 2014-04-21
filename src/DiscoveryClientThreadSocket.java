/* 
discoverySystem
DiscoveryClientThreadSocket
enthaelt den Socket welcher die Daten von Mitgliedern der Multicast
Gruppe empfaengt. Je nachdem ob dieses Mitglied schon in der 
Hashtable enthalten ist, wird sein Alter entweder auf DEFAULT_AGE zuruckgesetzt
oder er der Liste mit dem Alter DEFAULT_AGE neu hinzugefuegt 
WICHTIG: Der Socket brauch seinen eigenen Port PORT_MULTICAST_CLIENT
und empfaengt damit Daten aus der  Multicast Gruppe ADDR_MULTICAST
*/
import java.io.*;
import java.net.*;

public class DiscoveryClientThreadSocket extends Thread {
// Variable Declaration
private MulticastSocket socket = null;
private InetAddress address = null;
private DatagramPacket packet = null;
private Integer age = null;
       
public DiscoveryClientThreadSocket() throws IOException {
  this("DiscoveryClientThreadSocket");
}

public DiscoveryClientThreadSocket(String name) throws IOException {
  super(name);
  socket = new MulticastSocket(DiscoverySystem.PORT_MULTICAST_CLIENT);
  address = InetAddress.getByName(DiscoverySystem.ADDR_MULTICAST);
  socket.joinGroup(address);        
}
    
public void run() { 
  while (true){	    
    try {
     // TODO BOOM 512 or less? 240 is minimum!    	
     byte[] buf = new byte[348];
      packet = new DatagramPacket(buf, buf.length);
      socket.receive(packet);
        
	      try {
	    	ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
	    	DiscoverySystemNodeBeacon received = null;
			try {
				received = (DiscoverySystemNodeBeacon) iStream.readObject();
			} catch (ClassNotFoundException e) {
				// On Class Not Found - should never happen
				System.out.println("Discovery Client Socket System: Incorrect Class of recieved Object");
				//e.printStackTrace();
			}
	    	iStream.close();   	
	
	      if (received.getMode()==0)
	      { // New Node / Update Mode, please add me!
	    	  // Read out IP Address and enter into HashTable with Aging System
	          // check whether already known or not
		      if (DiscoverySystem.discoveryMap.containsKey(received.getIP()))
		      {
		      	age = DiscoverySystem.discoveryMap.get(received.getIP()).getAge(); 
		      	DiscoverySystem.discoveryMap.get(received.getIP()).setAge(DiscoverySystem.DEFAULT_AGE);  
		    	System.out.println("Discovery Client Socket System: Node " + received.getIP() + " / " + received.getNodeName() + " already known (Age:" + age + ") New: "+ DiscoverySystem.DEFAULT_AGE );
		      }
		      else
		      {
		    	DiscoverySystemNode tmpNode = (DiscoverySystemNodeBeacon) received;
		    	tmpNode.setAge(DiscoverySystem.DEFAULT_AGE);
		    	DiscoverySystem.discoveryMap.put(received.getIP(), tmpNode); 
		    	System.out.println("Discovery Client Socket System: Node " + received.getIP() + " / " + received.getNodeName() + " not known, added with Age " + DiscoverySystem.DEFAULT_AGE );                            
		        DiscoverySystemSerializer.saveHashtable();
		      }
	      }
	      else if (received.getMode()==1)
	      { // Signoff Mode, please delete me!
		      if (DiscoverySystem.discoveryMap.containsKey(received.getIP()))
		      {
		        DiscoverySystem.discoveryMap.remove(received.getIP());
		        System.out.println("Discovery Client Socket System: Node " + received.getIP() + " / " + received.getNodeName() +  " signed off from system -> deleted");       
		        DiscoverySystemSerializer.saveHashtable(); 
		      }    	  
	      }
	      
	      
	      } catch (StreamCorruptedException sce) {
	    	  InetAddress errorAddress = packet.getAddress();
	    	  System.out.println("Discovery Client Socket System: Recieved corrupted Packet from Node " + errorAddress.toString().substring(1));	    	  
	      }      
     
      
    }
    catch (IOException e) 
    {
      e.printStackTrace();
    }  
  }
	/*
  socket.leaveGroup(address);
	socket.close();
  */
}

}
