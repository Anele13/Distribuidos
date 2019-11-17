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
        	
        	public void crearArchivo() throws FileNotFoundException {
        		if(archivo == null)
        			archivo = new FileOutputStream(new File(filePath));
        	}

        	public void action() {
        		try {
					crearArchivo();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				ACLMessage msg= receive();
				if (msg!=null) {
					String mensaje = msg.getContent();
					if (mensaje.equals("-1")) {
						doDelete();//Flag fin de archivo
					}
					else {
						try {
							archivo.write(mensaje.getBytes());
							archivo.write("\n".getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}						
				}
		    }
        	
		});
	}
}