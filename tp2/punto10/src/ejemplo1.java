import javax.transaction.xa.*;

import java.sql.*;

 
import javax.sql.DataSource;
import javax.sql.XADataSource;
 
import javax.sql.XAConnection;
import org.postgresql.xa.*;


 
public class ejemplo1 {


	public static PGXADataSource getDataSource() throws SQLException{
		 PGXADataSource xaDS = new PGXADataSource();
		 xaDS.setServerName("localhost");
		 xaDS.setDatabaseName("banco1");		
		 xaDS.setUser("postgres");				
		 return xaDS;
	}

	
	public static void main(String[] argv) {
		 XADataSource xaDS;
		 XAConnection xaCon;
		 XAResource xaRes;
		 Xid xid;
		 Connection con;
		 Statement stmt;
		 int ret;

		 try { 
			  xaDS = getDataSource();
			  xaCon = xaDS.getXAConnection("jtatest", "jtatest");
			  
			  con = xaCon.getConnection();
			  stmt = con.createStatement();
			  
			  xaRes = xaCon.getXAResource();
			  xid = new MyXid(101, new byte[]{0x01}, new byte[]{0x02});
			  
			  
			  ResultSet resul2 = stmt.executeQuery("select * from cuentas");
				 
			 while(resul2.next()) {
				 System.out.println(resul2.getString(1));
			 }
				 
			  
			  
			  
			  
			  
			  /*
		 	  xaRes.start(xid, XAResource.TMNOFLAGS);
		 	  
			  	ResultSet resul = stmt.executeQuery("select * from cuentas where id=150");
			  	//stmt.executeUpdate("insert into cuentas (id, titular, bloqueada, saldo) values (100, 'nueva cuenta', False, 100000)");
			  	
			  	while(resul.next()) {
			  		System.out.println(resul.getObject(3));
			  	}
			  	
			  	//System.out.println();
			  xaRes.end(xid, XAResource.TMSUCCESS);
		*/
			  
			  
			  
			  //------------------------------
			  
			  
			  
			  ret = xaRes.prepare(xid);
			  
			  if (ret == XAResource.XA_OK) {
			      /*ret2 = xaRes2.prepare(xid);*/
				  // banco1.depostis(
				  //primero preguntar por la transaccion de arriba, luego si da OK que se prepararon todas pregunto 
				  // si la segunda esta OK tambien o sea la preparacion de todos los clientes recieb ahi debo consultar 
				  // al cliente si quiero commitear o rollback de todo. 
				  // ademas es interesante que hagamos un select igual al de arriba
				  // para ver el como estan los datos, si me deja verlos (saldos de las cuentas por ejemplo) y tambien
				  // en postgres.
				  // y luego segun la decision del cliente.
			      xaRes.commit(xid, false);
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