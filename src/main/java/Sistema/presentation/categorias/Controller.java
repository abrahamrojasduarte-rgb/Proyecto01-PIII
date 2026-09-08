package Sistema.presentation.categorias;

import Sistema.logic.Service;

public class Controller {
    private Sistema.presentation.categorias.Model model = new Sistema.presentation.categorias.Model();
    private viewCategorias view = new viewCategorias();
    private Service instance = Service.instance();

    public Controller(Model funcModel, viewCategorias catView) {
        this.model = model;
        this.view = view;
    }
}
