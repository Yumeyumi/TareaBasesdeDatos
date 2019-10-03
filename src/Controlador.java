
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
	}
	}
	public void eligeImport (String key) {
		switch (key) {
		case "1":
			miotroAcceso = new BasedeDatos();
			break;
		case "2":
			miotroAcceso = new Fichero();
			break;
		}
		}
	
	public void leerDatos() {
	miaccesoDatos.escribir(miaccesoDatos.leer());
	}
	public void ingresarDatos(String usr, String pass) {
		miaccesoDatos.leer();
		miaccesoDatos.ingresarDatos(usr,pass);
	}
	public void cambioDatos() {
		miaccesoDatos.pasarDatos(miotroAcceso.leer());
	}
	public void mostrarDato(int id) {
		miaccesoDatos.buscar(miaccesoDatos.leer(), id);
	}
	public void modificarDato(String usr, String pass, int id) {
		miaccesoDatos.modificarDatos(usr, pass, id);;
	}
	public void eliminarDato(int id) {
		miaccesoDatos.eliminarUno(id);
	}
	public void eliminarTodo() {
		miaccesoDatos.eliminarTodos();
	}
	
}
