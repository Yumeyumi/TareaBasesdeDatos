
public class Controlador {
	Interfaz miaccesoDatos;
	Interfaz miotroAcceso;
//Cuando leemos, siempre saldrá con el formato de hash map para traspasar los datos a otros métodos
	public void eligeAcceso (String key) {
	switch (key) {
	case "1":
		miaccesoDatos = new Fichero();
		miotroAcceso = new BasedeDatos();
		break;
	case "2":
		miaccesoDatos = new BasedeDatos();
		miotroAcceso = new Fichero();
		break;
	}
	}
	public void leerDatos() {
	miaccesoDatos.escribir(miaccesoDatos.leer());
	}
	public void ingresarDatos() {
		miaccesoDatos.leer();
		miaccesoDatos.ingresarDatos();
	}
	public void cambioDatos() {
		miaccesoDatos.pasarDatos(miotroAcceso.leer());
	}
	
}
