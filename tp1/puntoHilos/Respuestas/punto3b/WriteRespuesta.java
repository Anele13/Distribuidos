package punto3b;

public class WriteRespuesta extends Respuesta{

	int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public WriteRespuesta(int status) {
		this.setStatus(status);
	}
}
