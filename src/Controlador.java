import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Controlador {
	Interfaz miaccesoDatos;
	Interfaz miotroAcceso;
//Cuando leemos, siempre saldrá con el formato de hash map para traspasar los datos a otros métodos
	public void eligeAcceso (String key) {
	switch (key) {
	case "1":
		miaccesoDatos = new Fichero();
		break;
	case "2":
		miaccesoDatos = new BasedeDatos();
		break;
	case "3":
		miaccesoDatos = new Hibernate();
		break;
	}
	}
	
	public void eligeImport (String key, String keyAccess) {
		
		if (keyAccess.equals("1")) {
			switch (key) {
			case "1":
				miotroAcceso = new Fichero();
				break;
			case "2":
				miotroAcceso = new Hibernate();
				break;
			}
			
		}else if (keyAccess.equals("2")) {
			switch (key) {
			case "1":
				miotroAcceso = new BasedeDatos();
				break;
			case "2":
				miotroAcceso = new Hibernate();
				break;
			}
			
		}else if (keyAccess.equals("3")) {
			
			switch (key) {
			case "1":
				miotroAcceso = new BasedeDatos();
				break;
			case "2":
				miotroAcceso = new Fichero();
				break;
			}
		}
	}
	
	public void leerDatos() {
		escribir(miaccesoDatos.leer());
	}
	public void ingresarDatos(String usr, String pass) {
		miaccesoDatos.ingresarDatos(usr,pass,ultimaId(miaccesoDatos.leer()));
	}
	public void cambioDatos() {
		miaccesoDatos.pasarDatos(miotroAcceso.leer());
	}
	public void mostrarDato(int id) {
		buscar(miaccesoDatos.leer(), id);
	}
	public void modificarDato(String usr, String pass, int id) {
		miaccesoDatos.modificarDatos(miaccesoDatos.leer(),id , usr, pass );;
	}
	public void eliminarDato(int id) {
		miaccesoDatos.eliminarUno(miaccesoDatos.leer(),id);
	}
	public void eliminarTodo() {
		miaccesoDatos.eliminarTodos();
	}
	
	
	
	public int ultimaId(HashMap datos) {
		int ultimoValor = 0;
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		ultimoValor = (int) e.getKey();
		}
		System.out.println("Aquí está el id" + ultimoValor);
		return ultimoValor;
	}
	
	public void buscar(HashMap datos, Integer id) {
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		if ( e.getKey() == id )
		System.out.println( e.getValue());
		}
	}

	public void escribir(HashMap datos) {
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		System.out.println( e.getValue());
		}
	}
	
		
	
}
