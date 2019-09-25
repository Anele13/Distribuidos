package punto5;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Ventana {
	private JFrame frame;

//Constructor.
	public Ventana(Cliente cliente) {
		this.frame = new JFrame("Reloj");
		this.frame.setSize(300, 300);
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
		JLabel jl1 = new JLabel();
		jl1.setBounds(new Rectangle(10,10,100,20));
		jl1.setText("Reloj");
		cp.add(jl1);
		JLabel jl2 = new JLabel();
		jl2.setBounds(new Rectangle(10,50,100,20));
		jl2.setText("Desface");
		cp.add(jl2);
		
		
		Boton botonIniciar = new Boton("Iniciar",10,150,120);
		botonIniciar.addActionListener(cliente);
		cp.add(botonIniciar);
		
		Boton botonSincronizar = new Boton("Sincronizar",10,100,120);
		botonSincronizar.addActionListener(cliente);
		
		cp.add(botonSincronizar);
		Texto texto =new Texto(70,10,100,cp.getBackground());
		texto.setText("0");
		cp.add(texto);
		JTextField texto2 = new JTextField();
		texto2.setBounds(new Rectangle(120,50,50,20));
		texto2.setText("0");
		cp.add(texto2);
		//Muestro el frame.
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
	}
	
	public void imprimir_timer(String tiempo) {//Metodo público para llamar desde el cliente para setear el timer en la GUI.
		
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
		Component textBox = cp.getComponent(4);
		System.out.println(textBox);
		Texto texto = (Texto)textBox;
		texto.setText(tiempo);
	}
	
	public void imprimir_timer2(long milis) {//Metodo público para llamar desde el cliente para setear el timer en la GUI.
		
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
//		long currentDateTime = System.currentTimeMillis();
		//AGARRO EL PARAMETRO MILIS, LO TRANSFORMO A UN FORMATO HH:MM:SS Y LO IMPRIMO EN EL TABLERO.
		Date currentDate = new Date(milis);
		System.out.println("current Date: " + currentDate);
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		System.out.println("Milliseconds to Date: " + df.format(currentDate));
		
		
		Component textBox = cp.getComponent(4);
		System.out.println(textBox);
		Texto texto = (Texto)textBox;
		texto.setText(df.format(currentDate));
		
	}
	
	public int getDesface () {
		int valorRetorno = 0;
		Container cp = this.frame.getContentPane();
		cp.setLayout(null);
		
		Component textBox = cp.getComponent(5);
		System.out.println(textBox);
		JTextField texto = (JTextField)textBox;

		if(texto != null) {
			valorRetorno = Integer.parseInt(texto.getText());
		}
		return valorRetorno;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	
}
