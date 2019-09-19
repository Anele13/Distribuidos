package punto3b;


public class ClienteStub {
	private String host;
	private int port;

	
	public ClienteStub (String host, int port) {
		this.host = host;
		this.port = port;
	}

	public int abrir(String filename, String permisos) {
		OpenArgument argumento = new OpenArgument("777", filename);
		SocketClient s = new SocketClient(this.host, this.port);
		OpenRespuesta respuesta = (OpenRespuesta)s.run(argumento);
		return respuesta.getFd();
	}
	
	
	public int leer(int fd, byte[]buffer, int cantidadALeer) {
		ReadArgument argumento = new ReadArgument(fd, cantidadALeer);
		SocketClient s = new SocketClient(this.host, this.port);
		ReadRespuesta respuesta = (ReadRespuesta)s.run(argumento);
		if (respuesta.getCantidadLeida() != -1) {
			System.arraycopy(respuesta.getBuffer(),0,buffer, 0, respuesta.getCantidadLeida());
		}		
		return respuesta.getCantidadLeida();		
	}
	
	
	public int escribir(int fd, byte[] arreglo, int cantidadAEscribir) {
		WriteArgument argumento = new WriteArgument(arreglo, fd, cantidadAEscribir);
		SocketClient s = new SocketClient(this.host, this.port);
		WriteRespuesta respuesta = (WriteRespuesta)s.run(argumento);
		return respuesta.getStatus();
	}
	
	
	public int cerrar(int fd) {
		CloseArgument argumento = new CloseArgument(fd);
		SocketClient s = new SocketClient(this.host, this.port);
		CloseRespuesta respuesta = (CloseRespuesta)s.run(argumento);
		return respuesta.getStatus();
	}

}
