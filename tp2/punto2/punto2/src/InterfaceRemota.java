/*
 * InterfaceRemota.java
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface remota con los métodos que se pueden llamar en remoto
 */
public interface InterfaceRemota extends Remote {
	
	public OpenRespuesta abrir(OpenArgument request) throws RemoteException, IOException; 
	public ReadRespuesta leer(ReadArgument request) throws RemoteException, FileNotFoundException; 
	public WriteRespuesta escribir(WriteArgument request) throws RemoteException, FileNotFoundException; 
	public CloseRespuesta cerrar(CloseArgument request) throws RemoteException, IOException; 

}
