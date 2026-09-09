package Sistema.presentation.reserva;

import Sistema.logic.Recurso;
import Sistema.logic.Reserva;
import Sistema.presentation.AbstractTableModel;

import java.time.LocalDate;
import java.util.List;

public class TableModel extends AbstractTableModel<Reserva> {

    public static final int ID = 0;
    public static final int ACTIVIDAD = 1;
    public static final int FECHA = 2;
    public static final int HORARIO = 3;
    public static final int RECURSOS = 4;
    public static final int ESTADO = 5;

    public TableModel(int[] cols, List<Reserva> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Reserva reserva, int columnIndex) {
        switch (cols[columnIndex]) {

            case ID:
                return String.format("RES-%06d", reserva.getId());

            case ACTIVIDAD:
                return reserva.getActividad();

            case FECHA:
                return reserva.getFecha().toString();

            case HORARIO:
                return reserva.getHoraInicia() + " - " + reserva.getHoraTermina();

            case RECURSOS:
                String recursos = "";
                for (Recurso recurso : reserva.getRecursos()) {
                    if (!recursos.isEmpty()) recursos += ", ";
                    recursos += recurso.getId();
                }
                return recursos;

            case ESTADO:
                if (reserva.getFecha().isBefore(LocalDate.now())) return "FINALIZADA";
                return "ACTIVA";

            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[6];
        colNames[ID] = "Id";
        colNames[ACTIVIDAD] = "Actividad";
        colNames[FECHA] = "Fecha";
        colNames[HORARIO] = "Horario";
        colNames[RECURSOS] = "Recursos";
        colNames[ESTADO] = "Estado";
    }
}