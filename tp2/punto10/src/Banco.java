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
import punto10.PopUp;
import punto10.SaldoInsuficienteException;

public class Banco {
	
	private PGXADataSource xaDS = new PGXADataSource();
	private Xid transaccion;
	private XAConnection xaCon;
	private XAResource xaRes;
	private static int count= 100;
	private int idTransaccion= ++count;
	
	public Banco(String host, String BD, String user) throws SQLException {
		this.xaDS.setServerName(host);
		this.xaDS.setDatabaseName(BD);		
		this.xaDS.setUser(user);
		this.xaCon = xaDS.getXAConnection("jtatest", "jtatest");
		this.xaRes = xaCon.getXAResource();
		
	}
	
	public void iniciarTransaccion() throws XAException, SQLException {
		this.transaccion =  new MyXid(idTransaccion, new byte[]{0x01}, new byte[]{0x02});
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
		this.xaRes.commit(this.transaccion, false);
		//cerrarConecciones();
	}
	

	public void rollbackTransaccion() throws XAException, SQLException {
		this.xaRes.rollback(this.transaccion);
		cerrarConecciones();
	}
	
	
	public float getSaldoActual(int nroCuenta) throws Exception {
		float monto = 0;
		Statement stmt = this.xaCon.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet resul = stmt.executeQuery("select * from cuentas where id="+nroCuenta);		 
		
		//Existe Cuenta?
		if (!resul.next()) {
			throw new Exception("No existe la cuenta ingresada.");
		}
		resul.beforeFirst();
		while(resul.next()) {
			monto = resul.getFloat("saldo");
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
				throw new SaldoInsuficienteException("Saldo Insuficiente.");
			}
		}
		stmt.close();
	}
	
	
	public void depositar(int nroCuenta, float monto) throws Exception {
		float nuevoSaldo = this.getSaldoActual(nroCuenta)+monto;
		Statement stmt = this.xaCon.getConnection().createStatement();
		stmt.executeUpdate("update cuentas set saldo="+ nuevoSaldo +" where id="+nroCuenta);	
		stmt.close();
	}
	
	
	public void extraer(int nroCuenta, float monto) throws Exception {
		float nuevoSaldo = this.getSaldoActual(nroCuenta)-monto;
		Statement stmt = this.xaCon.getConnection().createStatement();
		stmt.executeUpdate("update cuentas set saldo="+ nuevoSaldo +" where id="+nroCuenta);
		stmt.close();
	}
	
	
	public void cerrarConecciones() throws SQLException {
		 xaCon.close();
	}
}
