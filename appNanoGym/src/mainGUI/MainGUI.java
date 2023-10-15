package mainGUI;

import java.awt.*;

import javax.swing.*;

import vistas.inicio.*;
import controladores.inicio.*;
import vistas.prinicipal.administrador.*;
import vistas.prinicipal.cliente.*;
import controladores.principal.administrador.*;
import controladores.principal.cliente.*;
import vistas.prinicipal.monitor.*;
import controladores.principal.monitor.*;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
//import vistas.prinicipal.administrador.*;
//import controladores.principal.administrador.*;
import nanoGym.NanoGym;
import sala.Sala;
import sala.SalaClimatizada;
//temp
import usuario.cliente.Cliente;
import usuario.cliente.TarjetaBancaria;
import utiles.Horario;
import usuario.*;
import java.time.*;

public class MainGUI extends JFrame {
    NanoGym modelo;

    ControladorMenuCliente controladorMenuCliente;
    ControladorMenuMonitor controladorMenuMonitor;
    ControladorMenuAdministrador controladorMenuAdministrador;

    public MainGUI() {
        super("NanoGym");

        this.modelo = new NanoGym();

        Container container = this.getContentPane();
		container.setLayout(new CardLayout());

        /*Creo vista inicio sesion */
        VistaInicioSesion panelInicioSesion = new VistaInicioSesion();
        ControladorInicioSesion controladorInicioSesion = new ControladorInicioSesion(panelInicioSesion, modelo, this);
        panelInicioSesion.setControlador(controladorInicioSesion);

        /*Creo vista registro */
        VistaRegistro panelRegistro = new VistaRegistro();
        ControladorRegistro controladorRegistro = new ControladorRegistro(panelRegistro, modelo, this);
        panelRegistro.setControlador(controladorRegistro);

        /**********************************************************************************************/
        /*===================================Vistas de cliente========================================*/
        /**********************************************************************************************/
        /*Creo vista de reservas de cliente */
        //VistaReservasCliente panelReservasCliente = new VistaReservasCliente();
        //ControladorReservasCliente controladorReservasCliente = new ControladorReservasCliente(panelReservasCliente, modelo, this);
        //panelReservasCliente.setControlador(controladorReservasCliente);

        /*Creo vista de menu de cliente */
        VistaMenuCliente panelMenuCliente = new VistaMenuCliente(modelo, this);
        controladorMenuCliente = new ControladorMenuCliente(panelMenuCliente, modelo, this);
        panelMenuCliente.setControlador(controladorMenuCliente);
        controladorMenuCliente.stateChanged(null);


        /**********************************************************************************************/
        /*===================================Vistas de monitor========================================*/
        /**********************************************************************************************/
        /*Creo vista de reservas de monitor */
        //VistaReservasMonitor panelReservasMonitor = new VistaReservasMonitor();
        //ControladorReservasMonitor controladorReservasMonitor = new ControladorReservasMonitor(panelReservasMonitor, modelo, this);
        //panelReservasMonitor.setControlador(controladorReservasMonitor);

        /*Creo vista de menu de monitor */
        VistaMenuMonitor panelMenuMonitor = new VistaMenuMonitor(modelo, this);
        controladorMenuMonitor = new ControladorMenuMonitor(panelMenuMonitor, modelo, this);
        panelMenuMonitor.setControlador(controladorMenuMonitor);


        /**********************************************************************************************/
        /*================================Vistas de administrador=====================================*/
        /**********************************************************************************************/
        /*Creo vista de menu de administrador */
        VistaMenuAdministrador panelMenuAdministrador = new VistaMenuAdministrador(modelo, this);
        controladorMenuAdministrador = new ControladorMenuAdministrador(panelMenuAdministrador, modelo, this);
        panelMenuAdministrador.setControlador(controladorMenuAdministrador);


        /**********************************************************************************************/
        /*======================================Añado vistas==========================================*/
        /**********************************************************************************************/
        /*Inicio */
        this.add(panelInicioSesion, "panelInicioSesion");
        this.add(panelRegistro, "panelRegistro");

        /*Cliente */
        this.add(panelMenuCliente, "panelMenuCliente");

        /*Monitor */
        this.add(panelMenuMonitor, "panelMenuMonitor");

        /*Administrador */
        //this.add(panelCrearTareasAdministrador, "panelCrearTareasAdministrador");
        this.add(panelMenuAdministrador, "panelMenuAdministrador");


        /* Propiedades JFrame */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this.setSize(800, 600);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension tamVentana = tk.getScreenSize();  // Tamaño de la pantalla
        this.setSize(tamVentana);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainGUI ventana = new MainGUI();

        //temp
        

        ventana.modelo.loadBackUp();    // Carga el backup de src/backup
        Cliente c = new Cliente("pato", "12345", "hola", LocalDate.of(1990, 12, 12));
        TarjetaBancaria tb;
        try {
            tb = new TarjetaBancaria("1234567890123456", LocalDate.of(2030, 01, 01), "123");
            c.setTarjetaBancaria(tb);
        } catch (InvalidCardNumberException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        
        ventana.modelo.addUsuario(c);
        Monitor m = new Monitor("moni", "12345", "Monitor Prueba", "monitor-prueba@gmail.com", "12345678V");
        ventana.modelo.addUsuario(m);
        Administrador a = new Administrador("admin", "12345", ventana.modelo);
        ventana.modelo.addUsuario(a);

        Sala s = new Sala("sala 1", 10, "sala de pesas");
        ventana.modelo.addSala(new Sala("sala 1", 1, "sala de pesas"));
        ventana.modelo.addSala(new Sala("sala 2", 2, "sala de cardio"));
        ventana.modelo.addSala(new Sala("sala 3", 3, "sala de estiramientos"));
        ventana.modelo.addSala(new SalaClimatizada(new Horario(LocalTime.of(12,0), LocalTime.of(13,0)),"sala 4", 3, "sala de estiramientos"));
        ventana.modelo.addSala(new SalaClimatizada(new Horario(LocalTime.of(12,0), LocalTime.of(13,0)),"sala 5", 4, "sala de estiramientos"));

        Horario horario = new Horario(LocalTime.of(12,0), LocalTime.of(13,0));
        LocalDate fecha = LocalDate.now().plusDays(2);
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        
        horario = new Horario(LocalTime.of(14,0), LocalTime.of(15,0));
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(14,0), LocalTime.of(15,0));
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(16,0), LocalTime.of(17,0));
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(18,0), LocalTime.of(19,0));
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(20,0), LocalTime.of(21,0));
        ventana.modelo.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);

        ventana.modelo.addActividadGrupal("Yoga", "Yoga para mejorar la mente y el cuerpo", horario, fecha, s, m, "Yoga");

        ventana.mostrarInicioSesion();
        ventana.requestFocusInWindow(); //Para que no se ponga directamente en el primer espacio de texto

    }

 
    /***********************************************************************************************/
    /*==========================Funciones para mostrar distintas pestañas========================= */
    /***********************************************************************************************/

    /*Pestañas de inicio */
    public void mostrarRegistro() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        cl.show(this.getContentPane(), "panelRegistro");
    }

    public void mostrarInicioSesion() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        cl.show(this.getContentPane(), "panelInicioSesion");
    }

    /*Pestañas de cliente */ //MANTENER SOLO LA MENU

    public void mostrarReservasCliente() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        cl.show(this.getContentPane(), "panelReservasCliente");
    }

    public void mostrarMenuCliente() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        controladorMenuCliente.stateChanged(null);
        cl.show(this.getContentPane(), "panelMenuCliente");
    }

    /*Pestañas de monitor */ 

    public void mostrarMenuMonitor() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        controladorMenuMonitor.stateChanged(null);
        cl.show(this.getContentPane(), "panelMenuMonitor");
    }

    /*Pestañas de administrador */
    public void mostrarMenuAdministrador() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        controladorMenuAdministrador.stateChanged(null);
        cl.show(this.getContentPane(), "panelMenuAdministrador");
    }

    /*
    public void mostrarPrincipalAdministrador() {
        CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
        cl.show(this.getContentPane(), "panelPrincipalAdministrador");
    }
    */

    
}
