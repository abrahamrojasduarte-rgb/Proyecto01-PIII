package Sistema.presentation.funcionarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class viewFuncionarios {
    private JPanel FuncionarioAdmin;
    private JPanel busquedaPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton buscarButton;
    private JButton imprimirButton;
    private JPanel Funcionario;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTable funcionarioTable;

    public viewFuncionarios() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getFuncionarioAdmin() {
        return FuncionarioAdmin;
    }
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener((PropertyChangeListener) this);
    }
}
