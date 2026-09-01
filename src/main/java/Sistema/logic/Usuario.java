package Sistema.logic;

public abstract class Usuario {
    protected String id;
    protected int password;
    protected String puesto;
    protected int numTelefono;

    public Usuario(){
        id = " ";
        password = 0;
        puesto = " ";
        numTelefono = 0;
    }
    public Usuario(String i, int pass, String p, int n){
        id = i;
        password = pass;
        puesto = p;
        numTelefono = n;
    }

    public String getId() {
        return id;
    }

    public int getPassword() {
        return password;
    }

    public String getPuesto() {
        return puesto;
    }

    public int getNumTelefono() {
        return numTelefono;
    }
}
