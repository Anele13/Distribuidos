package punto3b;

public class WriteArgument extends Argument{

	byte[] buf;
	int	 fd;
	
	public byte[] getBuf() {
		return buf;
	}
	public void setBuf(byte[] buf) {
		this.buf = buf;
	}
	public int getFd() {
		return fd;
	}
	public void setFd(int fd) {
		this.fd = fd;
	}
	
	public WriteArgument(byte[] arreglo, int fd) {
		this.setBuf(arreglo);
		this.setFd(fd);
	}
	
}
