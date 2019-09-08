/*
 * ObjetoRemoto.java
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Objeto que implementa la interfaz remota
 */
public class ORSumaResta extends UnicastRemoteObject 
    implements IRSumaResta
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Construye una instancia de ObjetoRemoto
     * @throws RemoteException
     */
    protected ORSumaResta () throws RemoteException
    {
        super();
    }

    /**
     * Obtiene la suma de los sumandos que le pasan y la devuelve.
     */
    public int suma(int a, int b) 
    {
	    System.out.println ("Sumando " + a + " + " + b +"...");
        return a+b;
    }
    
    public int resta(int a, int b) 
    {
	    System.out.println ("Restando " + a + " - " + b +"...");
        return a-b;
    }
    
}
