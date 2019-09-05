package punto3b;

import java.io.FileDescriptor;

public class ReadArgument extends Argument {

	int fd;
	int cantidadALeer;

	public int getCantidadALeer() {
		return cantidadALeer;
	}

	public void setCantidadALeer(int cantidadALeer) {
		this.cantidadALeer = cantidadALeer;
	}

	public int getFd() {
		return fd;
	}

	public void setFd(int fd) {
		this.fd = fd;
	}
	
	public ReadArgument(int fd, int cantidadALeer) {
		this.setFd(fd);
		this.setCantidadALeer(cantidadALeer);
	}
}
