package controladores.principal.cliente;

//import java.awt.event.*;

import controladores.principal.ControladorPerfil;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import vistas.prinicipal.cliente.VistaPerfilCliente;

public class ControladorPerfilCliente extends ControladorPerfil {

    public ControladorPerfilCliente(VistaPerfilCliente vista, NanoGym modelo, MainGUI ventana) {
        super(vista, modelo, ventana);
    }

    /* No hace falta sobreescribir -> CoontroladorPerfil.java
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(this.vista.getBotonCerrarSesion())) {
            modelo.logOut();
            ventana.mostrarInicioSesion();
        }
    }
    */
}
