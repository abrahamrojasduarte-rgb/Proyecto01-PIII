package Sistema.presentation.cambiarContra;

import Sistema.logic.Service;
import Sistema.logic.Usuario;
import Sistema.presentation.funcionarios.Model;
import Sistema.presentation.funcionarios.viewFuncionarios;

import javax.swing.*;

public class Controller {
    private Sistema.presentation.cambiarContra.Model model;
    private Sistema.presentation.cambiarContra.viewCambiarContra view;
    private String idUsuario;
    private JDialog dialog;

    public Controller(Sistema.presentation.cambiarContra.Model model, Sistema.presentation.cambiarContra.viewCambiarContra view ){
        this.model = model;
        this.view = view;
    }

    public void cambiarClave() {
        String claveActual = view.getClaveActual();
        String claveNueva = view.getClaveNueva();
        String claveNuevaConfirm = view.getClaveNuevaConfirm();

        if (claveActual == null || claveActual.isEmpty()
                || claveNueva == null || claveNueva.isEmpty()
                || claveNuevaConfirm == null || claveNuevaConfirm.isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = Service.instance().encontrarUsuario(idUsuario);
        if (usuario == null) {
            JOptionPane.showMessageDialog(view.getPanel(), "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.getClave().equals(claveActual)) {
            JOptionPane.showMessageDialog(view.getPanel(), "Clave actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!claveNueva.equals(claveNuevaConfirm)) {
            JOptionPane.showMessageDialog(view.getPanel(), "Las claves nuevas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Service.instance().actualizarClave(idUsuario, claveNueva);
            JOptionPane.showMessageDialog(view.getPanel(), "Clave actualizada correctamente", "", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelar() {
        dialog.dispose();
    }

}
