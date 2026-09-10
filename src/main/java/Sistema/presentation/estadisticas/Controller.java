package Sistema.presentation.estadisticas;

import Sistema.logic.Recurso;
import Sistema.logic.Reserva;
import Sistema.logic.Service;
import Sistema.presentation.util.PDFReportGenerator;
import org.jfree.data.time.Day;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Model model;
    private viewEstadisticas view;

    public Controller(Model model, viewEstadisticas view){
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    private void validar(LocalDate desde, LocalDate hasta) throws Exception{
        if (desde == null || hasta == null){
            throw new Exception("Debe indicar las fechas desde y hasta");
        }
        if(hasta.isBefore(desde)){
            throw new Exception("La fecha hasta debe ser posterio a la fecha desde");
        }
    }

    public void cargarRecursos(LocalDate desde, LocalDate hasta) throws Exception{
        validar(desde,hasta);

        List<String> nombres = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for(Reserva reserva : Service.instance().findAllReservas()){
            LocalDate fecha = reserva.getFecha();
            if(fecha.isBefore(desde) || fecha.isAfter(hasta)){
                continue;
            }
            for(Recurso recurso : reserva.getRecursos()){
                if(recurso.getCategoria() == null){
                    continue;
                }
                String nombre = recurso.getCategoria().getDescripcion();
                int pos = nombres.indexOf(nombre);

                if (pos == -1){
                    nombres.add(nombre);
                    cantidades.add(1);
                } else{
                    cantidades.set(pos,cantidades.get(pos) + 1);
                }
            }
        }
        List<String[]> datos = new ArrayList<>();
        for(int i = 0; i < nombres.size(); i++){
            datos.add(new String[]{nombres.get(i), String.valueOf(cantidades.get(i))});
        }
        model.setRecursos(datos);
    }

    public void cargarActividades(LocalDate desde, LocalDate hasta) throws Exception{
        validar(desde,hasta);
        List<String[]> datos = new ArrayList<>();
        LocalDate lunes = desde.with(DayOfWeek.MONDAY);

        while(!lunes.isAfter(hasta)){
            LocalDate domingo = lunes.plusDays(6);
            int cantidad = 0;

            for(Reserva reserva : Service.instance().findAllReservas()){
                LocalDate fecha = reserva.getFecha();
                if(fecha.isBefore(desde) || fecha.isAfter(hasta)){
                    continue;
                }
                if(!fecha.isBefore(lunes) && !fecha.isAfter(domingo)){
                    cantidad++;
                }
            }
            datos.add(new String[]{lunes.toString(), String.valueOf(cantidad)});
            lunes = lunes.plusDays(7);
        }
        model.setActividades(datos);
    }

    public void imprimirRecursos(String ruta) throws Exception{
        if(model.getRecursos().isEmpty()){
            throw new Exception("Debe cargar las estadisticas de recursos");
        }
        PDFReportGenerator.generarReporteMatriz("Recursos reservados",new String[]{"Categoria", "Cantidad"}, model.getRecursos(),ruta);
    }

    public void imprimirActividades(String ruta) throws Exception{
        if(model.getActividades().isEmpty()){
            throw new Exception("Debe cargar las estadisticas de actividades");
        }
        PDFReportGenerator.generarReporteMatriz("Actividades programadas", new String[]{"Semana", "Cantidad"}, model.getActividades(), ruta);
    }
}
