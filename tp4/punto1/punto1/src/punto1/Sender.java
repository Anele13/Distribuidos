package punto1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class Sender extends Agent {
	
	String ContainerDestino = null;
	String filePath = null;
	FileInputStream archivo = null;
	String AgenteReceptor = null;
	int max = 1000;
	byte[] respuesta = new byte[max];

	public void setup()	{
		
		Object[] arguments = getArguments();
        AgenteReceptor = (String) arguments[0];
        filePath = (String) arguments[1];
        ContainerDestino = (String) arguments[2];;
        if (ContainerDestino != null)
        	doMove(new ContainerID(ContainerDestino, null));
        
        
		addBehaviour(new SimpleBehaviour(this) {
			
			
			public void open() {
        		try {
        			if(archivo == null)
            			archivo = new FileInputStream(new File(filePath));
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
        	
        	
			public byte[] read() {
	            byte[] buffer = new byte[max];
				int cantidad;

				try {
					cantidad = archivo.read(buffer,0,max);
		            if (cantidad == -1) {
		            	return null;
		            }
		            return buffer;
				} catch (FileNotFoundException e) {
					return null;
				} catch (IOException e) {
					return null;
				}	
			}
			
			
			@Override
			public void action() {
				ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
				mensaje.addReceiver(new AID(AgenteReceptor,AID.ISGUID));
				
				open();	
				while ((respuesta = read()) != null) {
					mensaje.setContent(new String(respuesta).trim());
					send(mensaje);					
				}
				mensaje.setContent("-1");
				send(mensaje);
				close();
			}
			
			
			@Override
			public boolean done() {
				doDelete();
				return true;
			}
		});
	}
}