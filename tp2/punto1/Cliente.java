/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

import java.rmi.Naming;                    /* lookup         */
import java.rmi.registry.Registry;         /* REGISTRY_PORT  */

import java.net.MalformedURLException;     /* Exceptions...  */
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Ejemplo de cliente rmi 
*/
public class Cliente {
    
    /** Crea nueva instancia de Cliente */
    public Cliente(String alfa, String beta, String gamma, String delta) 
    {
    	int entero1 = Integer.parseInt(gamma);
    	int entero2 = Integer.parseInt(delta);
        try
        {
			if (beta.equals("suma") || beta.equals("resta")) {
				String rname = "//" + alfa + ":" + Registry.REGISTRY_PORT + "/ORSumaResta";
				IRSumaResta objetoRemoto =  (IRSumaResta)Naming.lookup (rname);
			       if (beta.equals("suma")) {
				    	// Se realiza la suma resta remota.
				           System.out.print (entero1 +" + "+ entero2 +" = ");
				           System.out.println (objetoRemoto.suma(entero1,entero2));
				       }

				       else {
				    	   if (beta.equals("resta")) {
				    		System.out.println(entero1 +" - "+entero2 +" = ");
				    		System.out.println(objetoRemoto.resta(entero1,entero2));
				    	   }
				       } 
			}
			else {
				if (beta.equals("multiplicacion") || beta.equals("division")) {
					System.out.println("es una multiplicacion o division");
					String rname = "//" + alfa + ":" + Registry.REGISTRY_PORT + "/ORMultiplicacionDivision";
//					System.out.println("Llegue aca");
					IRMultiplicacionDivision objetoRemoto =  (IRMultiplicacionDivision)Naming.lookup (rname);
//					System.out.println("instancie el objeto");
					if (beta.equals("multiplicacion")) {
		    	 //Se realiza la multiplicacion division remota.
						System.out.print (entero1 +" * "+ entero2 +" = ");
						System.out.println (objetoRemoto.multiplicacion(entero1,entero2));
					}

					else {
						if (beta.equals("division")) {
							System.out.println(entero1 +" / "+ entero2 + " = ");
							System.out.println(objetoRemoto.division(entero1, entero2));
						}
					} 
				}
			}       
        } 
        	catch (MalformedURLException e) {
        		e.printStackTrace();
        	} 
        	catch (RemoteException e) {
        		e.printStackTrace();
        	} 
        	catch (NotBoundException e) {
        		e.printStackTrace();
        	}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	if (args.length<4 ) {
        	System.out.println("Ingrese: <host> <operador> <operando 1> <operando 2>");    		
    	}
        new Cliente(args[0],args[1],args[2],args[3]);
    }
    
}
