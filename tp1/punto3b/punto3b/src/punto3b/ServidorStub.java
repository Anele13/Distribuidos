package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorStub {
	
	private Respuesta respuesta = null;
	private Servidor server = new Servidor();
//	private ManejadorArchivos manejador = new ManejadorArchivos();
	

	public void handleClient(Argument request) throws ClassNotFoundException, IOException {
		if (request instanceof OpenArgument) {
			OpenArgument argumento = (OpenArgument)request;
//			File file = this.server.abrir(argumento.getFilename());
//			OpenedFile of = new OpenedFile(file);
//			manejador.setOpenedFile(of);
//			this.respuesta = new OpenRespuesta(of.getId());
			int id = this.server.abrir(argumento.getFilename(),request,"localhost",7896);
			this.respuesta = new OpenRespuesta(id);
		}
		
		else if (request instanceof ReadArgument) {
//			ReadArgument argumento = (ReadArgument)request;
//			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
//			ReadRespuesta resp = this.server.leer(argumento.getCantidadALeer(),of.getFileInputStream(),request);
			ReadRespuesta resp = this.server.leer(1,"localhost",7896,request);
			this.respuesta = resp;
		}
		
		else if (request instanceof WriteArgument) {
			WriteArgument argumento = (WriteArgument)request;
//			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());	
//			WriteRespuesta resp = this.server.escribir(argumento.getBuf(), of.getFileOutputStream());

			WriteRespuesta resp = this.server.escribir(1,"localhost",7896,request);
			this.respuesta = resp;
		}
		
		else {
//			CloseArgument argumento = (CloseArgument)request;
//			OpenedFile of = manejador.getOpenedFileById(argumento.getFd());
//			int resultado = this.server.cerrar(of.dameFis(), of.dameFos());
//			manejador.deleteOpenedFileById(of.getId());
			int resultado = this.server.cerrar("localhost", 7896, request);
			this.respuesta = new CloseRespuesta(0);
		}
	}
	
	public void run() {
		try {
      		ServerSocket escuchandoSocket = new ServerSocket(7896);
      		while (true) {
    		    Socket socketCliente = escuchandoSocket.accept();
    			ObjectInputStream in = new ObjectInputStream(socketCliente.getInputStream());
    	        ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream());
    			Argument request = (Argument)in.readObject();
    			this.handleClient(request);
    			out.writeObject(this.respuesta);
    		    socketCliente.close();
      	   	}
		}
     	catch(Exception e) {
      		e.printStackTrace();
     	}
	}
}
