import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

	public class ORMultiplicacionDivision extends UnicastRemoteObject implements IRMultiplicacionDivision {
   
	private int resultado;
	private static final long serialVersionUID = 1L;

	
    public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	protected ORMultiplicacionDivision (String host) throws RemoteException, MalformedURLException {
        super();
		String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORMultiplicacionDivision";
		Naming.rebind(rname, this);
    }

	@Override
	public int multiplicacion(int a, int b) throws RemoteException {
		System.out.println ("Multiplicando " + a + " + " + b +"...");
		this.setResultado(a*b);
		System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("Resultado: " +this.getResultado());
        return this.getResultado();
	}


	@Override
	public int division(int a, int b) throws RemoteException {
		System.out.println ("Dividiendo " + a + " + " + b +"...");
		this.setResultado(a/b);
		System.out.println("Waiting..");
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    System.out.println("Resultado: " +this.getResultado());
        return this.getResultado();
	}

	public void cambiarResultado(IRSumaResta objetoRemoto ) throws RemoteException {
		objetoRemoto.setResultado(-1);
	}
	
	public void cambiarResultado(ObjetoLocal objetoLocal) {
		objetoLocal.resultado = -1;
	}
	
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        new ORMultiplicacionDivision(args[0]);
	}
}