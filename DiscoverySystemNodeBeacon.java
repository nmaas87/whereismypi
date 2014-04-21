/* 
discoverySystem
DiscoverySystemNodeBeacon
Das NodeBeacon Objekt wird vom Node Objekt abgeleitet
und ist das Objekt was auch vom DiscoveryServerThreadSocket
uebertragen wird. Es enthaelt die Daten des Node Objektes
sowie die IP des Systems und den Mode.
Als Mode sind folgende drin:
0 - hinzufuegen / update des Eintrags
1 - signoff / loeschen des Eintrags
--> ggf 2 fuer den umzug aber noch nicht umgesetzt
*/
public class DiscoverySystemNodeBeacon extends DiscoverySystemNode {

	private static final long serialVersionUID = 6925430186776874224L;
	private int   Mode;
	private String IP;
	
	protected synchronized int getMode() {
		return Mode;
	}

	protected synchronized void setMode(byte mode) {
		Mode = mode;
	}

	protected synchronized String getIP() {
		return IP;
	}

	protected synchronized void setIP(String iP) {
		IP = iP;
	}

	public DiscoverySystemNodeBeacon(int Mode, String IP, String NodeName) {
		super(NodeName);
		this.Mode=Mode;
		this.IP=IP;
	}

	public DiscoverySystemNodeBeacon(int Mode, String IP, String NodeName, int Age) {
		super(NodeName,Age);
		this.Mode=Mode;
		this.IP=IP;
	}
	
}
