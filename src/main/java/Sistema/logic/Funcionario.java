package Sistema.logic;

public class Funcionario extends Usuario{
    private String nombre;
    private String telefono;

    public Funcionario(){
        super();
    }

    public Funcionario(String id, String nombre, String telefono){
        super(id, Rol.FUNCIONARIO);
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
