package controladores.principal.monitor;

import controladores.principal.ControladorNotificaciones;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.monitor.VistaNotificacionesMonitor;

public class ControladorNotificacionesMonitor extends ControladorNotificaciones {

    public ControladorNotificacionesMonitor(VistaNotificacionesMonitor vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }
}
