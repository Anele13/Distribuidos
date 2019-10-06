package punto10;

public class CuentaBloqueadaException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CuentaBloqueadaException(String mensaje) {
		super(mensaje);
	}

}
