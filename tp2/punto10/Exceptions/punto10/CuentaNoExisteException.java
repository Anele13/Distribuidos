package punto10;

public class CuentaNoExisteException extends Exception {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CuentaNoExisteException(String mensaje) {
		super(mensaje);
	}

}
