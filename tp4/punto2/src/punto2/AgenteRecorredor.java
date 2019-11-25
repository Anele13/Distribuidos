package punto2;


import jade.core.*;

import jade.core.behaviours.CyclicBehaviour;


import java.util.*;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import jade.lang.acl.*;
import jade.content.*;
import jade.content.onto.basic.*;
import jade.content.lang.sl.*;
import jade.domain.JADEAgentManagement.*;
import jade.domain.mobility.*;


public class AgenteRecorredor extends Agent
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean error = false;
	private int operacion = 0;
	
	private ArrayList containers = new ArrayList(); // Obtiene una Lista de los Contenedores registrados en JADE.
	private int cantidad_maxima_contenedores = 0;
	private int cant_contenedores = 0;	
	 
//**********************************************************************************************************
	
	//ContainerID destino = null;
	Location destino= null;
	Location origen = null;	
	Location mudarse = null;	
	

	public void setup()
	{
		System.out.println("Se crea al agente --> " + getName()+"\n");
				
		// Registramos el lenguaje y ontologia para la movilidad del agente.
		getContentManager().registerLanguage(new SLCodec());
	    getContentManager().registerOntology(MobilityOntology.getInstance());
		
	    origen = here();
		System.out.println("Origen --> " + origen.getName()+"\n");		
				
		// registra el comportamiento deseado del agente
		addBehaviour( 
			new CyclicBehaviour(this)
			{
				public void action() 
				{										
					if (_state == 0)
					{												
							verContainers();
							cantidad_maxima_contenedores = containers.size();
							cant_contenedores = 0;

							if (cantidad_maxima_contenedores != 0)
								_state = 1;
							else
								_state = 2;  
					}
					contenedor_actual= here();
					System.out.println("TEST ---------------- CONTENEDOR ACTUAL = "+contenedor_actual.getName());
					
					System.out.println("TEST ---------------- ESTADO ="+_state);
					switch(_state)
					{
						case 1:
							// ME MUDO A LA SIGUIENTE MAQUINA  
							System.out.println("Contenedor actual: "+cant_contenedores);
							System.out.println("Cantidad Máxima de contenedores: "+cantidad_maxima_contenedores);
							//[TODO]FILTRAR EL MAIN CONTAINER DE LA  LISTA
							if (cant_contenedores < cantidad_maxima_contenedores) {
								destino = (Location)containers.get(cant_contenedores);
								++cant_contenedores;
							
								System.out.println("Estado 1 Comienza la migracion del agente al destino --> " + destino.getName()+"\n");
								try 
								{
									System.out.println("TEST ----------------  me muevo a destino");
									doMove(destino);
									
									try
								    {
								    	System.out.println("Esperando 8 segundos ...\n");
								        Thread.sleep(8000);
								    }
								    catch(InterruptedException ex)
								    {
								        Thread.currentThread().interrupt();
								    }
									
									
									System.out.println("TEST ---------------- CONTENEDOR ACTUAL = "+contenedor_actual.getName());
									System.out.println("Despues de doMove en CyclicBehaviour de Estado 1 --> " + destino.getName()+"\n");
									System.out.println("TEST ---------------- Ya migre. Relevando información ...\n\n");
									
									//Mostramos info que requiere el punto:
									//La Hora.
									DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
									Date date = new Date();
									System.out.println("Hora actual: " + dateFormat.format(date));
									
									//La carga de procesamiento.
								    OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
								    System.out.println("Carga de procesamiento: "+bean.getSystemLoadAverage());
								    
//								    //La IP.
								    try 
								    {
								    	String thisIp = InetAddress.getLocalHost().getHostAddress();
								    	System.out.println("IP:"+thisIp);
								    }
								    catch(Exception e) 
								    {
								    	e.printStackTrace();
								    }
								    
								    // Info adicional que nos interesa mostrar.
								    System.out.println("Sistema Operativo: ");
								    System.out.println("--> Tipo: "+bean.getName());
								    System.out.println("--> Versión: "+bean.getVersion());
								    System.out.println("--> Arquitectura: "+bean.getArch());
								    System.out.println("Hardware: ");
								    System.out.println("--> Cantidad de núcleos: "+bean.getAvailableProcessors());
								    System.out.println("--> Tamaño de memoria: "+bean.getTotalPhysicalMemorySize()+" bytes.");  
								    RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
								    System.out.println("Plataforma local:");
								    System.out.println("--> Nombre : "+runtimeBean.getVmName());
								    System.out.println("--> Vendor : "+runtimeBean.getVmVendor());
								    System.out.println("--> Versión : "+runtimeBean.getVmVersion());
								    
								    //Espera del tiempo solicitado.
								    try
								    {
								    	System.out.println("Esperando 10 segundos ...\n");
								        Thread.sleep(10000);
								    }
								    catch(InterruptedException ex)
								    {
								        Thread.currentThread().interrupt();
								    }
								    System.out.println("TEST ---------------- Fin del relevo de información.");
								} 
								catch (Exception e) 
								{
									System.out.println("fallo al moverse \n");
									e.getMessage();
								}
								break;
							}
							else
							{
								//[TODO] Migro al origen e imprimo resultados.
								_state = 2;
								break;
							}
						case 2:	
							System.out.println("TEST ---------------- EMPIEZA EL CASO 2 +++++++++++++++");
							System.out.println("Estado 2 Comienza la auto eliminacion del agente en origen --> " + getName()+"\n");
//							System.out.println("TEST ---------------- COMIENZA EL TRY");
							try 
							{
//								System.out.println("TEST ---------------- REALIZO EL WAIT=");
//								wait(1000);
								System.out.println("TEST ---------------- Voy a hacer el doDELETE");
								doDelete();
								System.out.println("TEST ---------------- Hice el doDELETE");
								System.out.println("Despues de la auto eliminacion del agente Estado 2 --> " + getName() +"\n");
							} 
							catch (Exception e) 
							{
								System.out.println("fallo al moverse al Container-1 \n");
								e.getMessage();
							}
							break; 
																					
					}
				}
				private Location contenedor_actual=null;
				private int _state = 0; // variable de maquina de estados del agente
			}
		);
	}

	@Override
    protected void beforeMove() {
        System.out.println("Preparing to move");
        super.beforeMove();
    }

    @Override
    protected void afterMove() {
        System.out.println("Arrived to destination");
        super.afterMove();
    }



	//Metodo para actualizar a lista de containers disponibles
	protected void actualizarContainers()
	{	 	
		getContentManager().registerLanguage(new SLCodec());
	    getContentManager().registerOntology(MobilityOntology.getInstance());
	    
	    origen = here();
	    containers.clear();
	    ACLMessage request= new ACLMessage(ACLMessage.REQUEST);
	    request.setLanguage(new SLCodec().getName());
	    
	    // Establecemos que MobilityOntology sea la ontologia de este mensaje.
	    request.setOntology(MobilityOntology.getInstance().getName());
	    
	    // Solicitamos a AMS una lista de containers disponibles
	    Action action= new Action(getAMS(), new QueryPlatformLocationsAction());
	    
	    try 
	    {
	      getContentManager().fillContent(request, action);
	      request.addReceiver(action.getActor());
	      send(request);
	 
	      // Filtramos los mensajes INFORM que llegan desde el AMS
	      MessageTemplate mt= MessageTemplate.and(MessageTemplate.MatchSender(getAMS()), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
	 
	      ACLMessage resp= blockingReceive(mt);
	      ContentElement ce= getContentManager().extractContent(resp);
	      Result result=(Result) ce;
	      jade.util.leap.Iterator it= result.getItems().iterator();
	      // Almacena un ArrayList "Locations" de "Containers" donde puede moverse el agente movil.
	      
	      while(it.hasNext()) 
	      {
		    Location loc=(Location) it.next();
		    containers.add(loc);
	      }
	    }
	    catch(Exception ex) 
	    {  ex.printStackTrace();	}
	}	
	
	protected void verContainers()
	{
	    //ACTUALIZAR
	    actualizarContainers();
	    //VISUALIZAR
	    System.out.println("******Containers disponibles: *******\n");
	    for(int i=0; i<containers.size(); i++)	    	
	    {
	      System.out.println("    ["+ i + "] " + ((Location)containers.get(i)).getName());
	    }
	    System.out.println("\n");
	 }
	
	
}	