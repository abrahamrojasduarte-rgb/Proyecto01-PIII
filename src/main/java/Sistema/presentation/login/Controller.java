package Sistema.presentation.login;

import Sistema.presentation.login.Model;
import Sistema.presentation.login.viewLogin;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private viewLogin view;

    public Controller(Model model, viewLogin view) {
        this.model = model;
        this.view = view;

        this.view.getIniciarSesionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarLogin();
            }
        });
    }

    private void validarLogin() {
        String puesto = view.getUser();
        String password = view.getTxtpass();

        if ("Administrador".equals(puesto) && "Admin".equals(password)) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "¡Bienvenido, Administrador!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
            abrirVentanaPrincipal("Panel de Administrador");

        } else if ("Empleado".equals(puesto) && "1234".equals(password)) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "¡Bienvenido, Empleado!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
            abrirVentanaPrincipal("Panel de Empleado");

        } else {
            JOptionPane.showMessageDialog(view.getPanelLog(), "Puesto o contraseña incorrectos", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVentanaPrincipal(String TituloVentana) {
        JFrame ventanaPrincipal = new JFrame(TituloVentana);
        ventanaPrincipal.setSize(600, 400);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);

        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(view.getPanelLog());
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
    }
}
