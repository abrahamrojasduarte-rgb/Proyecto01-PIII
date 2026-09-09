package Sistema.data;

import Sistema.logic.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class data {

    @XmlElementWrapper(name = "Funcionarios")
    @XmlElement(name = "Funcionario")
    private List<Funcionario> funcionarios = new ArrayList<>();

    @XmlElementWrapper(name = "Administradores")
    @XmlElement(name = "Admin")
    private List<Administrador> administradors = new ArrayList<>();

    @XmlElementWrapper(name = "Categorias")
    @XmlElement(name = "Categoria")
    private List<CategoriaRecurso> categorias = new ArrayList<>();

    @XmlElementWrapper(name = "Recursos")
    @XmlElement(name = "Recurso")
    private List<Recurso> recursos = new ArrayList<>();

    @XmlElementWrapper(name = "Reservas")
    @XmlElement(name = "Reserva")
    private List<Reserva> reservas = new ArrayList<>();

    public data() {
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Administrador> getAdministradors() {
        return administradors;
    }

    public void setAdministradors(List<Administrador> administradors) {
        this.administradors = administradors;
    }

    public List<CategoriaRecurso> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaRecurso> categorias) {
        this.categorias = categorias;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
