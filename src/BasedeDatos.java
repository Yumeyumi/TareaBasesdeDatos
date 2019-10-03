import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class BasedeDatos implements Interfaz{
	
	Connection conexion;
	Statement stmt;
	String url;
	private String login;
	private String pwd;
	private String driver;
	private String query = "SELECT * FROM usuarios";
	Scanner sc = new Scanner(System.in);
	static int contador=0;
	HashMap<Integer,Usuario> datos = new HashMap<Integer,Usuario>();
	
	public BasedeDatos() {
		
		try {
			//Leemos el fichero de configuración y asignamos los valores
			Properties propiedades = new Properties();
			try {
				propiedades.load(new FileReader("Configuracion.txt"));
				url = propiedades.getProperty("urlbd");
				login = propiedades.getProperty("user");
				pwd = propiedades.getProperty("password");
				driver = propiedades.getProperty("driver");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, login, pwd);

			System.out.println(" - Conexión con MySQL establecida -");

		} catch (ClassNotFoundException cnfe) {
			System.out.println(" – Driver JDBC no encontrado -");
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println(" – Error al conectarse a la base de datos -");
			sqle.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
			System.out.println(" – Error general -");
			e.printStackTrace();
		}
	}

	
	@Override
	public HashMap leer() {
		try {
			stmt = conexion.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			while (rset.next()) {
				Usuario us = new Usuario();
				us.setId(rset.getString("ID"));
				us.setPass(rset.getString("Pass"));
				us.setUser(rset.getString("User"));
				datos.put(rset.getInt("ID"), us);
				contador++;
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datos;
	}
	

	@Override
	public void escribir(HashMap datos) {
		
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		System.out.println( e.getValue());
		}
	}


	@Override
	public void ingresarDatos(String usr, String pass) {
		try {
			stmt = conexion.createStatement();
			contador++;
			String queryinsert = "INSERT INTO usuarios (ID,User,Pass) VALUES ('" + contador + "','" + usr + "','" + pass + "')";
			int result = 0;
			result = stmt.executeUpdate(queryinsert);
			System.out.println("Filas insertadas: " + result);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void pasarDatos(HashMap datos) {
		try {
		stmt = conexion.createStatement();
		int result = 0;
		result = stmt.executeUpdate("DELETE FROM usuarios");
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		String dato = e.getValue().toString();
		String[] partes =  dato.split(",");
		String queryinsert = "INSERT INTO usuarios (ID,User,Pass) VALUES ('" + partes[0] + "','" + partes[1] + "','" + partes[2] + "')";
		result = stmt.executeUpdate(queryinsert);
		System.out.println("Filas insertadas: " + result);
	}} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	try {
		stmt.close();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}

}
	
