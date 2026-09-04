package Sistema.presentation.funcionarios;

import Sistema.logic.Funcionario;

public class Controller {
    private Model model;
    private viewFuncionarios view;

    public Controller(Model model, viewFuncionarios view){
        this.model = model;
        this.view = view;
    }

    public void clear() {
        model.setCurrent(new Funcionario());
    }
}
