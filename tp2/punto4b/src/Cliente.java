/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

import java.rmi.Naming;                    /* lookup         */
import java.rmi.registry.Registry;         /* REGISTRY_PORT  */
import java.rmi.server.RMISocketFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;     /* Exceptions...  */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Ejemplo de cliente rmi 
*/
public class Cliente implements ActionListener{
    
	private IRSumaResta sumaResta;
	private IRMultiplicacionDivision mulDivision;
	JTextField tf1,tf2,tf3;  
    JButton b1,b2,b3,b4;  
	
	public Cliente() throws MalformedURLException, RemoteException, NotBoundException {
		
		// Objeto Remoto Suma-Resta
    	String rname = "//" + "localhost" + ":" + Registry.REGISTRY_PORT + "/ORSumaResta";
		IRSumaResta objetoRemoto =  (IRSumaResta)Naming.lookup (rname);
		
		// Objeto Remoto Multiplicacion-Division
		String rname2 = "//" + "localhost" + ":" + Registry.REGISTRY_PORT + "/ORMultiplicacionDivision";
		IRMultiplicacionDivision objetoRemoto2 =  (IRMultiplicacionDivision)Naming.lookup (rname2);
		this.setMulDivision(objetoRemoto2);
		this.setSumaResta(objetoRemoto);
	}
	
	public IRSumaResta getSumaResta() {
		return sumaResta;
	}

	public void setSumaResta(IRSumaResta sumaResta) {
		this.sumaResta = sumaResta;
	}

	public IRMultiplicacionDivision getMulDivision() {
		return mulDivision;
	}

	public void setMulDivision(IRMultiplicacionDivision mulDivision) {
		this.mulDivision = mulDivision;
	}

	
	public void runVentana() {
		JFrame f= new JFrame();  
        tf1=new JTextField();  
        tf1.setBounds(50,50,150,20);  
        tf2=new JTextField();  
        tf2.setBounds(50,100,150,20);  
        tf3=new JTextField();  
        tf3.setBounds(50,150,150,20);  
        tf3.setEditable(false);   
        
        b1=new JButton("+");  
        b1.setBounds(50,200,50,50);  
        b2=new JButton("-");  
        b2.setBounds(120,200,50,50);  
        
        b3=new JButton("/");  
        b3.setBounds(190,200,50,50);  
        b4=new JButton("*");  
        b4.setBounds(260,200,50,50);  
        
        
        b1.addActionListener(this);  
        b2.addActionListener(this); 
        b3.addActionListener(this);  
        b4.addActionListener(this); 
        
        f.add(tf1);f.add(tf2);f.add(tf3);f.add(b1);f.add(b2);f.add(b3);f.add(b4);  
        
        
        f.setSize(400,400);  
        f.setLayout(null);  
        f.setVisible(true);  
	}
	
	
	@Override
	  public void actionPerformed(ActionEvent e) {
    	String s1=tf1.getText();  
        String s2=tf2.getText();  
        int a=Integer.parseInt(s1);  
        int b=Integer.parseInt(s2);
        int c=0; 
        
        try {
	    	if (e.getActionCommand() == "+") {
				c = this.getSumaResta().suma(a, b);
	    	}
	    	else {
	    		if(e.getActionCommand() == "-"){
	    			c = this.getSumaResta().resta(a, b);
	    		}
	    		else {
	    			if(e.getActionCommand() == "/") {
	    				c = this.getMulDivision().division(a, b);
	    			}
	    			else {
	    				c = this.getMulDivision().multiplicacion(a, b);
	    			}
	    		}
	    	}
    	} catch (RemoteException e1) {
			e1.printStackTrace();
		}
        String result=String.valueOf(c);  
        tf3.setText(result);  
    }  
    
	
	
	public static void main(String[] args) throws NotBoundException, IOException {
    	RMISocketFactory.setSocketFactory(new TimeoutFactory(5));
		Cliente c = new Cliente();
		c.runVentana();
    }
}
