package punto3b;


public class OpenRespuesta extends Respuesta{

	private static final long serialVersionUID = 1L;
	private int fd;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFd() {
		return fd;
	}

	public void setFd(int fd) {
		this.fd = fd;
	}
	
	public OpenRespuesta(int fd) {
		this.setFd(fd);		
	}
}
