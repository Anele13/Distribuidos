package punto3b;

public class CloseArgument extends Argument {

	int fd;

	public int getFd() {
		return fd;
	}

	public void setFd(int fd) {
		this.fd = fd;
	}
	
	public CloseArgument(int fd) {
		this.setFd(fd);
	}
}
