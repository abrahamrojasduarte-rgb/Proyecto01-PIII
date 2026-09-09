package Sistema;
import Sistema.presentation.funcionarios.Controller;
import Sistema.presentation.funcionarios.Model;
import Sistema.presentation.funcionarios.viewFuncionarios;
import Sistema.presentation.login.viewLogin;
import Sistema.presentation.recursos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        doLogin();
    }

    private static void doLogin() {
        Sistema.presentation.login.viewLogin view = new Sistema.presentation.login.viewLogin();
        Sistema.presentation.login.Model model = new Sistema.presentation.login.Model();

        JFrame loginWindow = new JFrame();
        loginWindow.setSize(350, 220);
        loginWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginWindow.setTitle("Login - Sistema");
        loginWindow.setContentPane(view.getPanelLog());
        loginWindow.setLocationRelativeTo(null);

        Sistema.presentation.login.Controller controller =
                new Sistema.presentation.login.Controller(model, view, loginWindow, Application::doRun);

        loginWindow.setVisible(true);
    }

    private static void doRun() {
        JFrame window = new JFrame("Sistema");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.setTitle("Sistema - " + Sesion.getUsuario().getId() + " (" + Sesion.getUsuario().getRol() + ")");

        switch (Sesion.getUsuario().getRol()) {
            case ADMINISTRADOR:
                Sistema.presentation.funcionarios.Model funcModel = new Sistema.presentation.funcionarios.Model();
                Sistema.presentation.funcionarios.viewFuncionarios funcView = new Sistema.presentation.funcionarios.viewFuncionarios();
                new Sistema.presentation.funcionarios.Controller(funcModel, funcView);
                tabbedPane.addTab("Funcionarios", funcView.getFuncionarioAdmin());

                Sistema.presentation.categorias.Model catModel = new Sistema.presentation.categorias.Model();
                Sistema.presentation.categorias.viewCategorias catView = new Sistema.presentation.categorias.viewCategorias();
                new Sistema.presentation.categorias.Controller(catModel, catView);
                tabbedPane.addTab("Categorias", catView.getPanelCategorias());

                Sistema.presentation.recursos.Model recModel = new Sistema.presentation.recursos.Model();
                Sistema.presentation.recursos.viewRecursos recView = new Sistema.presentation.recursos.viewRecursos();
                Sistema.presentation.recursos.Controller recController = new Sistema.presentation.recursos.Controller(recModel, recView);
                tabbedPane.addTab("Recursos", recView.getPanelRecursos());

                tabbedPane.addChangeListener(e -> {
                    int selectedIndex = tabbedPane.getSelectedIndex();
                    if (selectedIndex != -1 && "Recursos".equals(tabbedPane.getTitleAt(selectedIndex))) {
                        recController.refresh();
                    }
                });
                break;

            case FUNCIONARIO:
                // TODO: tabs específicos del empleado
                break;
        }

        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);

}
