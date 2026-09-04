package Sistema.presentation.funcionarios;
import Sistema.logic.Funcionario;
import Sistema.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model extends AbstractModel {
    Funcionario current;
    List<Funcionario> funcionarios;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model(){
        current = new Funcionario();
        funcionarios = new ArrayList<Funcionario>();
    }
    public Funcionario getCurrent() {
        return current;
    }

    public void setCurrent(Funcionario current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
