import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import modelo.Usuario;

public class Mongo implements Interfaz {

	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("Usuario");
	MongoCollection<Document> usuarioCollection = db.getCollection("usuario");
	FindIterable findIterableUsuario = usuarioCollection.find();
	Iterator iteratorUsuario = findIterableUsuario.iterator();


	@Override
	public HashMap<Integer, Usuario> leer() {
		
		HashMap<Integer,Usuario> datos = new HashMap<Integer,Usuario>();
		while (iteratorUsuario.hasNext()) {
			Document docUser = (Document) iteratorUsuario.next();
			Usuario usuario = new Usuario();
			usuario.setId(Integer.parseInt("" + docUser.get("id")));
			usuario.setUser("" + docUser.get("user"));
			usuario.setPass("" + docUser.get("pass"));
			
			
			datos.put(usuario.getId(), usuario);
	
		}

		return datos;
	}

	@Override
	public void ingresarDatos(String usr, String pass, int id) {
		
		usuarioCollection.insertOne(
                 new Document()
                         .append("id", id)
                         .append("user", usr)
                         .append("pass", pass));

	}

	@Override
	public void pasarDatos(HashMap datos) {
		usuarioCollection.drop();
		
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		Usuario usuario = (Usuario)e.getValue();
		
		usuarioCollection.insertOne(
                new Document()
                        .append("id", usuario.getId())
                        .append("user", usuario.getUser())
                        .append("pass", usuario.getPass()));
		}

	}

	@Override
	public void eliminarTodos() {
		usuarioCollection.drop();

	}

	@Override
	public void eliminarUno(HashMap datos, int id) {
		
		Document delete = new Document();
        delete.append("id", id);
        usuarioCollection.deleteOne(delete);

	}

	@Override
	public void modificarDatos(HashMap datos, int id, String usr, String pass) {
		
		Document query = new Document();
        query.append("id", id);
        
		Document setDataUser = new Document();
		setDataUser.append("user", usr );
		
		Document updateUser = new Document();
		updateUser.append("$set", setDataUser);
		
		Document setDataPass = new Document();
		setDataPass.append("user", pass );
		
		Document updatePass = new Document();
		updatePass.append("$set", setDataPass);
	    
		usuarioCollection.updateOne(query, updatePass);
	    usuarioCollection.updateOne(query, updateUser);
	    
		}

}
