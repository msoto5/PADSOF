package controladores.principal;

import java.awt.event.*;

import mainGUI.*;
import nanoGym.*;
import vistas.prinicipal.*;

public abstract class ControladorReservas implements ActionListener {
    protected VistaReservas vista;
    protected NanoGym modelo;
    protected MainGUI ventana;

    public ControladorReservas(VistaReservas vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
        if (event.getSource().equals(this.vista.getBoton())) {
            vista.clearNotificaciones();
        }
        */
    }
}
