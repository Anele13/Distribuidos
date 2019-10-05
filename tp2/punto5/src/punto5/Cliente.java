package punto5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;


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
		
		Ventana ventana ;
		int desface;
		
		if (e.getActionCommand() == "Sincronizar") {		
			System.out.println("DESDE CLIENTE - Atiendo el boton sincronizar.");
			HiloSincronizacion hiloSincronizacion = new HiloSincronizacion(this);
			System.out.println("DESDE CLIENTE - Levante el hilo de sincronizacion.");
		}
		if (e.getActionCommand() == "Iniciar") {
			System.out.println("DESDE CLIENTE - Atiendo el boton iniciar.");
			ventana = this.getVentana();

//			JFrame frame = ventana.getFrame();
//			Container cp = frame.getContentPane();
//			cp.setLayout(null);
//			Component botonIniciar = cp.getComponent(2);
//			System.out.println("BOTON"+botonIniciar);
//			botonIniciar.enable(false);
			//[TODO] DESHABILITAR BOTON INICIAR CUANDO SE APRETA. SINO CREA MUCHOS HILOS.
			desface = ventana.getDesface();
			this.iniciar_timer(desface);
		}

		
		if (e.getActionCommand() == "Actualizar") {
			System.out.println("DESDE CLIENTE - Atiendo el boton actualizar.");
			ventana = this.getVentana();
			desface = ventana.getDesface();
			this.actualizar_timer(desface);
			
		}
		
		
		
	}

	
	private void actualizar_timer(int desface) {
		Reloj reloj = this.getReloj();
		reloj.setTime(desface);
	}

	private void iniciar_timer(int desface) {
		reloj.setTime(desface);
		reloj.start();
	}

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		System.out.println("ARRANQUE DEL PROGRAMA.");
		Cliente cliente = new Cliente();
		String rname = "//" + "localhost" +":" + Registry.REGISTRY_PORT + "/ORServidor";
		InterfazRemota objetoRemoto = (InterfazRemota)Naming.lookup(rname);
		cliente.setIr(objetoRemoto);		
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
