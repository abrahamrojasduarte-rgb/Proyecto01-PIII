package Sistema;
import Sistema.presentation.funcionarios.Controller;
import Sistema.presentation.funcionarios.Model;
import Sistema.presentation.funcionarios.viewFuncionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
//        viewLogin View = new viewLogin();
//        Model model = new Model();
//        Controller controller = new Controller(model, View); // Se instancia el controlador
//
//        JFrame loginWindow = new JFrame();
//        loginWindow.setSize(350, 220);
//        loginWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        loginWindow.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e) ;
//            }
//        });
//        loginWindow.setTitle("Login - Sistema");
//        loginWindow.setContentPane(View.getPanelLog());
//        loginWindow.setLocationRelativeTo(null);
//        loginWindow.setVisible(true);

        viewFuncionarios View = new viewFuncionarios();
        Model model = new Model();
        Controller controller = new Controller(model, View);

        JFrame loginWindow = new JFrame();
        loginWindow.setSize(700, 300);
        loginWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        loginWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e) ;
            }
        });
        loginWindow.setTitle("Funcionario - Administrador");
        loginWindow.setContentPane(View.getFuncionarioAdmin());
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}
