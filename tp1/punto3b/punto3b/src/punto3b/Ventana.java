package punto3b;

import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.*;


public class Ventana{
	public Ventana(Cliente cliente) {
		JFrame f = new JFrame("Titulo de ventana");
		f.setSize(600, 600);
		Container cp = f.getContentPane();
		cp.setLayout(null);
		cp.add(new Texto("localhost",10,10,100));
		cp.add(new Texto("7896",120,10,100));

		cp.add(new Texto ("ingrese un archivo local",10,40,300));
		cp.add(new Texto ("ingrese un archivo remoto",10,70,300));
		
		JTextArea textArea =new JTextArea();
		textArea.setBounds(new Rectangle(330,0,300,600));
		textArea.setFont(new Font("arian", Font.BOLD, 14));

		JScrollPane scroll = new JScrollPane(textArea);
		
		cp.add(textArea);
		cp.add(scroll);


		Boton botonLeer = new Boton("Leer",10,100,80);
		Boton botonEscribir = new Boton("Escribir",150,100,120);
		botonLeer.addActionListener(cliente);
		botonEscribir.addActionListener(cliente);
		
		cp.add(botonLeer);
		cp.add(botonEscribir);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
