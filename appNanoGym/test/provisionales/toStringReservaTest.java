package provisionales;

import usuario.cliente.*;
import utiles.Reserva;
import servicio.Servicio;
import servicio.actividad.EntrenamientoLibre;
import utiles.EstadoReserva;
import utiles.Horario;
import sala.*;

import java.time.*;

public class toStringReservaTest {
    public static void main(String ...args) {

        Cliente c1 = new Cliente("juan", "123", "Juan Luis", LocalDate.of(2000, 12, 1));
        Sala s1 = new Sala("Principal", 20, "Pesas");
        Servicio se1 = new EntrenamientoLibre("se1", "des_se1", new Horario(LocalTime.of(12,0,0), LocalTime.of(13,0,0)), LocalDate.now(), s1);
        Reserva r = null;

        try {
        r = new Reserva(c1, se1, EstadoReserva.PAGADO);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(r);
    }
}
