package Sistema.presentation.login;

import Sistema.presentation.Highlighter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class viewLogin implements PropertyChangeListener{
    private JTextField userTXT;
    private JTextField txtpass;
    private JButton iniciarSesionButton;
    private JPanel panelLog;
    private JButton cancelarButton;
    private JButton cambiarContrasenaButton;

    Model model;
    public JPanel getPanelLog(){
        return panelLog;
    }
    public String getUser() {
        return userTXT.getText().trim();
    }

    public String getTxtpass(){
        return txtpass.getText();
    }

    Highlighter highlighter = new Highlighter(Color.green);

    public JButton getIniciarSesionButton() {
        return iniciarSesionButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
//     userTXT.addMouseListener(Highlighter);
}
