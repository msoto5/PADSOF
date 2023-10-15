package controladores.principal.administrador;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import mainGUI.MainGUI;
import nanoGym.NanoGym;
import sala.*;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.Administrador;
import usuario.Monitor;
import usuario.Usuario;
import utiles.*;
import vistas.prinicipal.administrador.VistaCrearTareasAdministrador;

public class ControladorCrearTareasAdministrador implements ActionListener{

    private VistaCrearTareasAdministrador vista;
    private NanoGym modelo;
    private MainGUI ventana;

    private String nombreActividad, descripcion, horaInicio, horaFin, fechaActividad, idSala, nickMonitor;
    private Horario horario;
    private LocalDate fecha;
    private Sala sala;
    private Monitor moni;

    private Administrador admin;

    private int flag_libreGrupal = -1; /*Si == 0, libre, == 1, grupal */

    public ControladorCrearTareasAdministrador(VistaCrearTareasAdministrador vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(vista.getBotonEntrenamientoLibre())) {
            this.flag_libreGrupal = 0;
            vista.inicializarGeneralSalaLibre();
            this.actualizarSalas();
            vista.mostrarPanelSalas();
        } else if (event.getSource().equals(vista.getBotonActividadGrupal())) {
            this.flag_libreGrupal = 1;
            vista.inicializarGeneralSalaGrupal();
            this.actualizarSalas();
            vista.mostrarPanelSalas();
        } else if (event.getSource().equals(vista.getBotonSiguiente())) {
            if (this.getInfoSalas()) {
                this.actualizarMonitores();
                vista.inicializarMonitores();
                vista.mostrarPanelMonitores();
            }
        }  else if (event.getSource().equals(vista.getBotonCancelar())) {
            vista.mostrarPanelCrearTarea();
        }  else if (event.getSource().equals(vista.getBotonCrearTarea())) {
            admin = (Administrador)modelo.getCurrentUser();
            if (flag_libreGrupal == 0) {
                if (this.getInfoSalas()){
                    admin.crearEntrenaminetoLibre(nombreActividad, descripcion, horario, fecha, sala);
                    JOptionPane.showMessageDialog(vista, "Entrenamiento libre creado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    vista.mostrarPanelCrearTarea();
                }
            } else { /*Tengo que pillar monitor y ya creo actividad grupal */
                nickMonitor = vista.getNickMonitor().getText();
                String tipoActividad = vista.getTipoActividad().getText();
                TipoActividad ta = new TipoActividad(tipoActividad);

                if (modelo.getUsuarios().containsKey(nickMonitor)) {
                    if (modelo.getUsuarios().get(nickMonitor).esMonitor()) {
                        moni = (Monitor)modelo.getUsuarios().get(nickMonitor);
                    } else {
                        JOptionPane.showMessageDialog(vista, "El usuario con nick: " + nickMonitor + " no es un monitor", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No existe el monitor con nick: " + nickMonitor, "Error", JOptionPane.ERROR_MESSAGE);
                }

                admin.crearActividadGrupal(nombreActividad, descripcion, horario, fecha, sala, moni, ta);
                JOptionPane.showMessageDialog(vista, "Actividad grupal creada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                vista.mostrarPanelCrearTarea();
                modelo.generateBackUp();
            }
        } 
    }

    public void actualizarSalas() {

        HashMap<Integer, Sala> salas = modelo.getSalas();
        JPanel panelSalas = vista.getPanelSalas();
        panelSalas.removeAll();
        int i = 0;
        int j = 0;

        for (Map.Entry<Integer, Sala> entry : salas.entrySet()) {
            Sala sala = entry.getValue();
            if (sala.getHijos().size() == 0) {
                JPanel temp = new JPanel(new GridBagLayout());
                temp.setPreferredSize(new Dimension(350, 120));
                temp.setBorder(new LineBorder(Color.black));
                JLabel id = new JLabel("Sala con id: " + sala.getId());
                id.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel info = new JLabel("Nombre: " + sala.getNombre() + ". Aforo: " + sala.getAforo());
                JLabel desc = new JLabel(sala.getDescripcion());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(0, 0, 0, 0);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                temp.add(id, gbc);
                gbc.gridy = 1;
                temp.add(info, gbc);
                gbc.gridy = 2;
                temp.add(desc, gbc);
                if (sala.esClimatizada()) {
                    SalaClimatizada salac= (SalaClimatizada)sala;
                    JLabel climatizada = new JLabel("Sala climatizada en: " + salac.getHorario().toString());
                    gbc.gridy = 3;
                    temp.add(climatizada, gbc);
                    if (sala.getPadre() != null) {
                        JLabel padre = new JLabel("Sala padre: " + sala.getPadre().getNombre() + "(" + sala.getPadre().getId() + ")");
                        gbc.gridy = 4;
                        temp.add(padre, gbc);
                    }
                } else if (sala.getPadre() != null) {
                    JLabel padre = new JLabel("Sala padre: " + sala.getPadre().getNombre() + "(" + sala.getPadre().getId() + ")");
                    gbc.gridy = 3;
                    temp.add(padre, gbc);
                }
                gbc.gridy = i;
                gbc.gridx = j;
                gbc.insets = new Insets(0, 0, 10, 10);
                gbc.anchor = GridBagConstraints.CENTER;
                panelSalas.add(temp, gbc);
                if (j == 3) {
                    j = 0;
                    i++;
                } else {
                    j++;
                }
            }
        }
        panelSalas.revalidate(); //Para que actualize el JPanel
    }

    public void actualizarMonitores() {
        HashMap<String, Usuario> monitores = modelo.getUsuarios();
        JPanel panelMonitores = vista.getPanelMonitores();
        panelMonitores.removeAll();
        int i = 0;
        int j = 0;

        for (Map.Entry<String, Usuario> entry : monitores.entrySet()) {
            Usuario u = entry.getValue();
            Monitor monitor;
            if (!u.esMonitor()) {
                continue;
            } else {
                monitor = (Monitor)u;
            }
            if (!monitor.disponible(fecha, horario)) {
                continue;
            }
            JPanel temp = new JPanel(new GridBagLayout());
            temp.setPreferredSize(new Dimension(350, 120));
            temp.setBorder(new LineBorder(Color.black));
            JLabel id = new JLabel("Nick: " + monitor.getUsername());
            id.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel info = new JLabel("Nombre: " + monitor.getName());
            JLabel desc = new JLabel(monitor.getEmail());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            temp.add(id, gbc);
            gbc.gridy = 1;
            temp.add(info, gbc);
            gbc.gridy = 2;
            temp.add(desc, gbc);
            gbc.gridy = i;
            gbc.gridx = j;
            gbc.insets = new Insets(0, 0, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER;
            panelMonitores.add(temp, gbc);
            if (j == 3) {
                j = 0;
                i++;
            } else {
                j++;
            }
        }
        panelMonitores.revalidate(); //Para que actualize el JPanel
    }

    public boolean getInfoSalas() {
        this.nombreActividad = vista.getNombreActividad().getText();
        this.descripcion = vista.getDescripcion().getText();
        this.horaInicio = vista.getHoraInicio().getText();
        this.horaFin = vista.getHoraFin().getText();
        this.fechaActividad = vista.getFechaActividad().getText();
        this.idSala = vista.getIdSala().getText();
                
        if (nombreActividad.isEmpty() || descripcion.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || fechaActividad.isEmpty() || idSala.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "No puede haber campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {

            String tiempo[];
            LocalTime inicio = null;
            LocalTime fin = null;
            tiempo = horaInicio.split(" ");
            try {
                inicio = LocalTime.of(Integer.parseInt(tiempo[0]), Integer.parseInt(tiempo[1]));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Formato incorrecto hora de inicio", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            tiempo = horaFin.split(" ");
            try {
                fin = LocalTime.of(Integer.parseInt(tiempo[0]), Integer.parseInt(tiempo[1]));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Formato incorrecto hora de fin", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (inicio.compareTo(fin) >= 0) {
                JOptionPane.showMessageDialog(ventana, "La hora de inicio debe ser anterior a la hora de fin", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } 

            try {
                horario = new Horario(inicio, fin);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Horario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            tiempo = fechaActividad.split(" ");
            try {
                fecha = LocalDate.of(Integer.parseInt(tiempo[2]), Integer.parseInt(tiempo[1]), Integer.parseInt(tiempo[0]));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Formato incorrecto fecha", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (fecha.compareTo(LocalDate.now()) < 0) {
                JOptionPane.showMessageDialog(ventana, "La fecha debe ser posterior a la actual", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int id = -1;
            try {
                id = Integer.parseInt(idSala);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Formato incorrecto id sala", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
                    
            if (modelo.getSalas().containsKey(id)) {
                sala = modelo.getSalas().get(id);

                if (sala.ocupada(fecha, fin)) {
                    JOptionPane.showMessageDialog(ventana, "La sala esta ocupada en ese horario", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (sala.esClimatizada()) {
                    SalaClimatizada salac = (SalaClimatizada) sala;
                    if (!salac.getHorario().horarioSolapan(horario)) {
                        JOptionPane.showMessageDialog(ventana, "Horario fuera de hora de climatizacion de la sala", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            
            } else {
                JOptionPane.showMessageDialog(ventana, "No existe la sala", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
    
