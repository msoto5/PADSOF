package vistas.prinicipal.cliente;

import java.awt.*;
//import java.awt.event.ActionListener;

import javax.swing.*;

//import controladores.principal.ControladorPerfil;
import vistas.prinicipal.VistaPerfil;

public class VistaPerfilCliente extends VistaPerfil {
    //private JButton cerrarSesion;
    /*Variables para datos cliente (relacionados con el gimnasio) */
    private JLabel gastos;
    private JLabel faltas;

    /*Variables para informacion del cliente*/
    private JLabel nick;
    private JLabel nombre;
    private JLabel fechaNacimiento;
    private JLabel tarifa;
    private JLabel tarjeta;


    public VistaPerfilCliente() {

        /*GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        JLabel label = new JLabel("Perfil");
        label.setFont(new Font("Times New Roman", Font.BOLD, 28));*/

        JLabel tituloValores = new JLabel("DATOS DEL CLIENTE:");
        tituloValores.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel tituloInformacion = new JLabel("INFORMACION DEL CLIENTE:");
        tituloInformacion.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel labelGastos = new JLabel("Gastos del cliente:");
        gastos = new JLabel();

        JLabel labelFaltas = new JLabel("Numero de faltas:");
        faltas = new JLabel();

        JLabel labelNick = new JLabel("Nick del cliente:");
        nick = new JLabel();

        JLabel labelNombre = new JLabel("Nombre del cliente:");
        nombre = new JLabel();

        JLabel labelFechaNacimiento = new JLabel("Fecha de nacimiento del cliente:");
        fechaNacimiento = new JLabel();

        JLabel labelTarifa = new JLabel("Tarifa del cliente:");
        tarifa = new JLabel();

        JLabel labelTarjeta = new JLabel("Tarjeta del cliente:");
        tarjeta = new JLabel();

        cerrarSesion = new JButton("Cerrar sesion");

        /*GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(label, gbc);*/

        //DEJAR ESPACIO PARA METER LOGO PERFIL

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(tituloValores, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.WEST;

        this.add(labelGastos, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.WEST;

        this.add(gastos, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelFaltas, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0); 

        this.add(faltas, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(tituloInformacion, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.WEST;

        this.add(labelNick, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(nick, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelNombre, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(nombre, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelFechaNacimiento, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(fechaNacimiento, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelTarifa, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(tarifa, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelTarjeta, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0);

        this.add(tarjeta, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(cerrarSesion, gbc);

    }

    /*public void setControlador(ActionListener controlador) {
        cerrarSesion.addActionListener(controlador);
        
    }*/

    public JButton getBotonCerrarSesion() {
        return cerrarSesion;
    }

    public JLabel getLabelGastos() {
        return gastos;
    }

    public JLabel getLabelFaltas() {
        return faltas;
    }

    public JLabel getLabelNick() {
        return nick;
    }

    public JLabel getLabelNombre() {
        return nombre;
    }

    public JLabel getLabelFechaNacimiento() {
        return fechaNacimiento;
    }

    public JLabel getLabelTarifa() {
        return tarifa;
    }

    public JLabel getLabelTarjeta() {
        return tarjeta;
    }
    
}
