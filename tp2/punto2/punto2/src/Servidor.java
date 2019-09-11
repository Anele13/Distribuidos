/*
 * ObjetoRemoto.java
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Servidor extends UnicastRemoteObject implements InterfaceRemota {
	
	private static final long serialVersionUID = 1L;
	private ManejadorArchivos manejador = new ManejadorArchivos();
	
	
	protected Servidor () throws RemoteException, MalformedURLException  {
        super();
		String rname = "//localhost:" + Registry.REGISTRY_PORT  + "/Servidor";
        Naming.rebind (rname, this);
    }

	
	// Abrir
	// Si pudo abrir el archivo devuelve el file descriptor. caso contrario devuelve -1
	public int abrir(String filename, String permisos) {
		int fd;
		
		try {
			OpenedFile of = new OpenedFile(new File(filename));
			this.manejador.setOpenedFile(of);
			fd = of.getId();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			fd = -1;
		}
		return fd;
	}
	
	
	// Leer
	// Devuelve una estructura indicando buffer y flag si hay mas datos.
	public ReadRespuesta leer(int fd, int cantidadALeer) {
		OpenedFile of = this.manejador.getOpenedFileById(fd);
		ReadRespuesta resp = null;
		StringBuffer buf = new StringBuffer("");
		boolean hayMasDatos = true;

		try {
			int i;
			int contador = 0;
			
			while (contador < cantidadALeer) {
				++contador;
				i = of.getFileInputStream().read();
				if (i == -1) {
					hayMasDatos = false;
					break;
				}
				else {
					buf.append((char) i);
				}
			}
			resp = new ReadRespuesta((new String(buf)).getBytes(), hayMasDatos);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	
	// Escribir
	// Devuelve la cantidad de datos escritos. en caso de error -1
	public int escribir(int fd, byte[] buffer){
		OpenedFile of = this.manejador.getOpenedFileById(fd);
		int resp;
		
		try {
			of.getFileOutputStream().write(buffer);
			resp = buffer.length;
		} catch (IOException e) {
			resp = -1;
			e.printStackTrace();
		}
		return resp;
	}
	
	
	// Cerrar
	// Devuelve 0 si pudo cerrar el archivo, 1 en caso contrario.
	public int cerrar(int fd){
		OpenedFile of = this.manejador.getOpenedFileById(fd);
		FileInputStream fis = of.dameFis();
		FileOutputStream fos = of.dameFos();
		
		try {				
			if (fis != null) {
				fis.close();
			}
			
			if (fos != null) {
				fos.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	

	
/* ------------------- MAIN PRINCIPAL ------------------ */  
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
		new Servidor();
	}

}
