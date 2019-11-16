package punto1;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;


public class Main {

	public static void main(String[] args) throws StaleProxyException {
		
		//Get the JADE runtime interface (singleton)
		Runtime runtime = Runtime.instance();
		//Create a Profile, where the launch arguments are stored
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, "Server");
		profile.setParameter(Profile.LOCAL_HOST, "192.168.1.20");
		profile.setParameter(Profile.MAIN_HOST, "192.168.1.21");
		//create a non-main agent container
		ContainerController container = runtime.createAgentContainer(profile);
		
	
		
		// Fire up the agent
		Object agentArgs[] = new Object[3];
		
        AgentController receptor = container.createNewAgent("Sender", "punto1.Sender", agentArgs);
        receptor.start();
        
       
		
	}
}

