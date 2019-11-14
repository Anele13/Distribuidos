package punto1;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class Receptor extends Agent {
	String strdir = "/";
	String[] list;
	ContainerID destino = null;
	Location origen = null;
	String nombreContainer = null;
	
	

	public void setup()	{
		//destino = new ContainerID(this.nombreContainer, null);
		origen = here();
	
		addBehaviour(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				return false;
			}
			
			@Override
			public void action() {
				ACLMessage msg = null;
				msg = blockingReceive();
				System.out.println(msg.getContent());
			}
		});		
	}
}