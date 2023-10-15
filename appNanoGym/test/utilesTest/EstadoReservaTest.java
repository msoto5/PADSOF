package utilesTest;

import org.junit.*;
import static org.junit.Assert.*;

import utiles.EstadoReserva;

public class EstadoReservaTest {

    @Test
    public void testGetEstado() {
        EstadoReserva estado = EstadoReserva.PAGADO;
        assertEquals(0, estado.getEstado());
    }

    @Test
    public void testSetEstado() {
        EstadoReserva estado = EstadoReserva.RESERVADA;
        estado.setEstado(1);
        assertEquals(1, estado.getEstado());
    }

    @Test
    public void testEnumValues() {
        EstadoReserva[] estados = EstadoReserva.values();
        //assertEquals(3, estados.length());
        assertEquals(EstadoReserva.PAGADO, estados[0]);
        assertEquals(EstadoReserva.LISTA_ESPERA, estados[1]);
        assertEquals(EstadoReserva.RESERVADA, estados[2]);
    }

}
