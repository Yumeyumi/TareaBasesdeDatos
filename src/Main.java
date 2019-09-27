import java.util.Scanner;


public class Main {
public static void main(String[] args) {
	
	Controlador miControlador = new Controlador();
	
	Scanner sc = new Scanner(System.in);
		System.out.println("Elige el método de acceso a datos que quieras utilizar ");
		System.out.println("1 : Ficheros 2: Base de datos");
		System.out.println("Introduce el número de la acción que quieras realizar");
		int key = sc.nextInt();
		
		miControlador.eligeAcceso(key);
		System.out.println("¿Qué deseas hacer?");
		System.out.println("1 - Leer datos\r\n" + 
				"2 - Ingresar datos\r\n" + 
				"3 - Pasar datos de una base a otra\r\n");
		System.out.println("Introduce el número de la acción que quieras realizar");
		
		int keydos = sc.nextInt();
		switch (keydos) {
		case 1:
			miControlador.leerDatos();
			break;
		case 2:
			miControlador.ingresarDatos();
			break;
		case 3:
			miControlador.cambioDatos();
			break;
		}
		
		
}
}
