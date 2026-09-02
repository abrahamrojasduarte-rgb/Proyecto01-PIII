package Sistema.logic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    private String id;
    private String actividad;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Empleado funcionario;
    private EstadoReserva estado;
    private List<DetalleReserva> detalles = new ArrayList<>();

    public Reserva(String id, String actividad, LocalDate fecha, LocalTime horaInicio,
                   LocalTime horaFin, Empleado funcionario) {
        this.id = id;
        this.actividad = actividad;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.funcionario = funcionario;
        this.estado = EstadoReserva.ACTIVA;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Empleado getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Empleado funcionario) {
        this.funcionario = funcionario;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public List<DetalleReserva> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleReserva detalle) {
        detalles.add(detalle);
    }

    public void cancelar() {
        this.estado = EstadoReserva.CANCELADA;
    }

    @Override
    public String toString() {
        return "Reserva{id='" + id + "', actividad='" + actividad + "', fecha=" + fecha
                + ", estado=" + estado + "}";
    }
}
