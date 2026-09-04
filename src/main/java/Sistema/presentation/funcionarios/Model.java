package Sistema.presentation.funcionarios;
import Sistema.logic.Funcionario;
import Sistema.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model extends AbstractModel {
    private Funcionario current;
    private List<Funcionario> funcionarios;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setList(List<Funcionario> list) {
        this.funcionarios = list;
        firePropertyChange(LIST);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }
}
