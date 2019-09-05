package punto3b;

public interface Irfs {
	/* Interfaz que vamos a utilizar declarando las cabeceras de los metodos solamente. similar a RFS.X en rpcgen  */
	float abrir(float numero1, float numero2);
	float leer(float numero1, float numero2);
	float escribir(float numero1, float numero2);
	float cerrar(float numero1, float numero2);
}
