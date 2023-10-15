package provisionales;

import java.io.IOException;
import java.time.*;

import nanoGym.NanoGym;
import servicio.*;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.TipoActividad;
import utiles.Horario;
import sala.*;
import usuario.*;

public class serviciosTestprob {
    
    public static void main(String ... args) throws IOException, ClassNotFoundException{
        // Crear los objetos necesarios para la prueba
        Sala s1 = new Sala("Principal", 20, "Pesas");
        Sala s2 = new Sala("Secundaria", 20, "SubPesas");
        Monitor m4 = new Monitor("miguel", "126", "Miguel Jose", "miguel@gmail.com", "73433244C");

        Servicio se1 = new EntrenamientoLibre("se1", "des_se1", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s1);
        Servicio se2 = new ActividadGrupal("se2", "des_se2", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s2, m4, new TipoActividad("Pilates"));
        NanoGym ng = new NanoGym();

        // Añadir las salas al NanoGym
        ng.addServicio(se2);
        ng.addServicio(se1);

        System.out.println("Primer print:\n");
        System.out.println(ng+ "\n");

        ng.guardarServicios();

        ng.removeServicio(se2);
        ng.removeServicio(se1);

        System.out.println("Print despues de borrar usuarios:\n");
        System.out.println(ng + "\n");

        ng.leerServicios();

        System.out.println("Print despues de leer usuarios:\n");
        System.out.println(ng);

        System.out.println("\n" + ng.getServicios().size());
    
    }
}