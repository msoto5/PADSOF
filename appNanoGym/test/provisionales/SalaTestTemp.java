package provisionales;

import java.io.IOException;

import nanoGym.NanoGym;
import sala.*;
import utiles.*;
import java.time.*;

public class SalaTestTemp {
    
    public static void main(String ... args) throws IOException, ClassNotFoundException{
        // Crear los objetos necesarios para la prueba
        Horario h = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));

        Sala s1 = new Sala("Principal", 20, "Pesas");
        Sala s2 = new Sala("Secundaria", 20, "SubPesas");
        SalaClimatizada s3 = new SalaClimatizada(h, "Climatizada", 20, "Sauna");

        NanoGym ng = new NanoGym();

        // Añadir las salas al NanoGym
        ng.addSala(s1);
        ng.addSala(s2);
        ng.addSala(s3);

        System.out.println("Primer print:\n");
        System.out.println(ng+ "\n");

        ng.guardarSalas();

        ng.removeSala(s2);
        ng.removeSala(s1);
        ng.removeSala(s3);

        System.out.println("Print despues de borrar salas:\n");
        System.out.println(ng + "\n");

        ng.leerSalas();

        System.out.println("Print despues de leer salas:\n");
        System.out.println(ng);

        System.out.println("\n" + ng.getSalas().size());
    
    }
}
