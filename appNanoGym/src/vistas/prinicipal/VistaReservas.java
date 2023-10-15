package vistas.prinicipal;

import javax.swing.JPanel;

import controladores.principal.ControladorReservas;
import servicio.EntrenamientoPersonalizado;
import servicio.Servicio;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import utiles.Horario;
import vistas.prinicipal.bloqueservicios.BloqueServicio;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public abstract class VistaReservas extends JPanel {

    protected JLabel titulo;

    protected ArrayList<BloqueServicio> servs = new ArrayList<BloqueServicio>();
    protected ArrayList<Servicio> servsServicios = new ArrayList<Servicio>();

    protected JLabel labelSinRes;

    protected int numServ;
    
    public VistaReservas() {

        GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        titulo = new JLabel("Reservas");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 28));
        
        labelSinRes = new JLabel("No tienes reservas en este momento");
        labelSinRes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(titulo, gbc);

        numServ = 3;

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(labelSinRes, gbc);
    }

    public void setControlador(ControladorReservas controlador) {
        return;
    }

    private int addServicio(Servicio s, int num) {
        this.labelSinRes.setVisible(false);

        String tipo = "";
        BloqueServicio bs = new BloqueServicio(new ArrayList<JButton>());
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

        return num;
    }

    public void addServicios(ArrayList<Servicio> servicios) {

        if (servicios == null || servicios.size() == 0) {
            this.labelSinRes.setVisible(true);
            return;
        }

        this.labelSinRes.setVisible(false);
        for (Servicio s : servicios) {
            numServ = addServicio(s, numServ);
            numServ++;
        }
    }

    public void clearServicios() {
        for (BloqueServicio bs : servs) {
            this.remove(bs);
        }
        numServ = 3;
        colocarLabelSinResPrincipio();
        this.revalidate();
        this.repaint();
    }

    private void colocarLabelSinResPrincipio() {
        this.remove(labelSinRes);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(labelSinRes, gbc);

        labelSinRes.setVisible(true);

    }

    public ArrayList<BloqueServicio> getServicios() {
        return servs;
    }

    public ArrayList<Servicio> getServiciosServicios() {
        return servsServicios;
    }
}
