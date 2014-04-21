/* 
discoverySystem
DiscoverySystemSerializer
This class contains the serialization/deserialization methods for the hashtable.
It does save the hashtable every time a new node is added or an old one is deleted.
On startup, this hashtable is loaded so that the node starts with an prepopulated
hashtable
*/
import java.util.*;
import java.io.*;

public class DiscoverySystemSerializer {

	protected static void saveHashtable(){
		System.out.println("Discovery System Serializer: Save Hashtable");
        try{
			//System.out.println("Creating File/Object output stream...");
			FileOutputStream fileOut = new FileOutputStream(DiscoverySystem.HASHTABLE_FILE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			//System.out.println("Writing Hashtable Object...");
			out.writeObject(DiscoverySystem.discoveryMap);
			//System.out.println("Closing all output streams...\n");
            out.close();
            fileOut.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("unchecked")
	protected static void loadHashtable(){
		System.out.println("Discovery System Serializer: Load Hashtable");
		try{
			//System.out.println("Creating File/Object input stream...");
			FileInputStream fileIn = new FileInputStream(DiscoverySystem.HASHTABLE_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
			//System.out.println("Loading Hashtable Object...");
			DiscoverySystem.discoveryMap = (Hashtable<String, DiscoverySystemNode>)in.readObject();
			//System.out.println("Closing all input streams...\n");
            in.close();
            fileIn.close();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		for(Enumeration<String> e = DiscoverySystem.discoveryMap.keys(); e.hasMoreElements();){
			// load elements in hashtable
			String node = e.nextElement();
			// modify age to default
			DiscoverySystem.discoveryMap.get(node).setAge(DiscoverySystem.DEFAULT_AGE);
			// print loaded elements
			System.out.println("Discovery System Serializer: Loaded Element (" + node + ") = " + DiscoverySystem.discoveryMap.get(node));
		}
	}
    
}
