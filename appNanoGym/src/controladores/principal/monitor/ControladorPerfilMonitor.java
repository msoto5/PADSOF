package controladores.principal.monitor;

import java.awt.event.*;
import java.time.*;

import javax.swing.JOptionPane;

import controladores.principal.ControladorPerfil;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import usuario.*;
import vistas.prinicipal.monitor.*;

public class ControladorPerfilMonitor extends ControladorPerfil {

    public ControladorPerfilMonitor(VistaPerfilMonitor vista, NanoGym modelo, MainGUI mainGUI) {
        super(vista, modelo, mainGUI);
    } 

    @Override
    public void actionPerformed(ActionEvent event) {
        VistaPerfilMonitor vista = (VistaPerfilMonitor)this.vista;
        if (event.getSource().equals(vista.getBotonCerrarSesion())) {
            modelo.logOut();
            ventana.mostrarInicioSesion();
        } else if (event.getSource().equals(vista.getBotonDescargarNomina())) {
            String mes = vista.getMesNomina().getText();
            String anyo = vista.getAnyoNomina().getText();

            if (mes.equals("Mes (MM)") || anyo.equals("Año (YYYY)")) {
                JOptionPane.showMessageDialog(vista, "Debe introducir un a fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Monitor monitor = (Monitor)this.modelo.getCurrentUser();

            monitor.generarPDF(monitor, Month.of(Integer.parseInt(mes)), Year.of(Integer.parseInt(anyo)));
            JOptionPane.showMessageDialog(vista, "Nomina generada correctamente en ./nominasMonitor", "Nomina generada", JOptionPane.OK_OPTION);

        }
    }
}
