import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

	public class ORMultiplicacionDivision extends UnicastRemoteObject implements IRMultiplicacionDivision {
   
	private int segundos;
	private static final long serialVersionUID = 1L;


    protected ORMultiplicacionDivision (String host, String segundos) throws RemoteException, MalformedURLException {
        super();
        this.segundos = Integer.parseInt(segundos);
		String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORMultiplicacionDivision";
		Naming.rebind(rname, this);
    }

	@Override
	public int multiplicacion(int a, int b) throws RemoteException {
		System.out.println("Resolviendo multiplliacion "+a + " * "+b+" = ...");
		System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(this.segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Respondido");
		return a*b;
	}


	@Override
	public int division(int a, int b) throws RemoteException {
		System.out.println("Resolviendo division "+a + " / "+b+" = ...");
		System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(this.segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Respondido");
		return a/b;
	}

	
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORMultiplicacionDivision(args[0], args[1]);
	}
}