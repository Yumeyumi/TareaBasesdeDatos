import java.util.Scanner;


public class Main {
public static void main(String[] args) {
	
	Controlador miControlador = new Controlador();
	String key="0";
	while (!key.equals("3")) {
	Scanner sc = new Scanner(System.in);
		System.out.println("Elige el m�todo de acceso a datos que quieras utilizar ");
		System.out.println("1 : Ficheros 2: Base de datos 3: Salir del men�");
		System.out.println("Introduce el n�mero de la acci�n que quieras realizar");
		key = sc.next();
		if (key.equals("1") || key.equals("2")) {
		miControlador.eligeAcceso(key);
		System.out.println("�Qu� deseas hacer?");
		System.out.println("1 - Leer datos\r\n" + 
				"2 - Ingresar datos\r\n" + 
				"3 - Pasar datos de una base a otra\r\n");
		System.out.println("Introduce el n�mero de la acci�n que quieras realizar");
		
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
		
}else if(key.equals("3")) {
	System.out.println("Has salido de la aplicaci�n");
}else {
	System.out.println("No has escrito correctamente qu� quieres hacer, Debes elegir entre los n�meros = 1, 2 o 3 ");
}
	}
}
}
