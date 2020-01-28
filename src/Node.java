
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

public class Node implements Interfaz {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH; // Datos de
																											// la
																											// conexion

	public Node() {

		encargadoPeticiones = new ApiRequests();

		SERVER_PATH = "http://localhost:9000/user";
		

	}

	@Override
	public HashMap<Integer, Usuario> leer() {

		HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();

		String url = SERVER_PATH; // Sacadas de configuracion

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
						user = row.get("User").toString();
						pass = row.get("Pass").toString();
						id = Integer.parseInt(row.get("ID").toString());

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

			objUser.put("User", usr);
			objUser.put("Pass", pass);
			objUser.put("ID", idnew);

			String json = objUser.toJSONString();

			System.out.println("Lanzamos peticion JSON para almacenar un jugador");
			
			String url = SERVER_PATH;

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

					System.out.println("Almacenado usuario");

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

		String url = SERVER_PATH;
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
		String url = SERVER_PATH; // Sacadas de configuracion

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
		objUser.put("ID", id);

		String json = objUser.toJSONString();

		String url = SERVER_PATH + "/"+id; // Sacadas de configuracion
		try {
			encargadoPeticiones.deleteRequest(url, json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void modificarDatos(HashMap datos, int id, String usr, String pass) {

		JSONObject objUser = new JSONObject();

		objUser.put("ID", id);
		objUser.put("User", usr);
		objUser.put("Pass", pass);


		String json = objUser.toJSONString();
		String url = SERVER_PATH+ "/"+id;; // Sacadas de configuracion
		try {
			encargadoPeticiones.putRequest(url, json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

