package controladores.principal.administrador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import controladores.principal.ControladorPerfil;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import sala.Sala;
import sala.SalaClimatizada;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import usuario.Administrador;
import usuario.cliente.Cliente;
import usuario.cliente.Suspension;
import usuario.cliente.tarifa.TarifaPlana;
import vistas.prinicipal.administrador.VistaPerfilAdministrador;
import usuario.Monitor;

public class ControladorPerfilAdministrador extends ControladorPerfil {
    private String nombre, nick, pw, email, nif;
    
    public ControladorPerfilAdministrador(VistaPerfilAdministrador vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        VistaPerfilAdministrador vista = (VistaPerfilAdministrador)this.vista;
        Administrador admin = (Administrador)modelo.getCurrentUser();

        if (event.getSource().equals(this.vista.getBotonCerrarSesion())) {
            modelo.logOut();
            ventana.mostrarInicioSesion();
        } else if (event.getSource().equals(vista.getBotonParametros())) {
            vista.getDescuentoTarifaPlanaLibre().setText("Valor previo:" + Double.toString((TarifaPlana.getDescEntLibre())));
            vista.getDescuentoTarifaPlanaPersonalizado().setText("Valor previo:" + Double.toString((TarifaPlana.getDescEntPersonalizado())));
            vista.getPrecioActividadGrupal().setText("Valor previo:" + Double.toString((ActividadGrupal.getPRECIO())));
            vista.getPrecioActividadLibre().setText("Valor previo:" + Double.toString((EntrenamientoLibre.getPRECIO())));
            vista.getDevolucionCancelacionCliente().setText("Valor previo:" + Double.toString((TarifaPlana.getDEVOLUCION_CLIENTE())));
            vista.getDevolucionCancelacionMonitor().setText("Valor previo:" + Double.toString((TarifaPlana.getDEVOLUCION_MONITOR())));
            vista.getMaximoFaltas().setText("Valor previo:" + Double.toString((Cliente.getMAX_FALTAS())));
            vista.getDuracionPenalizacion().setText("Valor previo:" + Double.toString((Suspension.getDiasSuspension())));
            vista.getPrecioTarifaPlana().setText("Valor previo: " + Double.toString(TarifaPlana.getPrecioBase()));
            vista.getSueldoBaseMonitores().setText("Valor previo: " + Double.toString(Monitor.getNOMINA_BASE()));
            vista.getSueldoExtraMonitores().setText("Valor previo: " + Double.toString(Monitor.getEXTRA_SUELDO_SESION()));
            vista.mostrarPanelParametros();
        } else if (event.getSource().equals(vista.getBotonMonitor())) {
            vista.mostrarPanelMonitor();
        } else if (event.getSource().equals(vista.getBotonCrearSala())) {
            this.actualizarSalas();
            vista.mostrarPanelSala();
        }else if (event.getSource().equals(vista.getBotonGuardar())) {
            Double valor;
            int gr;
            if (!vista.getDescuentoTarifaPlanaLibre().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getDescuentoTarifaPlanaLibre().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El descuento de la tarifa plana libre debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarDescuentoTarifaPlanaLibre(valor);
            }
            if (!vista.getDescuentoTarifaPlanaPersonalizado().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getDescuentoTarifaPlanaPersonalizado().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El descuento de la tarifa plana personalizada debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarDescuentoTarifaPlanaPersonalizado(valor);
            }
            if (!vista.getPrecioActividadGrupal().getText().contains("Valor previo:")) {
                try {
                    gr = Integer.valueOf(vista.getPrecioActividadGrupal().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El precio de la actividad grupal debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarPrecioActividadGrupal(gr);
            }
            if (!vista.getPrecioActividadLibre().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getPrecioActividadLibre().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El precio de la actividad libre debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarPrecioEntrenamientoLibre(valor);
            }

            if (!vista.getDevolucionCancelacionCliente().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getDevolucionCancelacionCliente().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "La devolución al cliente debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarDevolucionCancelacionCliente(valor);
            }

            if (!vista.getDevolucionCancelacionMonitor().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getDevolucionCancelacionMonitor().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "La devolución al monitor debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarDevolucionCancelacionMonitor(valor);
            }

            if (!vista.getMaximoFaltas().getText().contains("Valor previo:")) {
                try {
                    gr = Integer.valueOf(vista.getMaximoFaltas().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El máximo de faltas debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarMaxFaltas(gr);
            }

            if (!vista.getDuracionPenalizacion().getText().contains("Valor previo:")) {
                try {
                    gr = Integer.valueOf(vista.getDuracionPenalizacion().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "La duración de la penalización debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarDuracionSuspension(gr);
            }

            if (!vista.getPrecioTarifaPlana().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getPrecioTarifaPlana().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El precio de la tarifa plana debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarPrecioTarifaPlana(valor);
            }

            if (!vista.getSueldoBaseMonitores().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getSueldoBaseMonitores().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El sueldo base de los monitores debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarSueldoBaseMonitores(valor);
            }

            if (!vista.getSueldoExtraMonitores().getText().contains("Valor previo:")) {
                try {
                    valor = Double.valueOf(vista.getSueldoExtraMonitores().getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "El sueldo extra de los monitores debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                admin.modificarSueldoExtraClaseMonitores(valor);
            }

            JOptionPane.showMessageDialog(vista, "Parámetros modificados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            modelo.generateBackUp();
            vista.mostrarPanelPerfil();
        } else if (event.getSource().equals(vista.getBotonRegistrar())) {
            this.nombre = vista.getNombreMonitor().getText();
            this.nick = vista.getNickMonitor().getText();
            this.nif = vista.getNIFMonitor().getText();
            this.email = vista.getEmailMonitor().getText();
            this.pw = new String(vista.getContraseñaMonitor().getPassword());

            if (nombre.isEmpty() || nick.isEmpty() || nif.isEmpty() || email.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No puede haber campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (pw.length() < 5) {
                JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (modelo.getUsuarios().containsKey(nick)) {
                JOptionPane.showMessageDialog(vista, "Ya existe un usuario con ese nick", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (nif.length() != 9) {
                JOptionPane.showMessageDialog(vista, "El NIF debe tener 9 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 

            admin.registrarMonitor(this.nick, this.pw, this.nombre, this.email, this.nif);
            vista.getNickMonitor().setText("");
            vista.getContraseñaMonitor().setText("");
            vista.getNombreMonitor().setText("");
            vista.getNIFMonitor().setText("");
            vista.getEmailMonitor().setText("");
    
            JOptionPane.showMessageDialog(vista, "Monitor registrado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            modelo.generateBackUp();

            vista.mostrarPanelPerfil();
        } else if (event.getSource().equals(vista.getBotonVolverMonitor())) {
            vista.getNickMonitor().setText("");
            vista.getContraseñaMonitor().setText("");
            vista.getNombreMonitor().setText("");
            vista.getNIFMonitor().setText("");
            vista.getEmailMonitor().setText("");

            vista.mostrarPanelPerfil();
        } else if (event.getSource().equals(vista.getBotonVolverSala())) {
            vista.getNombreSala().setText("");
            vista.getAforoSala().setText("");
            vista.getDescripcionSala().setText("");
            vista.getIdSala().setText("");
            vista.getClimatizacion().setSelected(false);

            vista.mostrarPanelPerfil();
        } else if (event.getSource().equals(vista.getClimatizacion())) {
            this.actualizarSalas();
        } else if (event.getSource().equals(vista.getBotonRegistrarSala())) {
            String nombre = vista.getNombreSala().getText();
            int aforo = 0, id;
            try {
                aforo = Integer.valueOf(vista.getAforoSala().getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Aforo incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String descripcion = vista.getDescripcionSala().getText();
            try {
                id = Integer.valueOf(vista.getIdSala().getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Id incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombre.isEmpty() || descripcion.isEmpty()) {  
                JOptionPane.showMessageDialog(vista, "Debe introducir un nombre y una descripcion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (aforo <= 0) {
                JOptionPane.showMessageDialog(vista, "Aforo debe ser positivo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!modelo.getSalas().containsKey(id)) {
                JOptionPane.showMessageDialog(vista, "No existe ninguna sala con es id", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Sala salaPadre = modelo.getSalas().get(id);
            if (aforo > salaPadre.aforoDisponible()) {
                JOptionPane.showMessageDialog(vista, "No hay aforo suficiente en la sala padre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sala sala = salaPadre.crearSalaHija(nombre, aforo, descripcion);

            modelo.addSala(sala);
            modelo.generateBackUp();
            JOptionPane.showMessageDialog(vista, "Sala registrada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

            vista.getNombreSala().setText("");
            vista.getAforoSala().setText("");
            vista.getDescripcionSala().setText("");
            vista.getClimatizacion().setSelected(false);
            vista.getIdSala().setText("");
            vista.mostrarPanelPerfil();

        }
    }
    public void actualizarSalas() {
        VistaPerfilAdministrador vista = (VistaPerfilAdministrador)this.vista;

        HashMap<Integer, Sala> salas = modelo.getSalas();
        JPanel panelSalas = vista.getPanelSalas();
        panelSalas.removeAll();
        int i = 0;
        int j = 0;
        
        if (vista.getClimatizacion().isSelected()) { //Si no es climatizada muestro solo salas sin climatizar, si lo es muestro ambas
            /*Itero el hashmap */
            for (Map.Entry<Integer, Sala> entry : salas.entrySet()) {
                Sala sala = entry.getValue();
                JPanel temp = new JPanel(new GridBagLayout());
                temp.setPreferredSize(new Dimension(350, 120));
                temp.setBorder(new LineBorder(Color.black));
                JLabel id = new JLabel("Sala con id: " + sala.getId());
                id.setFont(new Font("Arial", Font.BOLD, 14));
                JLabel info = new JLabel("Nombre: " + sala.getNombre() + ". Aforo disponible: " + sala.aforoDisponible() + "/" + sala.getAforo());
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
        } else {
            for (Map.Entry<Integer, Sala> entry : salas.entrySet()) {
                Sala sala = entry.getValue();
                if (!sala.esClimatizada()) {
                    JPanel temp = new JPanel(new GridBagLayout());
                    temp.setPreferredSize(new Dimension(350, 120));
                    temp.setBorder(new LineBorder(Color.black));
                    JLabel id = new JLabel("Sala con id: " + sala.getId());
                    id.setFont(new Font("Arial", Font.BOLD, 14));
                    JLabel info = new JLabel("Nombre: " + sala.getNombre() + ". Aforo disponible: " + sala.aforoDisponible() + "/" + sala.getAforo());
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
                    if (sala.getPadre() != null) {
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
        }
        panelSalas.revalidate(); //Para que actualize el JPanel
    }
}

    
    
