package vistas.prinicipal.bloqueservicios;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import controladores.principal.ControladorActividades;

public class BloqueServicio extends JPanel {
    
    JLabel nombre;
    JLabel desc;
    JLabel fecha;
    JLabel horario;
    JLabel monitor;
    JLabel aforoDisponible;
    JLabel numReservas;
    JLabel aforoTotal;
    ArrayList<JButton> botones;

    public BloqueServicio(ArrayList<JButton> botones) {

        super();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Nombre
        nombre = new JLabel();
        nombre.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        this.add(nombre, gbc);

        // Descripción
        desc = new JLabel();
        desc.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(desc, gbc);

        // Fecha
        fecha = new JLabel();
        fecha.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(fecha, gbc);

        // horario
        horario = new JLabel();
        horario.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        this.add(horario, gbc);

        // Monitor
        monitor = new JLabel();
        monitor.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        this.add(monitor, gbc);

        // Aforo Disponible
        aforoDisponible = new JLabel();
        aforoDisponible.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        this.add(aforoDisponible, gbc);

        // Número de reservas
        numReservas = new JLabel();
        numReservas.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        this.add(numReservas, gbc);

        // Aforo Total
        aforoTotal = new JLabel();
        aforoTotal.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        this.add(aforoTotal, gbc);

        // Botones
        this.botones = botones;
        if (botones.size() == 0) {}
        else if (botones.size() == 1) {
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(botones.get(0), gbc);
        } else {
            for (int i = 0; i < botones.size(); i++) {
                gbc.gridx = i%3;
                gbc.gridy = 3 + i/3;
                gbc.gridwidth = 1;
                this.add(botones.get(i), gbc);
            }
        }

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.setBackground(Color.WHITE);
    }


    public JLabel getNombre() {
        return nombre;
    }

    public JLabel getDesc() {
        return desc;
    }

    public JLabel getFecha() {
        return fecha;
    }

    public JLabel getHorario() {
        return horario;
    }

    public JLabel getMonitor() {
        return monitor;
    }

    public JLabel getAforoDisponible() {
        return aforoDisponible;
    }

    public JLabel getNumReservas() {
        return numReservas;
    }

    public JLabel getAforoTotal() {
        return aforoTotal;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setControlador(ControladorActividades controlador) {
        for (JButton boton : botones) {
            boton.addActionListener(controlador);
        }
    }
}
