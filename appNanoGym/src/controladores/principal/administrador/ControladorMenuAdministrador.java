package controladores.principal.administrador;

import javax.swing.event.*;

import controladores.principal.ControladorMenu;
import mainGUI.MainGUI;
import nanoGym.NanoGym;
import usuario.Administrador;
import vistas.prinicipal.VistaMenu;
import vistas.prinicipal.administrador.VistaPerfilAdministrador;

public class ControladorMenuAdministrador extends ControladorMenu {

    public ControladorMenuAdministrador(VistaMenu vista, NanoGym modelo, MainGUI mainGUI) {
        super(vista, modelo, mainGUI);
    }
    
    @Override
    public void stateChanged(ChangeEvent event) {
        int selectedIndex = vista.getTabbedPane().getSelectedIndex();

        switch (selectedIndex) {
            case 0: //Caso actividades
                super.stateChanged(event);
                break;
            case 1: //Caso crear actividad
                  
                break;
            case 2: //Caso notificaciones
                super.stateChanged(event);

                break;

            case 3: //Caso perfil
                /*Actualizo los datos del perfil */
                VistaPerfilAdministrador perfil = (VistaPerfilAdministrador) vista.getPerfil();
                Administrador admin = (Administrador)modelo.getCurrentUser();
                
                perfil.getLabelNick().setText(admin.getUsername());
                perfil.setPw(admin.getContraseña());

                perfil.getLabelGastosMonitor().setText("Gastos: " + Double.toString(admin.getGastos()));
                perfil.getLabelBeneficioActividades().setText("Ingresos: " + Double.toString(admin.getIngresos()));
                perfil.getLabelBalance().setText("Balance total NanoGym: " + Double.toString(admin.getBalance()));
        
                
                break;
            default:
                break;
        }
    }
}
