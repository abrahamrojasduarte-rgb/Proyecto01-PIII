package Sistema.presentation.reserva;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Reserva;
import Sistema.presentation.Highlighter;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class viewReserva implements PropertyChangeListener {

    private Model model;
    private Controller controller;

    private JPanel panelReserva;
    private JPanel nuevaReservaPanel;
    private JPanel categoriasPanel;
    private JPanel botonesPanel;
    private JPanel misReservasPanel;
    private JScrollPane fraseScroll;
    private JScrollPane categoriasScroll;
    private JScrollPane reservasScroll;

    private JTextArea fraseTxt;
    private JButton extraerButton;
    private JTextField actividadTxt;
    private DatePicker datePicker;
    private JComboBox<String> horaInicioCombo;
    private JComboBox<String> horaFinCombo;
    private JList<CategoriaRecurso> categoriasList;
    private JButton reservarButton;
    private JButton cancelarButton;
    private JButton limpiarButton;
    private JButton imprimirButton;
    private JTable reservasTable;

    public viewReserva() {

        extraerButton.addActionListener(e -> {
            try {
                controller.extraerConIA(fraseTxt.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelReserva, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        reservarButton.addActionListener(e -> {
            try {
                controller.reservar(
                        actividadTxt.getText(),
                        datePicker.getDate(),
                        LocalTime.parse((String) horaInicioCombo.getSelectedItem()),
                        LocalTime.parse((String) horaFinCombo.getSelectedItem()),
                        new ArrayList<>(categoriasList.getSelectedValuesList()));
                JOptionPane.showMessageDialog(panelReserva, "Reserva realizada correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelReserva, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> {
            try {
                controller.cancelar(reservasTable.getSelectedRow());
                JOptionPane.showMessageDialog(panelReserva, "Reserva cancelada", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelReserva, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        limpiarButton.addActionListener(e -> {
            fraseTxt.setText("");
            controller.clear();
        });

        imprimirButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("reservas.pdf"));

            if (chooser.showSaveDialog(panelReserva) != JFileChooser.APPROVE_OPTION) return;

            try {
                controller.imprimir(chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(panelReserva, "Reporte generado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelReserva, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        reservasTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    controller.selectRow(reservasTable.getSelectedRow());
                }
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        actividadTxt.addMouseListener(highlighter);
        fraseTxt.addMouseListener(highlighter);
    }

    public void setModel(Model model) {
        this.model = model;
        cargarHoras();
        model.addPropertyChangeListener(this);
    }

    private void cargarHoras() {
        DefaultComboBoxModel<String> inicio = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> fin = new DefaultComboBoxModel<>();

        for (int h = 6; h <= 22; h++) {
            String hora = String.format("%02d:00", h);
            inicio.addElement(hora);
            fin.addElement(hora);
        }

        horaInicioCombo.setModel(inicio);
        horaFinCombo.setModel(fin);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanelReserva() {
        return panelReserva;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {

            case Model.CATEGORIAS:
                DefaultListModel<CategoriaRecurso> lista = new DefaultListModel<>();
                for (CategoriaRecurso categoria : model.getCategorias()) {
                    lista.addElement(categoria);
                }
                categoriasList.setModel(lista);
                break;

            case Model.CURRENT:
                Reserva current = model.getCurrent();

                actividadTxt.setText(current.getActividad() == null ? "" : current.getActividad());

                if (current.getFecha() != null) datePicker.setDate(current.getFecha());
                else datePicker.setDate(java.time.LocalDate.now());

                if (current.getHoraInicia() != null) horaInicioCombo.setSelectedItem(current.getHoraInicia().toString());
                else horaInicioCombo.setSelectedItem("08:00");

                if (current.getHoraTermina() != null) horaFinCombo.setSelectedItem(current.getHoraTermina().toString());
                else horaFinCombo.setSelectedItem("09:00");

                marcarCategorias();
                break;

            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.ACTIVIDAD, TableModel.FECHA,
                        TableModel.HORARIO, TableModel.RECURSOS, TableModel.ESTADO};
                reservasTable.setModel(new TableModel(cols, model.getReservas()));
                break;
        }
        panelReserva.revalidate();
        panelReserva.repaint();
    }

    private void marcarCategorias() {
        categoriasList.clearSelection();

        List<CategoriaRecurso> seleccionadas = model.getSeleccionadas();
        if (seleccionadas.isEmpty()) return;

        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < categoriasList.getModel().getSize(); i++) {
            CategoriaRecurso categoria = categoriasList.getModel().getElementAt(i);
            for (CategoriaRecurso seleccionada : seleccionadas) {
                if (seleccionada.getID().equals(categoria.getID())) {
                    indices.add(i);
                    break;
                }
            }
        }

        int[] arreglo = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            arreglo[i] = indices.get(i);
        }
        categoriasList.setSelectedIndices(arreglo);
    }
}