import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Ventana extends JFrame{
	
	public Ventana() {
		this.setSize(420, 350);
		Container cp = this.getContentPane();
		cp.setLayout(null);
		
		JLabel label1 = new JLabel();
		label1.setBounds(new Rectangle(10,100,70,20));
		label1.setText("Importe: ");
		cp.add(label1);
		
		JLabel label2 = new JLabel();
		label2.setBounds(new Rectangle(10,10,140,20));
		label2.setText("Desde cuenta N°: ");
		cp.add(label2);
		
		JLabel label3 = new JLabel();
		label3.setBounds(new Rectangle(10,50,140,20));
		label3.setText("Hacia cuenta N°: ");
		cp.add(label3);
		
		JLabel label4 = new JLabel();
		label4.setBounds(new Rectangle(10,150,80,20));
		label4.setText("Cuenta 1: ");
		cp.add(label4);
		
		JLabel label5 = new JLabel();
		label5.setBounds(new Rectangle(210,150,80,20));
		label5.setText("Cuenta 2: ");
		cp.add(label5);
		
		JTextField texto1 = new JTextField();
		texto1.setBounds(new Rectangle(130,10,100,20));
		texto1.setText("0");
		cp.add(texto1);
		
		JTextField texto2 = new JTextField();
		texto2.setBounds(new Rectangle(130,50,100,20));
		texto2.setText("0");
		cp.add(texto2);
		
		JTextField texto3 = new JTextField();
		texto3.setBounds(new Rectangle(75,100,100,20));
		texto3.setText("0");
		cp.add(texto3);
		
		JTextField texto4 = new JTextField();
		texto4.setBounds(new Rectangle(80,150,100,20));
		texto4.setText("0");
		cp.add(texto4);
		
		JTextField texto5 = new JTextField();
		texto5.setBounds(new Rectangle(280,150,100,20));
		texto5.setText("0");
		cp.add(texto5);
		
		
		JButton boton1 = new JButton();
		boton1.setBounds(new Rectangle(200,100,180,20));
		boton1.setText("Realizar Transacción");
		cp.add(boton1);
		
		JButton boton2 = new JButton();
		boton2.setBounds(new Rectangle(50,200,140,20));
		boton2.setText("Comprometer");
		cp.add(boton2);
		
		JButton boton3 = new JButton();
		boton3.setBounds(new Rectangle(200,200,100,20));
		boton3.setText("Abortar");
		cp.add(boton3);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public void set_numero_cuenta_1(int valor) {
		String stringValor = String.valueOf(valor);
		Container cp = this.getContentPane();
		Component componente = cp.getComponent(5);
		JTextField textBox = (JTextField)componente;
		textBox.setText(stringValor);
	}
	
	
	public void set_numero_cuenta_2(int valor) {
		String stringValor = String.valueOf(valor);
		Container cp = this.getContentPane();
		Component componente = cp.getComponent(6);
		JTextField textBox = (JTextField)componente;
		textBox.setText(stringValor);
	}
	
	
	public void set_importe_transaccion(int valor) {
		String stringValor = String.valueOf(valor);
		Container cp = this.getContentPane();
		Component componente = cp.getComponent(7);
		JTextField textBox = (JTextField)componente;
		textBox.setText(stringValor);
	}
		
	
	public void set_valor_cuenta_1(int valor) {
		String stringValor = String.valueOf(valor);
		Container cp = this.getContentPane();
		Component componente = cp.getComponent(8);
		JTextField textBox = (JTextField)componente;
		textBox.setText(stringValor);
	}
	
	
	public void set_valor_cuenta_2(int valor) {
		String stringValor = String.valueOf(valor);
		Container cp = this.getContentPane();
		Component componente = cp.getComponent(9);
		JTextField textBox = (JTextField)componente;
		textBox.setText(stringValor);
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ventana ventana = new Ventana();
//		ventana.set_numero_cuenta_1(10000);
//		ventana.set_numero_cuenta_2(1434534);
//		ventana.set_valor_cuenta_1(22222);
//		ventana.set_valor_cuenta_2(333333);
//		ventana.set_importe_transaccion(84848484);
	}

}
