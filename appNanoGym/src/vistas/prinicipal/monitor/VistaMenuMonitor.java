package vistas.prinicipal.monitor;

import java.awt.*;

//import javax.swing.*;

import controladores.principal.monitor.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.VistaMenu;

public class VistaMenuMonitor extends VistaMenu {

    /*// Creamos un panel por cada pestaña
    private VistaActividadesMonitor actividades;
    private VistaReservasMonitor reservas = new VistaReservasMonitor();
    private VistaNotificacionesMonitor notificaciones = new VistaNotificacionesMonitor();
    private VistaPerfilMonitor perfil;*/

    // Creamos un contenedor de tipo JTabbedPane (no JPanel).
    //private JTabbedPane pestanias = new JTabbedPane(JTabbedPane.TOP);

    public VistaMenuMonitor(NanoGym modelo, MainGUI ventana) {
        super(modelo, ventana);

        /*BorderLayout layoutPrincipal = new BorderLayout();
        this.setLayout(layoutPrincipal);*/

        actividades = new VistaActividadesMonitor();
        ControladorActividadesMonitor controladorActividadesMonitor = new ControladorActividadesMonitor((VistaActividadesMonitor) actividades, modelo, ventana);
        actividades.setControlador(controladorActividadesMonitor);

        reservas = new VistaReservasMonitor();
        ControladorReservasMonitor controladorReservasMonitor = new ControladorReservasMonitor((VistaReservasMonitor) reservas, modelo, ventana);
        //System.out.println("aa: " + controladorReservasMonitor.toString());
        reservas.setControlador(controladorReservasMonitor);
        //System.out.println("VistaMenuMonitor: " );

        notificaciones = new VistaNotificacionesMonitor();
        ControladorNotificacionesMonitor controladorNotificacionesMonitor = new ControladorNotificacionesMonitor((VistaNotificacionesMonitor) notificaciones, modelo, ventana);
        notificaciones.setControlador(controladorNotificacionesMonitor);

        perfil = new VistaPerfilMonitor();
        ControladorPerfilMonitor controladorPerfilMonitor = new ControladorPerfilMonitor((VistaPerfilMonitor) perfil, modelo, ventana);
        perfil.setControlador(controladorPerfilMonitor); 

        //pestanias.setBounds(50,50,200,200);  

        // Añadimos los paneles al contenedor con el método addTab(<título>,<panel>)
        pestanias.addTab("Actividades", iconoActividades, actividades, "Actividades");
        pestanias.addTab("Clases", iconoReservas, reservas, "Clases");
        pestanias.addTab("Notificaciones", iconoNotificaciones, notificaciones, "Notificaciones");
        pestanias.addTab("Perfil", iconoPerfil, perfil, "Perfil");

        //pestanias.setIconAt(2, new ImageIcon("a.jpg"));

        //pestanias.setBackgroundAt(0, Color.yellow);
    
        
        // Podemos seleccionar una pestaña del contenedor con setSelectedIndex(<indice>)
        pestanias.setSelectedIndex(0);
        // Podemos eliminar una pestaña del contenedor con removeTabAt(<indice>)
        //pestanias.removeTabAt(1);

        // Añadimos el contenedor de pestañas al panel principal
        this.add(pestanias, BorderLayout.NORTH);
    }
        
    public void setControlador(ControladorMenuMonitor controladorMenuMonitor) {
        pestanias.addChangeListener(controladorMenuMonitor);
    }

    /*public JTabbedPane getTabbedPane() {
        return pestanias;
    }

    public VistaPerfilMonitor getPerfil() {
        return perfil;
    }

    public VistaActividadesMonitor getActividades() {
        return actividades;
    }

    public VistaReservasMonitor getReservas() {
        return reservas;
    }

    public VistaNotificacionesMonitor getNotificaciones() {
        return notificaciones;
    }
    */
}
