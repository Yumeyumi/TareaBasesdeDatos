import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import modelo.ApiRequests;
import modelo.Usuario;

public class ClienteServidor implements Interfaz {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET_USER, SET_USER, DELETE_USER, UPDATE_USER, DELETEALL_USER, IMPORT_USER; // Datos de
																											// la
																											// conexion

	public ClienteServidor() {

		encargadoPeticiones = new ApiRequests();

		SERVER_PATH = "http://localhost/bea/Usuarios/";
		GET_USER = "leeUsuario.php";
		SET_USER = "escribirUsuario.php";
		DELETE_USER = "borrarUsuario.php";
		UPDATE_USER = "updateUsuario.php";
		DELETEALL_USER = "deleteAllUsuario.php";
		IMPORT_USER = "importUsuario.php";

	}

	@Override
	public HashMap<Integer, Usuario> leer() {

		HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();

		String url = SERVER_PATH + GET_USER; // Sacadas de configuracion

		String response = null;
		try {
			response = encargadoPeticiones.getRequest(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		if (respuesta == null) {
			System.exit(-1);
		} else { // El JSON recibido es correcto
			// Sera "ok" si todo ha ido bien o "error" si hay algún problema
			String estado = (String) respuesta.get("estado");
			// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
			if (estado.equals("ok")) {
				JSONArray array = (JSONArray) respuesta.get("usuarios");

				if (array.size() > 0) {
					// Declaramos variables
					Usuario us;
					String user;
					String pass;
					int id;

					for (int i = 0; i < array.size(); i++) {
						JSONObject row = (JSONObject) array.get(i);
						user = row.get("user").toString();
						pass = row.get("pass").toString();
						id = Integer.parseInt(row.get("id").toString());

						us = new Usuario();
						us.setId(id);
						us.setPass(pass);
						us.setUser(user);
						usuarios.put(id, us);
					}
				}
			}
		}

		return usuarios;
	}

	@Override
	public void ingresarDatos(String usr, String pass, int id) {
		try {
			int idnew = id + 1;
			JSONObject objUser = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objUser.put("user", usr);
			objUser.put("pass", pass);
			objUser.put("id", idnew);

			objPeticion.put("peticion", "add");
			objPeticion.put("userAnnadir", objUser);

			String json = objPeticion.toJSONString();

			System.out.println("Lanzamos peticion JSON para almacenar un jugador");

			String url = SERVER_PATH + SET_USER;

			System.out.println("La url a la que lanzamos la petición es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);
			// System.exit(-1);

			String response = encargadoPeticiones.postRequest(url, json);

			System.out.println("El json que recibimos es: ");

			System.out.println(response); // Traza para pruebas
			System.exit(-1);

			// Parseamos la respuesta y la convertimos en un JSONObject

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else { // El JSON recibido es correcto

				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				if (estado.equals("ok")) {

					System.out.println("Almacenado jugador enviado por JSON Remoto");

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));
					System.exit(-1);
				}
			}
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el método 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecución");
			System.exit(-1);
		}

	}

	@Override
	public void pasarDatos(HashMap datos) {
		// TODO Auto-generated method stub
		 eliminarTodos();

		String url = SERVER_PATH + IMPORT_USER;
		JSONObject objPeticion = new JSONObject();
		ArrayList<JSONObject> arrayUsuarios = new ArrayList<JSONObject>();

		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Usuario dato = (Usuario) e.getValue();
			JSONObject objUser = new JSONObject();

			objUser.put("user", dato.getUser());
			objUser.put("pass", dato.getPass());
			objUser.put("id", dato.getId());

			arrayUsuarios.add(objUser);
		}

		objPeticion.put("userImport", arrayUsuarios);
		objPeticion.put("peticion", "import");

		String json = objPeticion.toJSONString();
		 try {
			encargadoPeticiones.postRequest(url, json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarTodos() {
		String url = SERVER_PATH + DELETEALL_USER; // Sacadas de configuracion

		try {
			encargadoPeticiones.getRequest(url);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void eliminarUno(HashMap datos, int id) {
		JSONObject objUser = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		objUser.put("id", id);

		objPeticion.put("peticion", "delete");
		objPeticion.put("userDelete", objUser);

		String json = objPeticion.toJSONString();

		String url = SERVER_PATH + DELETE_USER; // Sacadas de configuracion
		try {
			encargadoPeticiones.postRequest(url, json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void modificarDatos(HashMap datos, int id, String usr, String pass) {

		JSONObject objUser = new JSONObject();
		JSONObject objPeticion = new JSONObject();

		objUser.put("id", id);
		objUser.put("user", usr);
		objUser.put("pass", pass);

		objPeticion.put("peticion", "update");
		objPeticion.put("userUpdate", objUser);

		String json = objPeticion.toJSONString();
		String url = SERVER_PATH + UPDATE_USER; // Sacadas de configuracion
		try {
			encargadoPeticiones.postRequest(url, json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
