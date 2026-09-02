package Sistema.logic;

public abstract class Usuario {
    protected String id;
    protected String password;
    protected String puesto;

    public Usuario(){
        id = " ";
        password = "";
        puesto = " ";
    }
    public Usuario(String i, String pass, String p){
        id = i;
        password = pass;
        puesto = p;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPuesto() {
        return puesto;
    }

    public boolean validarClave(String claveIngresada) {
        return password != null && password.equals(claveIngresada);
    }
}
