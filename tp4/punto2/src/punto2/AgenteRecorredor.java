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
import java.net.DatagramSocket;


public class AgenteRecorredor extends Agent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		String[] columnas = {"Hora de arribo","Carga de procesamiento","IP","Sistema Operativo", "Versión SO", "Arquitectura SO","Cantidad de núcleos","Tamaño de memoria","Nombre de Plataforma Local","Vendor de Plataforma Local","Versión de Plataforma Local"};	
		verContainers();
		cantidad_maxima_contenedores = containers.size();
		String[][] datos = new String[cantidad_maxima_contenedores+1][columnas.length];

		// registra el comportamiento deseado del agente
		addBehaviour( 
			new CyclicBehaviour(this)
			{
				public void action() 
				{										
					if (_state == 0)
					{		
						cant_contenedores = 0;
						if (cantidad_maxima_contenedores != 0)
							_state = 1;
						else
							_state = 2;  
					}
					switch(_state)
					{
						case 1:
							// Estado que recorre la lista de contenedores y releva info. 
							if (cant_contenedores <= cantidad_maxima_contenedores) {
								try	{
									destino = (Location)containers.get(cant_contenedores);
								}
								catch(Exception e) {}
								++cant_contenedores;
								System.out.println("Estado 1 Comienza la migracion del agente al destino --> " + destino.getName()+"\n");
								try 
								{
									doMove(destino);
									DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
									Date date = new Date();
									OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();								    
									try(final DatagramSocket socket = new DatagramSocket())
									{
										socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
										String ip = socket.getLocalAddress().getHostAddress();
								    	RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();									    
										String[] aux = {dateFormat.format(date),Double.toString(bean.getSystemLoadAverage()),ip,bean.getName(),bean.getVersion(),bean.getArch(),Integer.toString(bean.getAvailableProcessors()),Long.toString(bean.getTotalPhysicalMemorySize()),runtimeBean.getVmName(),runtimeBean.getVmVendor(),runtimeBean.getVmVersion()};
										datos[auxiliar]=aux;
							    	}
								    catch(Exception e) 
								    {
								    	e.printStackTrace();
								    }
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
									System.out.println("ERROR !!!! -------- fallo al moverse \n");
									e.getMessage();
								}
								++auxiliar;
								break;
							}
							else
							{
								_state = 2;
								System.out.println("ME MUEVO AL ORIGEN");
								doMove(origen);
								break;
							}
						case 2:	
							try 
							{
								//Estado que registra info del main container y elimina al agente.
								//Guardo la info del main container
								DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
								Date date = new Date();
								OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();							 
								try(final DatagramSocket socket = new DatagramSocket())
								{
									socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
									String ip = socket.getLocalAddress().getHostAddress();
									RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();									    
									String[] aux = {dateFormat.format(date),Double.toString(bean.getSystemLoadAverage()),ip,bean.getName(),bean.getVersion(),bean.getArch(),Integer.toString(bean.getAvailableProcessors()),Long.toString(bean.getTotalPhysicalMemorySize()),runtimeBean.getVmName(),runtimeBean.getVmVendor(),runtimeBean.getVmVersion()};
									datos[auxiliar]=aux;
								}	
							    catch(Exception e){}
								//Elimino el agente.
								doDelete();
								System.out.println("Despues de la auto eliminacion del agente Estado 2 --> " + getName() +"\n");
							} 
							catch (Exception e) 
							{
								System.out.println("fallo al moverse al Container-1 \n");
								e.getMessage();
							}
							//Imprimo la tabla al finalizar.
							Tabla t = new Tabla(columnas, datos);
							break; 
					}
				}
				int auxiliar =0;
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
		    if (!loc.getName().equals("Main-Container")) {
			    containers.add(loc);
		    }
	      }
	    }
	    catch(Exception ex) 
	    {  ex.printStackTrace();	}
	}	
	
	
	public void eliminarMainContainer(String id) {
		System.out.println("EN ELIMINAR MAIN CONTAINER");
		System.out.println("CONTAINERS : "+containers);

		for (int i=0; i<containers.size(); i++) 
		{ 
			
		    Location container = (Location) containers.get(i);
		    System.out.println("nombre = "+container.getName());
		    System.out.println("PARAMETRORECIBIDO ID = "+id);
		    System.out.println("ID DEL CONTAINER = "+container.getID());
		    if (id=="Main-Container@192.168.0.103") {
		    	System.out.println("ES ESTE EL CONTENDOR !!!!");
		    	containers.remove(i);
		    }
		    if (id==container.getID()) {
		    	System.out.println("ES ESTE EL CONTENDOR !!!!");
		    	containers.remove(i);
		    }
		    if (container.getName()=="Main-Container") {
		    	System.out.println("ES ESTE EL CONTENDOR !!!!");
		    	containers.remove(i);
		    }
		}
		System.out.println("CONTAINERS : "+containers);

		System.out.println("TERMINANDO ELIMINAR MAIN CONTAINER");
		
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