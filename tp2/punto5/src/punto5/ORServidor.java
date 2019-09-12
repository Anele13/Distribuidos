package punto5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ORServidor extends UnicastRemoteObject implements InterfazRemota{

	protected ORServidor() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getTiempo() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Me pidieron mi tiempo.");
		return System.currentTimeMillis();
	}

}
