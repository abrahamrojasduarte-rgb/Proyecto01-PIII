package Sistema.presentation.login;

import Sistema.logic.Funcionario;
import Sistema.logic.Rol;
import Sistema.logic.Service;
import Sistema.logic.Usuario;
import Sistema.presentation.login.Model;
import Sistema.presentation.login.viewLogin;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model = new Model();
    private viewLogin view = new viewLogin();
    private Service instance = Service.instance();

    public Controller(Model model, viewLogin view) {
        this.model = model;
        this.view = view;

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
        if(id == null || id.isEmpty() || password == null || password.isEmpty() || !existe(id)) {
            JOptionPane.showMessageDialog(view.getPanelLog(), "ID o contraseña incorrectos", "Error de acceso", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Usuario usuario = instance.encontrarUsuario(id);

        if(usuario.getClave().equals(password)){
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                JOptionPane.showMessageDialog(view.getPanelLog(), "¡Bienvenido, Administrador!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
                abrirVentanaPrincipal("Panel de Administrador",true);
            } else if (usuario.getRol() == Rol.FUNCIONARIO) {
                JOptionPane.showMessageDialog(view.getPanelLog(), "¡Bienvenido, Empleado!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
                abrirVentanaPrincipal("Panel de Empleado",false);
            }

        }

    }

    private void abrirVentanaPrincipal(String TituloVentana, boolean esAdmin) {
        JFrame ventanaPrincipal = new JFrame(TituloVentana);
        ventanaPrincipal.setSize(800, 600);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);

        JTabbedPane tabbedPane = new JTabbedPane();

        if (esAdmin) {
            Sistema.presentation.funcionarios.Model funcModel = new Sistema.presentation.funcionarios.Model();
            Sistema.presentation.funcionarios.viewFuncionarios funcView = new Sistema.presentation.funcionarios.viewFuncionarios();
            new Sistema.presentation.funcionarios.Controller(funcModel, funcView);

            tabbedPane.addTab("Funcionarios", funcView.getFuncionarioAdmin());
        }


        ventanaPrincipal.add(tabbedPane);
        ventanaPrincipal.setVisible(true);

        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(view.getPanelLog());
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
    }

    private boolean existe(String id){
        boolean exist = (instance.encontrarUsuario(id) != null);
        return exist;
    }

}
