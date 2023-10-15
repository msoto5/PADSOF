package controladores.principal.monitor;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.time.*;

import vistas.prinicipal.monitor.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;

import usuario.*;

import javax.swing.event.*;

import controladores.principal.ControladorMenu;

public class ControladorMenuMonitor extends ControladorMenu {

    public ControladorMenuMonitor(VistaMenuMonitor vista, NanoGym modelo, MainGUI mainGUI) {
        super(vista, modelo, mainGUI);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        int selectedIndex = vista.getTabbedPane().getSelectedIndex();

        switch (selectedIndex) {
            case 0: //Caso actividades
                vista.getReservas().clearServicios();
                super.stateChanged(event);
                break;

            case 1: //Caso reservas
                vista.getReservas().clearServicios();
                super.stateChanged(event);
                break;

            case 2: //Caso notificaciones
                vista.getReservas().clearServicios();
                super.stateChanged(event);
                break;

            case 3: //Caso perfil
                vista.getActividades().clearServicios();
                vista.getReservas().clearServicios();
                vista.getNotificaciones().clearNotificaciones();
                /*Actualizo los datos del perfil */
                VistaPerfilMonitor perfil = (VistaPerfilMonitor) vista.getPerfil();
                Monitor monitor = (Monitor)modelo.getCurrentUser();

                perfil.getLabelNominaBase().setText(Double.toString((Monitor.getNOMINA_BASE())));
                perfil.getLabelHorasTrabajadas().setText(Double.toString(monitor.getHorasTrabajadas(LocalDate.now().getMonth(), Year.of(LocalDate.now().getYear()))));
                perfil.getLabelExtraPorHora().setText(Double.toString(Monitor.getEXTRA_SUELDO_SESION()));

                perfil.getLabelNombre().setText(monitor.getNombreCompleto());
                perfil.getLabelNick().setText(monitor.getUsername());
                perfil.getLabelNIF().setText(monitor.getNif());
                perfil.getLabelEmail().setText(monitor.getEmail());
                
                break;
            default:
                break;
        }
    }
    
}
