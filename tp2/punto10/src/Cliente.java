import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import punto10.CuentaBloqueadaException;
import punto10.CuentaNoExisteException;
import punto10.PopUp;
import punto10.SaldoInsuficienteException;

public class Cliente implements ActionListener{

	private Banco banco1;
	private Banco banco2;
	private Ventana ventana;
	
	
	public Cliente() {
		ventana = new Ventana(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if (ventana.estanLosCamposLlenos()) {
			try {				
				// Creacion de los bancos
				banco1 = new Banco("localhost", "banco1", "postgres");
				banco2 = new Banco("localhost", "banco2", "postgres");
				
				// Consulto si puedo operar
				banco1.puedoDepositar(ventana.getCuentaADepositar());
				banco2.puedoExtraer(ventana.getCuentaAExtraer(), ventana.getMontoAExtraer());
				
				
				// Inicio transaccion 1
				banco1.iniciarTransaccion();
				banco1.depositar(ventana.getCuentaADepositar(), ventana.getMontoADepositar());
				banco1.finalizarTransaccion();

								
				// Inicio transaccion 2
				banco2.iniciarTransaccion();
				banco2.extraer(ventana.getCuentaAExtraer(), ventana.getMontoAExtraer());
				banco2.finalizarTransaccion();
				

				
				// Consulto si se pudieron preparar las transacciones luego al usuario una ultima vez
				if (banco1.prepararTransaccion() && banco2.prepararTransaccion()) {
					int usuarioDecideCommit = JOptionPane.showConfirmDialog (null, "Desea confirmar la transaccion?\n"+
																					" "+"\n"+
																					"Cuenta "+ ventana.getCuentaAExtraer() +" Banco 1 tiene saldo: "+banco1.getSaldoActual(ventana.getCuentaAExtraer())+"\n"+
																					"Cuenta "+ ventana.getCuentaADepositar() +" Banco 2 tiene saldo: "+banco2.getSaldoActual(ventana.getCuentaADepositar())+"\n"
																					,"Warning",JOptionPane.YES_NO_OPTION);
					if(usuarioDecideCommit == JOptionPane.YES_OPTION){
						banco1.commitTransaccion();
						banco2.commitTransaccion();
						JOptionPane.showConfirmDialog (null, "Cuenta "+ ventana.getCuentaAExtraer() +" Banco 1 tiene saldo: "+banco1.getSaldoActual(ventana.getCuentaAExtraer())+"\n"+
															"Cuenta "+ ventana.getCuentaADepositar() +" Banco 2 tiene saldo: "+banco2.getSaldoActual(ventana.getCuentaADepositar())+"\n"
															,"Transaccion Completada",JOptionPane.DEFAULT_OPTION);
						banco1.cerrarConecciones();
						banco2.cerrarConecciones();
					}
					else {
						banco1.rollbackTransaccion();
						banco2.rollbackTransaccion();
					}
				}
				else {
					banco1.rollbackTransaccion();
					banco2.rollbackTransaccion();
				}
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (CuentaBloqueadaException e) {
				PopUp.infoBox("La cuenta que quiere acceder se encuentra bloqueada.", e.getMessage());
			}
			catch (CuentaNoExisteException e) {
				PopUp.infoBox("La cuenta ingresada no existe.", e.getMessage());
			}
			catch (SaldoInsuficienteException e) {
				PopUp.infoBox("El saldo a debitar de la cuenta no es suficiente.", e.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			PopUp.infoBox("Debe completar todos los campos.","Error");
		}
	}
	
	
	public static void main(String[] args) {
		new Cliente();
	}
}
