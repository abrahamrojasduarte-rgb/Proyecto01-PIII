package Sistema.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "categoria")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaRecurso {
    private int id;
    private String descripcion;

    public CategoriaRecurso() {
        this.id = 0;
        this.descripcion = "";
    }

    public CategoriaRecurso(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
