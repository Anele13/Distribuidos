import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.postgresql.xa.PGXADataSource;

public class aplicacion {

	
	public static PGXADataSource getDataSourceBanco(String host, String BD, String user) throws SQLException{
		 PGXADataSource xaDS = new PGXADataSource();
		 xaDS.setServerName(host);
		 xaDS.setDatabaseName(BD);		
		 xaDS.setUser(user);				
		 return xaDS;
	}
	

	
	public static void main(String[] args) {
         
		try { 
			 XADataSource xaDS = getDataSourceBanco("localhost", "banco1", "postgres");
			 
			 
			 XAConnection xaCon = xaDS.getXAConnection("jtatest", "jtatest");
			 XAResource xaRes = xaCon.getXAResource();
			 Connection con = xaCon.getConnection();
			 Statement stmt = con.createStatement();
			 Xid transaccionBanco1 = new MyXid(101, new byte[]{0x01}, new byte[]{0x02});
			 
			 
			 
			 //Inicio una transaccion con el banco 1
			 xaRes.start(transaccionBanco1, XAResource.TMNOFLAGS);
			 	stmt.executeUpdate("insert into cuentas (id, titular, bloqueada, saldo) values (100, 'nueva cuenta', False, 100000)");
			 xaRes.end(transaccionBanco1, XAResource.TMSUCCESS);

			  
			  
			  
			 if (xaRes.prepare(transaccionBanco1) == XAResource.XA_OK) {
			      /*ret2 = xaRes2.prepare(xid);*/
				  //primero preguntar por la transaccion de arriba, luego si da OK que se prepararon todas pregunto 
				  // si la segunda esta OK tambien o sea la preparacion de todos los clientes recieb ahi debo consultar 
				  // al cliente si quiero commitear o rollback de todo. 
				  // ademas es interesante que hagamos un select igual al de arriba
				  // para ver el como estan los datos, si me deja verlos (saldos de las cuentas por ejemplo) y tambien
				  // en postgres.
				  // y luego segun la decision del cliente.
			     xaRes.commit(transaccionBanco1, false);
			 }
			  
			  
			 stmt.close();
			 con.close();
			 xaCon.close();
		 
		 }
		 catch (XAException e) {
			 e.printStackTrace();
		 }
		 catch (SQLException e) {
			 e.printStackTrace();
		 }
		 
		 finally {
			 
		 }
	}
	
	
}
