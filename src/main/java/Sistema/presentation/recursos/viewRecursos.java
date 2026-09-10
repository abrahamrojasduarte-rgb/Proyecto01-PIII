package Sistema.presentation.recursos;

import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Recurso;
import Sistema.presentation.Highlighter;
import Sistema.presentation.util.PDFReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class viewRecursos implements PropertyChangeListener {

    private JPanel panelRecursos;

    private JTextField idTxt;
    private JTextField descripcionTxt;
    private JComboBox<CategoriaRecurso> categoriaCmb;
    private JComboBox<CategoriaRecurso> buscarCategoriaCmb;
    private JTable recursosTbl;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JButton limpiarBtn;
    private JButton pdfBtn;
    private JButton buscarBtn;

    private Controller controller;
    private Model model;

    public viewRecursos() {
        recursosTbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = recursosTbl.getSelectedRow();
                if (row != -1 && model != null && model.getRecursos() != null) {
                    Recurso seleccionado = model.getRecursos().get(row);
                    model.setCurrent(seleccionado);
                }
            }
        });

        guardarBtn.addActionListener(e -> guardar());
        borrarBtn.addActionListener(e -> borrar());
        limpiarBtn.addActionListener(e -> {
            if (controller != null) controller.clear();
        });
        pdfBtn.addActionListener(e -> generarPDF());
        buscarBtn.addActionListener(e -> {
            if (controller != null) {
                controller.search((CategoriaRecurso) buscarCategoriaCmb.getSelectedItem(), descripcionTxt.getText());            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        idTxt.addMouseListener(highlighter);
        descripcionTxt.addMouseListener(highlighter);
    }

    public JPanel getPanelRecursos() {
        return panelRecursos;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    private void guardar() {
        try {
            Recurso r = new Recurso();
            r.setId(idTxt.getText().trim());
            r.setDescripcion(descripcionTxt.getText().trim());
            r.setCategoria((CategoriaRecurso) categoriaCmb.getSelectedItem());

            controller.save(r);
            JOptionPane.showMessageDialog(panelRecursos, "Recurso guardado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelRecursos, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrar() {
        try {
            controller.delete(idTxt.getText().trim());
            JOptionPane.showMessageDialog(panelRecursos, "Recurso eliminado", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelRecursos, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte PDF");
        if (fileChooser.showSaveDialog(panelRecursos) == JFileChooser.APPROVE_OPTION) {
            try {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.toLowerCase().endsWith(".pdf")) {
                    path += ".pdf";
                }
                PDFReportGenerator.generarReporteRecursos(model.getRecursos(), path);
                JOptionPane.showMessageDialog(panelRecursos, "PDF generado con éxito en:\n" + path, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelRecursos, "Error al generar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                Recurso c = model.getCurrent();
                if (c != null) {
                    idTxt.setText(c.getId() != null ? c.getId() : "");
                    descripcionTxt.setText(c.getDescripcion() != null ? c.getDescripcion() : "");
                    idTxt.setEnabled(c.getId() == null || c.getId().isEmpty());
                    if (c.getCategoria() != null) {
                        categoriaCmb.setSelectedItem(c.getCategoria());
                    }
                }
                break;

            case Model.LIST:
                recursosTbl.setModel(new TableModel(new int[]{TableModel.ID, TableModel.CATEGORIA, TableModel.DESCRIPCION}, model.getRecursos()));
                break;

            case Model.CATEGORIAS:
                DefaultComboBoxModel<CategoriaRecurso> cbModel = new DefaultComboBoxModel<>();
                DefaultComboBoxModel<CategoriaRecurso> filterModel = new DefaultComboBoxModel<>();

                CategoriaRecurso todas = new CategoriaRecurso(null, "--- Todas ---");
                filterModel.addElement(todas);

                if (model.getCategorias() != null) {
                    for (CategoriaRecurso cat : model.getCategorias()) {
                        cbModel.addElement(cat);
                        filterModel.addElement(cat);
                    }
                }

                categoriaCmb.setModel(cbModel);
                buscarCategoriaCmb.setModel(filterModel);
                break;
        }
    }
}
