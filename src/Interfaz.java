import java.util.HashMap;

import modelo.Usuario;

public interface Interfaz {
//leer archivos
	
public HashMap<Integer,Usuario> leer();	
public void ingresarDatos(String usr, String pass, int id);
public void pasarDatos(HashMap datos);
public void eliminarTodos();
public void eliminarUno(HashMap datos, int id);
public void modificarDatos(HashMap datos, int id, String usr, String pass);
}
