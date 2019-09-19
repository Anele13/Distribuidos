package punto5;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JTextField;
import javax.swing.border.Border;

public class Texto extends JTextField{
	public Texto(String textoInterno, int x, int y,int width, Color backGroundColor) {
		this.setBounds(new Rectangle(x,y,width,20));
		this.setText(textoInterno);
		this.setBackground(backGroundColor);
	}

}