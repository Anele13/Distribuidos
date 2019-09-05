package punto3b;

import java.util.ArrayList;
import java.util.List;


public class ManejadorArchivos {

	private List<OpenedFile> arrayOpenedFiles = new ArrayList<OpenedFile>();
	
	
	public OpenedFile getOpenedFileById(int fileDescriptor) {
		OpenedFile of = null;
		for (int i = 0; i < arrayOpenedFiles.size(); i++) {
			of = arrayOpenedFiles.get(i);
			if (of.getId() == fileDescriptor) {
				return of;
			}
			else {
				of = null;
			}
		}
		return of;
	}
	
	
	public void deleteOpenedFileById(int id) {
		//System.out.println("ELIMINANDO ARCHIVOS");
		//System.out.println("ID: "+id);
		//System.out.println("leng:"+ arrayOpenedFiles.size());
		boolean encontrado = false;
		OpenedFile of = null;
		for (int i = 0; i < arrayOpenedFiles.size(); i++) {
			of = arrayOpenedFiles.get(i);
			if (of.getId() == id) {
				encontrado = true;
				break;
			}
		}
		if (encontrado) {
			arrayOpenedFiles.remove(of);
			of = null;
		}
		//System.out.println("leng:"+ arrayOpenedFiles.size());
		
	}
	
	
	public void setOpenedFile(OpenedFile openedFile) {
		this.arrayOpenedFiles.add(openedFile);
	}
}

