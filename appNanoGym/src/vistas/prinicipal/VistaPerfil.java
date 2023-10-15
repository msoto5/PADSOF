package vistas.prinicipal;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

//import controladores.principal.ControladorPerfil;

public abstract class VistaPerfil extends JPanel {

    protected JButton cerrarSesion;

    public VistaPerfil() {
        GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        JLabel label = new JLabel("Perfil");
        label.setFont(new Font("Times New Roman", Font.BOLD, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(label, gbc);
    }

    public abstract JButton getBotonCerrarSesion();

    public void setControlador(ActionListener controlador) {
        cerrarSesion.addActionListener(controlador);
    }

    //public abstract VistaPerfil getPerfil();
}
