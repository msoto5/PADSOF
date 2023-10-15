package vistas.inicio;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*Hacer clase padre abstracta(puede ser interfaz) para todas las vistas con: metodos limpiar, setControlador */

/**
 * Clase encargada de la interfaz grafica de registro 
 */
public class VistaRegistro extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel imagenLabel;
    private JPanel tarifa, datos;
    private JButton inicio_sesion;
    private JButton siguiente;
    private JTextField usuario;
    private JPasswordField pw;
    private JCheckBox ver_pw;
    //private JPasswordField pw_rep; SI HAY TIEMPO IMPLEMENTAR
    private JTextField nombre;
    private JTextField apellidos;
    private JTextField nacimiento;
    /*Para segundo panel*/
    private JTextField tarjeta;
    private JTextField caducidad;
    private JTextField cvv;
    private JButton registrarse;
    private JButton volver;
    private JRadioButton tarifa_plana;
    private JRadioButton tarifa_uso;
    private JLabel texto_tarifa;
    private JComboBox<String> actividades;
    private JRadioButton unMes;
    private JRadioButton tresMeses;
    private JRadioButton doceMeses;
    private JLabel duracion_plana;

    private static final String IMAGEN_LOGO = "./src/vistas/inicio/logo_nano.png";

    /*Ver como hacer transicción a pestaña de tarifa y pago */
    public VistaRegistro() {
        //asignar el layout

        CardLayout layoutPrincipal = new CardLayout();
        this.setLayout(layoutPrincipal);

        this.datos = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        this.datos.setLayout(layout);

        this.crearPanelTarifa();

        //crear componentes
        JLabel logo = new JLabel("NANO GYM");
        logo.setFont(new Font("Times New Roman", Font.BOLD, 28));

        JLabel etiqueta = new JLabel("REGISTRO DE USUARIO:");

        usuario = new JTextField(25);
        usuario.setText("Introduzca su usuario");
        usuario.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usuario.getText().equals("Introduzca su usuario")) {
                    usuario.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usuario.getText().equals("")) {
                    usuario.setText("Introduzca su usuario");
                }
            }
        });

        // Contraseña
        String texto_pw = "Introduzca su contraseña";
        pw = new JPasswordField(25);
        pw.setText(texto_pw);
        pw.setEchoChar((char)0);
        pw.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { //Muestra contraseña si pulso
                if (new String(pw.getPassword()).equals(texto_pw)) {
                    pw.setText("");
                    pw.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) { //Oculta contraseña si no pulso
                if (new String(pw.getPassword()).equals("")) {
                    pw.setText(texto_pw);
                    pw.setEchoChar((char)0);
                }
            }
        });

        // Ver contraseña
        ver_pw = new JCheckBox("Mostrar contraseña");
        ver_pw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ver_pw.isSelected()) {
                    pw.setEchoChar((char)0);
                } else if (!new String(pw.getPassword()).equals(texto_pw)) {
                    pw.setEchoChar('*');
                }
            }
        });

        // Nombre
        nombre = new JTextField(25);
        nombre.setText("Introduzca su nombre");
        nombre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nombre.getText().equals("Introduzca su nombre")) {
                    nombre.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nombre.getText().equals("")) {
                    nombre.setText("Introduzca su nombre");
                }
            }
            
        
        });

        apellidos = new JTextField(25);
        apellidos.setText("Introduzca sus apellidos");
        apellidos.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (apellidos.getText().equals("Introduzca sus apellidos")) {
                    apellidos.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (apellidos.getText().equals("")) {
                    apellidos.setText("Introduzca sus apellidos");
                }
            }
            
        
        });

        nacimiento = new JTextField(25);
        nacimiento.setText("DD MM YYYY");
        nacimiento.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nacimiento.getText().equals("DD MM YYYY")) {
                    nacimiento.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nacimiento.getText().equals("")) {
                    nacimiento.setText("DD MM YYYY");
                }
            }
            
        
        });

        siguiente = new JButton("Siguiente");
        inicio_sesion = new JButton("Iniciar sesion");

        Dimension tamano = usuario.getPreferredSize();
        // Imagen
        try {
            int add = 40;
            BufferedImage bufimagen = ImageIO.read(new File(IMAGEN_LOGO));
            int altura = (int) Math.round((double) (tamano.getWidth()+add)/bufimagen.getWidth() * bufimagen.getHeight());
            tamano.setSize(tamano.getWidth()+add, altura);
            
            Image imagen = ImageIO.read(new File(IMAGEN_LOGO)).getScaledInstance(tamano.width+add, altura, Image.SCALE_SMOOTH);
            imagenLabel = new JLabel(new ImageIcon(imagen));
        } catch (IOException e) {
            //System.err.println("Error de lectura de imagen");
            imagenLabel = new JLabel("Imagen. Error de lectura.");
        }

        //añadir componentes
        int y = 0;
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(imagenLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;   
        gbc.gridy = y; 
        gbc.gridwidth = 2;    
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;    
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(10, 0, 0, 0);
        this.datos.add(logo, gbc);
        y++;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;   
        gbc.gridy = y; 
        gbc.gridwidth = 2;    
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;    
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(10, 0, 0, 0);
        this.datos.add(etiqueta, gbc);
        y++;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(usuario, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(pw, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        y++;
        this.datos.add(ver_pw, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(nombre, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(apellidos, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        y++;
        this.datos.add(nacimiento, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 50, 0, 10);
        //y++;
        this.datos.add(siguiente, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 0, 0, 0);

        this.datos.add(inicio_sesion, gbc);

        this.add(this.datos, "panelDatos");
        this.add(this.tarifa, "panelTarifa");
    }

    public void setControlador(ActionListener controlador) {
        siguiente.addActionListener(controlador);
        inicio_sesion.addActionListener(controlador);
        tarifa_plana.addActionListener(controlador);
        tarifa_uso.addActionListener(controlador);
        unMes.addActionListener(controlador);
        tresMeses.addActionListener(controlador);
        doceMeses.addActionListener(controlador);
        registrarse.addActionListener(controlador);
        volver.addActionListener(controlador);
    }

    public JButton getBotonSiguiente() {
        return this.siguiente;
    }

    public JButton getBotonInicioSesion() {
        return this.inicio_sesion;
    }

    public JButton getBotonRegistrarse() {
        return this.registrarse;
    }

    public JButton getBotonVolver() {
        return this.volver;
    }

    public JRadioButton getBotonTarifaPlana() {
        return this.tarifa_plana;
    }

    public JRadioButton getBotonTarifaUso() {
        return this.tarifa_uso;
    }

    public JRadioButton getBotonTresMeses() {
        return this.tresMeses;
    }

    public JRadioButton getBotonunMes() {
        return this.unMes;
    }

    public JRadioButton getBotonDoceMeses() {
        return this.doceMeses;
    }

    public JComboBox<String> getActividades() {
        return this.actividades;
    }

    public JTextField getUsuario() {
        return this.usuario;
    }

    public JPasswordField getContrasena() {
        return this.pw;
    }

    public JTextField getNombre() {
        return this.nombre;
    }

    public JTextField getApellidos() {
        return this.apellidos;
    }

    public JTextField getNacimiento() {
        return this.nacimiento;
    }

    public JTextField getNumeroTarjeta() {
        return this.tarjeta;
    }

    public JTextField getFechaCaducidad() {
        return this.caducidad;
    }

    public JTextField getCVV() {
        return this.cvv;
    }

    public JLabel getTextoTarifa() {
        return this.texto_tarifa;
    }

    public JLabel getTextoDuracionPlana() {
        return this.duracion_plana;
    }

    public void crearPanelTarifa() {
        GridBagLayout layout = new GridBagLayout();

        this.tarifa = new JPanel();
        this.tarifa.setLayout(layout);

        JLabel logo = new JLabel("NANO GYM");
        logo.setFont(new Font("Times New Roman", Font.BOLD, 28));
        JLabel etiqueta = new JLabel("Seleccione su tarifa:");
        this.texto_tarifa = new JLabel("");
        JLabel texto_tarjeta = new JLabel("Introduzca los datos de su tarjeta:");

        this.tarifa_plana = new JRadioButton("TARIFA PLANA");

        this.tarifa_uso = new JRadioButton("TARIFA USO");

        ButtonGroup grupoTarifa = new ButtonGroup();

        grupoTarifa.add(this.tarifa_plana);
        grupoTarifa.add(this.tarifa_uso);

        this.duracion_plana = new JLabel("Seleccione la duracion de la tarifa plana:");
        this.duracion_plana.setVisible(false);

        this.unMes = new JRadioButton("1 mes");
        this.unMes.setVisible(false);
        this.tresMeses = new JRadioButton("3 meses");
        this.tresMeses.setVisible(false);
        this.doceMeses = new JRadioButton("12 meses");
        this.doceMeses.setVisible(false);

        ButtonGroup grupoDuracion = new ButtonGroup();

        grupoDuracion.add(this.unMes);
        grupoDuracion.add(this.tresMeses);
        grupoDuracion.add(this.doceMeses);

        String[] actividadesTemp = {"Seleccione Actividad", "Yoga", "Pilates", "Zumba", "Spinning", "Crossfit", "Natacion", "Boxeo", "Judo"};

        this.actividades = new JComboBox<String>(actividadesTemp);
        actividades.setVisible(false);

        this.tarjeta = new JTextField(25);
        this.tarjeta.setText("Introduzca su numero de tarjeta");            
        this.tarjeta.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tarjeta.getText().equals("Introduzca su numero de tarjeta")) {
                    tarjeta.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (tarjeta.getText().equals("")) {
                    tarjeta.setText("Introduzca su numero de tarjeta");
                }
            }
            
        
        });

        this.caducidad = new JTextField(10);
        this.caducidad.setText("DD MM YYYY");
        this.caducidad.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (caducidad.getText().equals("DD MM YYYY")) {
                    caducidad.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (caducidad.getText().equals("")) {
                    caducidad.setText("DD MM YYYY");
                }
            }
        });

        this.cvv = new JTextField(5);
        this.cvv.setText("CVV");
        this.cvv.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (cvv.getText().equals("CVV")) {
                    cvv.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (cvv.getText().equals("")) {
                    cvv.setText("CVV");
                }
            }
        });

        this.volver = new JButton("Volver");
        this.registrarse = new JButton("Registrarse");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(logo, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(etiqueta, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.tarifa_plana, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.tarifa_uso, gbc);

        // TEXTO QUE SE ACTUALIZA DEPENDIENDO DE QUE BOTON ESTA ACTIVO
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.LINE_START;

        this.tarifa.add(texto_tarifa, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(duracion_plana, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(unMes, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(tresMeses, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(doceMeses, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 15, 0);

        this.tarifa.add(actividades, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 0, 15, 0);

        this.tarifa.add(texto_tarjeta, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.tarjeta, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.caducidad, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.cvv, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.volver, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;

        this.tarifa.add(this.registrarse, gbc);
    }

    public void mostrarTarifa() {
        CardLayout cl = (CardLayout)(this.getLayout());
        cl.show(this, "panelTarifa");
    }

    public void mostrarPrincipal() {
        CardLayout cl = (CardLayout)(this.getLayout());
        cl.show(this, "panelDatos");
    }

    public void limpiar() {
        this.usuario.setText("Introduzca su usuario");
        this.pw.setText("Introduzca su contraseña");
        this.pw.setEchoChar((char)0);
        this.nombre.setText("Introduzca su nombre");
        this.apellidos.setText("Introduzca sus apellidos");
        this.nacimiento.setText("DD MM YYYY");
        this.tarjeta.setText("Introduzca su numero de tarjeta");
        this.caducidad.setText("DD MM YYYY");
        this.cvv.setText("CVV");
    }
}
