package Sistema.logic;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int id;
    private String actividad;
    private Funcionario funcionario;
    private List<Recurso> recursos;
    private LocalDate fecha;
    private LocalTime horaInicia;
    private LocalTime horaTermina;

    public Reserva(){
        recursos = new ArrayList<>();
    }

    public Reserva(int id, String actividad, Funcionario funcionario, LocalDate fecha, LocalTime horaInicia, LocalTime horaTermina){
        this.id = id;
        this.actividad = actividad;
        this.funcionario = funcionario;
        this.recursos = new ArrayList<>();
        this.fecha = fecha;
        this.horaInicia = horaInicia;
        this.horaTermina = horaTermina;
    }

    public int getId(){
        return this.id;
    }

    public String getActividad(){
        return this.actividad;
    }

    public Funcionario getFuncionario(){
        return this.funcionario;
    }

    public List<Recurso> getRecursos(){
        return this.recursos;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }

    public LocalTime getHoraInicia(){
        return this.horaInicia;
    }

    public LocalTime getHoraTermina(){
        return this.horaTermina;
    }

    public void setActividad(String actividad){
        this.actividad = actividad;
    }

    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }

    public void setRecursos(List<Recurso> recursos){
        this.recursos = recursos;
    }

    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public void setHoraInicia(LocalTime horaInicia){
        this.horaInicia = horaInicia;
    }

    public void setHoraTermina(LocalTime horaTermina){
        this.horaTermina = horaTermina;
    }

}
