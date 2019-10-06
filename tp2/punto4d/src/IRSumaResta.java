/*
 * InterfaceRemota.java
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRSumaResta extends Remote {
    public int suma (int a, int b) throws RemoteException; 
    public int resta (int a, int b) throws RemoteException;
    public int getResultado() throws RemoteException;
    public void setResultado(int numero) throws RemoteException;
}
