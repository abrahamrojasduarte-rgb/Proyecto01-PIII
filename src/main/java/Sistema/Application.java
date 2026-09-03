package Sistema;
import Sistema.presentation.login.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        viewLogin View = new viewLogin();
        Model model = new Model();
        Controller controller = new Controller(model, View); // Se instancia el controlador

        JFrame loginWindow = new JFrame();
        loginWindow.setSize(350, 220);
        loginWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        loginWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e) ;
            }
        });
        loginWindow.setTitle("Login - Sistema");
        loginWindow.setContentPane(View.getPanelLog());
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}
