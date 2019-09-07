/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

import java.rmi.Naming;                    /* lookup         */
import java.rmi.registry.Registry;         /* REGISTRY_PORT  */
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;     /* Exceptions...  */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;



public class Cliente implements ActionListener{
	
	private InterfaceRemota servidor = null;
	
	
	public Cliente(String alfa) throws MalformedURLException, RemoteException, NotBoundException {
		String rname = "//" + alfa + ":" + Registry.REGISTRY_PORT + "/Servidor";
        InterfaceRemota objetoRemoto = (InterfaceRemota)Naming.lookup (rname);
        this.servidor = objetoRemoto;
	}
	
	
	private Component[] getComponentes(ActionEvent e) { 
		Component component = (Component) e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		Component[] componentes = frame.getContentPane().getComponents();
		return componentes;
	}
	
	public InterfaceRemota getObjetoRemoto() {
		return this.servidor;
	}
    
	
	/* ---------- METODOS STUB ----------------*/
	
	public int _abrir(String filename,String host, int port) throws RemoteException, IOException {
		OpenArgument argumento = new OpenArgument("777", filename);
		InterfaceRemota s = this.getObjetoRemoto();
		OpenRespuesta respuesta = (OpenRespuesta)s.abrir(argumento);
		return respuesta.getFd();
	}
	
	
	public ReadRespuesta _leer(int cantidad, int fd,String host, int port) throws RemoteException, FileNotFoundException {
		ReadArgument argumento = new ReadArgument(fd, cantidad);
		InterfaceRemota s = this.getObjetoRemoto();
		ReadRespuesta respuesta = (ReadRespuesta)s.leer(argumento);
		return respuesta;		
	}
	
	
	public int _escribir(byte[] arreglo, int fd,String host,int port) throws RemoteException, FileNotFoundException {
		WriteArgument argumento = new WriteArgument(arreglo, fd);
		InterfaceRemota servidor = this.getObjetoRemoto();
		WriteRespuesta respuesta = (WriteRespuesta)servidor.escribir(argumento);
		return respuesta.getStatus();
	}
	
	
	public int _cerrar(int fd,String host,int port) throws RemoteException, IOException {
		CloseArgument argumento = new CloseArgument(fd);
		InterfaceRemota servidor = this.getObjetoRemoto();
		CloseRespuesta respuesta = (CloseRespuesta)servidor.cerrar(argumento);
		return respuesta.getStatus();
	}
	
	
	
	
	
	
    
    

	@Override
	public void actionPerformed(ActionEvent e) {
		String host,fileLocal,fileServer;
		int port;

		Component[] componentes = getComponentes(e); 
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
	        boolean cosa = true;
			int fd;
			
			
			try {
				fos = new FileOutputStream(new File(fileLocal));
				fd = this._abrir(fileServer,host, port);
				textAreaBox.setText("");
				while(cosa) {
					ReadRespuesta resp = this._leer(50, fd, host, port);
					textAreaBox.append(resp.getBuffer());
					try {
						fos.write(resp.getBuffer().getBytes());
					} catch (IOException a) {
						a.printStackTrace();
					}
					cosa = resp.hayMasDatos;
				}
				this._cerrar(fd, host, port);
				fos.close();
				System.out.println("Hora de finalizacion lectura");
		        System.out.println( sdf.format(cal.getTime()) );
			
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			catch (RemoteException e1) {
				e1.printStackTrace();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		
		if (e.getActionCommand() == "Escribir") {
			StringBuffer buf = new StringBuffer("");
			int fd;
			int i;
			int maxCaracteres = 1;
			FileInputStream fis;
			
			try {
				fis = new FileInputStream(new File(fileLocal));
				fd = this._abrir(fileServer,host, port);
				while (true) {
					i = fis.read();
					if (i == -1) {
						break;
					}
					buf.append((char)i);
					if (buf.length() == maxCaracteres) {
						this._escribir(new String(buf).getBytes(), fd, host, port);
						buf.delete(0, buf.length());
					}
				}
				this._cerrar(fd, host, port);
			
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException, RemoteException, NotBoundException {
        Cliente c = new Cliente(args[0]);
        Ventana v = new Ventana(c);
    }
    
}
