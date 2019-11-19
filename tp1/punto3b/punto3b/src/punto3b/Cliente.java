package punto3b;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Cliente  implements ActionListener{
	

	public static void main(String[] args) {
	}
	
	public Cliente() {
		Ventana v = new Ventana(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String host,fileLocal,fileServer;
		int port;

		Component[] componentes = getComponentes(e); //Obtengo los componentes del JFrame e inicializo objetos de los que quiero.
		Texto hostTextBox = (Texto)componentes[0];
		Texto portTextBox = (Texto)componentes[1];
		Texto fileLocalTextBox = (Texto)componentes[2];
		Texto fileServerTextBox = (Texto)componentes[3];
		JTextArea textAreaBox = (JTextArea)componentes[4];
		host = hostTextBox.getText();
		port = Integer.parseInt(portTextBox.getText());
		fileLocal = fileLocalTextBox.getText();
		fileServer = fileServerTextBox.getText();
		
		if (e.getActionCommand() == "Leer") {

			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        System.out.println("Hora de inicio lectura");
	        System.out.println( sdf.format(cal.getTime()) );
			
	             

			int max = 1000;
			int fd;
			int cantidadLeida;
			byte[] buffer = new byte[max];
			FileOutputStream fos = null;
			
			ClienteStub stub = new ClienteStub(host, port);
			fd = stub.abrir(fileServer, "777");
			textAreaBox.setText("");
			cantidadLeida = stub.leer(fd, buffer, max);
			if (cantidadLeida != -1) {
				
				
				try {
					fos = new FileOutputStream(new File(fileLocal));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			
			try {
				while(cantidadLeida != -1) {
					textAreaBox.append(new String(buffer));
					fos.write(buffer,0,cantidadLeida);
					cantidadLeida = stub.leer(fd, buffer, max);
				}
				stub.cerrar(fd);
				fos.close();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			}
	        System.out.println("Hora de finalizacion lectura");
	        Calendar cal2 = Calendar.getInstance();
	        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	        System.out.println( sdf2.format(cal2.getTime()) );
		}

		
		
		if (e.getActionCommand() == "Escribir") {
			System.out.println("Voy a comenzar una escritura");

			FileInputStream fis = null;
			try {
				fis = new FileInputStream(new File(fileLocal));
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			
			int fd;
			int i;
			int max = 50;
			byte[] buffer = new byte[max];
			
			ClienteStub stub = new ClienteStub(host,port);
			fd = stub.abrir(fileServer,"777");
			try {		
				while (true) {
					i = fis.read(buffer,0,max);
					if (i == -1) {
						break;
					}
					stub.escribir(fd,buffer, i);
				}
				stub.cerrar(fd);
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	private Component[] getComponentes(ActionEvent e) { //Funcion que obtiene la lista de componentes del jpanel.
		Component component = (Component) e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(component);//Obtengo el jframe.
		Component[] componentes = frame.getContentPane().getComponents();//Obtengo todos los componentes colocados en el contentpane del jframe.
		return componentes;
	}
}
