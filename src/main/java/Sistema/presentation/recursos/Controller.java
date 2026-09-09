package Sistema.presentation.recursos;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Recurso;
import Sistema.logic.Service;

public class Controller {
    private Model model;
    private viewRecursos view;

    public Controller(Model model, viewRecursos view) {
        this.model = model;
        this.view = view;

        this.view.setController(this);
        this.view.setModel(model);

        refresh();
    }

    public void refresh() {
        model.setCategorias(Service.instance().findAllCategorias());
        model.setRecursos(Service.instance().findAllRecursos());
        model.setCurrent(new Recurso());
    }

    public void clear() {
        model.setCurrent(new Recurso());
    }

    public void save(Recurso r) throws Exception {
        if (Service.instance().findRecursoById(r.getId()) == null) {
            Service.instance().createRecurso(r);
        } else {
            Service.instance().updateRecurso(r);
        }
        clear();
        model.setRecursos(Service.instance().findAllRecursos());
    }

    public void delete(String id) throws Exception {
        Service.instance().deleteRecurso(id);
        clear();
        model.setRecursos(Service.instance().findAllRecursos());
    }

    public void filterByCategoria(CategoriaRecurso cat) {
        model.setRecursos(Service.instance().searchRecursosPorCategoria(cat));
    }
}
