package controladores.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.VistaPerfil;

public abstract class ControladorPerfil implements ActionListener {
    protected NanoGym modelo;
    protected VistaPerfil vista;
    protected MainGUI ventana;

    public ControladorPerfil(VistaPerfil vista, NanoGym modelo, MainGUI ventana) {
        this.modelo = modelo;
        this.vista = vista;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.vista.getBotonCerrarSesion())) {
            modelo.logOut();
            modelo.generateBackUp();
            ventana.mostrarInicioSesion();
        }
    }
}
