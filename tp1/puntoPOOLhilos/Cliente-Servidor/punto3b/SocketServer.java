package punto3b;

import java.net.*;
import java.io.*; 

public class SocketServer {
	
	public static void main(String args[])  {
		ServidorStub stub = new ServidorStub();
		ManejadorArchivos manejador = new ManejadorArchivos();
		try {
      		ServerSocket escuchandoSocket = new ServerSocket(7896);
      		while (true) {
    		    Socket socketCliente = escuchandoSocket.accept();
    			ObjectInputStream in = new ObjectInputStream(socketCliente.getInputStream());
    	        ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream());
    			Argument request = (Argument)in.readObject();
    		    //Respuesta respuesta = stub.handleClient(request, manejador);
    		    out.writeObject(new Respuesta());
    		    socketCliente.close();
      	   	}
		}
     	catch(Exception e) {
      		e.printStackTrace();
     	}
	}
}