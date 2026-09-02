package Sistema.logic;

public class Administrador extends Usuario{
    public Administrador(){
        super();
    }

    public Administrador(String id, String clave){
        super(id,clave,Rol.ADMINISTRADOR);
    }
}
