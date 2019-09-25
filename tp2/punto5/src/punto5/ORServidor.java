package punto5;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ORServidor extends UnicastRemoteObject implements InterfazRemota{

	private static final long serialVersionUID = 1L;

	protected ORServidor(String host) throws RemoteException,MalformedURLException {
       super();
       //[TODO] cambiar la cadena "localhost" por el parametro host.
       System.out.println("VINE AL OBJETO REMOTO");
       String rname = "//"+ "localhost" +":" + Registry.REGISTRY_PORT  + "/ORServidor";
       Naming.rebind(rname, this);
       
	}

	@Override
	public long getTiempo() throws RemoteException {
		System.out.println("Me pidieron mi tiempo.");
		
		DateFormat tiempoRecepcion = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendario = Calendar.getInstance();
		Date dateRecepcion = calendario.getTime();
		System.out.println("Tiempo de envio en date: " + tiempoRecepcion.format(dateRecepcion));

		System.out.println("Tiempo de recepcion en milis: "+ System.currentTimeMillis());

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date dateEnvio = calendario.getTime();
		System.out.println("Estoy entregando el tiempoa la hora: "+ dateEnvio);
		return System.currentTimeMillis();
	}
	
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORServidor(args[0]);

	}
	
}
