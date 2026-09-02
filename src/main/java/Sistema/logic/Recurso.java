package Sistema.logic;

public class Recurso {
    private String id;
    private String descripcion;
    private CategoriaRecurso categoria;

    public Recurso(){

    }

    public Recurso(String id, String descripcion, CategoriaRecurso categoria){
        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public CategoriaRecurso getCategoria() {
        return this.categoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(CategoriaRecurso categoria) {
        this.categoria = categoria;
    }

}
