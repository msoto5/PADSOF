package vistas.prinicipal.administrador;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class VistaCrearTareasAdministrador extends JPanel {

    private CardLayout cl;

    private JButton botonEntrenamientoLibre, botonActividadGrupal;
    private JLabel titulo, tituloSeleccionActividad;
    private JPanel generalCrearTarea, generalSala, infoActividad, salas, generalMonitor, monitores, infoMonitores; 
    
    /*Panel monitor */
    private JLabel labelNickMonitor, labelTipoActividad;
    private JTextField nickMonitor, tipoActividad;

    /*Panel sala */
    private JButton botonCrearTarea, botonCancelar;
    private JLabel labelNombreActividad, labelDescripcion, labelHoraInicio, labelHoraFin, labelFechaActividad, labelIdSala;
    private JTextField nombreActividad, descripcion, horaInicio, horaFin, fechaActividad, idSala;
    /*Panel sala libre */

    /*Panal sala grupal */
    private JButton botonSiguiente;

    
    public VistaCrearTareasAdministrador() {

        this.cl = new CardLayout();
        this.setLayout(cl);

        generalMonitor = new JPanel(new BorderLayout());
        generalSala = new JPanel(new BorderLayout());
        generalCrearTarea = new JPanel(new GridBagLayout());
        infoActividad = new JPanel(new GridBagLayout());
        salas = new JPanel(new GridBagLayout());
        monitores = new JPanel(new GridBagLayout());

        botonEntrenamientoLibre = new JButton("Entrenamiento Libre");
        botonActividadGrupal = new JButton("Actividad Grupal");
        botonCrearTarea = new JButton("Crear tarea");
        botonCancelar = new JButton("Cancelar");
        botonSiguiente = new JButton("Siguiente");

        generalSala.add(infoActividad, BorderLayout.NORTH);
        generalSala.add(salas, BorderLayout.CENTER);

        titulo = new JLabel("CREAR ACTIVIDAD");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 28));

        tituloSeleccionActividad = new JLabel("Seleccione el tipo de actividad:");
        tituloSeleccionActividad.setFont(new Font("Times New Roman", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 0, 0);
        generalCrearTarea.add(titulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(100, 0, 0, 0);        
        generalCrearTarea.add(tituloSeleccionActividad, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(15, 0, 0, 0);
        generalCrearTarea.add(botonEntrenamientoLibre, gbc);

        gbc.gridx = 1;
        generalCrearTarea.add(botonActividadGrupal, gbc);

        this.add(generalCrearTarea, "generalCrearTarea");
        this.add(generalSala, "generalSala");
        this.add(generalMonitor, "generalMonitor");

    }

    public void mostrarPanelCrearTarea() {
        cl.show(this, "generalCrearTarea");
    }

    public void mostrarPanelSalas() {
        cl.show(this, "generalSala");
    }

    public void mostrarPanelMonitores() {
        cl.show(this, "generalMonitor");
    }

    public void setControlador(ActionListener c) {
        botonCrearTarea.addActionListener(c);
        botonCancelar.addActionListener(c);
        botonEntrenamientoLibre.addActionListener(c);
        botonActividadGrupal.addActionListener(c);
        botonSiguiente.addActionListener(c);
    }

    public JButton getBotonCrearTarea() {
        return botonCrearTarea;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }

    public JButton getBotonEntrenamientoLibre() {
        return botonEntrenamientoLibre;
    }

    public JButton getBotonActividadGrupal() {
        return botonActividadGrupal;
    }

    public JButton getBotonSiguiente() {
        return botonSiguiente;
    }

    public JPanel getPanelSalas() {
        return salas;
    }

    public JPanel getPanelMonitores() {
        return monitores;
    }

    public JTextField getNombreActividad() {
        return this.nombreActividad;
    }

    public JTextField getDescripcion() {
        return this.descripcion;
    }

    public JTextField getHoraInicio() {
        return this.horaInicio;
    }

    public JTextField getHoraFin() {
        return this.horaFin;
    }

    public JTextField getFechaActividad() {
        return this.fechaActividad;
    }

    public JTextField getIdSala() {
        return this.idSala;
    }

    public JTextField getNickMonitor() {
        return this.nickMonitor;
    }
    
    public JTextField getTipoActividad() {
        return this.tipoActividad;
    }

    /*SIN MONITOR (NI PANEL MONITOR POR CONSIGIUENTE) */
    public void inicializarGeneralSalaLibre() {
        this.infoActividad.removeAll();

        labelNombreActividad = new JLabel("Nombre de la actividad:");
        labelDescripcion = new JLabel("Descripción:");
        labelHoraInicio = new JLabel("Hora de inicio (HH MM):");
        labelHoraFin = new JLabel("Hora de fin (HH MM):");
        labelFechaActividad = new JLabel("Fecha de la actividad (DD MM YYYY):");
        labelIdSala = new JLabel("Id de la sala donde realizar actividad:");
        JLabel tituloLibre = new JLabel("CREAR ENTRENAMIENTO LIBRE");
        tituloLibre.setFont(new Font("Times New Roman", Font.BOLD, 28));

        nombreActividad = new JTextField(20);
        descripcion = new JTextField(20);
        horaInicio = new JTextField(20);
        horaFin = new JTextField(20);
        fechaActividad = new JTextField(20);
        idSala = new JTextField(5);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        infoActividad.add(tituloLibre, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        infoActividad.add(labelNombreActividad, gbc);

        gbc.gridx = 1;
        infoActividad.add(nombreActividad, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        infoActividad.add(labelDescripcion, gbc);

        gbc.gridx = 1;
        infoActividad.add(descripcion, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        infoActividad.add(labelHoraInicio, gbc);

        gbc.gridx = 1;
        infoActividad.add(horaInicio, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        infoActividad.add(labelHoraFin, gbc);

        gbc.gridx = 1;
        infoActividad.add(horaFin, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        infoActividad.add(labelFechaActividad, gbc);

        gbc.gridx = 1;
        infoActividad.add(fechaActividad, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        infoActividad.add(labelIdSala, gbc);

        gbc.gridx = 1;
        infoActividad.add(idSala, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 0, 0);
        infoActividad.add(botonCrearTarea, gbc);

        gbc.gridx = 1;
        infoActividad.add(botonCancelar, gbc);

    }

    /* CON MONITOR */
    public void inicializarGeneralSalaGrupal() {
        this.infoActividad.removeAll();

        labelNombreActividad = new JLabel("Nombre de la actividad:");
        labelDescripcion = new JLabel("Descripción:");
        labelHoraInicio = new JLabel("Hora de inicio (HH MM):");
        labelHoraFin = new JLabel("Hora de fin (HH MM):");
        labelFechaActividad = new JLabel("Fecha de la actividad (DD MM YYYY):");
        labelIdSala = new JLabel("Id de la sala donde realizar actividad:");
        labelTipoActividad = new JLabel("Tipo de la actividad grupal:");
        JLabel tituloGrupal = new JLabel("CREAR ACTIVIDAD GRUPAL");
        tituloGrupal.setFont(new Font("Times New Roman", Font.BOLD, 28));

        nombreActividad = new JTextField(20);
        descripcion = new JTextField(20);
        horaInicio = new JTextField(20);
        horaFin = new JTextField(20);
        fechaActividad = new JTextField(20);
        idSala = new JTextField(5);
        tipoActividad = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        infoActividad.add(tituloGrupal, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        infoActividad.add(labelNombreActividad, gbc);

        gbc.gridx = 1;
        infoActividad.add(nombreActividad, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        infoActividad.add(labelDescripcion, gbc);

        gbc.gridx = 1;
        infoActividad.add(descripcion, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        infoActividad.add(labelHoraInicio, gbc);

        gbc.gridx = 1;
        infoActividad.add(horaInicio, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        infoActividad.add(labelHoraFin, gbc);

        gbc.gridx = 1;
        infoActividad.add(horaFin, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        infoActividad.add(labelFechaActividad, gbc);

        gbc.gridx = 1;
        infoActividad.add(fechaActividad, gbc);


        gbc.gridy = 6;
        gbc.gridx = 0;
        infoActividad.add(labelTipoActividad, gbc);

        gbc.gridx = 1;
        infoActividad.add(tipoActividad, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        infoActividad.add(labelIdSala, gbc);

        gbc.gridx = 1;
        infoActividad.add(idSala, gbc);

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 0, 0);
        infoActividad.add(botonSiguiente, gbc);

        gbc.gridx = 1;
        infoActividad.add(botonCancelar, gbc);

    }

    public void inicializarMonitores() {

        infoMonitores = new JPanel(new GridBagLayout());
        JLabel titulo = new JLabel("SELECCION DE MONITOR");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 28));

        labelNickMonitor = new JLabel("Introduzca nick del monitor que desea:");

        nickMonitor = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        infoMonitores.add(titulo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        infoMonitores.add(labelNickMonitor, gbc);

        gbc.gridx = 1;
        infoMonitores.add(nickMonitor, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 0, 0);
        infoMonitores.add(botonCrearTarea, gbc);

        gbc.gridx = 1;
        infoMonitores.add(botonCancelar, gbc);

        generalMonitor.add(infoMonitores, BorderLayout.NORTH);
        generalMonitor.add(monitores, BorderLayout.CENTER);
    }
}
