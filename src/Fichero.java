import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import modelo.Usuario;

public class Fichero implements Interfaz {
	HashMap<Integer,Usuario> datos = new HashMap<Integer,Usuario>();
	Scanner sc = new Scanner(System.in);
	

	@Override
	public HashMap leer() {
		String nombreFichero = "Fichero.txt";
		// Declarar una variable BufferedReader
		BufferedReader br = null;
		try {
			// Crear un objeto BufferedReader al que se le pasa
			// un objeto FileReader con el nombre del fichero
			br = new BufferedReader(new FileReader(nombreFichero));
			// Leer la primera línea, guardando en un String
			String texto = br.readLine();

			// Repetir mientras no se llegue al final del fichero
			while (texto != null) {
				Usuario us = new Usuario();
				String[] partes = texto.split(",");
				us.setId(Integer.parseInt(partes[0]));
				us.setUser(partes[1]);
				us.setPass(partes[2]);
				datos.put(Integer.parseInt(partes[0]), us);
				texto = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: Fichero no encontrado");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Error de lectura del fichero");
			System.out.println(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				System.out.println("Error al cerrar el fichero");
				System.out.println(e.getMessage());
			}
		}
		return datos;
	}

	@Override
	public void ingresarDatos(String urs, String pass, int id) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		id++;
		try {
			String data = "\n"+id+ "," + urs + "," + pass;
			File file = new File("Fichero.txt");
			// Si el archivo no existe, se crea!
			if (!file.exists()) {
				file.createNewFile();
			}
			// flag true, indica adjuntar información al archivo.
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}

	@Override
	public void pasarDatos(HashMap datos) {
			String data = "";
			BufferedWriter bw = null;
			FileWriter fw = null;
			try {
				Iterator it = datos.entrySet().iterator();
				while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				Usuario dato = (Usuario)e.getValue();;
				
				data += dato.getId()+","+ dato.getUser()+","+ dato.getPass()+"\n";
				}
				File file = new File("Fichero.txt");
				// Si el archivo no existe, se crea!
				if (!file.exists()) {
					file.createNewFile();
				}
				// flag true, indica adjuntar información al archivo.
				fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				bw.write(data);
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// Cierra instancias de FileWriter y BufferedWriter
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
	}

	@Override
	public void modificarDatos(HashMap datos, int id, String usr, String pass) {
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		if ((int)e.getKey() == id) {
			Usuario us = new Usuario();
			us.setId(id);
			us.setUser(usr);
			us.setPass(pass);
			e.setValue(us);
		}
		}
		pasarDatos(datos);
		
	}

	@Override
	public void eliminarTodos() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Fichero.txt"));
			bw.write("");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarUno(HashMap datos, int id) {
		Set entrySet = datos.entrySet();
		Iterator iterator = entrySet.iterator();
		while (iterator.hasNext()){
		    Map.Entry e = (Map.Entry) iterator.next();
		    if((int)e.getKey()==id){
		        iterator.remove();
		    }
		}
		pasarDatos(datos);	
	}

}
