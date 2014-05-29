/* 
discoverySystem
WMPSerializer
This class contains the serialization/deserialization methods for the hashtable.
It does save the hashtable every time a new node is added or an old one is deleted.
On startup, this hashtable is loaded so that the node starts with an prepopulated
hashtable
*/
import java.util.*;
import java.io.*;

public class WMPSerializer {

	protected static void saveHashtable(){
		System.out.println("WMPSerializer: Save Hashtable");
        try{
			//System.out.println("Creating File/Object output stream...");
			FileOutputStream fileOut = new FileOutputStream(WMP.HASHTABLE_FILE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			//System.out.println("Writing Hashtable Object...");
			out.writeObject(WMP.discoveryMap);
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
		System.out.println("WMPSerializer: Load Hashtable");
		try{
			//System.out.println("Creating File/Object input stream...");
			FileInputStream fileIn = new FileInputStream(WMP.HASHTABLE_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
			//System.out.println("Loading Hashtable Object...");
			WMP.discoveryMap = (Hashtable<String, WMPNode>)in.readObject();
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
		for(Enumeration<String> e = WMP.discoveryMap.keys(); e.hasMoreElements();){
			// load elements in hashtable
			String node = e.nextElement();
			// modify age to default
			WMP.discoveryMap.get(node).setAge(WMP.DEFAULT_AGE);
			// print loaded elements
			System.out.println("WMPSerializer: Loaded Element (" + node + ") = " + WMP.discoveryMap.get(node));
		}
	}
    
}
