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
	
	public int abrir(String filename, String permisos) throws IOException {

		File file = new File(filename);
		OpenedFile of = new OpenedFile(file);
		this.manejador.setOpenedFile(of);
		return of.getId();
	}
	
	
	//Leer
	public ReadRespuesta leer(int fd, int cantidadALeer) throws FileNotFoundException {

		OpenedFile of = this.manejador.getOpenedFileById(fd);
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
	public WriteRespuesta escribir(int fd, byte[] buffer) throws FileNotFoundException {
		OpenedFile of = this.manejador.getOpenedFileById(fd);
		FileOutputStream fos = of.getFileOutputStream();		
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
	public int cerrar(int fd) throws IOException {
		OpenedFile of = this.manejador.getOpenedFileById(fd);
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