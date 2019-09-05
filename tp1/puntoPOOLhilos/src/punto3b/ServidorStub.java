package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServidorStub extends Thread {
	

	 //Servidor server = new Servidor();
	 ManejadorArchivos manejador = new ManejadorArchivos();
	

	public void run() {
		try {
			ExecutorService pool = Executors.newFixedThreadPool(5);
			
			ServerSocket escuchandoSocket = new ServerSocket(7896);
      		while (true) {
    		    Socket socketCliente = escuchandoSocket.accept();
		    	HiloServidor runnable = new HiloServidor(socketCliente, this.manejador);
		    	pool.execute(runnable);
      	   	}
		}
     	catch(Exception e) {
      		e.printStackTrace();
     	}
	}

}
	
