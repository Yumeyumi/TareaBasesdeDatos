import java.util.HashMap;

public interface Interfaz {
//leer archivos
	
public HashMap<Integer,Usuario> leer();	
public void escribir(HashMap datos);
public void ingresarDatos(String usr, String pass);
public void pasarDatos(HashMap datos);
public void modificarDatos(String usr, String pass, int id);
public void buscar(HashMap datos, Integer id );
public void eliminarTodos();
public void eliminarUno(int id);
}
