package vistas.prinicipal;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controladores.principal.ControladorActividades;
import servicio.EntrenamientoPersonalizado;
import servicio.Servicio;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import utiles.Horario;
import vistas.prinicipal.bloqueservicios.BloqueServicio;

public abstract class VistaActividades extends JPanel {

    protected ControladorActividades contAct;

    protected JButton reservar;
    protected JButton listaEspera;

    protected JLabel proxAct;
    protected JLabel labelSinServ2;
    protected int numServ2;
    protected ArrayList<BloqueServicio> servs = new ArrayList<BloqueServicio>();
    protected ArrayList<Servicio> servsServicios = new ArrayList<Servicio>();

    // Busqueda
    protected JTextField busqueda;
    protected JLabel labelSinServ;
    protected int numServ;
    protected JButton buscar;
    protected JCheckBox bEntLibre;
    protected JCheckBox bActGrupal;
    protected JCheckBox bEntPers;

    public VistaActividades() {

        GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        JLabel titulo = new JLabel("Actividades");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 28));

        JLabel labelBusqueda = new JLabel("Busqueda de servicios:");
        labelBusqueda.setFont(new Font("Times New Roman", Font.BOLD, 15));
        

        busqueda = new JTextField(25);
        busqueda.setText("Introduzca su busqueda");
        busqueda.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (busqueda.getText().equals("Introduzca su busqueda")) {
                    busqueda.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (busqueda.getText().equals("")) {
                    busqueda.setText("Introduzca su busqueda");
                }
            }
        });

        buscar = new JButton("Buscar");


        labelSinServ = new JLabel("");
        labelSinServ.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bEntLibre = new JCheckBox("Entrenamiento Libre");
        bActGrupal = new JCheckBox("Actividad Grupal");
        bEntPers = new JCheckBox("Entrenamiento Personalizado");

        proxAct = new JLabel("Proximas actividades del gimnasio:");
        proxAct.setFont(new Font("Times New Roman", Font.BOLD, 15));

        labelSinServ2 = new JLabel("");
        labelSinServ2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Añadir elementos al panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(titulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(labelBusqueda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        this.add(busqueda, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        this.add(buscar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        this.add(bEntLibre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        this.add(bActGrupal, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        this.add(bEntPers, gbc);

        numServ = 4;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(labelSinServ, gbc);

        // Proximas actividades
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(proxAct, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(labelSinServ2, gbc);
        numServ2 = 5;
    }

    public void setControlador(ControladorActividades controlador) {
        contAct = controlador;
        buscar.addActionListener(controlador);
        for (BloqueServicio bs : servs) {
            bs.setControlador(controlador);
        }
        bEntLibre.addActionListener(controlador);
        bActGrupal.addActionListener(controlador);
        bEntPers.addActionListener(controlador);
    }

    protected int addServicio(Servicio s, int num) {
        this.labelSinServ.setVisible(false);

        ArrayList<JButton> botones = new ArrayList<JButton>();
        /*if (s.aforoDisponible() > 0) {
            reservar = new JButton("Reservar");
            botones.add(reservar);
        } else if (s.tieneListaEspera()) {
            listaEspera = new JButton("Reservar en lista de espera");
            botones.add(listaEspera);
        }*/
        String tipo = "";
        BloqueServicio bs = new BloqueServicio(botones);
        if (s instanceof EntrenamientoLibre) {
            tipo = "Entrenamiento Libre";
        } else if (s instanceof ActividadGrupal) {
            tipo = "Actividad Grupal";
        } else if (s instanceof EntrenamientoPersonalizado) {
            tipo = "Entrenamiento Personalizado";
        } else {
            tipo = "Servicio";
        }

        bs.getNombre().setText(tipo + ": " + s.getNombre());
        bs.getDesc().setText("Descripcion: " + s.getDescripcion());
        bs.getFecha().setText("Fecha: " + s.getFecha().toString());

        if (!s.getHorario().equals(Horario.horarioNULL())) {
            bs.getHorario().setText("Horario: " + s.getHorario().toString());
        } /*else {
            bs.getHorario().setText("Horario: Sin horario");
        }*/
        
        if (!s.getMonitorNombre().isEmpty()) {
            bs.getMonitor().setText("Monitor: " + s.getMonitorNombre());
        } /*else {
            bs.getMonitor().setText("Monitor: Sin monitor");
        }*/

        bs.getAforoDisponible().setText("Aforo disponible: " + Integer.valueOf(s.aforoDisponible()).toString());
        bs.getNumReservas().setText("Numero de reservas: " + Integer.valueOf(s.getReservas().size()).toString());
        bs.getAforoTotal().setText("Aforo Total: " + Integer.valueOf(s.aforoTotal()).toString());

        servs.add(bs);
        servsServicios.add(s);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = num;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(bs, gbc);
        this.revalidate();
        this.repaint();
        num++;

        bs.setControlador(contAct);
        return num;
    }

    public void addServiciosBusqueda(ArrayList<Servicio> servicios) {
        if (servicios == null || servicios.size() == 0) {
            this.labelSinServ.setVisible(true);
            return;
        }

        this.labelSinServ.setVisible(false);
        for (Servicio s : servicios) {
            numServ = addServicio(s, numServ);
            numServ2 = numServ + 2;
        }
    }

    public void addServiciosDisponibles(ArrayList<Servicio> servicios) {
        colocarProxAct(numServ + 1);

        if (servicios == null || servicios.size() == 0) {
            this.labelSinServ2.setVisible(true);
            return;
        }

        this.labelSinServ2.setVisible(false);
        numServ2 = numServ + 2;
        for (Servicio s : servicios) {
            numServ2 = addServicio(s, numServ2);
            numServ2++;
        }
    }

    public void clearServicios() {
        for (BloqueServicio bs : servs) {
            this.remove(bs);
        }
        numServ = 4;
        numServ2 = 6;
        colocarProxAct(4);
        colocarLabelSinServPrincipio();
        this.revalidate();
        this.repaint();
    }

    private void colocarProxAct(int posy) {
        this.remove(proxAct);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = posy;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(proxAct, gbc);
    }

    private void colocarLabelSinServPrincipio() {
        this.remove(labelSinServ);
        this.remove(labelSinServ2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(labelSinServ, gbc);

        labelSinServ.setVisible(true);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(labelSinServ2, gbc);
        numServ2 = 5;

        labelSinServ2.setVisible(true);
    }

    public ArrayList<BloqueServicio> getServicios() {
        return servs;
    }

    public ArrayList<Servicio> getServiciosServicios() {
        return servsServicios;
    }

    public ArrayList<JButton> getBotones(int i) {
        return servs.get(i).getBotones();
    }

    public JButton getBuscar() {
        return buscar;
    }

    public JTextField getBusqueda() {
        return busqueda;
    }

    public JCheckBox getBEntLibre() {
        return bEntLibre;
    }

    public JCheckBox getBActGrupal() {
        return bActGrupal;
    }

    public JCheckBox getBEntPers() {
        return bEntPers;
    }
}