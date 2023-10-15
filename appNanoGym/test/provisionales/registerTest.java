package provisionales;
import java.time.LocalDate;

import nanoGym.NanoGym;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.TarifaPlana;
import usuario.Usuario;

public class registerTest {
    public static void main(String ... args) {
        NanoGym ng = new NanoGym();
        TarjetaBancaria tb = null;

        try{
        tb = new TarjetaBancaria("1234123412341234", LocalDate.of(2024,07,30), "234");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        ng.aniadirCliente("hola", "12345", "nicol", LocalDate.of(2003,07,30), tb, new TarifaPlana(3, new TipoActividad("PIATES")));

        //ng.registrarCliente();

        if (ng.getUsuarios().get("hola") == null) {
            System.out.println("Error: El usuario no se ha registrado");
        } else {
            System.out.println("El usuario se ha registrado correctamente");
        }

        try {
            if (ng.logIn() == true) {
                System.out.println("El usuario se ha logeado correctamente");
            } 
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        Usuario u = ng.getCurrentUser();
        if (u != null) {
            System.out.println(u.getUsername());
        }

        try {
            if (ng.logOut() == true) {
                System.out.println("El usuario se ha deslogeado correctamente");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

