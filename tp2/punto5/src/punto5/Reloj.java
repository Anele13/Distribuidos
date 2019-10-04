package punto5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		
	
	//SET TIME SE USA PARA SETEAR LA HORA SEGUN EL RELOJ LOCAL.
	public void setTime(int desface) {
		long milis = System.currentTimeMillis();
//		System.out.println(milis);
		milis = milis + (60 *1000 * desface);
		//[TODO] ESTÁ HACIENDO LA RESTA PERO LA HORA DA IGUAL. VER COMO RESTAR EN MINUTOS EL DESFACE.
//		System.out.println(milis);
		Date resultDate = new Date(milis);
		int horas = resultDate.getHours();
		int minutos = resultDate.getMinutes();
		int segundos = resultDate.getSeconds();
		
		this.setHoras(horas);
		this.setMinutos(minutos);
		this.setSegundos(segundos);	
	}
	
	
	//SOBRECARGA AL METODO SET TIME PARA LLAMARLO CON UN LONG QUE USE PARA SETEAR LA HORA, ES USADO PARA SINCRONIZACION.
	public void setTime(long milis) {
		Date resultDate = new Date(milis);
		int horas = resultDate.getHours();
		int minutos = resultDate.getMinutes();
		int segundos = resultDate.getSeconds();
		
		this.setHoras(horas);
		this.setMinutos(minutos);
		this.setSegundos(segundos);		
	}

	
	public long getMilis() throws ParseException {
		long longTiempo = 0;
		int horas = this.getHoras();
		int minutos = this.getMinutos();
		int segundos = this.getSegundos();

		
		String cadenaHora = String.format("%02d:%02d:%02d", horas, minutos, segundos);
		Date dateHora=new SimpleDateFormat("HH:mm:ss").parse(cadenaHora);  
		
		longTiempo = dateHora.getTime();		
		return longTiempo;
	}

	public void run() {
		
		System.out.println("Reloj corriendo");
		Cliente cliente = this.getCliente();
		Ventana ventana = cliente.getVentana();
		String stringTimer;
		
		while (true) {
			while(this.getHoras()<24) {
				stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
				ventana.imprimir_timer(stringTimer);
				while(this.getMinutos()<60) {
					stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
					ventana.imprimir_timer(stringTimer);
					while(this.getSegundos()<60) {
						stringTimer = String.format("%02d:%02d:%02d", this.getHoras(),this.getMinutos(),this.getSegundos());
						ventana.imprimir_timer(stringTimer);
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
		System.out.println("DESDE RELOJ - Seteo mi tiempo por segundo en: " + tiempoPorSegundo + " milis");
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
