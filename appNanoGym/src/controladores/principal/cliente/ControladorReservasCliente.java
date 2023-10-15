package controladores.principal.cliente;

import controladores.principal.ControladorReservas;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.cliente.VistaReservasCliente;

public class ControladorReservasCliente extends ControladorReservas {

    public ControladorReservasCliente(VistaReservasCliente vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }
    
}