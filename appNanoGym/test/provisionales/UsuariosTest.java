package provisionales;
import java.io.IOException;
import java.time.*;

import nanoGym.NanoGym;
import usuario.cliente.Cliente;
import usuario.*;

public class UsuariosTest {
    
    public static void main(String ... args) throws IOException, ClassNotFoundException{
        // Crear los objetos necesarios para la prueba

        Usuario s1 = new Cliente("juan", "123", "Juan Luis", LocalDate.of(2000, 12, 1));
        Usuario s2 = new Cliente("luis", "124", "Luis Carlos", LocalDate.of(1990, 2, 28));
        Usuario s3 = new Cliente("ivan", "125", "Ivan Soto", LocalDate.of(1980, 10, 13));
        Usuario s4 = new Monitor("miguel", "126", "Miguel Jose", "miguel@gmail.com", "7343324C");

        NanoGym ng = new NanoGym();

        // Añadir las salas al NanoGym
        ng.addUsuario(s1);
        ng.addUsuario(s2);
        ng.addUsuario(s3);
        ng.addUsuario(s4);

        System.out.println("Primer print:\n");
        System.out.println(ng+ "\n");

        ng.guardarUsuarios();

        ng.removeUsuario(s2);
        ng.removeUsuario(s1);
        ng.removeUsuario(s3);
        ng.removeUsuario(s4);

        System.out.println("Print despues de borrar usuarios:\n");
        System.out.println(ng + "\n");

        ng.leerUsuarios();

        System.out.println("Print despues de leer usuarios:\n");
        System.out.println(ng);

        System.out.println("\n" + ng.getUsuarios().size());
    
    }
}
