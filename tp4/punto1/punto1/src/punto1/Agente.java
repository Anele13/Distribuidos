package punto1;

import jade.core.*;
import java.io.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;

public class Agente extends Agent{
	String strdir = "/";
	// Dir to list
	String[] list;
	ContainerID destino = null;
	Location origen = null;
	
	
	public Agente() {
		
	}

	public void setup()	{
		destino = new ContainerID("otroche", null);
		origen = here();
		
			
		
		addBehaviour(new CyclicBehaviour(this){
			public void action() {
			switch(_state){
					case 0:
						_state++;
						try {
							doMove(destino);
						} catch (Exception e) {
							System.out.println("fallo al moverse al Container-1");e.getMessage();
						}
						break;
					
					case 1:
						// el agente llegó al destino, recupera el directorio y regresa
						_state++;
						try {
							File dir = new File(strdir);
							list = dir.list();
						} catch (Exception e) {System.out.println("Falló al recuperar directorio dir.list()");e.getMessage();}
						try {
							System.out.println("regresando a --> " + origen.getID());
							doMove(origen);
							System.out.println("despues de domove en CyclicBehaviour estado 1 --> " + here().getID());
						} catch (Exception e) {System.out.println("Falla al mover al regresar al origen"); e.getMessage();}
						System.out.println("ANTES DE ROMPER estado 1");
						break;
						
					case 2:
						// Regresó al origen, imprime el directorio y destruye al agente
						System.out.println("Estado 2 Regresó a origen, imprime directorio y destruye agente --> " + here().getID());
						for (int i = 0; i < list.length; i++) {
							System.out.println(i + ": " + list[i]);
						}
						// destruye al agente
						System.out.println("destruye al agente --> " + getName());
						doDelete();
						System.out.println("ANTES DE ROMPER estado 2");
						break;
					default:
						myAgent.doDelete();
				}
			}
			private int _state = 0; // variable de máquina de estados del agente
		});
		
		addBehaviour(new CyclicBehaviour(this){
			public void action() {
					// arranca muestra cartel, duerme 5 segundos y muestra otro cartel
					_contador++;
					System.out.println("Behaviour dummy antes de dormir ciclo--> " + _contador + " --> " + here().getID());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
							//Logger.getLogger(ListingAgent.class.getName()).log(Level.SEVERE, null, ex);
						System.out.println("error");
					}
					System.out.println("Behaviour dummy despues de dormir ciclo--> " + _contador + " --> " + here().getID());
				}
				private int _contador = 0; // cuenta la cantidad de ciclos en que se ejecuta el comportamiento
		});
		
		
	}
	// Luego de ser movido el agente ejecuta este código
	protected void afterMove() {
        System.out.println("Siempre ejecuta afterMove cuando al llegar --> " + here().getID());
    }
}
