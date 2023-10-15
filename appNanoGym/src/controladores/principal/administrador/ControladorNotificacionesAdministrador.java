package controladores.principal.administrador;

import controladores.principal.ControladorNotificaciones;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.administrador.VistaNotificacionesAdministrador;

public class ControladorNotificacionesAdministrador extends ControladorNotificaciones {
    
    public ControladorNotificacionesAdministrador(VistaNotificacionesAdministrador vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }
}
