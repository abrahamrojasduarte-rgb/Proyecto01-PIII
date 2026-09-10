package Sistema.presentation.login;

import Sistema.logic.Funcionario;
import Sistema.logic.Usuario;
import Sistema.presentation.Highlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class viewLogin implements PropertyChangeListener{
    private JTextField idTXT;
    private JTextField txtpass;
    private JButton iniciarSesionButton;
    private JPanel panelLog;
    private JButton cancelarButton;
    private JButton cambiarContrasenaButton;

    private static Usuario usuario;
    Model model;
    Controller controller;

    public viewLogin() {
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        cambiarContrasenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cambiarClave();
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        idTXT.addMouseListener(highlighter);
        txtpass.addMouseListener(highlighter);

    }
    private static void addMouseListener(MouseAdapter mouseAdapter) {
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JButton getIniciarSesionButton() {
        return iniciarSesionButton;
    }

    public JPanel getPanelLog() {
        return panelLog;
    }

    public String getID() {
        return idTXT.getText().trim();
    }

    public String getTxtpass() {
        return txtpass.getText();
    }

    public JButton getCambiarContrasenaButton() {
        return cambiarContrasenaButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Model.CURRENT.equals(evt.getPropertyName())) {
            Funcionario current = model.getCurrent();
            idTXT.setText(current.getNombre() != null ? current.getNombre() : "");
            txtpass.setText("");
        }
    }

}
