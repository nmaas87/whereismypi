/* 
discoverySystem alias Where is my Pi
Nico Maas
mail@nico-maas.de
26.11.2013 - 21.04.2014
Diese Klasse erzeugt die 3 noetigen Threads fuer den Betrieb des discoverySystems
WMPServerThreadSocket: Sendet Daten an die Multicast Gruppe
WMPClientThreadSocket: Empfaengt Daten von der Multicast Gruppe
WMPClientThreadAgeing: Laesst Eintrage in der lokalen Hashtable altern/sterben

WARNUNG zum Aktiven Warten:
Ich habe im Moment auf sleep verzichet und ein aktives Wartesystem eingebaut.
Am Anfang wird gecheckt mittels der aktuellen Systemzeit in Sekunden,
ob der Thread wieder arbeiten darf:
if ((WMP.getTime()-timeAgeing)>=WMP.WAIT_TIME_AGEING)
Falls ja, geht er in die Schleife und setzt am Ende die lokale Variable
timeAgeing=WMP.getTime();
wieder auf die "letzte Ausfuehrungszeit"
es wird immer gegen die Differenz der System spezifischen Konstante 
(z.B. WAIT_TIME_AGEING) geprueft. Dadurch blockiert niemals ein Thread und
kein Paket geht verloren / Chance sinkt gewaltig. Leider ist die CPU
damit zu 100 Prozent ausgelastet. Daher werd ich ggf doch wieder zu sleep
oder ahenlichem zurueck gehen oder eine Mittelloesung bilden.

(Diese Technik hier ist - auch wenn es bescheuert erscheint - hocheffizient
und stammt aus dem Mikrocontroller Bereich. Damit kann man praktisch FAST
ein Multihread System realisieren und en Controller FAST blockierfrei betreiben.
Bei denen macht es aber auch nichts aus dass sie dann komplett ausgelastet sind
;) - Ich sollte wieder (NICHT ARM7!) Mikrocontroller programmieren und das
sein lassen... ;) )

2. WARNUNG
Inzwischen habe ich eine Hybrid Loesung aus thread.sleep und dem Aktiven
Warten System gebaut. Thread Sleep legt den Thread immer nur fuer 250 ms
schlafend, danach checkt das aktive System wieder nach. Dadurch hat man
eine geringe Systemauslastung und gleichzeitig eine hohe Agilitaet im
Code. Ich denke das ist ein guter Kompromiss. Falls einer bessere Ideen
hat kann man das natuerlich spaeter einbauen :).
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class WMP {
// Constants
protected final static String  ADDR_MULTICAST="228.1.1.1";
protected final static Integer PORT_MULTICAST_SERVER=50000;
protected final static Integer PORT_MULTICAST_CLIENT=50001;
protected final static Integer WAIT_TIME_MULTICAST=10;
protected final static Integer WAIT_TIME_AGEING=5;
protected final static Integer DEFAULT_AGE=30;
protected final static String  HASHTABLE_FILE="WMPHashtable.ser";
// Variables
protected static volatile boolean shutdown = false;
protected static volatile boolean server_mode = false;
// Hashtable
protected static Hashtable<String, WMPNode> discoveryMap = new Hashtable<String, WMPNode>();
        
public static void main(String[] args) throws java.io.IOException {

  if ((args.length > 0) && (args[0].equals("server")))
  {
    server_mode = true;
    System.out.println("Headless Server Mode");
    WMPShutdown shutdownHook = new WMPShutdown();
    shutdownHook.attachShutDownHook();
    new WMPServerThreadSocket().start();
  }
  else
  {
    System.out.println("GUI Client Mode");
    WMPShutdown shutdownHook = new WMPShutdown();
    shutdownHook.attachShutDownHook();
    WMPSerializer.loadHashtable();
    //new WMPServerThreadSocket().start();
    new WMPClientThreadSocket().start();
    new WMPClientThreadAgeing().start();
    new WMPGUI().start();
  }
}

protected static long getTime(){
  return (System.currentTimeMillis()/1000);
}

protected static String getLocalIP() throws IOException{          
  // get the right IPv4 Address - even from an Linux Interface                
	try
  {
    Enumeration<NetworkInterface> netInter = NetworkInterface.getNetworkInterfaces();
    // get Interfaces
    while ( netInter.hasMoreElements() )
    {
      NetworkInterface ni = netInter.nextElement();
      // iterate through Interfaces
      if ( !ni.isLoopback() && !ni.isVirtual() && ni.isUp() )
      // only work on Interfaces which are NOT Loopbacks, Virtual or Down Intefaces 
      {
        for ( InetAddress iaddress : Collections.list(ni.getInetAddresses()) )
        { // get the IP Adresses of those Interfaces      
          if (iaddress instanceof Inet4Address)
          { // only work with the IPv4 Adresses of those Interfaces
        	return iaddress.getHostAddress();
          }     
        }    
      } 
    }
  } 
  catch (IOException e) 
  {
    e.printStackTrace();
  }
    return "0.0.0.0";
  	// only return if everything fails
    // TODO Catch that error
}

protected static String getLocalName() throws IOException{          
	  // get the right IPv4 Address - even from an Linux Interface                
		try
	  {
	    Enumeration<NetworkInterface> netInter = NetworkInterface.getNetworkInterfaces();
	    // get Interfaces
	    while ( netInter.hasMoreElements() )
	    {
	      NetworkInterface ni = netInter.nextElement();
	      // iterate through Interfaces
	      if ( !ni.isLoopback() && !ni.isVirtual() && ni.isUp() )
	      // only work on Interfaces which are NOT Loopbacks, Virtual or Down Intefaces 
	      {
	        for ( InetAddress iaddress : Collections.list(ni.getInetAddresses()) )
	        { // get the IP Adresses of those Interfaces      
	          if (iaddress instanceof Inet4Address)
	          { // only work with the IPv4 Adresses of those Interfaces
	        	return iaddress.getHostName();
	          }     
	        }    
	      } 
	    }
	  } 
	  catch (IOException e) 
	  {
	    e.printStackTrace();
	  }
	    return "NoName";
	  	// only return if everything fails
	    // TODO Catch that error
	}

}
