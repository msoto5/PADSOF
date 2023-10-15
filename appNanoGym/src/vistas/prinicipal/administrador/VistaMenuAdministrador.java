package vistas.prinicipal.administrador;

import java.awt.*;
import javax.swing.event.*;

import controladores.principal.administrador.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.VistaMenu;

public class VistaMenuAdministrador extends VistaMenu {

    private VistaCrearTareasAdministrador crearTarea;

    public VistaMenuAdministrador(NanoGym modelo, MainGUI ventana) {
        super(modelo, ventana);

        actividades = new VistaPrincipalAdministrador();
        ControladorPrincipalAdministrador controladorPrincipalAdministrador = new ControladorPrincipalAdministrador((VistaPrincipalAdministrador) actividades, modelo, ventana);
        actividades.setControlador(controladorPrincipalAdministrador);

        /*
        reservas = new VistaReservasAdministrador();
        ControladorReservasAdministrador controladorReservasAdministrador = new ControladorReservasAdministrador((VistaReservasAdministrador) reservas, modelo, ventana);
        reservas.setControlador(controladorReservasAdministrador);*/

        crearTarea = new VistaCrearTareasAdministrador();
        ControladorCrearTareasAdministrador controladorCrearTareasAdministrador = new ControladorCrearTareasAdministrador((VistaCrearTareasAdministrador) crearTarea, modelo, ventana);
        crearTarea.setControlador(controladorCrearTareasAdministrador);

        notificaciones = new VistaNotificacionesAdministrador();
        ControladorNotificacionesAdministrador controladorNotificacionesAdministrador = new ControladorNotificacionesAdministrador((VistaNotificacionesAdministrador) notificaciones, modelo, ventana);
        notificaciones.setControlador(controladorNotificacionesAdministrador);

        perfil = new VistaPerfilAdministrador();
        ControladorPerfilAdministrador controladorPerfilAdministrador = new ControladorPerfilAdministrador((VistaPerfilAdministrador) perfil, modelo, ventana);
        perfil.setControlador(controladorPerfilAdministrador);

        // Add tabs
        pestanias.addTab("Actividades", iconoActividades, actividades, "Actividades");
        pestanias.addTab("Crear Tareas", iconoReservas, crearTarea, "CrearTareas");
        pestanias.addTab("Notificaciones", iconoNotificaciones, notificaciones, "Notificaciones");
        pestanias.addTab("Perfil", iconoPerfil, perfil, "Perfil");

        pestanias.setSelectedIndex(0);

        // Añadimos el contenedor de pestañas al panel principal
        this.add(pestanias, BorderLayout.NORTH);
    }
    
    public void setControlador(ChangeListener controlador) {
        pestanias.addChangeListener(controlador);
    }
}
