package punto5;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reloj extends Thread{
	Cliente cliente;
	private long tiempoPorSegundo;
	private int horas;
	private int minutos;
	private int segundos;
	
	
	public Reloj(int deriva,Cliente cliente) {
		this.setTiempoPorSegundo(1000 + deriva);
		this.setCliente(cliente);
	
	}
	
	public void setTime(int desface) {
		long milis = System.currentTimeMillis();
		System.out.println(milis);
		milis = milis + (60 *1000 * desface);
		//[TODO] ESTÁ HACIENDO LA RESTA PERO LA HORA DA IGUAL. VER COMO RESTAR EN MINUTOS EL DESFACE.
		System.out.println(milis);
		Date resultdate = new Date(milis);
		int horas = resultdate.getHours();
		int minutos = resultdate.getMinutes();
		int segundos = resultdate.getSeconds();
		
		this.setHoras(horas);
		this.setMinutos(minutos);
		this.setSegundos(segundos);
		
	}
	
	
	public void run() {
		System.out.println("Reloj corriendo");
		Cliente cliente = this.getCliente();
		Ventana ventana = cliente.getVentana();

		String stringTimer;
		while (true) {
			while(this.getHoras()<24) {
				stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
				System.out.println(stringTimer);
				ventana.imprimir_timer(stringTimer);
				while(this.getMinutos()<60) {
					stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
					System.out.println(stringTimer);	
					ventana.imprimir_timer(stringTimer);
					while(this.getSegundos()<60) {
						stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
						System.out.println(stringTimer);
						ventana.imprimir_timer(stringTimer);
						//DUERMO UN SEGUNDO.
						try {
							this.sleep(this.getTiempoPorSegundo());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.setSegundos(this.getSegundos()+1);
					}
					this.setSegundos(0);
					this.setMinutos(this.getMinutos()+1);
				}
				this.setMinutos(0);
				this.setHoras(this.getHoras()+1);
			}
			this.setHoras(0);
			System.out.println("Es un nuevo dia perrroooo");
		}
	}
	
//Getters & Setters

	public long getTiempoPorSegundo() {
		return tiempoPorSegundo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setTiempoPorSegundo(long tiempoPorSegundo) {
		this.tiempoPorSegundo = tiempoPorSegundo;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
	
}
