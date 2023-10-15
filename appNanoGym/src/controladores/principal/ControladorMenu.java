package controladores.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.time.*;
import java.util.ArrayList;

import vistas.prinicipal.VistaActividades;
import vistas.prinicipal.VistaMenu;
import vistas.prinicipal.VistaNotificaciones;
//import vistas.prinicipal.monitor.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import servicio.Servicio;
import usuario.*;
import usuario.cliente.Cliente;

import javax.swing.event.*;

public abstract class ControladorMenu implements ActionListener, ChangeListener {
    protected NanoGym modelo;
    protected MainGUI ventana;
    protected VistaMenu vista;

    public ControladorMenu(VistaMenu vista, NanoGym modelo, MainGUI mainGUI) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    // Los comportamientos en común de todos los usuarios
    @Override
    public void stateChanged(ChangeEvent event) {
        int selectedIndex = vista.getTabbedPane().getSelectedIndex();
        
        switch (selectedIndex) {
            case 0: //Caso actividades
                //vista.getReservas().clearServicios();
                vista.getNotificaciones().clearNotificaciones();
                VistaActividades actividades = vista.getActividades();
                ArrayList<Servicio> servicios = modelo.getServiciosSiguientes();

                actividades.clearServicios();

                actividades.addServiciosBusqueda(null);
                actividades.addServiciosDisponibles(servicios);
                
                break;
            case 1: //Caso reservas
                vista.getNotificaciones().clearNotificaciones();
                vista.getActividades().clearServicios();
                //vista.getReservas().clearServicios();

                Usuario usuario = modelo.getCurrentUser();
                if (usuario.esCliente()) {
                    Cliente cliente = (Cliente) usuario;
                    vista.getReservas().addServicios(cliente.getServiciosReservas());
                } else if (usuario.esMonitor()) {
                    //System.out.println("Soyyyy Monitor");
                    Monitor monitor = (Monitor) usuario;
                    //System.out.println("Servicios del monitor -> " + monitor.getServicios().size());
                    vista.getReservas().addServicios(monitor.getServicios());
                }
                    
                break;
            case 2: //Caso notificaciones
                vista.getActividades().clearServicios();
                //vista.getReservas().clearServicios();
                VistaNotificaciones notificaciones = vista.getNotificaciones();
                Usuario cl = modelo.getCurrentUser();

                if (cl.getNotificaciones().size() == 0) {
                    notificaciones.clearNotificaciones();
                }
                for (String msg : cl.readNotificaciones()) {
                    notificaciones.addNotificacion(msg);
                }

                break;

            case 3: //Caso perfil
                vista.getActividades().clearServicios();
                //vista.getReservas().clearServicios();
                vista.getNotificaciones().clearNotificaciones();
                break;

            default:
                break;
        }
    }
}
