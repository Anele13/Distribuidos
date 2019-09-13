package punto5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Cliente implements ActionListener{		
	
	private Ventana ventana;
	
//Constructor.
	public Cliente() {
		Ventana ventana = new Ventana(this);
		this.ventana = ventana;		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();
		int desface = 0;//Tiempo de mas o de menos con respecto a la hora real. AHORA HARDCODEADO, PONER UN COMPONENTE EN VENTANA.
		Reloj reloj = new Reloj(desface, cliente);
		reloj.start();
		
		}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Boton sincronizar presionado");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println("Hora de reinicio timer");
		System.out.println( sdf.format(cal.getTime()) );
		
		
		System.out.println("Atiendo el boton\n");
		Ventana ventana = this.getVentana();
		ventana.imprimir_timer(sdf.format(cal.getTime()));
	}

//Getters & Setters
	public Ventana getVentana() {
		return ventana;
	}

	public void setVentana(Ventana ventana) {
		this.ventana = ventana;
	}

}
