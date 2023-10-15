package controladores.principal;

import java.awt.event.*;

import mainGUI.*;
import nanoGym.*;
import vistas.prinicipal.VistaNotificaciones;

public abstract class ControladorNotificaciones implements ActionListener {
    protected VistaNotificaciones vista;
    protected NanoGym modelo;
    protected MainGUI ventana;

    public ControladorNotificaciones(VistaNotificaciones vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.vista.getBoton())) {
            vista.clearNotificaciones();
        }
    }
}
