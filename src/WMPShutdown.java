/* 
discoverySystem
WMPShutdown
Die Shutdown Klasse richtet eine sogenannte "ShutdownHook" ein.
Damit kann das OnExit von Java abgefangen und letzte Befehle ausgefuehrt werden.
In diesem Fall wird es genutzt um das Signoff an die umgegebenden Clients zu senden.

Im WMP wird der ShutdownHook einmalig registriert, danach
wird er beim Abschalten der Software, z.B. bei STRG+C ausgefuehrt.
Das System gibt die Nachricht Node shutting down aus und setzt
die Variable SHUTDOWN auf wahr.
Dadurch wird im DiscoveryServerThreadSocket die Wartezeit fuer die Aussendung des
Keepalives vollstaendig ausgesetzt und der Mode von 0 (New Node / Update Node) auf
1 geaendert (Signoff) - durch das Thread.sleep hier hat dann der Server Socket
ca 2 Sekunden Zeit das Netzwerk mit Signoff Nachrichten zu fluten. Danach
schaltet sich die Software endgueltig ab.
Die DiscoveryClientThreadSocket der anderen Nodes empfangen die Nachricht,
wechseln in den entsprechenden Mode 1 und loeschen - nachdem sie das vorhandensein
des Eintrags in der Hashtable geprueft haben, den Eintrag raus und speichern
die Hashtable
*/

public class WMPShutdown {

 public void attachShutDownHook(){
 Runtime.getRuntime().addShutdownHook(new Thread() {
   @Override
   public void run() {
  	System.out.println("WMPShutdown: Node shutting down");
  	WMP.shutdown=true;
  	try 
  	{
  		if (WMP.server_mode == true)
      {
        Thread.sleep(2000);
      }
  	} 
  	catch (InterruptedException e) 
  	{ 
  		// well - do something?!
    }   
   }
  });
  System.out.println("WMPShutdown: Shut Down Hook Attached.");
 }
		
}

	

