import javax.swing.*;

public class DiscoverySystemGUI extends Thread {
// Swing
protected static DefaultListModel listenModell = new DefaultListModel();
 
public void run(){ 
JFrame dS = new JFrame();
JPanel panel = new JPanel();
JList endListe = new JList(listenModell);
endListe.setVisibleRowCount(15);
endListe.setFixedCellHeight(20);
endListe.setFixedCellWidth(245);
JScrollPane endListe2 = new JScrollPane(endListe);

      dS.setTitle("Where is my Pi?");
      dS.setSize(260, 340);
      dS.setResizable(false);;
      panel.add(endListe2);
      dS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      dS.add(panel);
      dS.setVisible(true); 
    }
}