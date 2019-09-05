package punto3b;

import java.awt.Rectangle;

import javax.swing.JTextField;

public class Texto extends JTextField{
		public Texto(String textoInterno, int x, int y,int width) {
			this.setBounds(new Rectangle(x,y,width,20));
			this.setText(textoInterno);
		}

}
