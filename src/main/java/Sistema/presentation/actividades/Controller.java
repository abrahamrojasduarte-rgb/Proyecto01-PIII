package Sistema.presentation.actividades;

import Sistema.logic.Reserva;
import Sistema.logic.Service;
import Sistema.presentation.util.PDFReportGenerator;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private viewActividades view;
    private LocalDate lunes;
    private List<Reserva> reservas;

    public Controller(Model model, viewActividades view){
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public void cargar(LocalDate referencia) throws Exception{
        if (referencia == null){
            throw new Exception("Debe seleccionar una fecha de referencia");
        }
        LocalDate lunes = referencia.with(DayOfWeek.MONDAY);
        LocalDate domingo = lunes.plusDays(6);
        model.setLunes(lunes);

        List<Reserva> semana = new ArrayList<>();
        for(Reserva reserva : Service.instance().findAllReservas()){
            LocalDate fecha = reserva.getFecha();
            if(!fecha.isBefore(lunes) && !fecha.isAfter(domingo)){
                semana.add(reserva);
            }
        }
        model.setReservas(semana);
    }

    public void imprimir(String ruta, String[] columnas, List<String[]> filas) throws Exception{
        PDFReportGenerator.generarReporteMatriz("Programacion de Actividades - Semana del " + model.getLunes(), columnas, filas, ruta);
    }
}
