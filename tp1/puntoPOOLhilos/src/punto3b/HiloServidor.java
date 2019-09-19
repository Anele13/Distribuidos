package punto3b;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloServidor implements Runnable{

	Socket socketCliente;
	ObjectInputStream entrada;
	ObjectOutputStream salida;
	Servidor server;
	Respuesta respuesta = null;
	
		
	
	public HiloServidor(Socket unSocketCliente, Servidor server) {
		try {
			/* Ya hay una conexion con un cliente, streams de I/O */
			this.socketCliente = unSocketCliente;
			this.entrada= new ObjectInputStream(socketCliente.getInputStream());
			this.salida = new ObjectOutputStream(socketCliente.getOutputStream());
			this.server = server;
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	
	public void run()  {
		try{
			Argument request = (Argument)this.entrada.readObject();
			this.handleClient(request);
			this.salida.writeObject(this.respuesta);
		    this.socketCliente.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void handleClient(Argument request) throws ClassNotFoundException, IOException {

		if (request instanceof OpenArgument) {
			OpenArgument argumento = (OpenArgument)request;
			int fd = this.server.abrir(argumento.getFilename(),argumento.getPermisos());
			this.respuesta = new OpenRespuesta(fd);
		}
		
		else if (request instanceof ReadArgument) {
			ReadArgument argumento = (ReadArgument)request;
			ReadRespuesta resp = new ReadRespuesta();
			int cantidadLeida;
			cantidadLeida = this.server.leer(argumento.getFd(), resp.getBuffer(), argumento.getCantidadALeer());//que leer devuelva entero o en su defecto el buffer y se arme al respuesta aca.
			resp.setCantidadLeida(cantidadLeida);
			this.respuesta = resp;
		}
		
		else if (request instanceof WriteArgument) {
			WriteArgument argumento = (WriteArgument)request;
			int status = this.server.escribir(argumento.getFd(), argumento.getBuf(), argumento.getCantidadAEscribir());
			WriteRespuesta resp = new WriteRespuesta(status);
			this.respuesta = resp;
		}
		
		else {
			CloseArgument argumento = (CloseArgument)request;
			int resultado = this.server.cerrar(argumento.getFd());
			this.respuesta = new CloseRespuesta(resultado);
		}
	}
	
}
