import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Main {
	
	public static void escribir(HashMap datos) {
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		System.out.println( e.getValue());
		}
	}
	
	public static void main(String[] args) {

		Controlador miControlador = new Controlador();
		String key = "0";
		int id;
		while (!key.equals("5")) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Elige el m�todo de acceso a datos que quieras utilizar ");
			System.out.println("1 : Ficheros 2: Base de datos 3: Hibernate 4: Mongo 5: Cliente/Servidor 6:Node 7:Salir del men�");
			System.out.println("Introduce el n�mero de la acci�n que quieras realizar");
			key = sc.next();
			if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4")|| key.equals("5")|| key.equals("6")) {
				miControlador.eligeAcceso(key);
				
				System.out.println("�Qu� deseas hacer?");
				System.out.println("1 - Leer datos\r\n" + "2 - Ingresar datos\r\n"
						+ "3 - Pasar datos de una base a otra\r\n" + "4 - Mostrar un dato \r\n"
						+ "5 - Modificar un dato \r\n" + "6 - Eliminar un dato\r\n" + "7 - Eliminar todo\r\n");
				System.out.println("Introduce el n�mero de la acci�n que quieras realizar");

				int keydos = sc.nextInt();
				switch (keydos) {
				case 1:
					escribir(miControlador.leerDatos());
					break;
				case 2:
					System.out.println("Inserta nuevo usuario");
					String usr = sc.next();
					System.out.println("Inserta nueva contrase�a");
					String pass = sc.next();
					miControlador.ingresarDatos(usr, pass);
					break;
				case 3:
					System.out.println("Elige el tipo de acceso para la importaci�n");
					
					if (key.equals("1")) {
						System.out.println("1 : Ficheros 2: Hibernate 3: Mongo 4: Cliente/Servidor 5: Node");
					} else if (key.equals("2")) {
						System.out.println("1 : Base de datos 2: Hibernate 3: Mongo 4: Cliente/Servidor 5: Node");
					} else if (key.equals("3")) {
						System.out.println("1 : Base de datos 2: Ficheros 3: Mongo 4: Cliente/Servidor 5: Node");
					}else if (key.equals("4")) {
						System.out.println("1 : Base de datos 2: Ficheros 3: Hibernate 4: Cliente/Servidor 5: Node");
					}else if (key.equals("5")) {
						System.out.println("1 : Base de datos 2: Ficheros 3: Hibernate 4: Mongo 5: Node");
					}else if (key.equals("6")) {
						System.out.println("1 : Base de datos 2: Ficheros 3: Hibernate 4: Mongo 5:Cliente/Servidor");
					}

					System.out.println("Introduce el n�mero de la acci�n que quieras realizar");
					String keytres = sc.next();
					if (keytres.equals("1") || keytres.equals("2") || keytres.equals("3")|| keytres.equals("4")) {
						miControlador.eligeImport(keytres, key);
						System.out.println(
								"�Est� seguro de  continuar? Se borraran los datos anteriores de la base de datos");
						System.out.println("Escriba si o no");
						String respuesta = sc.next();
						if (respuesta.toLowerCase().equals("si")) {
							miControlador.cambioDatos();
						} else {
							System.out
									.println("No se ha gestionado el intercambio, si desea hacerlo debe escribir = si");
						}
						break;
					} else {
						System.out.println(
								"No has escrito correctamente qu� quieres hacer, Debes elegir entre los n�meros = 1, 2");
					}
				case 4:
					System.out.println("�Cual es el id del dato que quieres mostrar?");
					id = sc.nextInt();
					System.out.println(miControlador.mostrarDato(id).toString());
					break;
				case 5:
					System.out.println("Elige el id del dato que quieres modificar");
					id = sc.nextInt();
					System.out.println("Inserta nuevo usuario");
					String usr1 = sc.next();
					System.out.println("Inserta nueva contrase�a");
					String pass1 = sc.next();

					miControlador.modificarDato(usr1, pass1, id);
					break;
				case 6:
					System.out.println("Elige el id del dato que quieres eliminar");
					id = sc.nextInt();
					miControlador.eliminarDato(id);
					System.out.println("Se ha eliminado correctamente");
					break;
				case 7:
					miControlador.eliminarTodo();
					System.out.println("Has eliminado toda la base de datos");
					break;

				}
				
				
			} else if (key.equals("7")) {
				System.out.println("Has salido de la aplicaci�n");
			} else {
				System.out.println(
						"No has escrito correctamente qu� quieres hacer, Debes elegir entre los n�meros = 1, 2 o 3 ");
			}
		}
	}
}
