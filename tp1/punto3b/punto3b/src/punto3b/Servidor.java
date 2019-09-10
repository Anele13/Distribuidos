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
	
	
	//Leer
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
	
	
	
	//Escribir
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
	
	
	//Cerrar
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
}