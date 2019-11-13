package punto1;


import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Ventana implements ActionListener{

	private JFrame frame ;
	private AgenteManager manager;

	public Ventana(AgenteManager manager) {
		//Registro el agente.
		this.setManager(manager);
		
		//Creo la ventana.
		JFrame f = new JFrame("Titulo de ventana");
		f.setSize(600, 600);
		Container cp = f.getContentPane();
		cp.setLayout(null);
		
		//Agrego los text box al panel.
		cp.add(new Texto ("PATH-ARCHIVO-1",10,40,300));
		cp.add(new Texto ("PATH-ARCHIVO-2",10,70,300));
		cp.add(new Texto ("Container",10,10,100));
		
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
		
		//Defino quien ataja los eventos de los botones.
		botonLeer.addActionListener(this);
		botonEscribir.addActionListener(this);
		
		//Agrego los botones al panel.
		cp.add(botonLeer);
		cp.add(botonEscribir);
		
		//Seteo el frame al atributo privado y lo muestro.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFrame(f);
		f.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String contenedor,fileLocal,fileServer;
		Component[] componentes = getComponentes(e);
		
		//Obtengo los textos necesarios de la ventana.
		Texto fileLocalTextBox = (Texto)componentes[0];
		Texto fileServerTextBox = (Texto)componentes[1];
		Texto contenedorTextBox = (Texto)componentes[2];
		contenedor = contenedorTextBox.getText();
		fileLocal = fileLocalTextBox.getText();
		fileServer = fileServerTextBox.getText();
		
		//Si se hizo pidio una lectura.
		if (e.getActionCommand() == "Leer") {
			AgenteManager manager = this.getManager();
			manager.saludar();
			//[TODO] ACA LEVANTAR EL AGENTE QUE HACE EL LEER (prioridad maxima).
//			AgenteLeer agenteLeer = new AgenteLeer(contenedor,filelocal,fileremoto);
		}
		
		//Si se pidio una escritura.
		if (e.getActionCommand() == "Escribir") {
			AgenteManager manager = this.getManager();
			manager.saludar();
			//[TODO] ACA LEVANTAR EL AGENTE QUE HACE EL ESCRIBIR (prioridad maxima).
//			AgenteLeer agenteLeer = new AgenteLeer(contenedor,filelocal,fileremoto);
		}
	}

	
	public void setVisible(boolean valorVerdad) {
		JFrame f = this.getFrame();
		f.setVisible(valorVerdad);
		this.setFrame(f);
	}
	
	

	private Component[] getComponentes(ActionEvent e) { 
		Component component = (Component) e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		Component[] componentes = frame.getContentPane().getComponents();
		return componentes;
	}
	


	public AgenteManager getManager() {
		return manager;
	}


	public void setManager(AgenteManager manager) {
		this.manager = manager;
	}


	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
