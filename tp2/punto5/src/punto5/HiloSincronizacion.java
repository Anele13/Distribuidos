package punto5;

import java.rmi.RemoteException;

import java.text.ParseException;

public class HiloSincronizacion extends Thread {

	private Cliente cliente;
	
	public HiloSincronizacion(Cliente cliente) {
		this.setCliente(cliente);
		this.start();
	}
	
	
	public void run() {
		System.out.println("DESDE HILO SINCRONIZACION - COMIENZANDO SINCRONIZACION. *********************************");
		long milisEnvio = 0;
		long milisRemoto = 0;
		long milisRecepcion = 0;
		long diferenciaRelojes = 999999999;
		long tRoundMenor = 999999999;//Valor inicializado alto para que pase por alto la primer vez en if para obtener el menor tround. asi no se hace, no recomendable.
		long milisReal = 0;
		
		Cliente cliente = this.getCliente();
		Reloj reloj = cliente.getReloj();

		while(true) {
//		while ((250<diferenciaRelojes) || (diferenciaRelojes<-250)) {
			int i = 0;
			while (i< 5) {		
				i++;
				
				//INICIO ALGORITMO DE CRISTIAN.
				//PASO 1. TOMO EL TIEMPO ACTUAL.
				try {
					milisEnvio = reloj.getMilis();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//PASO 2. SE TOMA LA HORA DE CUANDO RECIBIMOS LA HORA DEL SERVIDOR PARA CALCULAR EL TIEMPO DE IDA Y VUELTA (timestream o algo asi se le llama).			
				try {
					System.out.println("DESDE HILO SINCRONIZACION - Voy a pedir el tiempo en el OR. Por vez "+i);
					milisRemoto = cliente.getIr().getTiempo();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					milisRecepcion = reloj.getMilis();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				//T3 ==> milisRemoto.
				//T4 ==> milisRecepcion.
				//T1 ==> milisEnvio.
				// Tround/2 = [C(T4)-C(T1)]/2  ==> (milisRecepcion - milisEnvio)/2
				long tRoundNuevo = (milisRecepcion - milisEnvio)/2;
				if (tRoundNuevo <= tRoundMenor) {
					tRoundMenor = tRoundNuevo;
				}
			}
			System.out.println("DESDE HILO SINCRONIZACION - La diferencia entre la hora de mi reloj y la hora del reloj remoto es (en milis):  "+tRoundMenor);
			milisReal = (milisRemoto % (24*60*60*1000)) + tRoundMenor ;//DIVIDO EL VALOR DE LA SUMA EN ESE NUMERO PARA QUEDARME SOLO CON LAS HORAS Y NO EL AÑO,MES,DIA.
			
//			
//			System.out.println("MILIS RECEPCION = "+ milisRecepcion);
//			System.out.println("MILIS REAL = "+ milisReal);
//			
			diferenciaRelojes = milisRecepcion - milisReal;
//			System.out.println("DIFERENCIA RELOJES = "+ diferenciaRelojes);
			System.out.println("DESDE HILO SINCRONIZACION -Diferencia entre horas = .....    " + (diferenciaRelojes));
			
			//SECTOR DONDE RECONOZCO LA DIFERENCIA Y ACTUALIZO EL TIEMPO POR SEGUNDO DEL RELOJ.
			if (diferenciaRelojes > 5000) {
				System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MAYOR A 5000 SUPER LEJOS");
				reloj.setTiempoPorSegundo(4000);
			}else {
				if (diferenciaRelojes >2000) {
					System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MAYOR A 2000 SUPER LEJOS");
					reloj.setTiempoPorSegundo(1800);				
				}
				else {
					if ((diferenciaRelojes <= 2000) && (diferenciaRelojes >750) ) {
						System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MAYOR A 750");
						reloj.setTiempoPorSegundo(1500);
					}
					else {
						if((diferenciaRelojes <= 750) && (diferenciaRelojes >250)) {
							System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MAYOR A 250");
							reloj.setTiempoPorSegundo(1200);
						}
						else {
							if((diferenciaRelojes <=250) && (diferenciaRelojes >=-250)) {
								System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> Hora Aceptable");
								reloj.setTiempoPorSegundo(1000);
							}
							else {
								if ((diferenciaRelojes < -250) && (diferenciaRelojes >= -750)) {
									System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MENOR A -250");
									reloj.setTiempoPorSegundo(700);
								}
								else {
									if((diferenciaRelojes < -750) && diferenciaRelojes >= -2000) {
										System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MENOR A -750");
										reloj.setTiempoPorSegundo(500);
									}
									else {
										if((diferenciaRelojes < -2000) && (diferenciaRelojes >= -5000)) {
											System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MENOR A -2000");
											reloj.setTiempoPorSegundo(200);
										}
										else {
											if(diferenciaRelojes < -5000) {
												System.out.println("DESDE HILO SINCRONIZACION - EL RANGO -------------->>> MENOR A -5000 SUPER LEJOS");
												reloj.setTiempoPorSegundo(10);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
//		System.out.println("TERMINA DE CORRER EL HILO DE SINCRONIZACION");
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
