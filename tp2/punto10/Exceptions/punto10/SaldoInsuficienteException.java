package punto10;

public class SaldoInsuficienteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(String mensaje) {
		super(mensaje);
	}
}
