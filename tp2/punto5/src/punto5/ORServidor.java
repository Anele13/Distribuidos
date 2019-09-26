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
		//CONTINUACION DEL ALGORITMO DE CRISTIAN.
		//PASO 3. TOMO EL TIEMPO DE RECEPCION.
		DateFormat tiempoRecepcion = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("Inicie el sdf");
		Calendar calendario1 = Calendar.getInstance();
		System.out.println("Inicie el calendario");
		Date dateRecepcion = calendario1.getTime();
		System.out.println("Tiempo de envio en date desde el OR: " + tiempoRecepcion.format(dateRecepcion));
		System.out.println("Tiempo de recepcion en milis desde el OR: "+ System.currentTimeMillis());

//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		Calendar calendario2 = Calendar.getInstance();
		Date dateEnvio = calendario2.getTime();
		//PASO 4. TOMO EL TIEMPO DE ENVIO DE LA RESPUESTA.
		System.out.println("Estoy entregando el tiempo en la hora (date): "+ dateEnvio);
		System.out.println("Estoy entregando el tiempo en la hora (milis)"+ System.currentTimeMillis());
		return System.currentTimeMillis();
	}
	
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORServidor(args[0]);

	}
	
}
