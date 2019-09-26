package punto5;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;

public class Cliente implements ActionListener{		
	
	private Ventana ventana;
	private Reloj reloj = new Reloj(0,this);
	private InterfazRemota ir;
	
//Constructor.
	public Cliente() {
		Ventana ventana = new Ventana(this);
		this.ventana = ventana;		
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		boolean continua = true;
		long milis = 0;
		long milisEnvio= 0;
		long milisRecepcion = 0;
		
		if (e.getActionCommand() == "Sincronizar") {		
			System.out.println("Atiendo el boton sincronizar\n");
			HiloSincronizacion hiloSincronizacion = new HiloSincronizacion(this);
			System.out.println("Levante el hilo de sincronizacion.");
			/*		
			DateFormat tiempoEnvio = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			System.out.println("Obtengo el tiempo de ida.");
			Calendar calendario = Calendar.getInstance();
//			System.out.println("Obtengo el calendario");

			
			int i = 0;
			long tRoundMenor = 999999;//Valor inicializado alto para que pase por alto la primer vez en if para obtener el menor tround. asi no se hace, no recomendable.

			while (i< 5) {

				System.out.println("*********");
				i++;
				//INICIO ALGORITMO DE CRISTIAN.
				//PASO 1. TOMO EL TIEMPO ACTUAL.
				Date tiempo1 = calendario.getTime();
//				System.out.println("Tiempo de envio en date desde el cliente: " + tiempoEnvio.format(tiempo1));
				milisEnvio = System.currentTimeMillis();
//				System.out.println("Tiempo de envio en milis desde el cliente: "+ milisEnvio);
				
				//PASO 2. SE TOMA LA HORA DE CUANDO RECIBIMOS LA HORA DEL SERVIDOR PARA CALCULAR EL TIEMPO DE IDA Y VUELTA (timestream o algo asi se le llama).			
				try {
					System.out.println("Voy a pedir el tiempo en el OR.");
					milis = this.getIr().getTiempo();
					Date tiempoRecepcion = calendario.getTime();
//					System.out.println("Obtuve el tiempo del OR en date, este es: "+ tiempoRecepcion);
//					System.out.println("Obtuve el tiempo del OR en milis, este es: " + milis);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				DateFormat tiempoVuelta = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				System.out.println("Obtengo el tiempo de vuelta.");
				Date tiempo2 = calendario.getTime();
//				System.out.println("Obtengo el tiempo de vuelta en date a las: "+tiempoVuelta.format(tiempo2));
				milisRecepcion = System.currentTimeMillis();
//				System.out.println("Obtengo el tiempod e vuelta en milis a las: "+ milisRecepcion);

				//T3 ==> milis.
				//T4 ==> milisRecepcion.
				//T1 ==> milisEnvio.
				// Tround/2 = [C(T4)-C(T1)]/2  ==> (milisRecepcion - milisEnvio)/2
				long tRoundNuevo = (milisRecepcion - milisEnvio)/2;
				if (tRoundNuevo <= tRoundMenor) {
					tRoundMenor = tRoundNuevo;
				}
//				System.out.println(tRoundMenor);
			}
//			System.out.println(milis + tRoundMenor);
			
			long milisReal = milis + tRoundMenor;
			
			DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
			Date horaReal = new Date(milisReal);
			
			System.out.println("la hora real es: "+formatoHora.format(horaReal)  );
			
			Reloj reloj = this.getReloj();
			
			
			
			while(continua) {
//				reloj.setTime(milisReal);
				
				
				if (continua == true) {
					continua = false;
				}
			}
			
			
			
			
			
			
			
			
			Ventana ventana = this.getVentana();
			ventana.imprimir_timer2(milis);
			
			
		*/
		}
		if (e.getActionCommand() == "Iniciar") {
			System.out.println("Atiendo el boton iniciar");
			Ventana ventana = this.getVentana();

//			JFrame frame = ventana.getFrame();
//			Container cp = frame.getContentPane();
//			cp.setLayout(null);
//			Component botonIniciar = cp.getComponent(2);
//			System.out.println("BOTON"+botonIniciar);
//			botonIniciar.enable(false);
			//[TODO] DESHABILITAR BOTON INICIAR CUANDO SE APRETA. SINO CREA MUCHOS HILOS.
			int desface = ventana.getDesface();
			this.iniciar_timer(desface);
		}
		
	}

	
	

	private void iniciar_timer(int desface) {
//		Reloj reloj = new Reloj(desface, this);
		reloj.setTime(desface);
		reloj.start();
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		System.out.println("ARRANQUE DEL PROGRAMA.");
		Cliente cliente = new Cliente();
		String rname = "//" + "localhost" +":" + Registry.REGISTRY_PORT + "/ORServidor";
		InterfazRemota objetoRemoto = (InterfazRemota)Naming.lookup(rname);
		cliente.setIr(objetoRemoto);
//		Ventana ventana = cliente.getVentana();
//		int desface = ventana.getDesface();
//		cliente.iniciar_timer(desface);
		
		}
	
//Getters & Setters
	public Ventana getVentana() {
		return ventana;
	}

	public void setVentana(Ventana ventana) {
		this.ventana = ventana;
	}


	public Reloj getReloj() {
		return reloj;
	}


	public void setReloj(Reloj reloj) {
		this.reloj = reloj;
	}


	public InterfazRemota getIr() {
		return ir;
	}


	public void setIr(InterfazRemota ir) {
		this.ir = ir;
	}


	
}
