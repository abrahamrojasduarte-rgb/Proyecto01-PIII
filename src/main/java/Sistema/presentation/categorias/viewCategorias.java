package Sistema.presentation.categorias;

import Sistema.logic.Usuario;
import Sistema.presentation.funcionarios.Controller;
import Sistema.presentation.login.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewCategorias {
    private static Usuario usuario;
    Model model;
    Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public viewCategorias() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getPanelCategorias() {
        return panelCategorias;
    }

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


}

