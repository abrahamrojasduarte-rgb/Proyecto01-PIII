package Sistema.presentation.actividades;

import Sistema.presentation.AbstractModel;
import Sistema.logic.Reserva;

import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Model extends AbstractModel {
    public static final String MATRIZ = "matriz";

    public static final int HORA_INICIO = 6;
    public static final int HORA_FIN = 21;
    public static final int DIAS = 7;

    private LocalDate lunes;
    private List<Reserva> reservas;

    public Model(){
        lunes = LocalDate.now().with(DayOfWeek.MONDAY);
        reservas = new ArrayList<>();
    }

    public LocalDate getLunes(){
        return lunes;
    }

    public void setLunes(LocalDate lunes){
        this.lunes = lunes;
    }

    public LocalDate getDia(int indice){
        return lunes.plusDays(indice);
    }

    public List<Reserva> getReservas(){
        return reservas;
    }

    public void setReservas(List<Reserva> reservas){
        this.reservas = reservas;
        firePropertyChange(MATRIZ);
    }

    public String buscarTexto(LocalDate dia, int hora){
        String texto = "";

        for(Reserva reserva : reservas){
            if (!reserva.getFecha().equals(dia)){
                continue;
            }
            if (hora >= reserva.getHoraInicia().getHour() && hora < reserva.getHoraTermina().getHour()){
                if(!texto.isEmpty()){
                    texto = texto + " | ";
                }
                texto = texto + reserva.getActividad() + " (" + reserva.getFuncionario().getNombre() + ")";
            }
        }
        return texto;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        super.addPropertyChangeListener(listener);
        firePropertyChange(MATRIZ);
    }


}
