package vistas.prinicipal.administrador;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import vistas.prinicipal.VistaPerfil;

public class VistaPerfilAdministrador extends VistaPerfil {


    /*Variables general */
    private JLabel nick;
    private JPasswordField pw;
    private JCheckBox verPw;
    private CardLayout cl = new CardLayout();
    private JPanel parametros, perfil, sala, monitor, general, panelSalas, generalSalas;
    private JButton botonParam, botonMonitor;

    /*Variables parametros */
    private JButton botonGuardar;
    private JTextField descuentoTarifaPlanaPersonalizado, descuentoTarifaPlanaLibre, precioActividadGrupal, precioActividadLibre, devolucionCancelacionCliente, devolucionCancelacionMonitor, maximoFaltas, duracionPenalizacion, precioTarifaPlana, sueldoBaseMonitores, sueldoExtraMonitores;
    private JLabel labelDescuentoTarifaPlanaPersonalizado, labelDescuentoTarifaPlanaLibre, labelPrecioActividadGrupal, labelPrecioActividadLibre, labelDevolucionCancelacionCliente, labelDevolucionCancelacionMonitor, labelMaximoFaltas, labelDuracionPenalizacion, labelPrecioTarifaPlana, labelSueldoBaseMonitores, labelSueldoExtraMonitores;

    /*Variables gestion gimnasio */
    private JLabel labelGastosMonitor, labelBeneficioActividades, labelBalance;

    /*Variables monitor */
    private JButton botonRegistrar, botonVolverMonitor;
    private JTextField nickMonitor, nombreMonitor, emailMonitor, nifMonitor;
    private JPasswordField contraseñaMonitor;
    private JLabel labelNickMonitor, labelContraseñaMonitor, labelNombreMonitor, labelEmailMonitor, labelNifMonitor; 
    private JCheckBox mostrarContraseñaCheckBox;

    /*Variables crear sala */
    private JButton botonCrearSala, botonVolverSala, botonRegistrarSala;
    private JTextField nombreSala, aforoSala, idSeleccion;
    private JTextArea descripcionSala;
    private JLabel labelNombreSala, labelAforoSala, labelDescripcionSala, labelIdSeleccion;
    private JCheckBox salaClimatizada;

    /* Variables Actividades */
    /*private JButton botonCrearActGru, botonCrearEntLib, botonVolverAct;
    private JTextField nombreActGru, nombreActLib;
    private JFormattedTextField aforoActGru, aforoEntLib;*/


