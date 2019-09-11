package punto3b;


public class ClienteStub {
	private String host;
	private int port;
	
	
	
	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}

	
	
	public ClienteStub (String host, int port) {
		this.host = host;
		this.port = port;
	}

	public int abrir(String filename) {
		OpenArgument argumento = new OpenArgument("777", filename);
		SocketClient s = new SocketClient(this.host, this.port);
		OpenRespuesta respuesta = (OpenRespuesta)s.run(argumento);
		return respuesta.getFd();
	}
	
	
	public ReadRespuesta leer(int cantidad, int fd) {
		ReadArgument argumento = new ReadArgument(fd, cantidad);
		SocketClient s = new SocketClient(this.host, this.port);
		ReadRespuesta respuesta = (ReadRespuesta)s.run(argumento);
		return respuesta;		
	}
	
	
	public int escribir(byte[] arreglo, int fd) {
		WriteArgument argumento = new WriteArgument(arreglo, fd);
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
