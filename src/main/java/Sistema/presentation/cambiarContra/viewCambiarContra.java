package Sistema.presentation.cambiarContra;

import Sistema.logic.Usuario;
import Sistema.presentation.Highlighter;
import Sistema.presentation.util.Iconos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewCambiarContra {
    private JTextField actualtxt;
    private JTextField nueva1txt;
    private JTextField nueva2txt;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel panelContra;

    private static Usuario usuario;
    Sistema.presentation.cambiarContra.Model model;
    Sistema.presentation.cambiarContra.Controller controller;

    public viewCambiarContra() {

        confirmarButton.setIcon(Iconos.cargarIcono("ok.png",24,24));
        cancelarButton.setIcon(Iconos.cargarIcono("cancel.png",24,24));

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cambiarClave();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cancelar();
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        actualtxt.addMouseListener(highlighter);
        nueva1txt.addMouseListener(highlighter);
        nueva2txt.addMouseListener(highlighter);
    }

    public JPanel getPanelContra() {
        return panelContra;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public JPanel getPanel() {
        return panelContra;
    }

    public String getClaveActual() {
        return actualtxt.getText();
    }

    public String getClaveNueva() {
        return nueva1txt.getText();
    }

    public String getClaveNuevaConfirm() {
        return nueva2txt.getText();
    }
}
