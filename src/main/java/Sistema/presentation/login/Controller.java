package Sistema.presentation.login;

import Sistema.Sesion;
import Sistema.logic.Funcionario;
import Sistema.logic.Rol;
import Sistema.logic.Service;
import Sistema.logic.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Sistema.presentation.login.Model model;
    private Sistema.presentation.login.viewLogin view;
    private Service instance = Service.instance();
    private JFrame loginWindow;
    private Runnable onLoginSuccess;

    public Controller(Sistema.presentation.login.Model model, Sistema.presentation.login.viewLogin view,
                      JFrame loginWindow, Runnable onLoginSuccess) {
        this.model = model;
        this.view = view;
        this.loginWindow = loginWindow;
        this.onLoginSuccess = onLoginSuccess;

        this.view.setController(this);
        this.view.setModel(model);

        this.view.getIniciarSesionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarLogin();
            }
        });

        this.view.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    public void clear() {
        model.setCurrent(new Funcionario());
    }

    private void validarLogin() {
        String id = view.getID();
        String password = view.getTxtpass();

        if (id == null || id.isEmpty() || password == null || password.isEmpty() || !existe(id)) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "ID o contraseña incorrectos", "Error de acceso", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = instance.encontrarUsuario(id);

        if (usuario.getClave().equals(password)) {
            Sesion.setUsuario(usuario);

            String bienvenida = usuario.getRol() == Rol.ADMINISTRADOR ? "Administrador" : "Empleado";
            JOptionPane.showMessageDialog(view.getPanelLog(), "¡Bienvenido, " + bienvenida + "!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);

            loginWindow.dispose();
            onLoginSuccess.run();
        } else {
            JOptionPane.showMessageDialog(view.getPanelLog(), "ID o contraseña incorrectos", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean existe(String id) {
        return instance.encontrarUsuario(id) != null;
    }

    // Este es el método que ABRE el diálogo de cambio de clave
    public void cambiarClave() {
        String id = view.getID();

        if (id == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "Ingrese su ID primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!existe(id)) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(loginWindow, "Cambiar Clave", true);

        Sistema.presentation.cambiarContra.Model modelCC = new Sistema.presentation.cambiarContra.Model();
        Sistema.presentation.cambiarContra.viewCambiarContra viewCC = new Sistema.presentation.cambiarContra.viewCambiarContra();
        new Sistema.presentation.cambiarContra.Controller(modelCC, viewCC, id, dialog);

        dialog.setContentPane(viewCC.getPanel());
        dialog.pack();
        dialog.setLocationRelativeTo(loginWindow);
        dialog.setVisible(true);
    }
}
