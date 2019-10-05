import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.postgresql.xa.PGXADataSource;

import punto10.CuentaBloqueadaException;
import punto10.CuentaNoExisteException;
import punto10.SaldoInsuficienteException;

public class Banco {
	
	private PGXADataSource xaDS = new PGXADataSource();
	private Xid transaccion;
	private XAConnection xaCon;
	private XAResource xaRes;
	
	public Banco(String host, String BD, String user) throws SQLException {
		this.xaDS.setServerName(host);
		this.xaDS.setDatabaseName(BD);		
		this.xaDS.setUser(user);
		this.xaCon = xaDS.getXAConnection("jtatest", "jtatest");
		this.xaRes = xaCon.getXAResource();
		
	}
	

	public void iniciarTransaccion() throws XAException, SQLException {
		this.transaccion =  new MyXid(101, new byte[]{0x01}, new byte[]{0x02});
		this.xaRes.start(this.transaccion, XAResource.TMNOFLAGS);
	}
	
	
	public void finalizarTransaccion() throws XAException {
		xaRes.end(this.transaccion, XAResource.TMSUCCESS);		
	}
	
	
	public boolean prepararTransaccion() throws XAException {
		boolean respuesta = true;
		int resultado = this.xaRes.prepare(this.transaccion);
		
		if (resultado != XAResource.XA_OK){
			respuesta = false;
		}
		return respuesta;
	}
	
	
	public void commitTransaccion() throws XAException, SQLException {
		this.xaRes.rollback(this.transaccion);
		cerrarConecciones();
	}
	

	public void rollbackTransaccion() throws XAException, SQLException {
		this.xaRes.commit(this.transaccion, false);
		cerrarConecciones();
	}
	
	
	public float getMontoActual(int nroCuenta) throws Exception {
		float monto = 0;
		Statement stmt = this.xaCon.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet resul = stmt.executeQuery("select * from cuentas where id="+nroCuenta);		 
		
		//Existe Cuenta?
		if (!resul.next()) {
			throw new Exception("No existe la cuenta ingresada.");
		}
		resul.beforeFirst();
		while(resul.next()) {
			monto = resul.getFloat("monto");
		}
		return monto;
	}
	
	
	public void puedoDepositar(int nroCuenta) throws Exception {
		Statement stmt = this.xaCon.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet resul = stmt.executeQuery("select * from cuentas where id="+nroCuenta);		 
		
		//Existe Cuenta?
		if (!resul.next()) {
			throw new CuentaNoExisteException("No existe la cuenta ingresada.");
		}
		
		//Cuenta Bloqueada?
		resul.beforeFirst();
		while(resul.next()) {
			if (resul.getBoolean("bloqueada")) {
				throw new CuentaBloqueadaException("Cuenta bloqueada.");
			}
		}
		stmt.close();
	}

	
	public void puedoExtraer(int nroCuenta, float monto) throws Exception {
		Statement stmt = this.xaCon.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet resul = stmt.executeQuery("select * from cuentas where id="+nroCuenta);		 
		
		//Existe Cuenta?
		if (!resul.next()) {
			throw new CuentaNoExisteException("No existe la cuenta ingresada.");
		}
		
		resul.beforeFirst();
		while(resul.next()) {
			//Cuenta Bloqueada?
			if (resul.getBoolean("bloqueada")) {
				throw new CuentaBloqueadaException("Cuenta bloqueada.");
			}
			//Te alcanza la moneda?
			if (resul.getFloat("saldo") - monto < 0) {
				throw new SaldoInsuficienteException("Estas sacando mas de lo que hay capo.");
			}
		}
		stmt.close();
	}
	
	
	public void depositar(int nroCuenta, float monto) throws SQLException {
		Statement stmt = this.xaCon.getConnection().createStatement();
		
		stmt.executeUpdate("select * from cuentas where id="+nroCuenta);
		
		stmt.close();
	}
	
	
	public void extraer(int nroCuenta, float monto) throws SQLException {
		Statement stmt = this.xaCon.getConnection().createStatement();
		ResultSet resul = stmt.executeQuery("select * from cuentas where id="+nroCuenta);
		//resul.get
		
		
		
		
		stmt.close();
	}
	
	
	private void cerrarConecciones() throws SQLException {
		 xaCon.close();
	}

	
	
	public static void main(String[] args) {
		int cuentaADepositar = 4;
		int cuentaADebitar = 4;
		int monto = 10;
		boolean usuarioDecideCommit = true;
		
		try {
			
			Banco banco1 = new Banco("localhost", "banco1", "postgres");
			Banco banco2 = new Banco("localhost", "banco2", "postgres");
			
			banco1.puedoDepositar(cuentaADepositar);
			banco2.puedoExtraer(cuentaADebitar, monto);
			
			banco1.iniciarTransaccion();
			banco1.depositar(cuentaADepositar, monto);
			banco1.finalizarTransaccion();
			
			
			banco2.iniciarTransaccion();
			banco2.extraer(cuentaADebitar, monto);
			banco2.finalizarTransaccion();
			
		
			if (banco1.prepararTransaccion() && banco2.prepararTransaccion()) {
				
				// Solicitar al cliente una ultima confirmacion para commitear
				
				if (usuarioDecideCommit) {
					banco1.commitTransaccion();
					banco2.commitTransaccion();
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
			e.printStackTrace();
		}
		catch (CuentaNoExisteException e) {
			e.printStackTrace();
		}
		catch (SaldoInsuficienteException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
