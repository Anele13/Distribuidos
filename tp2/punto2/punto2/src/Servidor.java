/*
 * ObjetoRemoto.java
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Objeto que implementa la interfaz remota
 */
public class Servidor extends UnicastRemoteObject implements InterfaceRemota {

	protected Servidor () throws RemoteException, MalformedURLException  {
        super();
		String rname = "//localhost:" + Registry.REGISTRY_PORT  + "/Servidor";
        Naming.rebind (rname, this);
    }


	public void nada () {
		System.out.println("HOLA CAPOOOOO");
	}
	
    public int suma(int a, int b) 
    {
	    System.out.println ("Sumando " + a + " + " + b +"...");
        return a+b;
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
		new Servidor();
	}
}
