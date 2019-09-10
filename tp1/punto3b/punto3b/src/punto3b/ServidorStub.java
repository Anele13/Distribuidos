package punto3b;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorStub {
	
	private Respuesta respuesta = null;
	private Servidor server = new Servidor();


	public void handleClient(Argument request) throws ClassNotFoundException, IOException {
		if (request instanceof OpenArgument) {
			OpenArgument argumento = (OpenArgument)request;
			int fd = this.server.abrir(argumento.getFilename(),argumento.getPermisos());
			this.respuesta = new OpenRespuesta(fd);
		}
		
		else if (request instanceof ReadArgument) {
			ReadArgument argumento = (ReadArgument)request;
			ReadRespuesta resp = this.server.leer(argumento.getFd(), argumento.getCantidadALeer());
			this.respuesta = resp;
		}
		
		else if (request instanceof WriteArgument) {
			WriteArgument argumento = (WriteArgument)request;
			int status = this.server.escribir(argumento.getFd(), argumento.getBuf());
			WriteRespuesta resp = new WriteRespuesta(status);
			this.respuesta = resp;
		}
		
		else {
			CloseArgument argumento = (CloseArgument)request;
			int resultado = this.server.cerrar(argumento.getFd());
			this.respuesta = new CloseRespuesta(resultado);
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
