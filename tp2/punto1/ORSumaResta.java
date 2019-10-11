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


    protected ORSumaResta (String host) throws RemoteException, MalformedURLException {
        super();
        String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORSumaResta";
        Naming.rebind(rname, this);
		
    }

    public int suma(int a, int b) {
	    System.out.println ("Sumando " + a + " + " + b +"...");
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return a+b;
    }
    
    public int resta(int a, int b) {
	    System.out.println ("Restando " + a + " - " + b +"...");
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return a-b;
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
//        System.setProperty("java.rmi.server.hostname","192.168.2.148");

    	new ORSumaResta(args[0]);
	}
}