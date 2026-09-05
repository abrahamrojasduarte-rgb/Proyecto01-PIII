package Sistema.logic;

public class Usuario {
    private String id;
    private String clave;
    private Rol rol;

    public Usuario(){

    }

    public Usuario(String id, Rol rol){
        this.id = id;
        this.clave = "456";
        this.rol = rol;
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){ this.id = id; }

    public String getClave(){
        return this.clave;
    }

    public Rol getRol(){
        return this.rol;
    }

    public void setClave(String clave){
        this.clave = clave;
    }

    public void setRol(Rol rol){
        this.rol = rol;
    }


}
