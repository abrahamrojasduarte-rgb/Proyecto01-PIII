package Sistema.presentation.calendarizacion;

import com.github.lgooddatepicker.components.DatePicker;
import Sistema.logic.Recurso;
import Sistema.logic.CategoriaRecurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class viewCalendarizacion implements PropertyChangeListener {
    private JPanel panelCalendarizacion;
    private JPanel filtrosPanel;
    private DatePicker datePicker;
    private JComboBox<CategoriaRecurso> categoriaCombo;
    private JButton cargarButton;
    private JButton imprimirButton;
    private JPanel calendarizacionPanel;
    private JScrollPane matrizScroll;
    private JTable matrizTable;

    private Model model;
    private Controller controller;

    public viewCalendarizacion(){
        cargarButton.addActionListener(e-> {
            try{
                controller.cargar(datePicker.getDate(),(CategoriaRecurso) categoriaCombo.getSelectedItem());
        } catch (Exception ex){
                JOptionPane.showMessageDialog(panelCalendarizacion, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        imprimirButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("calendarizacion.pdf"));
            if(chooser.showSaveDialog(panelCalendarizacion) != JFileChooser.APPROVE_OPTION){
                return;
            }
            try{
                controller.imprimir(chooser.getSelectedFile().getAbsolutePath(), armarColumnas(), armarFilas());
                JOptionPane.showMessageDialog(panelCalendarizacion,"Reporte generado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelCalendarizacion, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        matrizTable.setDefaultEditor(Object.class, null);
    }

    public void setModel(Model model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public JPanel getPanelCalendarizacion(){
        return panelCalendarizacion;
    }

    private String[] armarColumnas(){
        List<Recurso> recursos = model.getRecursos();

        String[] columnas = new String[recursos.size() + 1];
        columnas[0] = "Hora";

        for (int i = 0; i < recursos.size(); i++){
            columnas[i + 1] = recursos.get(i).getDescripcion();
        }
        return columnas;
    }

    private List<String[]> armarFilas(){
        List<Recurso> recursos = model.getRecursos();
        List<String[]> filas = new ArrayList<>();

        for(int hora = Model.HORA_INICIO; hora <= Model.HORA_FIN; hora++){
            String[] fila = new String[recursos.size() + 1];
            fila[0] = String.format("%02d:00", hora);

            for (int i = 0; i < recursos.size(); i++){
                fila[i+1] = model.buscarTexto(recursos.get(i), hora);
            }
            filas.add(fila);
        }
        return filas;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch (evt.getPropertyName()){
            case Model.CATEGORIAS:
                DefaultComboBoxModel<CategoriaRecurso> cbModel = new DefaultComboBoxModel<>();
                for(CategoriaRecurso categoria : model.getCategorias()){
                    cbModel.addElement(categoria);
                }
                categoriaCombo.setModel(cbModel);
                break;
            case Model.MATRIZ:
                DefaultTableModel tabla = new DefaultTableModel(armarColumnas(), 0);
                for (String[] fila : armarFilas()){
                    tabla.addRow(fila);
                }
                matrizTable.setModel(tabla);
                break;
        }
        panelCalendarizacion.revalidate();
        panelCalendarizacion.repaint();
    }
}
