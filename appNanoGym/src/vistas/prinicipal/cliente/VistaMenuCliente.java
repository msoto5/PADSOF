package vistas.prinicipal.cliente;

import java.awt.*;
//import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
//import javax.swing.*;

import controladores.principal.cliente.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.VistaMenu;

public class VistaMenuCliente extends VistaMenu {

    /*// Creamos un panel por cada pestaña
    private VistaActividadesCliente actividades;
    private VistaReservasCliente reservas = new VistaReservasCliente();
    private VistaNotificacionesCliente notificaciones = new VistaNotificacionesCliente();
    private VistaPerfilCliente perfil;

    // Creamos un contenedor de tipo JTabbedPane (no JPanel).
    private JTabbedPane pestanias = new JTabbedPane(JTabbedPane.TOP);

    // Iconos para las pestañas
    int icAlt = 32;
    private ImageIcon iconoActividades;
    private ImageIcon iconoReservas;
    private ImageIcon iconoNotificaciones;
    private ImageIcon iconoPerfil;
    */

    public VistaMenuCliente(NanoGym modelo, MainGUI ventana) {
        super(modelo, ventana);

        /*BorderLayout layoutPrincipal = new BorderLayout();
        this.setLayout(layoutPrincipal);*/

        actividades = new VistaActividadesCliente();
        ControladorActividadesCliente controladorActividadesCliente = new ControladorActividadesCliente((VistaActividadesCliente) actividades, modelo, ventana);
        actividades.setControlador(controladorActividadesCliente);

        reservas = new VistaReservasCliente();
        ControladorReservasCliente controladorReservasCliente = new ControladorReservasCliente((VistaReservasCliente) reservas, modelo, ventana);
        reservas.setControlador(controladorReservasCliente);

        notificaciones = new VistaNotificacionesCliente();
        ControladorNotificacionesCliente controladorNotificacionesCliente = new ControladorNotificacionesCliente((VistaNotificacionesCliente) notificaciones, modelo, ventana);
        notificaciones.setControlador(controladorNotificacionesCliente);

        perfil = new VistaPerfilCliente();
        ControladorPerfilCliente controladorPerfilCliente = new ControladorPerfilCliente((VistaPerfilCliente) perfil, modelo, ventana);
        perfil.setControlador(controladorPerfilCliente); 

        //pestanias.setBounds(50,50,200,200);  

        /*
        // Creamos los iconos
        Image aux = new ImageIcon("./resources/img/notificacionIcon96.png").getImage();
        iconoActividades = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/notificacionIcon96.png").getImage();
        iconoReservas = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/notificacionIcon96.png").getImage();
        iconoNotificaciones = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/notificacionIcon96.png").getImage();
        iconoPerfil = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));
        */

        // Añadimos los paneles al contenedor con el método addTab(<título>,<panel>)
        pestanias.addTab("Actividades", iconoActividades, actividades, "Actividades");
        pestanias.addTab("Reservas", iconoReservas, reservas, "Reservas");
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

    public void setControlador(ChangeListener controlador) {
        pestanias.addChangeListener(controlador);
    }

    /*public JTabbedPane getTabbedPane() {
        return pestanias;
    }

    public VistaPerfilCliente getPerfil() {
        return perfil;
    }

    public VistaActividadesCliente getActividades() {
        return actividades;
    }

    public VistaReservasCliente getReservas() {
        return reservas;
    }

    public VistaNotificacionesCliente getNotificaciones() {
        return notificaciones;
    }*/
}
