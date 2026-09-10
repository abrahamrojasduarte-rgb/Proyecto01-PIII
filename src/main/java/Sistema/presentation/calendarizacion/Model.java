package Sistema.presentation.calendarizacion;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Recurso;
import Sistema.logic.Reserva;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    public static final String CATEGORIAS = "categorias";
    public static final String MATRIZ = "matriz";
    public static final int HORA_INICIO = 6;
    public static final int HORA_FIN = 21;

    private LocalDate fecha;
    private List<CategoriaRecurso> categorias;
    private List<Recurso> recursos;
    private List<Reserva> reservas;

    public Model(){
        fecha = LocalDate.now();
        categorias = new ArrayList<>();
        recursos = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public LocalDate getFecha(){
        return fecha;
    }

    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public List<CategoriaRecurso> getCategorias(){
        return categorias;
    }

    public void setCategorias(List<CategoriaRecurso> categorias){
        this.categorias = categorias;
        firePropertyChange(CATEGORIAS);
    }

    public List<Recurso> getRecursos(){
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos){
        this.recursos = recursos;
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    public void setReservas(List<Reserva> reservas){
        this.reservas = reservas;
        firePropertyChange(MATRIZ);
    }

    public String buscarTexto(Recurso recurso, int hora){
        for(Reserva reserva : reservas){
            boolean usaRecurso = false;

            for(Recurso usado : reserva.getRecursos()){
                if(usado.getId().equals(recurso.getId())){
                    usaRecurso = true;
                    break;
                }
            }
            if (!usaRecurso) {
                continue;
            }
            if(hora >= reserva.getHoraInicia().getHour() && hora < reserva.getHoraTermina().getHour()){
                return reserva.getActividad() + " - " + reserva.getFuncionario().getNombre();
            }
        }
        return "";
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        super.addPropertyChangeListener(listener);
        firePropertyChange(CATEGORIAS);
        firePropertyChange(MATRIZ);
    }

}
