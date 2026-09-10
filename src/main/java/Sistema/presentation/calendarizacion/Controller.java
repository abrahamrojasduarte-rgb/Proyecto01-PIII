package Sistema.presentation.calendarizacion;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Reserva;
import Sistema.logic.Service;
import Sistema.presentation.util.PDFReportGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private viewCalendarizacion view;

    public Controller(Model model, viewCalendarizacion view){
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
        refresh();
    }

    public void refresh(){
        model.setCategorias(Service.instance().findAllCategorias());
    }

    public void cargar(LocalDate fecha, CategoriaRecurso categoria) throws Exception{
        if(fecha == null) {
            throw new Exception("Debe seleccionar una fecha");
        }
        if(categoria == null){
            throw new Exception("Debe seleccionar una categoria");
        }
        model.setFecha(fecha);
        model.setRecursos(Service.instance().searchRecursosPorCategoria(categoria));

        List<Reserva> delDia = new ArrayList<>();
        for (Reserva reserva : Service.instance().findAllReservas()){
            if (reserva.getFecha().equals(fecha)){
                delDia.add(reserva);
            }
        }
        model.setReservas(delDia);
    }

    public void imprimir(String ruta,String[] columnas, List<String[]> filas) throws Exception{
        if(model.getRecursos().isEmpty()){
            throw new Exception("Debe de cargar la calendarizacion");
        }
        PDFReportGenerator.generarReporteMatriz("Calendarizacion de Recursos - " + model.getFecha(), columnas, filas, ruta);
    }
}
