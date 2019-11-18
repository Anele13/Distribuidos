package punto1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;


public class Receptor extends Agent {
	
	String ContainerDestino = null;
	String filePath = null;
	FileOutputStream archivo = null;
	
	public void setup()	{
		
		Object[] arguments = getArguments();
		filePath = (String) arguments[0];
        ContainerDestino = (String) arguments[1];
        if (ContainerDestino != null)
        	doMove(new ContainerID(ContainerDestino, null));

                
        addBehaviour(new CyclicBehaviour(this) {
        	
        	
        	public void open() {
        		try {
        			if(archivo == null)
            			archivo = new FileOutputStream(new File(filePath));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
        	}

        	
        	public void close() {
        		try {
        			if(archivo != null)
            			archivo.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        	}
        	
        	
        	public void write(String mensaje) {
        		try {
					archivo.write(mensaje.getBytes());
					archivo.write("\n".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        	
        	public void action() {
        		open();
				ACLMessage msg= receive();
				if (msg!=null) {
					if (msg.getContent().equals("-1")) {
						close();
						doDelete();
					}
					else {
						write(msg.getContent());
					}						
				}
		    }
		});
	}
}