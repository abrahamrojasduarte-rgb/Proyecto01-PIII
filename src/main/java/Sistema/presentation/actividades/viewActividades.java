package Sistema.presentation.actividades;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class viewActividades implements PropertyChangeListener {
    private JPanel panelActividades;
    private JPanel semanaPanel;
    private JButton cargarButton;
    private JButton imprimirButton;
    private DatePicker datePicker;
    private JScrollPane matrizScroll;
    private JTable matrizTable;

    private Model model;
    private Controller controller;
    private final DateTimeFormatter formatoColumna = DateTimeFormatter.ofPattern("EEE dd/MM");

    public viewActividades(){
        cargarButton.addActionListener(e ->{
            try{
                controller.cargar(datePicker.getDate());
            } catch(Exception ex){
                JOptionPane.showMessageDialog(panelActividades, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        imprimirButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("actividades.pdf"));

            if(chooser.showSaveDialog(panelActividades) != JFileChooser.APPROVE_OPTION){
                return;
            }
            try {
                controller.imprimir(chooser.getSelectedFile().getAbsolutePath(), armarColumnas(), armarFilas());
                JOptionPane.showMessageDialog(panelActividades, "Reporte generado", "Informacion", JOptionPane.INFORMATION_MESSAGE) ;
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelActividades, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    public JPanel getPanelActividades(){
        return panelActividades;
    }

    private String[] armarColumnas(){
        String[] columnas = new String[Model.DIAS + 1];
        columnas[0] = "Hora";

        for(int i = 0; i < Model.DIAS; i++){
            columnas[i + 1] = model.getDia(i).format(formatoColumna);
        }
        return columnas;
    }

    private List<String[]> armarFilas(){
        List<String[]> filas = new ArrayList<>();

        for(int hora = Model.HORA_INICIO; hora <= Model.HORA_FIN; hora++){
            String[] fila = new String[Model.DIAS + 1];
            fila[0] = String.format("%02d:00", hora);

            for(int i = 0; i < Model.DIAS; i++){
                LocalDate dia = model.getDia(i);
                fila[i+1] = model.buscarTexto(dia,hora);
            }
            filas.add(fila);
        }
        return filas;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if (evt.getPropertyName().equals(Model.MATRIZ)){
            DefaultTableModel tabla = new DefaultTableModel(armarColumnas(),0);
            for (String [] fila : armarFilas()){
                tabla.addRow(fila);
            }
            matrizTable.setModel(tabla);
        }
        panelActividades.revalidate();
        panelActividades.repaint();
    }
}
