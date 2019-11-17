package punto1;


import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import jade.wrapper.StaleProxyException;


public class Ventana implements ActionListener{

	private ManejadorAgentes manejador = null;
	
	
	public Ventana() throws UnknownHostException {
		manejador = new ManejadorAgentes();
			
		//Creo la ventana.
		JFrame f = new JFrame("TP 4 Distribuidos");
		f.setSize(600, 600);
		Container cp = f.getContentPane();
		cp.setLayout(null);
		
		//Agrego los text box al panel.
		cp.add(new Texto ("Anele",10,10,100));
		cp.add(new Texto ("/home/anele/Escritorio/nuevo",10,40,300)); //local
		cp.add(new Texto ("/home/anele/Desktop/unarchivocopado",10,70,300)); //remoto
				
		//Creo el text area
		JTextArea textArea =new JTextArea();
		textArea.setBounds(new Rectangle(330,0,300,600));
		textArea.setFont(new Font("arian", Font.BOLD, 14));
		
		//Creo barra de scroll.  
		JScrollPane scroll = new JScrollPane(textArea); //[TODO]No anda(prioridad minima).
		
		//Agrego text area y scroll al panel.
		cp.add(textArea);
		cp.add(scroll);
		
		//Agrego los botones al panel.
		Boton botonLeer = new Boton("Leer",10,100,80);
		Boton botonEscribir = new Boton("Escribir",150,100,120);
		Boton botonCerrar = new Boton("Cerrar",200,250,100);
		
		//Defino quien ataja los eventos de los botones.
		botonLeer.addActionListener(this);
		botonEscribir.addActionListener(this);
		botonCerrar.addActionListener(this);
		
		//Agrego los botones al panel.
		cp.add(botonLeer);
		cp.add(botonEscribir);
		cp.add(botonCerrar);
		
		//Seteo el frame al atributo privado y lo muestro.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Obtengo los textos necesarios de la ventana.
		String contenedorDestino,fileLocal,fileServer;
		Component[] componentes = getComponentes(e);
		
		Texto contenedorTextBox = (Texto)componentes[0]; 
		Texto fileLocalTextBox = (Texto)componentes[1]; 
		Texto fileServerTextBox = (Texto)componentes[2];
		JTextArea textAreaBox = (JTextArea)componentes[3];
		
		contenedorDestino = contenedorTextBox.getText();
		fileLocal = fileLocalTextBox.getText();
		fileServer = fileServerTextBox.getText();
		
		System.out.println(contenedorDestino);
		System.out.println(fileLocal);
		System.out.println(fileServer);
		
		
		if (e.getActionCommand() == "Leer") {
			//textAreaBox.setText("HOLA CAPOOOOOO");
			try {
				manejador.leer(fileLocal, fileServer, contenedorDestino);
			} catch (StaleProxyException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getActionCommand() == "Escribir") {
			try {
				manejador.escribir(fileLocal, fileServer, contenedorDestino);
			} catch (StaleProxyException e1) {
				e1.printStackTrace();
			}
		}		
	}


	private Component[] getComponentes(ActionEvent e) { 
		Component component = (Component) e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		Component[] componentes = frame.getContentPane().getComponents();
		return componentes;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		Ventana v = new Ventana();
	}
	
}
