package vistas.prinicipal.cliente;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import servicio.EntrenamientoPersonalizado;
import servicio.Servicio;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import utiles.Horario;

//import java.awt.*;
//import javax.swing.*;

import vistas.prinicipal.VistaActividades;
import vistas.prinicipal.bloqueservicios.BloqueServicio;

public class VistaActividadesCliente extends VistaActividades {
 
    public VistaActividadesCliente() {
        super();
    }

    public void setControlador() {
        
    }

    @Override
    public int addServicio(Servicio s, int num) {
        this.labelSinServ.setVisible(false);

        ArrayList<JButton> botones = new ArrayList<JButton>();
        if (s.aforoDisponible() > 0) {
            reservar = new JButton("Reservar");
            botones.add(reservar);
        } else if (s.tieneListaEspera()) {
            listaEspera = new JButton("Reservar en lista de espera");
            botones.add(listaEspera);
        }
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
}
