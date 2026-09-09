package Sistema.presentation.reserva;

import Sistema.Sesion;
import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Funcionario;
import Sistema.logic.Reserva;
import Sistema.logic.ReservaExtraccion;
import Sistema.logic.Service;
import Sistema.presentation.util.PDFReportGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Model model;
    private viewReserva view;

    public Controller(Model model, viewReserva view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
        refresh();
    }

    public void refresh() {
        model.setCategorias(Service.instance().findAllCategorias());
        model.setReservas(buscarReservas());
        clear();
    }

    private List<Reserva> buscarReservas() {
        if (!(Sesion.getUsuario() instanceof Funcionario)) {
            return new ArrayList<>();
        }
        return Service.instance().findReservasByFuncionario(Sesion.getUsuario().getId());
    }

    public void clear() {
        model.setSeleccionadas(new ArrayList<>());
        model.setCurrent(new Reserva());
    }

    public void reservar(String actividad, LocalDate fecha, LocalTime inicio,
                         LocalTime fin, List<CategoriaRecurso> categorias) throws Exception {

        if (!(Sesion.getUsuario() instanceof Funcionario)) {
            throw new Exception("Solo un funcionario puede reservar");
        }
        if (actividad == null || actividad.isBlank()) {
            throw new Exception("Debe indicar la actividad");
        }
        if (fecha == null) {
            throw new Exception("Debe indicar la fecha");
        }
        if (!fin.isAfter(inicio)) {
            throw new Exception("La hora de fin debe ser mayor a la hora de inicio");
        }
        if (categorias.isEmpty()) {
            throw new Exception("Debe seleccionar al menos una categoria");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new Exception("No se pueden hacer reservas en fechas pasadas");
        }

        Funcionario funcionario = (Funcionario) Sesion.getUsuario();
        Service.instance().createReserva(actividad, funcionario, fecha, inicio, fin, categorias);

        model.setReservas(buscarReservas());
        clear();
    }

    public void cancelar(int row) throws Exception {
        if (row < 0 || row >= model.getReservas().size()) {
            throw new Exception("Debe seleccionar una reserva");
        }

        Reserva reserva = model.getReservas().get(row);

        if (reserva.getFecha().isBefore(LocalDate.now())) {
            throw new Exception("Solo se pueden cancelar reservas futuras");
        }

        Service.instance().deleteReserva(reserva.getId());

        model.setReservas(buscarReservas());
        clear();
    }

    public void selectRow(int row) {
        if (row >= 0 && row < model.getReservas().size()) {
            model.setCurrent(model.getReservas().get(row));
        }
    }

    public void extraerConIA(String frase) throws Exception {
        ReservaExtraccion datos = Service.instance().extraerReserva(frase);

        if (datos == null) {
            throw new Exception("No se pudo interpretar la frase");
        }

        Reserva reserva = new Reserva();
        reserva.setActividad(datos.getActividad());

        if (datos.getFecha() != null) {
            reserva.setFecha(LocalDate.parse(datos.getFecha()));
        }
        if (datos.getHoraInicio() != null) {
            reserva.setHoraInicia(LocalTime.parse(datos.getHoraInicio()));
        }
        if (datos.getHoraFinal() != null) {
            reserva.setHoraTermina(LocalTime.parse(datos.getHoraFinal()));
        }

        List<CategoriaRecurso> encontradas = new ArrayList<>();

        if (datos.getCategoriasRecurso() != null) {
            for (String nombre : datos.getCategoriasRecurso()) {
                for (CategoriaRecurso categoria : model.getCategorias()) {
                    if (categoria.getDescripcion().equalsIgnoreCase(nombre.trim())) {
                        encontradas.add(categoria);
                        break;
                    }
                }
            }
        }

        model.setSeleccionadas(encontradas);
        model.setCurrent(reserva);
    }

    public void imprimir(String ruta) throws Exception {
        if (model.getReservas().isEmpty()) {
            throw new Exception("No hay reservas para imprimir");
        }
        PDFReportGenerator.generarReporteReservas(model.getReservas(), ruta);
    }
}