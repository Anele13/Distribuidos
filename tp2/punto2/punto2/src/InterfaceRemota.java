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
	
	public int abrir(String filename, String permisos) throws RemoteException; 
	public byte[] leer(int fd, int cantidadALeer) throws RemoteException; 
	public int escribir(int fd, byte[] buffer, int cantidadAEscribir) throws RemoteException; 
	public int cerrar(int fd) throws RemoteException; 

}