    public VistaPerfilAdministrador() {
        super();

        // Crear paneles
        general = new JPanel();
        general.setLayout(cl);
        this.perfil = new JPanel();
        this.perfil.setLayout(new GridBagLayout());
        this.parametros = new JPanel();
        this.monitor = new JPanel();
        this.generalSalas = new JPanel();


        JLabel tituloValores = new JLabel("DATOS DEL ADMINISTRADOR:");
        tituloValores.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel tituloInformacion = new JLabel("GESTION DE GIMNASIO:");
        tituloInformacion.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel labelNick = new JLabel("Nick del administrador:");
        nick = new JLabel();

        JLabel labelPw = new JLabel("Contraseña:");
        pw = new JPasswordField(25);
        pw.setText("**********");
        pw.setEchoChar((char)0);
        pw.setEditable(false);

        verPw = new JCheckBox("Ver contraseña");
        verPw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verPw.isSelected()) {
                    pw.setEchoChar((char)0);
                } else {
                    pw.setEchoChar('*');
                }
            }
        });

        cerrarSesion = new JButton("Cerrar sesion");
        botonParam = new JButton("Modificar parametros");
        botonMonitor = new JButton("Registrar monitor");
        botonCrearSala = new JButton("Crear sala");

        labelGastosMonitor = new JLabel();
        labelBeneficioActividades = new JLabel();
        labelBalance = new JLabel();

        // Colorcar elementos en el panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        perfil.add(tituloValores, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 10, 0);
        perfil.add(labelNick, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        perfil.add(nick, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        perfil.add(labelPw, gbc);

        gbc.gridx = 1;
        perfil.add(pw, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        perfil.add(verPw, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        perfil.add(cerrarSesion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        perfil.add(tituloInformacion, gbc);

        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        perfil.add(labelGastosMonitor, gbc);

        gbc.gridx = 1;
        perfil.add(labelBeneficioActividades, gbc);

        gbc.gridx = 2;
        perfil.add(labelBalance, gbc);


        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        perfil.add(botonParam, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        perfil.add(botonMonitor, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        perfil.add(botonCrearSala, gbc);


        this.inicializarPanelParametros();
        this.inicializarPanelMonitor();
        this.inicializarPanelSala();

        general.add(parametros, "parametros");
        general.add(perfil, "perfil");
        general.add(monitor, "monitor");
        general.add(generalSalas, "sala");

        this.mostrarPanelPerfil();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        this.add(general, gbc);
    }

    @Override
    public void setControlador(ActionListener c) {
        cerrarSesion.addActionListener(c);
        botonParam.addActionListener(c);
        botonMonitor.addActionListener(c);
        botonGuardar.addActionListener(c);
        botonRegistrar.addActionListener(c);
        botonVolverMonitor.addActionListener(c);
        botonCrearSala.addActionListener(c);
        botonVolverMonitor.addActionListener(c);
        salaClimatizada.addActionListener(c);
        botonVolverSala.addActionListener(c);
        botonRegistrarSala.addActionListener(c);
    }

    @Override
    public JButton getBotonCerrarSesion() {
        return cerrarSesion;
    }

    public JButton getBotonParametros() {
        return botonParam;
    }

    public JButton getBotonMonitor() {
        return botonMonitor;
    }

    public JButton getBotonGuardar() {
        return botonGuardar;
    }

    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }

    public JButton getBotonCrearSala() {
        return botonCrearSala;
    }

    public JButton getBotonVolverMonitor() {
        return botonVolverMonitor;
    }

    public JButton getBotonVolverSala() {
        return botonVolverSala;
    }

    public JButton getBotonRegistrarSala() {
        return botonRegistrarSala;
    }
    
    public JLabel getLabelNick() {
        return nick;
    }

    public JLabel getLabelGastosMonitor() {
        return labelGastosMonitor;
    }

    public JLabel getLabelBeneficioActividades() {
        return labelBeneficioActividades;
    }

    public JLabel getLabelBalance() {
        return labelBalance;
    }

    public JPasswordField getPw() {
        return pw;
    }

    public JTextField getDescuentoTarifaPlanaPersonalizado() {
        return descuentoTarifaPlanaPersonalizado;
    }

    public JTextField getDescuentoTarifaPlanaLibre() {
        return descuentoTarifaPlanaLibre;
    }

    public JTextField getPrecioActividadGrupal() {
        return precioActividadGrupal;
    }

    public JTextField getPrecioActividadLibre() {
        return precioActividadLibre;
    }

    public JTextField getDevolucionCancelacionCliente() {
        return devolucionCancelacionCliente;
    }

    public JTextField getDevolucionCancelacionMonitor() {
        return devolucionCancelacionMonitor;
    }

    public JTextField getMaximoFaltas() {
        return maximoFaltas;
    }

    public JTextField getDuracionPenalizacion() {
        return duracionPenalizacion;
    }

    public JTextField getPrecioTarifaPlana() {
        return precioTarifaPlana;
    }

    public JTextField getSueldoBaseMonitores() {
        return sueldoBaseMonitores;
    }

    public JTextField getSueldoExtraMonitores() {
        return sueldoExtraMonitores;
    }

    public JTextField getNickMonitor() {
        return nickMonitor;
    }

    public JTextField getNombreMonitor() {
        return nombreMonitor;
    }

    public JPasswordField getContraseñaMonitor() {
        return contraseñaMonitor;
    }

    public JTextField getNIFMonitor() {
        return nifMonitor;
    }

    public JTextField getEmailMonitor() {
        return emailMonitor;
    }

    public JTextField getNombreSala() {
        return nombreSala;
    }

    public JTextField getAforoSala() {
        return aforoSala;
    }

    public JTextField getIdSala() {
        return this.idSeleccion;
    }

    public JTextArea getDescripcionSala() {
        return descripcionSala;
    }

    public JCheckBox getClimatizacion() {
        return salaClimatizada;
    }

    public JPanel getPanelSalas() {
        return panelSalas;
    }

    public void setPw(String pw) {
        this.pw.setEditable(true);
        this.pw.setText(pw);
        this.pw.setEchoChar('*');
        this.pw.setEditable(false);
    }

    public void mostrarPanelParametros() {
        cl.show(general, "parametros");
    }

    public void mostrarPanelPerfil() {
        cl.show(general, "perfil");
    }

    public void mostrarPanelMonitor() {
        cl.show(general, "monitor");
    }

    public void mostrarPanelSala() {
        cl.show(general, "sala");
    }

    public void inicializarPanelParametros() {
        
        this.parametros.setLayout(new GridBagLayout());

        this.labelDescuentoTarifaPlanaPersonalizado = new JLabel("Descuento de la tarifa plana entrenamiento personalizado:");
        this.labelDescuentoTarifaPlanaLibre = new JLabel("Descuento de la tarifa plana entrenamiento libre:");
        this.labelPrecioActividadGrupal = new JLabel("Precio de la actividad grupal:");
        this.labelPrecioActividadLibre = new JLabel("Precio de la actividad libre:");
        this.labelDevolucionCancelacionCliente = new JLabel("Devolucion por cancelacion del cliente:");
        this.labelDevolucionCancelacionMonitor = new JLabel("Devolucion por cancelacion del monitor:");
        this.labelMaximoFaltas = new JLabel("Maximo de faltas:");
        this.labelDuracionPenalizacion = new JLabel("Duracion de la penalizacion:");
        this.labelPrecioTarifaPlana = new JLabel("Precio de la tarifa plana:");
        this.labelSueldoBaseMonitores = new JLabel("Sueldo base de los monitores:");
        this.labelSueldoExtraMonitores = new JLabel("Sueldo extra de los monitores:");

        this.descuentoTarifaPlanaPersonalizado = new JTextField(12);
        descuentoTarifaPlanaPersonalizado.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descuentoTarifaPlanaPersonalizado.getText().contains("Valor previo:")) {
                    descuentoTarifaPlanaPersonalizado.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.descuentoTarifaPlanaLibre = new JTextField(12);
        descuentoTarifaPlanaLibre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descuentoTarifaPlanaLibre.getText().contains("Valor previo:")) {
                    descuentoTarifaPlanaLibre.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.precioActividadGrupal = new JTextField(12);
        precioActividadGrupal.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (precioActividadGrupal.getText().contains("Valor previo:")) {
                    precioActividadGrupal.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.precioActividadLibre = new JTextField(12);
        precioActividadLibre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (precioActividadLibre.getText().contains("Valor previo:")) {
                    precioActividadLibre.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.devolucionCancelacionCliente = new JTextField(12);
        devolucionCancelacionCliente.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (devolucionCancelacionCliente.getText().contains("Valor previo:")) {
                    devolucionCancelacionCliente.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.devolucionCancelacionMonitor = new JTextField(12);
        devolucionCancelacionMonitor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (devolucionCancelacionMonitor.getText().contains("Valor previo:")) {
                    devolucionCancelacionMonitor.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.maximoFaltas = new JTextField(12);
        maximoFaltas.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (maximoFaltas.getText().contains("Valor previo:")) {
                    maximoFaltas.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.duracionPenalizacion = new JTextField(12);
        duracionPenalizacion.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (duracionPenalizacion.getText().contains("Valor previo:")) {
                    duracionPenalizacion.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.precioTarifaPlana = new JTextField(12);
        precioTarifaPlana.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (precioTarifaPlana.getText().contains("Valor previo:")) {
                    precioTarifaPlana.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.sueldoBaseMonitores = new JTextField(12);
        sueldoBaseMonitores.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sueldoBaseMonitores.getText().contains("Valor previo:")) {
                    sueldoBaseMonitores.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        this.sueldoExtraMonitores = new JTextField(12);
        sueldoExtraMonitores.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sueldoExtraMonitores.getText().contains("Valor previo:")) {
                    sueldoExtraMonitores.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        this.botonGuardar = new JButton("Guardar");

        // Colorcar elementos en el panel
        this.labelDescuentoTarifaPlanaLibre.setForeground(Color.BLUE);
        this.labelDescuentoTarifaPlanaPersonalizado.setForeground(Color.BLUE);
        this.labelPrecioActividadGrupal.setForeground(Color.BLUE);
        this.labelPrecioActividadLibre.setForeground(Color.BLUE);
        this.labelDevolucionCancelacionCliente.setForeground(Color.BLUE);
        this.labelDevolucionCancelacionMonitor.setForeground(Color.BLUE);
        this.labelMaximoFaltas.setForeground(Color.BLUE);
        this.labelDuracionPenalizacion.setForeground(Color.BLUE);
        this.labelPrecioTarifaPlana.setForeground(Color.BLUE);
        this.labelSueldoBaseMonitores.setForeground(Color.BLUE);
        this.labelSueldoExtraMonitores.setForeground(Color.BLUE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.parametros.add(new JLabel("PARAMETROS A MODIFICAR:"), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.parametros.add(this.labelDescuentoTarifaPlanaLibre, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.descuentoTarifaPlanaLibre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.parametros.add(this.labelDescuentoTarifaPlanaPersonalizado, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.descuentoTarifaPlanaPersonalizado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.parametros.add(this.labelPrecioActividadGrupal, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.precioActividadGrupal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.parametros.add(this.labelPrecioActividadLibre, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.precioActividadLibre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.parametros.add(this.labelDevolucionCancelacionCliente, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.devolucionCancelacionCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.parametros.add(this.labelDevolucionCancelacionMonitor, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.devolucionCancelacionMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.parametros.add(this.labelMaximoFaltas, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.maximoFaltas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.parametros.add(this.labelDuracionPenalizacion, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.duracionPenalizacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        this.parametros.add(this.labelPrecioTarifaPlana, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.precioTarifaPlana, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        this.parametros.add(this.labelSueldoBaseMonitores, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.sueldoBaseMonitores, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        this.parametros.add(this.labelSueldoExtraMonitores, gbc);

        gbc.gridx = 1;
        this.parametros.add(this.sueldoExtraMonitores, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        this.parametros.add(this.botonGuardar, gbc);


    }

    public void inicializarPanelMonitor() {
        
        this.monitor.setLayout(new GridBagLayout());

        this.generalSalas = new JPanel(new BorderLayout());

        this.botonRegistrar = new JButton("Registrar monitor");
        this.botonVolverMonitor = new JButton("Volver");
        //this.monitor.setBorder(BorderFactory.createTitledBorder("Monitor"));
		//Monitor(nick, contraseña, nombre, email, nif);

        this.labelNickMonitor = new JLabel("Nick:");
        this.labelNombreMonitor = new JLabel("Nombre:");
        this.labelEmailMonitor = new JLabel("Email:");
        this.labelNifMonitor = new JLabel("NIF:");
        this.labelContraseñaMonitor = new JLabel("Contraseña:");

        contraseñaMonitor = new JPasswordField(20);
        pw.setEchoChar('*');

        // CheckBox contraseña
        mostrarContraseñaCheckBox = new JCheckBox("Mostrar contraseña");
        mostrarContraseñaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrarContraseñaCheckBox.isSelected()) {
                    pw.setEchoChar((char)0);
                } else {
                    pw.setEchoChar('*');
                }
            }
        });

        this.nickMonitor = new JTextField(20);
        this.nombreMonitor = new JTextField(20);
        this.emailMonitor = new JTextField(20);
        this.nifMonitor = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.monitor.add(new JLabel("INFORMACION MONITOR"), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.monitor.add(this.labelNickMonitor, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.nickMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.monitor.add(this.labelContraseñaMonitor, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.contraseñaMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.monitor.add(this.labelNombreMonitor, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.nombreMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.monitor.add(this.labelEmailMonitor, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.emailMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.monitor.add(this.labelNifMonitor, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.nifMonitor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        this.monitor.add(this.botonRegistrar, gbc);

        gbc.gridx = 1;
        this.monitor.add(this.botonVolverMonitor, gbc);
    }

    public void inicializarPanelSala() {

        this.sala = new JPanel(new GridBagLayout());
        this.generalSalas.setLayout(new BorderLayout());
        this.panelSalas = new JPanel(new GridBagLayout());

        this.botonVolverSala = new JButton("Volver");
        this.botonRegistrarSala = new JButton("Registrar sala");
        this.salaClimatizada = new JCheckBox("Desea que la sala esté climatizada?");
        this.labelNombreSala = new JLabel("Nombre de sala");
        this.labelAforoSala = new JLabel("Aforo de sala");
        this.labelDescripcionSala = new JLabel("Descricion de sala");
        this.labelIdSeleccion = new JLabel("Introduzca el id de la sala que desea seleccionar");

        this.nombreSala = new JTextField(20);
        this.aforoSala = new JTextField(20);
        this.idSeleccion = new JTextField(5);
        this.descripcionSala = new JTextArea();
        this.descripcionSala.setPreferredSize(new Dimension(225, 50));
        this.descripcionSala.setBorder(new LineBorder(Color.GRAY));
        this.descripcionSala.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.sala.add(new JLabel("INFORMACION SALA"), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.sala.add(this.labelNombreSala, gbc);

        gbc.gridx = 1;
        this.sala.add(this.nombreSala, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.sala.add(this.labelAforoSala, gbc);

        gbc.gridx = 1;
        this.sala.add(this.aforoSala, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.sala.add(this.labelDescripcionSala, gbc);

        gbc.gridx = 1;
        this.sala.add(this.descripcionSala, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        this.sala.add(this.salaClimatizada, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.sala.add(labelIdSeleccion, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.sala.add(idSeleccion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 50, 0, 0);
        this.sala.add(botonRegistrarSala, gbc);

        gbc.gridx = 1;
        this.sala.add(this.botonVolverSala, gbc);
        
        this.generalSalas.add(this.sala, BorderLayout.NORTH);
        this.generalSalas.add(this.panelSalas, BorderLayout.SOUTH);
    }
}
