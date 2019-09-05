package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorStub extends Thread {
	

	 //Servidor server = new Servidor();
	 ManejadorArchivos manejador = new ManejadorArchivos();
	

	public void run() {
		try {
			ServerSocket escuchandoSocket = new ServerSocket(7896);
      		while (true) {
    		    Socket socketCliente = escuchandoSocket.accept();
		    	HiloServidor hilo = new HiloServidor(socketCliente, this.manejador);
      	   	}
		}
     	catch(Exception e) {
      		e.printStackTrace();
     	}
	}

}
	
