package punto3b;

import java.io.IOException;

public class Main {
	static String[] cosa;
	
	public static void main(String args[]) throws IOException  {

		Cliente cliente = new Cliente();
		ServidorStub server = new ServidorStub();
		server.run();
	}
}
