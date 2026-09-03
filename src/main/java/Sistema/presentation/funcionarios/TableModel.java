package Sistema.presentation.funcionarios;

import Sistema.logic.Funcionario;
import Sistema.presentation.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Funcionario> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Funcionario> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int NUMTELEFONO = 2;
    public static final int ROL = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[NUMTELEFONO] = "Numero de Telefono";
        colNames[ROL] = "Estado";
    }

    @Override
    protected Object getPropetyAt(Funcionario f, int col){
        switch (cols[col]) {
            case ID:
                return f.getId();
            case NOMBRE:
                return f.getNombre();
            case NUMTELEFONO:
                return f.getTelefono();
            case ROL:
                return f.getRol();
            default:
                return "";
        }
    }
}
