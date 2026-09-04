package Sistema.presentation.funcionarios;

import Sistema.logic.Funcionario;
import Sistema.logic.Service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  Controller {
    private Model model;
    private viewFuncionarios view;

    public Controller(Model model, viewFuncionarios view) {
        this.model = model;
        this.view = view;

        this.view.setController(this);
        this.view.setModel(model);

        this.view.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }


    public void clear() {
        model.setCurrent(new Funcionario());
    }

    public void create(Funcionario e) throws Exception {
        Service.instance().create(e);
        model.setCurrent(new Funcionario());
        model.setList(Service.instance().findAll());
    }

    public void delete(String id) throws Exception {
        Service.instance().delete(id);
        model.setCurrent(new Funcionario());
        model.setList(Service.instance().findAll());
    }

    public void search(String id, String nombre) {
        model.setList(Service.instance().search(id, nombre));
    }
}
