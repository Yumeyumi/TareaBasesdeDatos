import java.util.HashMap;

public interface Interfaz {
//leer archivos
	
public HashMap<Integer,Usuario> leer();	
public void escribir(HashMap datos);
public void ingresarDatos(String usr, String pass);
public void pasarDatos(HashMap datos);
}
