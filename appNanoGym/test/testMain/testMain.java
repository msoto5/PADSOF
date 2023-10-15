package testMain;

import nanoGym.NanoGym;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.*;
import servicio.*;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.cliente.*;
import usuario.*;
import sala.*;
import utiles.Horario;

import java.time.*;


public class testMain {
    public static void main (String ... args) {
        NanoGym ng = new NanoGym();
        int respuesta = 0;

        ng.loadBackUp();

        //System.out.println(ng);
        //NanoGym.leer.nextLine();

        Usuario m1 = new Administrador("admin", "12345", ng);
        Usuario m = new Monitor("adios", "12345", "Jose Juan", "adios@gmail.com", "12345678A");
        Sala s2 = new Sala("Secundaria", 20, "SubPesas");
        ActividadGrupal ag = new ActividadGrupal("se2", "des_se2", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s2, (Monitor)m, new TipoActividad("Pilates"));

        ng.addSala(s2);
        ng.addServicio(ag);

        ((Monitor)m).addActividad(ag);

        ng.addUsuario(m);
        ng.addUsuario(m1);

        try {
            ng.aniadirCliente("hola", "12345", "hola", LocalDate.of(2003,3,3), new TarjetaBancaria("1234123412341234", LocalDate.of(2024,3,3), "234"), new TarifaPlana(3, new TipoActividad("Pilates")));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        /*__________________________________ */

        do {
            ng.mostrarMenuInicio();
            try{
                respuesta =  Integer.parseInt(NanoGym.leer.nextLine());
            } catch (Exception e) {
                System.out.println("Error: Opcion no valida. Vuelve a intentarlo");
            }
            if (respuesta < 1 || respuesta > 2) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("\nError: Opcion no valida. Vuelve a intentarlo");
            } else {
                if (respuesta == 1) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    try {
                        ng.registrarCliente();
                        System.out.println("Cliente registrado correctamente");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    try {
                    if (ng.logIn() == true) {
                        System.out.println("El usuario se ha logeado correctamente");
                    } 
                    } catch (Exception e) {
                        respuesta = 1;
                        System.out.println(e.getMessage());
                    } 
                }
            }
        } while (respuesta != 2);

        ng.getCurrentUser().mostrarMenuPrincipal();

        /*Mientras que no haga logOut puedo seguir en el bucle de mostarMenuPrincipal de usuario */
        while(respuesta != 1) {
        
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ng.getCurrentUser().mostrarMenuPrincipal();
            
            respuesta = Integer.parseInt(NanoGym.leer.nextLine());

            ng.getCurrentUser().switchMenuPrincipal(respuesta, ng);

            /*Cuando introduzco el 2 veo las próximas actividad, genérico para todos */
            /*Si hay tiempo implementar filtrado de actividades por tipo o al menos número de próximas actividades a mostrar */
            if(respuesta == 2) {
                int i = 1;
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("PROXIMAS ACTIVIDADES:");
                for(Servicio serv: ng.getServicios()) {
                    System.out.println("-----------------------------------------");
                    System.out.println("Actividad " + i + ":\n");
                    System.out.println(serv);
                    i++;
                    if (i > 10) {
                        break;
                    }
                }
                if (ng.getCurrentUser().esCliente() == true) {
                    int nres = 0;
                    do {
                        System.out.println("======================================================================================");
                        System.out.println("(Introduce el número de una actividad si desea reservarla o enter para volver al menu)");
                        System.out.println("======================================================================================");
                        String reserv = NanoGym.leer.nextLine();
                        if (reserv.length() != 0) {
                           nres = Integer.parseInt(reserv);

                             if (nres > 0 && nres <= i) {
                                  try {
                                    ((Cliente)ng.getCurrentUser()).reservarServicio(ng.getServicios().get(nres-1)); /*HACER FUNCIÓN */
                                    System.out.print("\033[H\033[2J");
                                    System.out.flush();
                                    System.out.println("=======================================================================");
                                    System.out.println("Actividad reservada correctamente. Presione enter para volver al menu.");
                                    System.out.println("=======================================================================");
                                    NanoGym.leer.nextLine();
                                  } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                  }
                             } else {
                                  System.out.println("Error: Opcion no valida. Vuelve a intentarlo");
                             }
                        } else {
                            break;
                        }
                    } while (nres <= 0 || nres > i);
                } else {
                    System.out.println("===================================");
                    System.out.println("Presione enter para volver al menu.");
                    System.out.println("===================================");
                    NanoGym.leer.nextLine();
                }
            }
        }

        /*Cuando introduzco el 1 hago logOut, genérico para todos */
        if (respuesta == 1) {
            if (ng.logOut()) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("=================================================");
                System.out.println("El usuario se ha deslogeado correctamente. Adios!");
                System.out.println("=================================================");
            } else {
                System.out.println("Error desconocido");
            }
        } else {
            System.out.println("Error desconocido");
        }

        //ng.generateBackUp();

    }   
}
