import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
	public class ORMultiplicacionDivision extends UnicastRemoteObject 
    implements IRMultiplicacionDivision
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Construye una instancia de ObjetoRemoto
     * @throws RemoteException
     */
    protected ORMultiplicacionDivision () throws RemoteException
    {
        super();
    }

	@Override
	public int multiplicacion(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Resolviendo multiplliacion "+a + " * "+b+" = ...");
		return a*b;
	}


	@Override
	public int division(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		if (b == 0) {
			System.out.println("no se puede dividir por cero.");
			return 0;
		}
		System.out.println("Resolviendo division "+a + " / "+b+" = ...");
		
		return a/b;
	}

    
}