/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

import java.rmi.Naming;                    /* lookup         */
import java.rmi.registry.Registry;         /* REGISTRY_PORT  */

import java.net.MalformedURLException;     /* Exceptions...  */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;



public class Cliente {
    
    public Cliente(String alfa) {
        try {

		    String rname = "//" + alfa + ":" + Registry.REGISTRY_PORT + "/Servidor";
		    InterfaceRemota objetoRemoto = (InterfaceRemota)Naming.lookup (rname);
	        objetoRemoto.nada();
	    
        } 
        catch (MalformedURLException e) {
        	e.printStackTrace();
		}
        catch (RemoteException e) {
		    e.printStackTrace();
		}
        catch (NotBoundException e) {
		    e.printStackTrace();
		}
    }
    

    public static void main(String[] args) {
        new Cliente(args[0]);
    }
    
}
