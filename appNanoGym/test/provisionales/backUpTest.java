package provisionales;

import nanoGym.NanoGym;
import sala.*;
import utiles.*;
import java.time.*;
import usuario.*;
import usuario.cliente.*;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.TipoActividad;
import servicio.*;

public class backUpTest {
    public static void main (String ... args) {
        Horario h = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));

        Sala s1 = new Sala("Principal", 20, "Pesas");
        Sala s2 = new Sala("Secundaria", 20, "SubPesas");
        SalaClimatizada s3 = new SalaClimatizada(h, "Climatizada", 20, "Sauna");

        Monitor m4 = new Monitor("miguel", "126", "Miguel Jose", "miguel@gmail.com", "73433244C");
        Usuario u1 = new Cliente("juan", "123", "Juan Luis", LocalDate.of(2000, 12, 1));
        Usuario u2 = new Cliente("luis", "124", "Luis Carlos", LocalDate.of(1990, 2, 28));
        Usuario u3 = new Cliente("ivan", "125", "Ivan Soto", LocalDate.of(1980, 10, 13));

        Servicio se1 = new EntrenamientoLibre("se1", "des_se1", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s1);
        Servicio se2 = new ActividadGrupal("se2", "des_se2", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s2, m4, new TipoActividad("PILATES"));

        NanoGym ng = new NanoGym();

        ng.addSala(s1);
        ng.addSala(s2);
        ng.addSala(s3);

        ng.addUsuario(u1);
        ng.addUsuario(u2);
        ng.addUsuario(u3);
        ng.addUsuario(m4);

        ng.addServicio(se1);
        ng.addServicio(se2);

        System.out.println("Primer print:\n");
        System.out.println(ng+ "\n");

        ng.generateBackUp();

        ng.removeSala(s1);
        ng.removeSala(s2);
        ng.removeSala(s3);

        ng.removeUsuario(u1);
        ng.removeUsuario(u2);
        ng.removeUsuario(u3);
        ng.removeUsuario(m4);

        ng.removeServicio(se1);
        ng.removeServicio(se2);

        System.out.println("Print despues de borrar usuarios:\n");
        System.out.println(ng + "\n");

        ng.loadBackUp();

        System.out.println("Print despues de leer usuarios:\n");
        System.out.println(ng);

    }
}
