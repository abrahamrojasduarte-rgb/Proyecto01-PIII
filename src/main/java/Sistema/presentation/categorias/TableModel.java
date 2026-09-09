package Sistema.presentation.categorias;

import Sistema.logic.CategoriaRecurso;
import Sistema.presentation.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<CategoriaRecurso> implements javax.swing.table.TableModel{
    public static final int ID = 0;
    public static final int DESCRIPCION = 1;

    public TableModel(int[] cols, List<CategoriaRecurso> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(CategoriaRecurso cat, int columnIndex) {
        switch (cols[columnIndex]) {
            case ID: return cat.getID();
            case DESCRIPCION: return cat.getDescripcion();
            default: return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[cols.length];
        colNames[ID] = "ID";
        colNames[DESCRIPCION] = "Descripción";
    }
}
