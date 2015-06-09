/* 
discoverySystem
WMPNodeBeacon
Das NodeBeacon Objekt wird vom Node Objekt abgeleitet
und ist das Objekt was auch vom DiscoveryServerThreadSocket
uebertragen wird. Es enthaelt die Daten des Node Objektes
sowie die IP des Systems und den Mode.
Als Mode sind folgende drin:
0 - hinzufuegen / update des Eintrags
1 - signoff / loeschen des Eintrags
--> ggf 2 fuer den umzug aber noch nicht umgesetzt
*/
public class WMPNodeBeacon extends WMPNode {

	private static final long serialVersionUID = 6925430186776874224L;
	private int   Mode;
	private String IP;
  private String MAC;
	
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

	protected synchronized String getMAC() {
		return MAC;
	}

	protected synchronized void setMAC(String mAC) {
		MAC = mAC;
	}

	public WMPNodeBeacon(int Mode, String IP, String MAC, String NodeName) {
		super(NodeName,MAC);
		this.Mode=Mode;
		this.IP=IP;
    this.MAC=MAC;
	}

	public WMPNodeBeacon(int Mode, String IP, String MAC, String NodeName, int Age) {
		super(NodeName,MAC,Age);
		this.Mode=Mode;
		this.IP=IP;
    this.MAC=MAC;    
	}
	
}
