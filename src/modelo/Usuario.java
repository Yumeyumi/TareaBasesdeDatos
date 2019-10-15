package modelo;
import java.util.HashMap;

public class Usuario {
	
private int id;
private String user;
private String pass;

public int getId() {
	return id;
}
public void setId(int partes) {
	this.id = partes;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}

public String toString() {
 String dato = id + "," + user + "," + pass;
	return dato;
	
}

}
