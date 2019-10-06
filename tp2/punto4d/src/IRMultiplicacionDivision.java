import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRMultiplicacionDivision extends Remote{
    public int multiplicacion (int a, int b) throws RemoteException; 
    public int division (int a, int b) throws RemoteException;
    
    public void cambiarResultado(IRSumaResta objetoRemoto ) throws RemoteException; 
	public void cambiarResultado(ObjetoLocal objetoLocal) throws RemoteException;
}
