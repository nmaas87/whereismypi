import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public class DiscoverySystemGUI extends Thread {
// Swing
protected static DefaultListModel listenModell = new DefaultListModel();
 
public void run(){ 
JFrame dS = new JFrame();
URL logoURL = getClass().getResource("wmp.png");
ImageIcon logoImageIcon = new ImageIcon(logoURL);
Image logoImage = logoImageIcon.getImage();
JPanel panel = new JPanel();
JList zwischenListe = new JList(listenModell);
zwischenListe.setVisibleRowCount(15);
zwischenListe.setFixedCellHeight(20);
zwischenListe.setFixedCellWidth(245);
JScrollPane fertigeListe = new JScrollPane(zwischenListe);

      dS.setTitle("Where is my Pi?");
      dS.setSize(260, 340);
      dS.setIconImage(logoImage);
      dS.setResizable(false);
      panel.add(fertigeListe);
      dS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      dS.add(panel);
      dS.setVisible(true); 
    }
}