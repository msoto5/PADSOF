package vistas.inicio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VistaInicioSesion extends JPanel {
    private JTextField usuario;
    private JButton iniciar_sesion;
    private JPasswordField pw;
    private JButton registrarse;
    private JCheckBox mostrarContraseñaCheckBox;
    private JLabel imagenLabel;

    private static final String IMAGEN_LOGO = "./src/vistas/inicio/logo_nano.png";
    //private ImagenDeFondoPanel fondo;

    public VistaInicioSesion() {
        //asignar el layout
        GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

        //crear componentes 
        JLabel logo = new JLabel("NANO GYM");
        logo.setFont(new Font("Times New Roman", Font.BOLD, 28));

        //ImageIcon imagen = new ImageIcon("./logo.png"); //ARREGLAR ESTO
        //logo.setIcon(imagen);
        JLabel etiqueta = new JLabel("INICIO DE SESION:");

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
            imagenLabel = new JLabel("Imagen. Error de lectura.");
        }
        
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
            public void focusLost(FocusEvent e) { //Deja de mostrar texto si salgo
                if (new String(pw.getPassword()).equals("")) {
                    pw.setText(texto_pw);
                    pw.setEchoChar((char)0);
                }
            }        
        });

        // CheckBox contraseña
        mostrarContraseñaCheckBox = new JCheckBox("Mostrar contraseña");
        mostrarContraseñaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrarContraseñaCheckBox.isSelected()) {
                    pw.setEchoChar((char)0);
                } else if (!new String(pw.getPassword()).equals(texto_pw)) {
                    pw.setEchoChar('*');
                }
            }
        });

        // Botones de inicio de sesion y registro
        iniciar_sesion = new JButton("Iniciar Sesion");
        registrarse = new JButton("Registrarse");

        // Añadir componentes al panel
        GridBagConstraints gbc = new GridBagConstraints();

        // Insertar imagen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);

        this.add(imagenLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;   
        gbc.gridy = 1; 
        gbc.gridwidth = 2;    
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;    
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(10, 0, 0, 0);

        this.add(logo, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;   
        gbc.gridy = 2; 
        gbc.gridwidth = 2;    
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;    
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(10, 0, 0, 0); 

        this.add(etiqueta, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;   
        gbc.gridy = 3;  
        gbc.gridwidth = 2;   
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;   
        gbc.anchor = GridBagConstraints.CENTER;   
        gbc.insets = new Insets(10, 0, 0, 0); 

        this.add(usuario, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;    
        gbc.gridy = 4;    
        gbc.gridwidth = 2;    
        gbc.gridheight = 1;  
        gbc.fill = GridBagConstraints.CENTER;   
        gbc.anchor = GridBagConstraints.CENTER;   
        gbc.insets = new Insets(10, 0, 0, 0);

        this.add(pw, gbc);

        // Mostrar Contraseña
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        this.add(mostrarContraseñaCheckBox, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;    // Columna 0
        gbc.gridy = 6;    // Fila 5
        gbc.gridwidth = 1;  // Ocupa 2 columnas    
        gbc.gridheight = 1;   
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.LINE_END;  
        gbc.insets = new Insets(10, 50, 0, 10); 

        this.add(iniciar_sesion, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;                              // Columna 0
        gbc.gridy = 6;                              // FILA 3
        gbc.gridwidth = 1;                          //ANCHO CASILLA
        gbc.gridheight = 1;                         //ALTO CASILLA
        gbc.fill = GridBagConstraints.CENTER;       //FILL MUEVE POSICION Y
        gbc.anchor = GridBagConstraints.LINE_START; //ANCHOR MUEVE LA POSICION X
        gbc.insets = new Insets(10, 0, 0, 0);       //MARGEN ARRIBA, IZQUIERDA, ABAJO, DERECHA

        this.add(registrarse, gbc);

        //this.setBackground(new Color(255, 255, 255, 128));
        iniciar_sesion.requestFocusInWindow();
        /*
        Image imagen;
        try {
            imagen = ImageIO.read(new File(IMAGEN_LOGO));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        */
    }

    public void setControlador(ActionListener controlador){
		this.iniciar_sesion.addActionListener(controlador);
		this.registrarse.addActionListener(controlador);
	}

    /*Mas util enviarlo como objeto por si esta mal poder resetear texto */
    public JTextField getUsuario(){
        return this.usuario;
    }

    public JPasswordField getContrasena(){
        return this.pw;
    }

    public JButton getBotonInicioSesion() {
        return this.iniciar_sesion;
    }

    public JButton getBotonRegistrarse() {
        return this.registrarse;
    }

    public void limpiar() {
        this.usuario.setText("Introduzca su usuario");
        this.pw.setText("Introduzca su contraseña");
        this.pw.setEchoChar((char)0);
    }
    /*
    public void setImagenFondo() {
        try {
            Image imagen = ImageIO.read(new File(IMAGEN_LOGO));
            this.fondo.setImagen(imagen);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    */
}
