package Sistema.presentation.funcionarios;

import Sistema.Application;
import Sistema.logic.Funcionario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class viewFuncionarios implements PropertyChangeListener{
    private JPanel FuncionarioAdmin;
    private JPanel busquedaPanel;
    private JTextField idBuscar;
    private JTextField nombreBuscar;
    private JButton buscarButton;
    private JButton imprimirButton;
    private JPanel Funcionario;
    private JTextField IDregistro;
    private JTextField nombreRegistro;
    private JTextField telefonoRegistro;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTable funcionarioTable;

    Controller controller;
    Model model;

    public viewFuncionarios() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Validate()) {
                    Funcionario n = take();
                    try {
                        controller.create(n);
                        JOptionPane.showMessageDialog(FuncionarioAdmin, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(FuncionarioAdmin, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (IDregistro.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(FuncionarioAdmin, "Seleccione o ingrese un ID para borrar", "", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    controller.delete(IDregistro.getText());
                    JOptionPane.showMessageDialog(FuncionarioAdmin, "REGISTRO ELIMINADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FuncionarioAdmin, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.search(idBuscar.getText(), nombreBuscar.getText());
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FuncionarioAdmin, "Funcionalidad de impresión pendiente", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
    }

    public JPanel getFuncionarioAdmin() {
        return FuncionarioAdmin;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Model.CURRENT.equals(evt.getPropertyName())) {
            Funcionario current = model.getCurrent();
            IDregistro.setText(current.getId() == null ? "" : current.getId());
            nombreRegistro.setText(current.getNombre() == null ? "" : current.getNombre());
            telefonoRegistro.setText(current.getTelefono() == null ? "" : current.getTelefono());
        } else if (Model.LIST.equals(evt.getPropertyName())) {
            int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.NUMTELEFONO, TableModel.ROL};
            funcionarioTable.setModel(new TableModel(cols, model.getFuncionarios()));
        }
    }

    public JTextField getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(JTextField nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

    public JTextField getIDregistro() {
        return IDregistro;
    }

    public void setIDregistro(JTextField IDregistro) {
        this.IDregistro = IDregistro;
    }

    public JTextField getTelefonoRegistro() {
        return telefonoRegistro;
    }

    public void setTelefonoRegistro(JTextField telefonoRegistro) {
        this.telefonoRegistro = telefonoRegistro;
    }

    public JTextField getIdBuscar() {
        return idBuscar;
    }

    public void setIdBuscar(JTextField idBuscar) {
        this.idBuscar = idBuscar;
    }

    public JTextField getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(JTextField nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public void setLimpiarButton(JButton limpiarButton) {
        this.limpiarButton = limpiarButton;
    }

    private boolean Validate() {
        boolean valid = true;
        if (IDregistro.getText().isEmpty()) {
            valid = false;
            IDregistro.setBackground(Application.BACKGROUND_ERROR);
            IDregistro.setToolTipText("id requerido");
        } else {
            IDregistro.setBackground(null);
            IDregistro.setToolTipText(null);
        }

        if (nombreRegistro.getText().isEmpty()) {
            valid = false;
            nombreRegistro.setBackground(Application.BACKGROUND_ERROR);
            nombreRegistro.setToolTipText("Nombre requerido");
        } else {
            nombreRegistro.setBackground(null);
            nombreRegistro.setToolTipText(null);
        }

        if (telefonoRegistro.getText().isEmpty()) {
            valid = false;
            telefonoRegistro.setBackground(Application.BACKGROUND_ERROR);
            telefonoRegistro.setToolTipText("Numero requerido");
        } else {
            telefonoRegistro.setBackground(null);
            telefonoRegistro.setToolTipText(null);
        }
        return valid;
    }

    public Funcionario take() {
        Funcionario e = new Funcionario();
        e.setID(IDregistro.getText());
        e.setNombre(nombreRegistro.getText());
        e.setTelefono(telefonoRegistro.getText());
        return e;
    }
}
