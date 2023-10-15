package controladores.inicio;

import java.awt.event.*;

import javax.swing.*;

import vistas.inicio.*;
import nanoGym.*;
import usuario.*;

import mainGUI.*;

public class ControladorInicioSesion implements ActionListener {
    private VistaInicioSesion vista;
    private NanoGym modelo;
    private Usuario u;
    private String nick;
    private String pw;
    private MainGUI ventana;

    public ControladorInicioSesion(VistaInicioSesion vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // validar valores en la vista
        if (event.getSource().equals(this.vista.getBotonInicioSesion())) {
            nick = vista.getUsuario().getText();
            pw = new String(vista.getContrasena().getPassword());
            if (nick.equals("Introduzca su usuario") || pw.equals("Introduzca su contraseña")) {
                JOptionPane.showMessageDialog(vista, "Debe introducir un nombre y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(pw.length() < 5) {
                JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 5 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (event.getSource().equals(this.vista.getBotonRegistrarse())) {
            //Cambio modelo para registro
            vista.limpiar();
            ventana.mostrarRegistro();
            return;
        }
        // modificar modelo
        try{
            u = modelo.logIn(nick, pw);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            vista.getUsuario().setText("Introduzca su usuario");
            vista.getContrasena().setText("Introduzca su contraseña");
            vista.getContrasena().setEchoChar((char)0);
            return;
        }

        if (u.esCliente()) {
            //Cambio a vista principal de cliente
            vista.limpiar();
            ventana.mostrarMenuCliente();
        } else if (u.esMonitor()) {
            //Cambio a vista principal de monitor
            vista.limpiar();
            ventana.mostrarMenuMonitor();
        } else {
            //Cambio a vista principal de administrador
            vista.limpiar();
            ventana.mostrarMenuAdministrador();
        }
    }

}
