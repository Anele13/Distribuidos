package punto3b;

public class CloseRespuesta extends Respuesta {

	private static final long serialVersionUID = 1L;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public CloseRespuesta(int status) {
		this.setStatus(status);
	}
	
}