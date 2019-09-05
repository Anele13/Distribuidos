package punto3b;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
	int port;
	String host;
	Respuesta respuesta = null;
	
	public SocketClient(String host, int port) {
	  this.port = port;
	  this.host = host;
	}
	
	public Respuesta run(Argument argumento) {
		try {
			Socket s = new Socket(this.host, this.port);
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
      		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	        out.writeObject(argumento);
	        Respuesta r  = (Respuesta)in.readObject();
	        this.respuesta = r;
	        
			s.close();
	   }
	   catch( Exception e) {
		   System.out.println("ERROR");
		   e.printStackTrace();
	   }
		return this.respuesta;
	}
}
