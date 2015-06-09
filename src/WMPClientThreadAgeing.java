/* 
discoverySystem
WMPClientThreadAgeing
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

public class WMPClientThreadAgeing extends Thread {
// Variable Declaration
private Enumeration<String> discoveryMapEnum;
private String node;
private Integer age;
private long timeAgeing;
       
public WMPClientThreadAgeing() throws IOException {
  this("WMPClientThreadAgeing");
}

public WMPClientThreadAgeing(String name) throws IOException {
  super(name);    
}

public void run(){
  while (true){    
    if ((WMP.getTime()-timeAgeing)>=WMP.WAIT_TIME_AGEING)
    {    
      discoveryMapEnum = WMP.discoveryMap.keys();
      //System.out.println("Discovery Client Ageing System");

      int gui_idx=0;
      WMPGUI.listenModell.removeAllElements();
      
      while(discoveryMapEnum.hasMoreElements()) 
      {
        node = (String) discoveryMapEnum.nextElement();
        age = WMP.discoveryMap.get(node).getAge();
        if (age > WMP.WAIT_TIME_AGEING)
        {                   
          WMP.discoveryMap.get(node).setAge(age-WMP.WAIT_TIME_AGEING);
          System.out.println("WMP Client Ageing System: Node " + node + " aged: " + (WMP.discoveryMap.get(node).getAge()-WMP.WAIT_TIME_AGEING)); 
          // Add to GUI List
          node = node + "   " + WMP.discoveryMap.get(node).getNodeName() + "   " + WMP.discoveryMap.get(node).getNodeMAC();
          WMPGUI.listenModell.add(gui_idx,node); 
          gui_idx++;
        } 
        else 
        {
          WMP.discoveryMap.remove(node);
          System.out.println("WMP Client Ageing System: Node " + node + " deprecated and deleted");       
          WMPSerializer.saveHashtable();
        }                     
      }      
      timeAgeing=WMP.getTime();   
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
