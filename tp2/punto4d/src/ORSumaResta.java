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

	public int resultado;
	private static final long serialVersionUID = 1L;
	
	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}    

	protected ORSumaResta (String host) throws RemoteException, MalformedURLException {
        super();
        String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORSumaResta";
		Naming.rebind(rname, this);
    }

    public int suma(int a, int b) {
	    System.out.println ("Sumando " + a + " + " + b +"...");
	    this.setResultado(a+b);
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("Resultado: " +this.getResultado());
        return this.getResultado();
    }
    
    public int resta(int a, int b) {
	    System.out.println ("Restando " + a + " - " + b +"...");
	    this.setResultado(a-b);
	    System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("Resultado: " +this.getResultado());
	    return this.getResultado();
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORSumaResta(args[0]);
	}
}
