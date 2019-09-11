package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Servidor {
	private Respuesta respuesta = null;
	private ManejadorArchivos manejador = new ManejadorArchivos();
	
	public Respuesta getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}


	public ManejadorArchivos getManejador() {
		return manejador;
	}


	public void setManejador(ManejadorArchivos manejador) {
		this.manejador = manejador;
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
	// Devuelve una escructura indicando buffer y flag si hay mas datos.
	//[TODO] NO TIENE QUE DEVOLVER UN READRESPUESTA, TIENE QUE DEVOLVER UN INT, EL TAMAÑO LEIDO.
	public int leer(int fd, int cantidadALeer) {
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
			this.respuesta = new ReadRespuesta((new String(buf)).getBytes(), hayMasDatos);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return buf.length();
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
}