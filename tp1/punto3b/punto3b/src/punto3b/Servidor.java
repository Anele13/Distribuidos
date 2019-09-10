package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Servidor {
	private ManejadorArchivos manejador = new ManejadorArchivos();
	
	//Abrir
	
	public int abrir(String filename,Argument request,String host, int port) throws IOException {
//No usamos host ni port, pero están para que los prototipos de 
//los metodos a nivel aplicacion sean iguales.
		File file = new File(filename);
		OpenedFile of = new OpenedFile(file);
		this.manejador.setOpenedFile(of);
		
		return of.getId();
	}
	
	
	//Leer
	public ReadRespuesta leer(int fd, String host, int port,Argument request) throws FileNotFoundException {
//No usamos host, port ni fd, pero están para que los prototipos de 
//los metodos a nivel aplicacion sean iguales.
		ReadArgument argumento = (ReadArgument)request;
		OpenedFile of = this.manejador.getOpenedFileById(argumento.getFd());
		int cantidadALeer = argumento.getCantidadALeer();
		FileInputStream fis = of.getFileInputStream();
				
		
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
	
	
	
	//Escribir
	public WriteRespuesta escribir(int fd, String host, int port,Argument request) throws FileNotFoundException {
	//No usamos host, port ni fd, pero están para que los prototipos de 
	//los metodos a nivel aplicacion.
		WriteArgument argumento = (WriteArgument)request;
		OpenedFile of = this.manejador.getOpenedFileById(argumento.getFd());
		FileOutputStream fos = of.getFileOutputStream();
		byte[] buffer = argumento.getBuf();
		
		
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
	
	//Cerrar
	public int cerrar(String host, int port,Argument request) throws IOException {
//No usamos host ni port pero están para que los prototipos de 
//los metodos a nivel aplicacion sean iguales.
		CloseArgument argumento = (CloseArgument)request;
		OpenedFile of = this.manejador.getOpenedFileById(argumento.getFd());
		FileInputStream fis = of.dameFis();
		FileOutputStream fos = of.dameFos();
		
		
		if (fis != null) {
			fis.close();
		}
		
		if (fos != null) {
			fos.close();
		}
		return 0;
	}
}