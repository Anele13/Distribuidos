package punto1;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Cliente implements ActionListener{


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();
//		Ventana ventana = new Ventana(cliente);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String contenedor,fileLocal,fileServer;
		Component[] componentes = getComponentes(e);

		Texto fileLocalTextBox = (Texto)componentes[0];
		Texto fileServerTextBox = (Texto)componentes[1];
		Texto contenedorTextBox = (Texto)componentes[2];
		contenedor = contenedorTextBox.getText();
		fileLocal = fileLocalTextBox.getText();
		fileServer = fileServerTextBox.getText();
		
		if (e.getActionCommand() == "Leer") {
			
//			AgenteLeer agenteLeer = new AgenteLeer(contenedor,filelocal,fileremoto);
		}
	}
	private Component[] getComponentes(ActionEvent e) { 
		Component component = (Component) e.getSource();
		JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		Component[] componentes = frame.getContentPane().getComponents();
		return componentes;
	}
}
