import java.util.HashMap;

public class Usuario {
	
private String id;
private String user;
private String pass;

public String getId() {
	return id;
}
public void setId(String partes) {
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
