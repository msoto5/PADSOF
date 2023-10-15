package vistas.prinicipal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controladores.principal.ControladorNotificaciones;

public class VistaNotificaciones extends JPanel {
    protected JButton marcarLeidas;
    protected JLabel title;
    protected JLabel labelSinNot;
    protected List<JLabel> notificaciones = new ArrayList<JLabel>();

    protected int numNot = 2;
    
    public VistaNotificaciones() {

        GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        title = new JLabel("Notificaciones");
        title.setFont(new Font("Times New Roman", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //this.add(title);

        labelSinNot = new JLabel("No tienes nuevas notificaciones en este momento");
        labelSinNot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        marcarLeidas = new JButton("Marcar como leidas");

        // Añadir elementos al panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 2;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(title, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(marcarLeidas, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 20, 10, 10);
        this.add(labelSinNot, gbc);
    }

    public void setControlador(ControladorNotificaciones controladorNotificacionesCliente) {
        marcarLeidas.addActionListener(controladorNotificacionesCliente);
    }

    public void addNotificacion(String not) {
        JLabel notLabel = new JLabel(not);
        notLabel.setOpaque(true);

        // Ajusta el color de fondo y la opacidad
        notLabel.setBackground(new Color(230, 230, 230, 200)); 
        //notLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajusta el color del borde y el grosor
        notLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // Ajusta el tamaño del borde redondeado
        notLabel.setBorder(BorderFactory.createCompoundBorder(notLabel.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        notificaciones.add(notLabel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = numNot;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 10, 0, 10);

        labelSinNot.setVisible(false);

        this.add(notLabel, gbc);
        this.revalidate();
        this.repaint();
        numNot++;
    }

    // Eliminar las notificaciones, todo menos el titulo
    public void clearNotificaciones() {
        for (JLabel label : notificaciones) {
            this.remove(label);
        }
        this.labelSinNot.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    public JButton getBoton() {
        return this.marcarLeidas;
    }
}
