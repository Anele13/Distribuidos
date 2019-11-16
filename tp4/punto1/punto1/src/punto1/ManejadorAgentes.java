package punto1;

import jade.core.Runtime;
import java.net.InetAddress;
import java.net.UnknownHostException;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;


public 	class ManejadorAgentes {
	
	ContainerController container = null;
	
	public ManejadorAgentes() throws UnknownHostException {
		/*
		 * Creo un contenedor local para luego sumar 2 agentes (uno que recibe datos y otro que envia)
		 */
		Runtime runtime = Runtime.instance();	
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, "ServerLocal");
		profile.setParameter(Profile.LOCAL_HOST, "192.168.1.20"); //TODO ver si se puede refactorizar esto de harcodear la ip.
		container = runtime.createAgentContainer(profile);	
	}

	
	public void leer(String FilePathLocal, String FilePathRemoto, String contenedorDestino) throws StaleProxyException {

		//Creo el agente receptor con el filepath donde debe escribir los datos que recibe
		Object agentArgs[] = new Object[2];
		agentArgs[0] = FilePathLocal;
		agentArgs[1] = null;
		AgentController agenteReceptor = container.createNewAgent("Receptor", "punto1.Receptor", agentArgs);
		
		//Creo el agente que envia datos con el filepath remoto
		Object agentArgs2[] = new Object[3];
		agentArgs2[0] = agenteReceptor.getName();
        agentArgs2[1] = FilePathRemoto;
        agentArgs2[2] = contenedorDestino;
        AgentController agenteSender = container.createNewAgent("Sender", "punto1.Sender", agentArgs2);
       
        //Arranco los agentes
        agenteReceptor.start();
        agenteSender.start();
	}
	
	
	public void escribir(String FilePathLocal, String FilePathRemoto, String contenedorDestino) throws StaleProxyException {
		
		//Creo el agente receptor con el filepath donde debe escribir los datos que recibe
		Object agentArgs[] = new Object[2];
		agentArgs[0] = FilePathRemoto;
		agentArgs[1] = contenedorDestino;
		AgentController agenteReceptor = container.createNewAgent("Receptor", "punto1.Receptor", agentArgs);
		
		//Creo el agente que envia datos con el filepath remoto
		Object agentArgs2[] = new Object[3];
		agentArgs2[0] = FilePathLocal;
        agentArgs2[1] = agenteReceptor.getName();
        agentArgs2[2] = null;
        AgentController agenteSender = container.createNewAgent("Sender", "punto1.Sender", agentArgs2);
       
        //Arranco los agentes
        agenteReceptor.start();
        agenteSender.start();
	}
	
	
	public static void main(String[] args) throws StaleProxyException, UnknownHostException {
		ManejadorAgentes m = new ManejadorAgentes();
		m.leer("hola", "/home/anele/Desktop/unarchivocopado", "Anele");
		m.leer("hola", "/home/anele/Desktop/unarchivocopado2", "Anele");
	}
}