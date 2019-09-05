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
	
	Servidor server = new Servidor();
	ManejadorArchivos manejador;
	Respuesta respuesta = null;
	ServidorStub stub;
		
	
	public HiloServidor(Socket unSocketCliente, ManejadorArchivos manejador) {
		try {
			/* Ya hay una conexion con un cliente, streams de I/O */
			this.socketCliente = unSocketCliente;
			this.entrada= new ObjectInputStream(socketCliente.getInputStream());
			this.salida = new ObjectOutputStream(socketCliente.getOutputStream());
			
			this.manejador = manejador;
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	
	public void run()  {
		try{
			Argument request = (Argument)this.entrada.readObject();
			this.handleClient(request);
		    
			//Respuesta r = new Respuesta();
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
			File file = this.server.abrir(argumento.getFilename());
			OpenedFile of = new OpenedFile(file);
			manejador.setOpenedFile(of);		
			this.respuesta = new OpenRespuesta(of.getId());
		}
		
		else if (request instanceof ReadArgument) {
			ReadArgument argumento = (ReadArgument)request;
			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
			ReadRespuesta resp = this.server.leer(argumento.getCantidadALeer(),of.getFileInputStream());
			this.respuesta = resp;
		}
		
		else if (request instanceof WriteArgument) {
			WriteArgument argumento = (WriteArgument)request;
			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());	
			WriteRespuesta resp = this.server.escribir(argumento.getBuf(), of.getFileOutputStream());
			this.respuesta = resp;
		}
		
		else {
			CloseArgument argumento = (CloseArgument)request;
			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
			int resultado = this.server.cerrar(of.dameFis(), of.dameFos());
			manejador.deleteOpenedFileById(of.getId());
			this.respuesta = new CloseRespuesta(resultado);
		}
	}
	
}
