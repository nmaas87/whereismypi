/* 
discoverySystem
DiscoveryClientThreadAgeing
betreibt das Ageing in der Hashtable / Datenbank des Discovery Systems:
Es laesst jeden Eintrag in dieser Hashtable discoveryMap alle WAIT_TIME_AGEING
Sekunden um die gleiche Zeit altern. Faellt das Alter/Age eines Eintrags
unter 0, so wird dieser Eintrag entfernt da der Client nicht rechtzeitig
wieder durch den DiscoveryClientThreadSocket erneuert wurde.
Mit diesem Alterungsmechanismus werden "verstorbene" Systeme erkannt und
verworfen.
*/
import java.io.*;
import java.util.*;
import javax.swing.*;

public class DiscoveryClientThreadAgeing extends Thread {
// Variable Declaration
private Enumeration<String> discoveryMapEnum;
private String node;
private Integer age;
private long timeAgeing;
       
public DiscoveryClientThreadAgeing() throws IOException {
  this("DiscoveryClientThreadAgeing");
}

public DiscoveryClientThreadAgeing(String name) throws IOException {
  super(name);    
}

public void run(){
  while (true){    
    if ((DiscoverySystem.getTime()-timeAgeing)>=DiscoverySystem.WAIT_TIME_AGEING)
    {    
      discoveryMapEnum = DiscoverySystem.discoveryMap.keys();
      //System.out.println("Discovery Client Ageing System");

      int gui_idx=0;
      DiscoverySystemGUI.listenModell.removeAllElements();
      
      while(discoveryMapEnum.hasMoreElements()) 
      {
        node = (String) discoveryMapEnum.nextElement();
        age = DiscoverySystem.discoveryMap.get(node).getAge();
        if (age > DiscoverySystem.WAIT_TIME_AGEING)
        {                   
          DiscoverySystem.discoveryMap.get(node).setAge(age-DiscoverySystem.WAIT_TIME_AGEING);
          System.out.println("Discovery Client Ageing System: Node " + node + " aged: " + (DiscoverySystem.discoveryMap.get(node).getAge()-DiscoverySystem.WAIT_TIME_AGEING)); 
          // Add to GUI List
          node = node + "   " + DiscoverySystem.discoveryMap.get(node).getNodeName();
          DiscoverySystemGUI.listenModell.add(gui_idx,node); 
          gui_idx++;
        } 
        else 
        {
          DiscoverySystem.discoveryMap.remove(node);
          System.out.println("Discovery Client Ageing System: Node " + node + " deprecated and deleted");       
          DiscoverySystemSerializer.saveHashtable();
        }                     
      }      
      timeAgeing=DiscoverySystem.getTime();   
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
