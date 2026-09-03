package Sistema.data;
import Sistema.logic.Administrador;
import Sistema.logic.Funcionario;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class data {
    @XmlElementWrapper(name = "Usuario")
    @XmlElement(name = "Funcionario")
    private List<Funcionario> funcionarios ;

    @XmlElementWrapper(name = "Usuario")
    @XmlElement(name = "Admin")
    private List<Administrador> administradors ;

    public data(){
        funcionarios = new ArrayList<>();
        administradors = new ArrayList<>();
    }

    public List<Funcionario> getPersonas() {
        return funcionarios;
    }

    public List<Administrador> getDepartamentos() {
        return administradors;
    }
}
