package Sistema.logic;

public class CategoriaRecurso {
    private static int idAuto = 1;
    private int id;
    private String descripcion;

    public CategoriaRecurso(){

    }

    public CategoriaRecurso(String descripcion){
        this.id = idAuto++;
        this.descripcion = descripcion;
    }

    public int getID(){
        return this.id;
    }

    public String getDescripcion(){
        return this.descripcion;
    }


    public void SetDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}
