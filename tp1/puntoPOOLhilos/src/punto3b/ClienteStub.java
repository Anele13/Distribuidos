package punto3b;


public class ClienteStub {
	
	public int abrir(String filename,String host, int port) {
		OpenArgument argumento = new OpenArgument("777", filename);
		SocketClient s = new SocketClient(host, port);
		OpenRespuesta respuesta = (OpenRespuesta)s.run(argumento);
		return respuesta.getFd();
	}
	
	
	public ReadRespuesta leer(int cantidad, int fd,String host, int port) {
		ReadArgument argumento = new ReadArgument(fd, cantidad);
		SocketClient s = new SocketClient(host, port);
		ReadRespuesta respuesta = (ReadRespuesta)s.run(argumento);
		return respuesta;		
	}
	
	
	public int escribir(byte[] arreglo, int fd,String host,int port) {
		WriteArgument argumento = new WriteArgument(arreglo, fd);
		SocketClient s = new SocketClient(host, port);
		WriteRespuesta respuesta = (WriteRespuesta)s.run(argumento);
		return respuesta.getStatus();
	}
	
	
	public int cerrar(int fd,String host,int port) {
		CloseArgument argumento = new CloseArgument(fd);
		SocketClient s = new SocketClient(host, port);
		CloseRespuesta respuesta = (CloseRespuesta)s.run(argumento);
		return respuesta.getStatus();
	}
}
