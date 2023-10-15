package vistas.prinicipal.monitor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//import org.bouncycastle.mime.MimeWriter;

import vistas.prinicipal.VistaPerfil;

public class VistaPerfilMonitor extends VistaPerfil {
    //private JButton cerrarSesion;
    private JButton descargarNomina;
    /*Variables para datos monitor (relacionados con el gimnasio) */
    private JLabel nominaBase;
    private JLabel extraPorHora;
    private JLabel horasTrabajadas;

    /*Variables para informacion del monitor*/
    private JLabel nick;
    private JLabel nombre;
    private JLabel email;
    private JLabel nif;

    private JTextField mesNomina;
    private JTextField anyoNomina;

    public VistaPerfilMonitor() {

        /*GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        JLabel label = new JLabel("Perfil");
        label.setFont(new Font("Times New Roman", Font.BOLD, 28));*/

        JLabel tituloValores = new JLabel("DATOS DEL MONITOR:");
        tituloValores.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel tituloInformacion = new JLabel("INFORMACION DEL MONITOR:");
        tituloInformacion.setFont(new Font("Times New Roman", Font.BOLD, 15));

        mesNomina = new JTextField(25);
        mesNomina.setText("Mes (MM)");
        mesNomina.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mesNomina.getText().equals("Mes (MM)")) {
                    mesNomina.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mesNomina.getText().equals("")) {
                    mesNomina.setText("Mes (MM)");
                }
            }
        });

        anyoNomina = new JTextField(25);
        anyoNomina.setText("Año (YYYY)");
        anyoNomina.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (anyoNomina.getText().equals("Año (YYYY)")) {
                    anyoNomina.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (anyoNomina.getText().equals("")) {
                    anyoNomina.setText("Año (YYYY)");
                }
            }
        });

        JLabel labelNominaBase = new JLabel("Nomina base:");
        nominaBase = new JLabel();

        JLabel labelExtra = new JLabel("Nomina extra por hora:");
        extraPorHora = new JLabel();

        JLabel labelHorasTrabajadas = new JLabel("Horas trabajadas:");
        horasTrabajadas = new JLabel();

        JLabel labelNick = new JLabel("Nick:");
        nick = new JLabel();

        JLabel labelNombre = new JLabel("Nombre:");
        nombre = new JLabel();

        JLabel labelCorreo = new JLabel("Correo:");
        email = new JLabel();

        JLabel labelNIF = new JLabel("NIF:");
        nif = new JLabel();

        cerrarSesion = new JButton("Cerrar sesion");
        descargarNomina = new JButton("Descargar nomina");

        //Añado label de perfil
        /*GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(label, gbc);*/

        //DEJAR ESPACIO PARA METER LOGO PERFIL

        //Añado label de titulo de datos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(tituloValores, gbc);

        //Añado label de nomina base
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelNominaBase, gbc);

        //Añado label de hueco para escribir nomina base
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(nominaBase, gbc);

        //Añado label de nomina extra por hora
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelExtra, gbc);

        //Añado label de hueco para escribir nomina extra por hora
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.CENTER; 

        this.add(extraPorHora, gbc);

        //Añado label de horas trabajadas
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0);

        this.add(labelHorasTrabajadas, gbc);

        //Añado label de hueco para escribir horas trabajadas
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);

        this.add(horasTrabajadas, gbc);

        //Añado label de titulo de informacion
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(tituloInformacion, gbc);

        //Añado label de nick
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.WEST;

        this.add(labelNick, gbc);

        //Añado label de hueco para escribir nick
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(nick, gbc);

        //Añado label de nombre
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelNombre, gbc);

        //Añado label de hueco para escribir nombre
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(nombre, gbc);

        //Añado label de correo
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(labelCorreo, gbc);

        //Añado label de hueco para escribir correo
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(email, gbc);

        //Añado label de NIF
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0);

        this.add(labelNIF, gbc);

        //Añado label de hueco para escribir NIF
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0);

        this.add(nif, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(descargarNomina, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(mesNomina, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.LINE_START;

        this.add(anyoNomina, gbc);

        //Añado boton de cerrar sesion
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;

        this.add(cerrarSesion, gbc);

    }

    @Override
    public void setControlador(ActionListener controlador) {
        super.setControlador(controlador);
        descargarNomina.addActionListener(controlador);
    }

    public JButton getBotonCerrarSesion() {
        return cerrarSesion;
    }

    public JButton getBotonDescargarNomina() {
        return descargarNomina;
    }

    public JLabel getLabelNominaBase() {
        return nominaBase;
    }

    public JLabel getLabelExtraPorHora() {
        return extraPorHora;
    }

    public JLabel getLabelHorasTrabajadas() {
        return horasTrabajadas;
    }

    public JLabel getLabelNick() {
        return nick;
    }

    public JLabel getLabelNombre() {
        return nombre;
    }

    public JLabel getLabelEmail() {
        return email;
    }

    public JLabel getLabelNIF() {
        return nif;
    }

    public JTextField getMesNomina() {
        return mesNomina;
    }

    public JTextField getAnyoNomina() {
        return anyoNomina;
    }
    
}
