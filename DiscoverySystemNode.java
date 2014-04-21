/* 
discoverySystem
DiscoverySystemNode
Das Node Objekt enthaelt die wichtigsten Daten eines Objektes,
welche auch in der Hashtable gespeichert werden.
Die enthaelt den Node Namen und das Alter
des Eintrags
*/
import java.io.Serializable;

public class DiscoverySystemNode implements Serializable {
	private static final long serialVersionUID = -5763722753408552522L;
	
	private String NodeName;
    private int Age;

    public DiscoverySystemNode(String NodeName, int Age)
    {
      this.NodeName = NodeName;
      this.Age = Age;
    	//super(i);
    }

    public DiscoverySystemNode(String NodeName)
    {
    	this.NodeName = NodeName;
    }
    
    public DiscoverySystemNode(int Age)
    {
      this.Age = Age;
    }
    
	protected synchronized String getNodeName() {
		return NodeName;
	}
	protected synchronized void setNodeName(String nodeName) {
		NodeName = nodeName;
	}
	protected synchronized int getAge() {
		return Age;
	}
	protected synchronized void setAge(int age) {
		Age = age;
	}
    
	@Override
	public String toString() {
		return "DiscoverySystemNode [NodeName=" + NodeName + ", Age=" + Age + "]";
	}
	
}
