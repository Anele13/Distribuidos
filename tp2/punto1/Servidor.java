/*
 * Servidor.java
 *
 * Contiene el código para instanciar y publicar el objetoRemoto en la rmiregistry
 */
 
import java.rmi.Naming;                    /* lookup         */
import java.rmi.registry.Registry;         /* REGISTRY_PORT  */

/**
 * Servidor para el ejemplo de RMI.
 * Exporte un metodo para sumar dos enteros y devuelve el resultado.
 */
public class Servidor 
{
    
    /** Crea nueva instancia de Servidor rmi */
    public Servidor(String host, String operador) {
        try 
        {
        	if (operador.equals("suma") || operador.equals("resta")) {
        		IRSumaResta objetoRemoto = new ORSumaResta();
    			String rname = "//localhost:" + Registry.REGISTRY_PORT  + "/ORSumaResta";
                Naming.rebind (rname, objetoRemoto);	
        	}
        	else {
        		if (operador.equals("multiplicacion") || operador.equals("division")) {
        			System.out.println("Es una multiplicacion o division");
        			IRMultiplicacionDivision objetoRemoto = new ORMultiplicacionDivision();
        			String rname = "//"+ host +":" + Registry.REGISTRY_PORT  + "/ORMultiplicacionDivision";
        			Naming.rebind(rname, objetoRemoto);
////        			Naming.rebind (rname, objetoRemoto);
        		}
        	}
            // Se publica el objeto remoto            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	if (args.length < 2) {
        	System.out.println("Ingrese: <host> <operador>");
    	}
        new Servidor(args[0],args[1]);
        System.out.println("Corrio el server");
    }
}
