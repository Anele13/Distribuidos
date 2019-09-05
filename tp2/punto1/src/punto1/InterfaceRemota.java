/*
 * InterfaceRemota.java
 *
 */
package punto1;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemota extends Remote {
    public int suma (int a, int b) throws RemoteException; 
    public int resta(int a, int b) throws RemoteException;
}
