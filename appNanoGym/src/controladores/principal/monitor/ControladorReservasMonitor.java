package controladores.principal.monitor;


import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.*;

import javax.swing.JOptionPane;

import controladores.principal.ControladorReservas;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import servicio.actividad.actividadmonitor.EntrenamientoMonitor;
import usuario.Monitor;
import usuario.Usuario;
import utiles.Horario;
import vistas.prinicipal.monitor.VistaReservasMonitor;

public class ControladorReservasMonitor extends ControladorReservas {

    public ControladorReservasMonitor(VistaReservasMonitor vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        VistaReservasMonitor vista = (VistaReservasMonitor) this.vista;
        //System.out.println("ControladorReservasMonitor.actionPerformed");
        if (event.getSource().equals(vista.getBotonCrearEntPersClases())) {
            //System.out.println("Crear Entrenamiento Personalizado");
            //System.out.println("NumActMon: " + modelo.getServiciosActividadMonitor().size());
            vista.inicializarCrearEntPersClases((Monitor) modelo.getCurrentUser(), EntrenamientoPersonalizado.getObjetivosEp(), modelo.getServiciosActividadMonitor(), modelo.getSalasHojas());
            
            //System.out.println("NumActMon: " + modelo.getServiciosActividadMonitor().size());
        }
        else if (event.getSource().equals(vista.getBotonVolverCrearEntPersClases())) {
            vista.ocultarCrearEntPersClases();
            vista.mostrarClases();
            Usuario usuario = modelo.getCurrentUser();
            Monitor monitor = (Monitor) usuario;
            vista.addServicios(monitor.getServicios());
            modelo.generateBackUp();
        }
        else if (event.getSource().equals(vista.getBotonNuevoObj())) {
            String newObj = vista.getNewObjText().getText();
            if (!newObj.equals("") && !newObj.equals("Introduzca el objetivo nuevo")) {
                EntrenamientoPersonalizado.addObjetivo(newObj);
                vista.addObjetivo(newObj);
            }
        }
        else if (event.getSource().equals(vista.getBotonCrearEntPers())) {
            //System.out.println("Crear Entrenamiento Personalizado");
            String nombre = vista.getNombre().getText();
            if (nombre.equals("")) {
                JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descripcion = vista.getDescripcion().getText();
            if (descripcion.equals("")) {
                JOptionPane.showMessageDialog(vista, "La descripcion no puede estar vacía", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String objetivo = vista.getComboObjetivo().getSelectedItem().toString();

            String regex = "\\(\\d{8}[a-zA-Z]\\)";
            Pattern pattern = Pattern.compile(regex);
            String monitorNif = "";
            Matcher matcher = pattern.matcher(vista.getComboMonitor().getSelectedItem().toString());
            if (matcher.find()) {
                monitorNif = matcher.group().substring(1, 10);
            }
            else {
                monitorNif = vista.getComboMonitor().getSelectedItem().toString();
            }
            //System.out.println("Monitor NIF: " + monitorNif);

            if (vista.getActividadesSeleccionadas().size() <= 0) {
                JOptionPane.showMessageDialog(vista, "No puede crear un entrenamiento personalizado sin actividades. Añada al menos una actividad para poder crear el entrenamiento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean ret = modelo.crearEntrenamientoPersonalizado(nombre, descripcion, modelo.getMonitor(monitorNif), vista.getActividadesSeleccionadas(), objetivo);
            if (ret == false) {
                JOptionPane.showMessageDialog(vista, "No se ha podido crear el entrenamiento personalizado porque el Monitor está ocupado a esa hora", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(vista, "Entrenamiento personalizado creado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);

            vista.ocultarCrearEntPersClases();
            vista.mostrarClases();
            Usuario usuario = modelo.getCurrentUser();
            Monitor monitor = (Monitor) usuario;
            vista.addServicios(monitor.getServicios());
        }
        else if (event.getSource().equals(vista.getBotonAddActividad())) {
            System.out.println("Add Actividad");
            vista.procedimientoAddActividad();
        }
        else if (event.getSource().equals(vista.getBotonCrearEntrenoMoni())) {
            //System.out.println("Crear Entrenamiento Monitor");
            String nombre = vista.getNombreEntrenoMoni().getText();
            if (nombre.equals("") || nombre.equals("Nombre del entrenamiento")) {
                JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descripcion = vista.getDescEntrenoMoni().getText();
            if (descripcion.equals("") || descripcion.equals("Descripcion del entrenamiento")) {
                JOptionPane.showMessageDialog(vista, "La descripcion no puede estar vacía", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String regexHora = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

            String horaInicialStr = vista.getHoraInicial().getText();
            if (horaInicialStr.equals("") || horaInicialStr.equals("Hora inicial (hh:mm)") || !horaInicialStr.contains(":") || !horaInicialStr.matches(regexHora)) {
                JOptionPane.showMessageDialog(vista, "La hora inicial no es correcta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] horaInicalArr = horaInicialStr.split(":");
            LocalTime horaInicial = LocalTime.of(Integer.parseInt(horaInicalArr[0]), Integer.parseInt(horaInicalArr[1]));
            
            String horaFinalStr = vista.getHoraFinal().getText();
            if (horaFinalStr.equals("") || horaFinalStr.equals("Hora final (hh:mm)") || !horaFinalStr.contains(":") || !horaFinalStr.matches(regexHora)) {
                JOptionPane.showMessageDialog(vista, "La hora final no es correcta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] horaFinalArr = horaFinalStr.split(":");
            LocalTime horaFinal = LocalTime.of(Integer.parseInt(horaFinalArr[0]), Integer.parseInt(horaFinalArr[1]));

            if (horaInicial.isAfter(horaFinal)) {
                JOptionPane.showMessageDialog(vista, "La hora inicial no puede ser posterior a la hora final", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Horario horario = new Horario(horaInicial, horaFinal);

            String regexFecha = "^([0-2][0-9]|3[0-1])/(0[1-9]|1[012])/(\\d{4})$";
            String fechaStr = vista.getFechaEntrenoMoni().getText();
            if (fechaStr.equals("") || fechaStr.equals("Fecha (dd/mm/yyyy)") || !fechaStr.contains("/") || !fechaStr.matches(regexFecha)) {
                JOptionPane.showMessageDialog(vista, "La fecha no es correcta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] fechaArr = fechaStr.split("/");
            LocalDate fecha = LocalDate.of(Integer.parseInt(fechaArr[2]), Integer.parseInt(fechaArr[1]), Integer.parseInt(fechaArr[0]));
            
            if (vista.getComboSalas().getSelectedItem().equals("-- Selecciona una actividad --")) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una sala", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Sala sala = vista.getSalaSelectedEntrenoMoni();
            
            Monitor m = (Monitor) modelo.getCurrentUser();

            EntrenamientoMonitor em = modelo.crearEntrenamientoMonitor(nombre, descripcion, m, sala, fecha, horario);
            if (em == null) {
                JOptionPane.showMessageDialog(vista, "No se ha podido crear el entrenamiento porque el Monitor está ocupado a esa hora", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(vista, "Entrenamiento con Monitor creado con exito. Ya lo puedes añadir al entrenamiento personalizado", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }

            vista.getNombreEntrenoMoni().setText("Nombre del entrenamiento");
            vista.getDescEntrenoMoni().setText("Descripcion del entrenamiento");
            vista.getHoraInicial().setText("Hora inicial (hh:mm)");
            vista.getHoraFinal().setText("Hora final (hh:mm)");
            vista.getFechaEntrenoMoni().setText("Fecha (dd/mm/yyyy)");
            vista.getComboSalas().setSelectedIndex(0);

            vista.setActividades2ComboActividades(modelo.getServiciosActividadMonitor());
            vista.getComboActividades().setSelectedItem(vista.actM2String(em));
        }
    }
}
