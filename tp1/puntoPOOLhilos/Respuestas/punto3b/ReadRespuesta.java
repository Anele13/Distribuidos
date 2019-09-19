package punto3b;

public class ReadRespuesta extends Respuesta {

	byte[] buf = new byte[50];
	int cantidadLeida;

	
	public byte[] getBuffer() {
		return this.buf;
	}
	
	
	public void setBuffer(byte[] buffer) {
		this.buf = buffer;
	}
	
	
	public int getCantidadLeida() {
		return cantidadLeida;
	}
	
	
	public void setCantidadLeida(int cantidad) {
		this.cantidadLeida = cantidad;
	}
	
}
