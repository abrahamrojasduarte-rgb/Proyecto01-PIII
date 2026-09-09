package Sistema.presentation.categorias;

import Sistema.logic.CategoriaRecurso;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class viewCategorias implements PropertyChangeListener {
    private Sistema.presentation.categorias.Model model;
    private Sistema.presentation.categorias.Controller controller;

    private JPanel panelCategorias;
    private JTextField Descripciontxt;
    private JButton buscarButton;
    private JButton imprimirButton;
    private JPanel busquedaPanel;
    private JPanel categoriaPanel;
    private JTextField idCategoria;
    private JTextField descripcion;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTable table1;

    public viewCategorias() {
        if (idCategoria != null) {
            idCategoria.setEditable(false);
        }

        buscarButton.addActionListener(e -> {
            if (controller != null) {
                controller.search(Descripciontxt.getText());
            }
        });

        guardarButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    CategoriaRecurso cat = model.getCurrent();
                    cat.setDescripcion(descripcion.getText());
                    controller.save(cat);
                    JOptionPane.showMessageDialog(panelCategorias, "Categoría guardada con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelCategorias, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        borrarButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    controller.delete(model.getCurrent());
                    JOptionPane.showMessageDialog(panelCategorias, "Categoría eliminada", "Información", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelCategorias, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(e -> {
            if (controller != null) {
                controller.clear();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && controller != null) {
                    int row = table1.getSelectedRow();
                    controller.selectRow(row);
                }
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanelCategorias() {
        return panelCategorias;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.DESCRIPCION};
                table1.setModel(new TableModel(cols, model.getCategorias()));
                break;

            case Model.CURRENT:
                CategoriaRecurso current = model.getCurrent();
                idCategoria.setText(current.getID() == null ? "" : String.valueOf(current.getID()));
                descripcion.setText(current.getDescripcion() == null ? "" : current.getDescripcion());
                break;
        }
        panelCategorias.revalidate();
        panelCategorias.repaint();
    }
}

