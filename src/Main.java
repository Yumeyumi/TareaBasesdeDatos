import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Controlador miControlador = new Controlador();
		String key = "0";
		int id;
		while (!key.equals("3")) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Elige el método de acceso a datos que quieras utilizar ");
			System.out.println("1 : Ficheros 2: Base de datos 3: Salir del menú");
			System.out.println("Introduce el número de la acción que quieras realizar");
			key = sc.next();
			if (key.equals("1") || key.equals("2")) {
				miControlador.eligeAcceso(key);
				System.out.println("¿Qué deseas hacer?");
				System.out.println("1 - Leer datos\r\n" + "2 - Ingresar datos\r\n"
						+ "3 - Pasar datos de una base a otra\r\n" + "4 - Mostrar un dato \r\n"
						+ "5 - Modificar un dato \r\n" + "6 - Eliminar un dato\r\n" + "7 - Eliminar todo\r\n");
				System.out.println("Introduce el número de la acción que quieras realizar");

				int keydos = sc.nextInt();
				switch (keydos) {
				case 1:
					miControlador.leerDatos();
					break;
				case 2:
					System.out.println("Inserta nuevo usuario");
					String usr = sc.next();
					System.out.println("Inserta nueva contraseña");
					String pass = sc.next();
					miControlador.ingresarDatos(usr, pass);
					break;
				case 3:
					System.out.println("Elige el tipo de acceso para la importación");
					System.out.println("1 : Base de datos 2: Ficheros");
					System.out.println("Introduce el número de la acción que quieras realizar");
					String keytres = sc.next();
					if (keytres.equals("1") || keytres.equals("2")) {
						miControlador.eligeImport(keytres);
						System.out.println(
								"¿Está seguro de  continuar? Se borraran los datos anteriores de la base de datos");
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
								"No has escrito correctamente qué quieres hacer, Debes elegir entre los números = 1, 2");
					}
				case 4:
					System.out.println("¿Cual es el id del dato que quieres mostrar?");
					id = sc.nextInt();
					miControlador.mostrarDato(id);
					break;
				case 5:
					System.out.println("Elige el id del dato que quieres modificar");
					id = sc.nextInt();
					System.out.println("Inserta nuevo usuario");
					String usr1 = sc.next();
					System.out.println("Inserta nueva contraseña");
					String pass1 = sc.next();

					miControlador.modificarDato(usr1, pass1, id);
					break;
				case 6:
					System.out.println("Elige el id del dato que quieres eliminar");
					id = sc.nextInt();
					miControlador.eliminarDato(id);
					break;
				case 7:
					miControlador.eliminarTodo();
					break;

				}
			} else if (key.equals("3")) {
				System.out.println("Has salido de la aplicación");
			} else {
				System.out.println(
						"No has escrito correctamente qué quieres hacer, Debes elegir entre los números = 1, 2 o 3 ");
			}
		}
	}
}
