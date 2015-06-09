import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public class WMPGUI extends Thread {
// Swing
protected static DefaultListModel listenModell = new DefaultListModel();
 
public void run(){ 
JFrame dS = new JFrame();
JPanel panel = new JPanel();
JList zwischenListe = new JList(listenModell);
zwischenListe.setVisibleRowCount(20);
zwischenListe.setFixedCellHeight(20);
zwischenListe.setFixedCellWidth(325);
JScrollPane fertigeListe = new JScrollPane(zwischenListe);

      dS.setTitle("Where is my Pi?");
      dS.setSize(340, 442);
      try
      {
        dS.setIconImage((new ImageIcon(getClass().getResource("wmp.png"))).getImage());
      }
      catch (NullPointerException e) 
      {
        e.printStackTrace();
      }  
      dS.setResizable(false);
      panel.add(fertigeListe);
      dS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      dS.add(panel);
      dS.setVisible(true); 
    }
}