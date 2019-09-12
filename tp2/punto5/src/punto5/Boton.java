package punto5;

import java.awt.Rectangle;

import javax.swing.JButton;

public class Boton extends JButton {
	
	private static final long serialVersionUID = 1L;

	public Boton(String nombre_boton,int x,int y, int width) {
		super(nombre_boton);
	//	this.addActionListener(this); /* Significa que esta clase maneja ella misma los eventos de click */
		this.setBounds(new Rectangle(x,y,width,20));
	}

}