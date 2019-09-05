package punto3b;

public class OpenArgument extends Argument {
	String filename;
	String permisos;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPermisos() {
		return permisos;
	}

	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}

	public OpenArgument(String permisos, String filename) {
		this.setFilename(filename);
		this.setPermisos(permisos);
	}
}
