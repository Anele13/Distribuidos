package punto1;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

public class AgenteManager extends Agent{
	
	Location origen = null;

	private Ventana ventana;

	public void setup()	{
	
		System.out.println("Se crea al agente --> " + getName());
		origen = here();
		Ventana ventana = new Ventana(this);
		this.setVentana(ventana);
		addBehaviour(new CyclicBehaviour(this){
			public void action() {
				
				AgenteLeer agenteLeer = new AgenteLeer();
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
	

	// Metodo para test.
	public void saludar() {
		System.out.println("TEST ---- Hola soy el agente "+getName()+".");
	}
	
// Getters & Setters.
	public Ventana getVentana() {
		return ventana;
	}

	public void setVentana(Ventana ventana) {
		this.ventana = ventana;
	}
	
}
