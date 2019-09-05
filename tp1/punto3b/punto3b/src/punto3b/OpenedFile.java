package punto3b;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpenedFile {

	private static int count= 0;
	private int id= ++count;
	private File file;
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;
	
	
	public int getId() {
		return id;
	}
	

	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	
	public FileInputStream getFileInputStream() throws FileNotFoundException {
		if (this.fileInputStream != null) {
			System.out.println("abierto entonces devuelvo");
			return this.fileInputStream;
		}
		else {
			System.out.println("CERRDO ENTONCES ABRO");
			this.setFileInputStream(new FileInputStream(this.getFile()));
			return this.fileInputStream;
		}
	}
		
	
	public FileOutputStream getFileOutputStream() throws FileNotFoundException {
		if(this.fileOutputStream != null) {
			return this.fileOutputStream;
		}
		else {
			this.setFileOutputStream(new FileOutputStream(this.getFile()));
			return this.fileOutputStream;
		}
	}
		
	
	public void setFileOutputStream(FileOutputStream fileOutputStream) {
		this.fileOutputStream = fileOutputStream;
	}
	
	
	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
	public FileInputStream dameFis() {
		return this.fileInputStream;
	}
	
	
	public FileOutputStream dameFos() {
		return this.fileOutputStream;
	}
	
	
	public OpenedFile(File file) throws FileNotFoundException {
		this.setFile(file);
	}
}