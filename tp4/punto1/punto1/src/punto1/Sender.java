package punto1;
import java.io.File;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class Sender extends Agent {
	String strdir = "/";
	String[] list;
	ContainerID destino = null;
	Location origen = null;
	String nombreContainer = null;
	
	
	public void setup()	{
		//destino = new ContainerID("cliente", null);
		origen = here();
		
		addBehaviour(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				return true;
			}
			
			@Override
			public void action() {
					ACLMessage message = new ACLMessage(ACLMessage.INFORM);
					AID r=new AID("Receptor@192.168.1.20:1099/JADE",AID.ISGUID);
					r.addAddresses("http://192.168.1.20:7778/acc");
					message.addReceiver(r);
					message.setContent("Hello.! mensaje puto de mierda!!!!");
					send(message);
					System.out.println("\nMessage asAASASSAS to "+r);
			}
		});		
	}
}