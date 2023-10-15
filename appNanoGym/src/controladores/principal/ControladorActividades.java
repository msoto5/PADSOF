package controladores.principal;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import exceptions.ClienteSuspendidoException;
import exceptions.ServicioAtMaximunCapacityException;
import mainGUI.*;
import nanoGym.*;
import vistas.prinicipal.VistaActividades;
import servicio.*;
import usuario.cliente.Cliente;
import usuario.cliente.Suspension;

public abstract class ControladorActividades implements ActionListener {
    protected VistaActividades vista;
    protected NanoGym modelo;
    protected MainGUI ventana;

    private ArrayList<String> busquedasCheckBox = new ArrayList<String>();
    private String busquedaText = "";

    public ControladorActividades(VistaActividades vista, NanoGym modelo, MainGUI ventana) {
        this.vista = vista;
        this.modelo = modelo;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.vista.getBuscar())) {
                
            // Busqueda de actividades
            String busqueda = this.vista.getBusqueda().getText();
            if (!busquedaText.equals(busqueda)) {
                busquedaText = busqueda;
            }
            recargarActividades();
            return;

        } else if (event.getSource().equals(this.vista.getBEntLibre())) {
            if (this.vista.getBEntLibre().isSelected() && !busquedasCheckBox.contains("Entrenamiento Libre")) {
                vista.getBEntLibre().setSelected(true);
                busquedasCheckBox.add("Entrenamiento Libre");
                recargarActividades();
            } else {
                //vista.getBEntLibre().setSelected(false);
                busquedasCheckBox.remove("Entrenamiento Libre");
                recargarActividades();
            }
            return;
            
        } else if (event.getSource().equals(this.vista.getBActGrupal())) {
            if (this.vista.getBActGrupal().isSelected() && !busquedasCheckBox.contains("Actividad grupal")) {
                vista.getBActGrupal().setSelected(true);
                busquedasCheckBox.add("Actividad grupal");
                recargarActividades();
            } else {
                //vista.getBActGrupal().setSelected(false);
                busquedasCheckBox.remove("Actividad grupal");
                recargarActividades();
            }
            return;

        } else if (event.getSource().equals(this.vista.getBEntPers())) {
            if (this.vista.getBEntPers().isSelected() && !busquedasCheckBox.contains("Entrenamiento Personalizado")) {
                vista.getBEntPers().setSelected(true);
                busquedasCheckBox.add("Entrenamiento Personalizado");
                recargarActividades();
            } else {
                //vista.getBEntPers().setSelected(false);
                busquedasCheckBox.remove("Entrenamiento Personalizado");
                recargarActividades();
            }
            return;

        } else if (modelo.getCurrentUser().esCliente()) {
            Cliente cl = (Cliente) modelo.getCurrentUser();
            for (int i = 0; i < vista.getServicios().size(); i++) {
                if (event.getSource().equals(this.vista.getBotones(i).get(0))) {
                    try {
                        boolean ret = cl.reservarServicio(vista.getServiciosServicios().get(i));
                        if (ret) {
                            JOptionPane.showMessageDialog(vista, "Se ha reservado la actividad con exito. Puede consultar sus reservas en la pestaña Reservas.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(vista, "Ha ocurrido un error al reservar la actividad. Puede ser que ya la haya reservado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (ClienteSuspendidoException e) {
                        JOptionPane.showMessageDialog(vista, "Estas suspendido. No puedes reservar servicios. Te suspendieron el día " + cl.getSuspension().getfInicial() + " por " + Suspension.getDiasSuspension() + " dias." , "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ServicioAtMaximunCapacityException e) {
                        JOptionPane.showMessageDialog(vista, "El servicio no admite más reservas. El aforo está completo.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(vista, "Ha ocurrido un error al reservar la actividad.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
            }
        }
    }

    private void recargarActividades() {
        int flag = 0;
        boolean bTxt = true;

        //System.out.println("Busqueda: " + busquedaText + " " + busquedasCheckBox.size() + "-> " + busquedasCheckBox);

        
        if (busquedaText.isEmpty() || busquedaText.equals("Introduzca su busqueda")) {
            bTxt = false;
        } 
        
        if (!bTxt && busquedasCheckBox.size() == 0) {

            JOptionPane.showMessageDialog(vista, "La busqueda no puede ser vacia", "Error", JOptionPane.ERROR_MESSAGE);

            this.setCheckBox2False();
            vista.clearServicios();
            vista.addServiciosBusqueda(null);
            vista.addServiciosDisponibles(modelo.getServiciosSiguientes());
        }
        else {
            // Buscar actividades
            ArrayList<Servicio> sbusq = new ArrayList<Servicio>();
            if (busquedasCheckBox.size() > 0) {
                sbusq = modelo.buscarServicios(busquedasCheckBox.get(0));
                //System.out.println("sbusq = " + sbusq.size());
                for (int i = 1; i < busquedasCheckBox.size(); i++) {
                    sbusq = modelo.busquedasOR(sbusq, busquedasCheckBox.get(i));
                    //System.out.println("sbusq = " + i + "->" + sbusq.size());
                }
                if (busquedaText.isEmpty() || busquedaText.equals("Introduzca su busqueda")) {
                    
                }
                if (bTxt) {
                    sbusq = modelo.busquedasAND(sbusq, busquedaText);
                    //System.out.println("sbusq_F = " + sbusq.size());
                }
            }
            else {
                sbusq = modelo.buscarServicios(busquedaText);
            }
            
            if (sbusq.size() == 0) {
                JOptionPane.showMessageDialog(vista, "No se han encontrado actividades", "Error", JOptionPane.ERROR_MESSAGE);

                vista.clearServicios();

                vista.addServiciosBusqueda(null);
                vista.addServiciosDisponibles(modelo.getServiciosSiguientes());

            } else {

                ArrayList<Servicio> servicios = modelo.getServiciosSiguientes();
                this.setCheckBox2False();
                vista.clearServicios();
                vista.addServiciosBusqueda(sbusq);
                vista.addServiciosDisponibles(servicios);
            }
        }

        if (flag == 1) {
            busquedaText = "";
        }
        return;
    }

    private void setCheckBox2False() {
        vista.getBActGrupal().setSelected(false);
        vista.getBEntLibre().setSelected(false);
        vista.getBEntPers().setSelected(false);
        busquedasCheckBox.clear();
    }
}
