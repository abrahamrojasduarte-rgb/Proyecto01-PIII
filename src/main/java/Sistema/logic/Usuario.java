package Sistema.logic;

public abstract class Usuario {
    private String id;
    private int password;
    private String puesto;

    public Usuario(){
        id = " ";
        password = 0;
        puesto = " ";
    }
    public Usuario(String i, int pass, String p){
        id = i;
        password = pass;
        puesto = p;
    }
}
