package Sistema.presentation.categorias;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Service;
import java.util.List;

public class Controller {
    private Model model;
    private viewCategorias view;

    public Controller(Model model, viewCategorias view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
        refresh();
    }

    public void refresh() {
        model.setCategorias(Service.instance().findAllCategorias());
        model.setCurrent(new CategoriaRecurso());
    }

    public void search(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            model.setCategorias(Service.instance().findAllCategorias());
        } else {
            List<CategoriaRecurso> filtradas = Service.instance().findAllCategorias().stream()
                    .filter(c -> c.getDescripcion().toLowerCase().contains(filtro.toLowerCase()))
                    .toList();
            model.setCategorias(filtradas);
        }
    }

    public void save(CategoriaRecurso categoria) throws Exception {
        if (categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()) {
            throw new Exception("La descripción de la categoría no puede estar vacía.");
        }

        if (categoria.getID() == 0) {
            Service.instance().createCategoria(categoria);
        } else {
            Service.instance().updateCategoria(categoria);
        }
        refresh();
    }

    public void delete(CategoriaRecurso categoria) throws Exception {
        if (categoria == null || categoria.getID() == 0) {
            throw new Exception("Debe seleccionar una categoría válida para borrar.");
        }
        Service.instance().deleteCategoria(categoria.getID());
        refresh();
    }

    public void clear() {
        model.setCurrent(new CategoriaRecurso());
    }

    public void selectRow(int row) {
        if (row >= 0 && row < model.getCategorias().size()) {
            model.setCurrent(model.getCategorias().get(row));
        }
    }
}
