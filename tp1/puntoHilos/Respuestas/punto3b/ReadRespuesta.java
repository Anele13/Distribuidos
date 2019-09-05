package punto3b;

public class ReadRespuesta extends Respuesta {

	byte[] buf;
	boolean hayMasDatos;

	
	public String getBuffer() {
		return new String (this.buf);
	}
	
	
	public void setBuffer(byte[] buffer) {
		this.buf = buffer;
	}
	
	
	public boolean isHayMasDatos() {
		return hayMasDatos;
	}
	
	
	public void setHayMasDatos(boolean hayMasDatos) {
		this.hayMasDatos = hayMasDatos;
	}
	
	
	public ReadRespuesta(byte[] buffer, boolean hayMasDatos) {
		this.setBuffer(buffer);
		this.setHayMasDatos(hayMasDatos);
	}
}
