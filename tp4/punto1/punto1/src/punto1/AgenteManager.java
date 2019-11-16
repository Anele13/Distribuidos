package punto1;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteManager extends Agent{
	
	Location origen = null;

	private Ventana ventana;

	public void setup()	{
	
		System.out.println("Se crea al agente Manager --> " + getName());
		origen = here();
		Ventana ventana = new Ventana(this);
		this.setVentana(ventana);
		
		addBehaviour(new CyclicBehaviour(this){
			public void action() {
				
				
//				switch(_state){
//						case 0:
//							// Comienza la migración del agente al destino
//							_state++;
//							System.out.println(" TEST ---- Pase por el estado 0." );
//							break;
//						
//						case 1:
//							// el agente llegó al destino, recupera el directorio y regresa
//							_state++;
//							System.out.println(" TEST ---- Pase por el estado 1." );
//							break;
//							
//						case 2:
//							// Regresó al origen, imprime el directorio y destruye al agente
//							System.out.println(" TEST ---- Pase por el estado 2." );
//	
//							// destruye al agente
//							System.out.println("TEST ---- Destruye al agente --> " + getName() +".");
//							ventana.setVisible(false);
//							doDelete();
//							break;
//						default:
//							System.out.println("TEST ---- Destruye al agente --> " + getName() +".");
//							ventana.setVisible(false);
//							myAgent.doDelete();
//					}
			}
			private int _state = 0; // variable de máquina de estados del agente
		});
			
	}
	
	public void cerrar() {
		System.out.println("Eliminando el agente --> "+getName()+".");
		doDelete();
	}

	// Metodo para test.
	public void saludar() {
		System.out.println("TEST ---- Hola soy el agente "+getName()+".");
		

        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        
        AID r=new AID("Receptor",AID.ISGUID);
        r.addAddresses("http://192.168.43.152:7778/acc");
        message.addReceiver(r);
        message.setContent("Hello.!");
        send(message);
        System.out.println("\nMessage enviado to "+r);    

//        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//        message.addReceiver(new AID("Receptor", true));
//        message.setContent("Hola CAPOOO soy el server");
//        send(message);
        
        
		
		AgenteLeer agenteLeer = new AgenteLeer();
		
	}
	
// Getters & Setters.
	public Ventana getVentana() {
		return ventana;
	}

	public void setVentana(Ventana ventana) {
		this.ventana = ventana;
	}
	
}
