package vistas.prinicipal;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import javax.swing.*;

//import controladores.principal.*;
//import controladores.principal.cliente.*;
import mainGUI.MainGUI;
import nanoGym.NanoGym;

public abstract class VistaMenu extends JPanel {

    // Creamos un panel por cada pestaña
    protected VistaActividades actividades;
    protected VistaReservas reservas;
    protected VistaNotificaciones notificaciones;
    protected VistaPerfil perfil;

    // Creamos un contenedor de tipo JTabbedPane (no JPanel).
    protected JTabbedPane pestanias = new JTabbedPane(JTabbedPane.TOP);

    // Iconos para las pestañas
    int icAlt = 28;
    protected ImageIcon iconoActividades;
    protected ImageIcon iconoReservas;
    protected ImageIcon iconoNotificaciones;
    protected ImageIcon iconoPerfil;

    public VistaMenu(NanoGym modelo, MainGUI ventana) {

        BorderLayout layoutPrincipal = new BorderLayout();
        this.setLayout(layoutPrincipal);

        /*actividades = v_act;
        ControladorNotificaciones controladorNotificacionesCliente = new ControladorNotificaciones(notificaciones, modelo, ventana);
        notificaciones.setControlador(controladorNotificaciones);

        perfil = v_perfil;
        ControladorPerfilCliente controladorPerfilCliente = new ControladorPerfilCliente(perfil, modelo, ventana);
        perfil.setControlador(controladorPerfilCliente);*/

        //pestanias.setBounds(50,50,200,200);  

        // Creamos los iconos
        Image aux = new ImageIcon("./resources/img/buscar96.png").getImage();
        iconoActividades = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/pesa96.png").getImage();
        iconoReservas = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/notificacionIcon96.png").getImage();
        iconoNotificaciones = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        aux = new ImageIcon("./resources/img/perfil96.png").getImage();
        iconoPerfil = new ImageIcon(aux.getScaledInstance(icAlt, icAlt, Image.SCALE_DEFAULT));

        // Añadimos los paneles al contenedor con el método addTab(<título>,<panel>)
        /*pestanias.addTab("Actividades", iconoActividades, actividades, "Actividades");
        pestanias.addTab("Reservas", iconoReservas, reservas, "Reservas");
        pestanias.addTab("Notificaciones", iconoNotificaciones, notificaciones, "Notificaciones");
        pestanias.addTab("Perfil", iconoPerfil, perfil, "Perfil");*/

        //pestanias.setIconAt(2, new ImageIcon("a.jpg"));

        //pestanias.setBackgroundAt(0, Color.yellow);
    
        
        // Podemos seleccionar una pestaña del contenedor con setSelectedIndex(<indice>)
        //pestanias.setSelectedIndex(3);
        // Podemos eliminar una pestaña del contenedor con removeTabAt(<indice>)
        //pestanias.removeTabAt(1);

        // Añadimos el contenedor de pestañas al panel principal
        this.add(pestanias, BorderLayout.NORTH);
    }

    public void setControlador(ChangeListener controlador) {
        pestanias.addChangeListener(controlador);
    }

    public JTabbedPane getTabbedPane() {
        return pestanias;
    }

    public VistaPerfil getPerfil() {
        return perfil;
    }

    public VistaActividades getActividades() {
        return actividades;
    }

    public VistaReservas getReservas() {
        return reservas;
    }

    public VistaNotificaciones getNotificaciones() {
        return notificaciones;
    }
}
