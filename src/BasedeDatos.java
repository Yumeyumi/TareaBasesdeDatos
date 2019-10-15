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

import modelo.Usuario;

public class BasedeDatos implements Interfaz{
	
	Connection conexion;
	Statement stmt;
	String url;
	private String login;
	private String pwd;
	private String driver;
	private String query = "SELECT * FROM usuarios";
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
				us.setId(rset.getInt("ID"));
				us.setPass(rset.getString("Pass"));
				us.setUser(rset.getString("User"));
				datos.put(rset.getInt("ID"), us);
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
	public void ingresarDatos(String usr, String pass, int id) {
		id++;
		try {
			stmt = conexion.createStatement();
			String queryinsert = "INSERT INTO usuarios (ID,User,Pass) VALUES ('" + id + "','" + usr + "','" + pass + "')";
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
		Usuario dato = (Usuario)e.getValue();	
		String queryinsert = "INSERT INTO usuarios (ID,User,Pass) VALUES ('" + dato.getId() + "','" + dato.getUser() + "','" + dato.getPass() + "')";
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


	@Override
	public void modificarDatos(HashMap datos, int id,String usr, String pass) {
		try {
			stmt = conexion.createStatement();
			String queryinsert = "UPDATE usuarios SET User = '" + usr +  "', Pass = '" + pass +  "' WHERE ID = '" + id + "'"; 
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
	public void eliminarTodos() {
		try {
		stmt = conexion.createStatement();
		int result = 0;
		result = stmt.executeUpdate("DELETE FROM usuarios");
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void eliminarUno(HashMap datos,int id) {
		try {
			stmt = conexion.createStatement();
			String queryinsert = "DELETE FROM usuarios WHERE ID = '" + id + "'"; 
			int result = 0;
			result = stmt.executeUpdate(queryinsert);
			System.out.println("Filas insertadas: " + result);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
	
