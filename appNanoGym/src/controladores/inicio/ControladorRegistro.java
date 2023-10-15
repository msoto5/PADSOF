package controladores.inicio;

import java.awt.event.*;

import javax.swing.*;

import mainGUI.MainGUI;

import java.time.*;
import nanoGym.*;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.*;
import vistas.inicio.*;

public class ControladorRegistro implements ActionListener {
    private VistaRegistro vista;
    private NanoGym modelo;
    private MainGUI ventana;
    private String nick;
    private String pw;
    private String nombre;
    private String apellidos;
    private String nacimiento;
    private int anio;
    private int mes;
    private int dia;
    LocalDate fecha;
    /*variables de tarifa */
    private String tipo_tarifa = "";
    private String tarjeta;
    private String cvv;
    private int duracion_plana = 0;
    private String fecha_caducidad;
    private LocalDate caducidad;

    public ControladorRegistro(VistaRegistro vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.vista.getBotonSiguiente())) {
            /*Inicializo valores de registro */
            this.nick = vista.getUsuario().getText();
            this.pw = new String(vista.getContrasena().getPassword());
            this.nombre = vista.getNombre().getText();
            this.apellidos = vista.getApellidos().getText();
            this.nacimiento = vista.getNacimiento().getText();

            /*Valido valores de registro */
            if (nick.equals("Introduzca su usuario") || pw.equals("Introduzca su contraseña") || nombre.equals("Introduzca su nombre") || apellidos.equals("Introduzca sus apellidos") || nacimiento.equals("DD MM YYYY")) {
                JOptionPane.showMessageDialog(vista, "Debe introducir todos los parametros.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            /*Contraseña invalida */
            } else if(pw.length() < 5) {
                JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 5 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (modelo.getUsuarios().containsKey(nick)) {
                JOptionPane.showMessageDialog(vista, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            /*Convierto fecha a algo legible por funcion de registro */
            String[] fecha = nacimiento.split(" ");
            try {
                this.dia = Integer.parseInt(fecha[0]);
                this.mes = Integer.parseInt(fecha[1]);
                this.anio = Integer.parseInt(fecha[2]);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(vista, "La fecha debe tener el formato DD MM YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            /*Fecha invalida */
            try {
                this.fecha = LocalDate.of(this.anio, this.mes, this.dia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "La fecha es invalida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Cambio vista a tarifa y tarjeta
            vista.mostrarTarifa();

        } else if (event.getSource().equals(this.vista.getBotonInicioSesion())) {
            vista.limpiar();
            ventana.mostrarInicioSesion();
            return;
        //Parte tarifa
        } else if (event.getSource().equals(this.vista.getBotonVolver())) {
            /*Vuelvo a la vista prinical de registro */
            this.vista.mostrarPrincipal();
            return;
        } else if (event.getSource().equals(this.vista.getBotonTarifaPlana())) {
            this.vista.getTextoTarifa().setText("*Precio: " + TarifaPlana.getPrecioBase() + " .Reservas gratis actividad grupal elegida.");
            this.tipo_tarifa = "plana";
            this.duracion_plana = 0;
            this.vista.getActividades().setVisible(true);
            this.vista.getTextoDuracionPlana().setVisible(true);
            this.vista.getBotonunMes().setVisible(true);
            this.vista.getBotonTresMeses().setVisible(true);
            this.vista.getBotonDoceMeses().setVisible(true);
            //DAR OPCION DE ELEGIR ENTRE DISTINTOS TIPOS DE CLASE PARA TARIFA
            return;

        } else if (event.getSource().equals(this.vista.getBotonTarifaUso())) {
            this.vista.getTextoTarifa().setText("*Deberá pagar por todos los servicios");
            this.tipo_tarifa = "uso";
            this.duracion_plana = -1;
            this.vista.getActividades().setVisible(false);
            this.vista.getTextoDuracionPlana().setVisible(false);
            this.vista.getBotonunMes().setVisible(false);
            this.vista.getBotonTresMeses().setVisible(false);
            this.vista.getBotonDoceMeses().setVisible(false);
            return;

        }  else if (event.getSource().equals(this.vista.getBotonunMes())) {
            this.duracion_plana = 1;
            return;

        } else if (event.getSource().equals(this.vista.getBotonTresMeses())) {
            this.duracion_plana = 3;
            return;

        } else if (event.getSource().equals(this.vista.getBotonDoceMeses())) {
            this.duracion_plana = 12;
            return;

        } else if (event.getSource().equals(this.vista.getBotonRegistrarse())) {
            /*Inicializo valores de tarifa*/
            this.tarjeta = this.vista.getNumeroTarjeta().getText();
            this.cvv = this.vista.getCVV().getText();
            this.fecha_caducidad = this.vista.getFechaCaducidad().getText();
            if (tarjeta.equals("Introduzca su numero de tarjeta") || cvv.equals("CVV") || fecha_caducidad.equals("DD MM YYYY")) {
                JOptionPane.showMessageDialog(vista, "Debe introducir todos los parametros.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.tipo_tarifa.equals("")) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un tipo de tarifa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.tarjeta.length() != 16) {
                JOptionPane.showMessageDialog(vista, "Numero de tarjeta incorrecto(debe tener 16 numeros).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.cvv.length() != 3) {
                JOptionPane.showMessageDialog(vista, "Numero de CVV incorrecto(debe tener 3 numeros)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (this.duracion_plana == 0) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una duracion para la tarifa plana.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /*Convierto fecha a algo legible por funcion de registro */
            String[] fecha = fecha_caducidad.split(" ");
            try {
                this.dia = Integer.parseInt(fecha[0]);
                this.mes = Integer.parseInt(fecha[1]);
                this.anio = Integer.parseInt(fecha[2]);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(vista, "La fecha debe tener el formato DD MM YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            /*Fecha invalida */
            try {
                this.caducidad = LocalDate.of(this.anio, this.mes, this.dia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "La fecha es invalida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (this.caducidad.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(vista, "La tarjeta esta caducada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipo_tarifa.equals("plana") && this.vista.getActividades().getSelectedItem().equals("Seleccione Actividad")) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una actividad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TarjetaBancaria tb;
            try{
                tb = new TarjetaBancaria(tarjeta, caducidad, cvv);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "La tarjeta es invalida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Tarifa tarifa;
            if (tipo_tarifa.equals("uso")) {
                tarifa = new TarifaPagoUso();
            } else {
                tarifa = new TarifaPlana(duracion_plana, new TipoActividad(this.vista.getActividades().getSelectedItem().toString()));
            }

            nombre += " " + apellidos;
            //HAY QUE ACTUALIZAR MODELO
            try {
                this.modelo.aniadirCliente(nick, pw, nombre, this.fecha, tb, tarifa);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error al crear usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(vista, "Usuario registrado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            modelo.generateBackUp();
            vista.limpiar();
            ventana.mostrarInicioSesion();
        }
    }

}
