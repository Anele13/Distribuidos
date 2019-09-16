
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Tabla extends JFrame {
 
	JFrame f;
	
    public Tabla(String[] column, String[][] data) {
  
	    f=new JFrame();

	    JTable jt=new JTable(data, column);
	    jt.setBounds(1,1,10,10);          
	    JScrollPane js = new JScrollPane(jt);
	    
	    //String[] columnas1 = Arrays.copyOfRange(column, 0, (column.length + 1)/2);
	    f.add(js, BorderLayout.CENTER);
	    f.setSize(700,400);    
	    f.setVisible(true);    
	} 
}
