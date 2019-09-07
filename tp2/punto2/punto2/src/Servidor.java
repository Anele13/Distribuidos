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

	
	private File _abrir(String filename) throws IOException {
		File file = new File(filename);
		return file;
	}
	
	
	private ReadRespuesta _leer(int cantidadALeer, FileInputStream fis) {
		ReadRespuesta resp = null;
		StringBuffer buf = new StringBuffer("");
		boolean hayMasDatos = true;
		try {
			int i;
			int contador = 0;
			
			while (contador < cantidadALeer) {
				++contador;
				i = fis.read();
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
	
	
	
	private WriteRespuesta _escribir(byte[] buffer, FileOutputStream fos) {
		WriteRespuesta resp = null;
		try {
			fos.write(buffer);
			resp = new WriteRespuesta(0);
		} catch (IOException e) {
			resp = new WriteRespuesta(1);
			e.printStackTrace();
		}
		return resp;
	}
	
	
	private int _cerrar(FileInputStream fis, FileOutputStream fos) throws IOException {
		if (fis != null) {
			fis.close();
		}
		if (fos != null) {
			fos.close();
		}
		return 0;
	}
	
	
	
	
/* ------------------- METODOS PUBLICOS ------------------ */
	
	public OpenRespuesta abrir(OpenArgument request) throws IOException {
		OpenArgument argumento = (OpenArgument)request;
		File file = _abrir(argumento.getFilename());
		OpenedFile of = new OpenedFile(file);
		manejador.setOpenedFile(of);
		return new OpenRespuesta(of.getId());
	}
	
	public ReadRespuesta leer(ReadArgument request) throws FileNotFoundException {
		ReadArgument argumento = (ReadArgument)request;
		OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
		ReadRespuesta resp = _leer(argumento.getCantidadALeer(),of.getFileInputStream());
		return resp;
	}
	
	public WriteRespuesta escribir(WriteArgument request) throws FileNotFoundException {
		WriteArgument argumento = (WriteArgument)request;
		OpenedFile of = manejador.getOpenedFileById(argumento.getFd());	
		WriteRespuesta resp = _escribir(argumento.getBuf(), of.getFileOutputStream());
		return resp;
	}
	
	public CloseRespuesta cerrar(CloseArgument request) throws IOException {
		CloseArgument argumento = (CloseArgument)request;
		OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
		int resultado = _cerrar(of.dameFis(), of.dameFos());
		manejador.deleteOpenedFileById(of.getId());
		return new CloseRespuesta(resultado);
	}
	

	
/* ------------------- MAIN PRINCIPAL ------------------ */  
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
		new Servidor();
	}

}
