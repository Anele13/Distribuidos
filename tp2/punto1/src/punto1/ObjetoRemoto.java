/*
 * ObjetoRemoto.java
 */
package punto1;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ObjetoRemoto extends UnicastRemoteObject implements InterfaceRemota{

	private static final long serialVersionUID = 1L;

	
	protected ObjetoRemoto () throws RemoteException, MalformedURLException{
        super();
        String rname = "//localhost:" + Registry.REGISTRY_PORT  + "/ObjetoRemoto";
        Naming.rebind (rname, this);
    }

	
    @Override
    public int suma(int a, int b){
	    System.out.println ("Sumando " + a + " + " + b +"...");
        return a+b;
    }
    

	@Override
	public int resta(int a, int b) throws RemoteException {
		System.out.println("restando" + a + " - " + b + "....");
		return a -b;
	}
	
	
	public static void main(String[] args) throws RemoteException {
		try {
			new ObjetoRemoto();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
    
}
