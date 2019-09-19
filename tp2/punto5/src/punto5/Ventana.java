package punto5;

import java.awt.Component;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;

public class Ventana {
	private JFrame frame;
//Constructor.
	public Ventana(Cliente cliente) {
		//Inicio el timer.
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println("Hora de inicintio timer");
		System.out.println( sdf.format(cal.getTime()) );
		
		
		

		this.frame = new JFrame("Reloj");
		this.frame.setSize(300, 300);
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
		Boton botonSincronizar = new Boton("Sincronizar",10,100,120);
		botonSincronizar.addActionListener(cliente);
		cp.add(botonSincronizar);
		cp.add(new Texto(sdf.format(cal.getTime()),10,10,100,cp.getBackground()));
		
		//Muestro el frame.
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
	}
	
	public void imprimir_timer(String tiempo) {//Metodo público para llamar desde el cliente para setear el timer en la GUI.
		
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
		Component textBox = cp.getComponent(1);
		Texto texto = (Texto)textBox;
		texto.setText(tiempo);
		
		
		
	}

}
