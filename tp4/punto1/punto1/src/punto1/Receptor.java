package punto1;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;


public class Receptor extends Agent {
	
	String ContainerDestino = null;
	String FilePath = null;
	
	

	public void setup()	{
		Object[] arguments = getArguments();
        FilePath = (String) arguments[0];
        ContainerDestino = (String) arguments[1];
        if (ContainerDestino != null)
        	doMove(new ContainerID(ContainerDestino, null));
	
        
		addBehaviour(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				doDelete();
				return true;
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