package punto1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class Sender extends Agent {
	
	String ContainerDestino = null;
	String FilePath = null;
	String AgenteReceptor = null;
	
	public void setup()	{
		
		Object[] arguments = getArguments();
        AgenteReceptor = (String) arguments[0];
        FilePath = (String) arguments[1];
        ContainerDestino = (String) arguments[2];;
        if (ContainerDestino != null)
        	doMove(new ContainerID(ContainerDestino, null));
        
        
		addBehaviour(new SimpleBehaviour(this) {
			
			public void leerArchivo(ACLMessage mensaje, String FilePath) {
				boolean hayErrores = false;
				try {
				    BufferedReader in = new BufferedReader(new FileReader(FilePath));
				    String str;
				    while ((str = in.readLine()) != null) {
				    	mensaje.setContent(str);
						send(mensaje);
				    }				    	
				    in.close();
				} catch (IOException e) {
					hayErrores = true;
				}
				if (hayErrores) {
					mensaje.setContent("Hubo un error en la ejecucion o el archivo no existe.");
					send(mensaje);
				}
			}
			
			
			@Override
			public void action() {
				ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
				mensaje.addReceiver(new AID(AgenteReceptor,AID.ISGUID));
				leerArchivo(mensaje, FilePath);
			}
			
			
			@Override
			public boolean done() {
				doDelete();
				return true;
			}
		});
	}
}