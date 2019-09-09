/*
 * ObjetoRemoto.java
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;


public class ORSumaResta extends UnicastRemoteObject implements IRSumaResta {

	private static final long serialVersionUID = 1L;
	private int segundos;


    protected ORSumaResta (String host, String segundos) throws RemoteException, MalformedURLException {
        super();
        this.segundos = Integer.parseInt(segundos);
        String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORSumaResta";
		Naming.rebind(rname, this);
    }

    public int suma(int a, int b) {
	    System.out.println ("Sumando " + a + " + " + b +"...");
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(this.segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Respondido");
        return a+b;
    }
    
    public int resta(int a, int b) {
	    System.out.println ("Restando " + a + " - " + b +"...");
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(this.segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Respondido");
        return a-b;
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORSumaResta(args[0],args[1]);
	}
}
