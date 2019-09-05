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
//		Ventana v = new Ventana();
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
			
	       
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(fileLocal));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			boolean cosa = true;
			int fd;
			ClienteStub stub = new ClienteStub();
			fd = stub.abrir(fileServer,host, port);
			textAreaBox.setText("");
			while(cosa) {
				ReadRespuesta resp = stub.leer(50, fd, host, port);
				textAreaBox.append(resp.getBuffer());
				try {
					fos.write(resp.getBuffer().getBytes());
				} catch (IOException a) {
					a.printStackTrace();
				}
				cosa = resp.hayMasDatos;
			}
			stub.cerrar(fd, host, port);
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        System.out.println("Hora de finalizacion lectura");
	        System.out.println( sdf.format(cal.getTime()) );
		}

		
		
		if (e.getActionCommand() == "Escribir") {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(new File(fileLocal));
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			StringBuffer buf = new StringBuffer("");
			int fd;
			int i;
			int maxCaracteres = 1;
			
			ClienteStub stub = new ClienteStub();
			fd = stub.abrir(fileServer,host, port);

			try {
				
				while (true) {
					i = fis.read();
					if (i == -1) {
						break;
					}
					buf.append((char)i);
					if (buf.length() == maxCaracteres) {
						stub.escribir(new String(buf).getBytes(), fd, host, port);
						buf.delete(0, buf.length());
					}
				}
				stub.cerrar(fd, host, port);
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e1) {
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
