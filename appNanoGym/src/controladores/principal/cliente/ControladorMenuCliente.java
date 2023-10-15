package controladores.principal.cliente;

//import java.awt.event.*;

import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;

import controladores.principal.ControladorMenu;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import usuario.cliente.Cliente;
//import vistas.prinicipal.*;
import vistas.prinicipal.cliente.VistaMenuCliente;
//import vistas.prinicipal.cliente.VistaNotificacionesCliente;
import vistas.prinicipal.cliente.VistaPerfilCliente;

public class ControladorMenuCliente extends ControladorMenu {

    public ControladorMenuCliente(VistaMenuCliente vista, NanoGym modelo, MainGUI mainGUI) {
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
                vista.getReservas().clearServicios();
                super.stateChanged(event);
                /*Actualizo los datos del perfil */
                VistaPerfilCliente perfil = (VistaPerfilCliente) vista.getPerfil();
                Cliente cliente = (Cliente)modelo.getCurrentUser();
                //System.out.println("Gastos cliente -> " + cliente.getGastoTotal());
                perfil.getLabelGastos().setText(Double.toString(cliente.getGastoTotal()));
                perfil.getLabelFaltas().setText(Integer.toString(cliente.getNumFaltas()));
                
                perfil.getLabelNick().setText(cliente.getUsername());
                perfil.getLabelNombre().setText(cliente.getNombreCompleto());
                perfil.getLabelFechaNacimiento().setText(cliente.getFechaNacimiento().toString());
                perfil.getLabelTarifa().setText(cliente.getTarifa().toStringInterfaz());
                String tarjeta = "**** **** **** ";
                tarjeta += cliente.getTarjetaBancaria().getNumeroTarjeta().substring(12, 16);
                perfil.getLabelTarjeta().setText(tarjeta);
                
                break;
            default:
                break;
        }
    }
    
}
