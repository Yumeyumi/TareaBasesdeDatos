import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import modelo.Usuario;


public class Hibernate implements Interfaz {
	
	HashMap<Integer,Usuario> datos = new HashMap<Integer,Usuario>();
	Session session;
	
	public Hibernate() {
		
		HibernateUtil util = new HibernateUtil(); 
		session = util.getSessionFactory().openSession();	
	}
	
	@Override
	public HashMap<Integer, Usuario> leer() {

        Query q= session.createQuery("select u from Usuario u");
        List results = q.list();
        
        Iterator userIterator= results.iterator();

        while (userIterator.hasNext()){
            Usuario us = (Usuario)userIterator.next();
            Integer id = us.getId();
        	datos.put(id, us);     
        }	
		return datos;
	}

	@Override
	public void ingresarDatos(String usr, String pass, int id) {
		Usuario us = new Usuario();
		us.setUser(usr);
		us.setPass(pass);
		us.setId(id);
		session.beginTransaction();
		session.save(us);
		session.getTransaction().commit();	
	}

	@Override
	public void pasarDatos(HashMap datos) {
		eliminarTodos();
		Iterator it = datos.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry e = (Map.Entry)it.next();
		
		session.beginTransaction();
		session.save(e.getValue());
		session.getTransaction().commit();
		}
		
	}

	@Override
	public void eliminarTodos() {
		session.beginTransaction();
		session.createQuery("delete from Usuario").executeUpdate();	
		session.getTransaction().commit();
	}

	@Override
	public void eliminarUno(HashMap datos, int id) {
		session.beginTransaction();
		Usuario us = new Usuario();
		us=  (Usuario) datos.get(id);
		session.delete(us);
		session.getTransaction().commit();
	}

	@Override
	public void modificarDatos(HashMap datos, int id, String usr, String pass) {
		Usuario us = new Usuario();
		us=  (Usuario) datos.get(id);
		us.setUser(usr);
		us.setPass(pass);
		session.beginTransaction();
		session.update(us);
		session.getTransaction().commit();
	}

}
