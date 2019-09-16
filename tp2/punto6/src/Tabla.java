
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;


public class Tabla extends JFrame {
 
	JFrame f;
	private String[][] data;
	private String[] columnas;
	
    public Tabla() {
  
	    f=new JFrame();

	    
	    
	    String[] columnas0 = Arrays.copyOfRange(column, (column.length + 1)/2, column.length);
	    String[][] data0 = new String[1][columnas0.length];
	    data0[0] = Arrays.copyOfRange(data[0], (data[0].length + 1)/2, data[0].length );
	    JTable jt1 =new JTable(data0, columnas0);          
	    
	    JScrollPane js = new JScrollPane(jt1);
	    js.setMaximumSize(new Dimension(10, 50));
	    
	    
	    
	    String[] columnas1 = Arrays.copyOfRange(column, 0, (column.length + 1)/2);
	    String[][] data1 = new String[1][columnas1.length];
	    data1[0] = Arrays.copyOfRange(data[0], 0, (data[0].length + 1)/2);
	    JTable jt2 =new JTable(data1, columnas1);
	    
	    
	    

	    
	           
	}
    
    public void setColumnas(String[] columnas) {
    	
    }
    
    public void mostrar() {
    	JTable jt2 =new JTable(this.data, this.columnas);
 	    JScrollPane js2 = new JScrollPane(jt2);
	    js2.setMaximumSize(new Dimension(10, 50));
 	    f.add(js2, BorderLayout.CENTER);
	    //f.add(js, BorderLayout.AFTER_LAST_LINE);
	    f.setSize(600,600);
    	this.setVisible(true);
    }
    
}
