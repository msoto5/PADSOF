package utilesTest;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.*;
import static org.junit.Assert.*;

import exceptions.ClienteSuspendidoException;
import servicio.actividad.EntrenamientoLibre;
import servicio.Servicio;
import sala.Sala;
import utiles.EstadoReserva;
import utiles.Horario;
import utiles.Reserva;
import usuario.cliente.Cliente;

public class ReservaTest {
	LocalDate d1 = LocalDate.of(2003,1,1);
	Horario h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
	Sala sala1 = new Sala("sala1", 10, "sala1_desc");
	Sala sala2 = new Sala("sala2", 10, "sala2_desc");
	Cliente c1 = new Cliente("ms", "1234", "Miguel", d1);
	Cliente c2 = new Cliente("nv", "1234", "Nico", d1);
	Cliente c3 = new Cliente("dg", "1234", "Diego", d1);
	Servicio s1 = new EntrenamientoLibre("el1", "el1_dec", h1, d1, sala1);
	Servicio s2 = new EntrenamientoLibre("el2", "el2_dec", h1, d1, sala2);
	Reserva r1;
	Reserva r2;

	@Before
	public void setUp() throws Exception {
		this.d1 = LocalDate.of(2003,1,1);
		this.h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
		this.sala1 = new Sala("sala1", 10, "sala1_desc");
		this.c1 = new Cliente("ms", "1234", "Miguel", d1);
		this.s1 = new EntrenamientoLibre("el1", "el1_dec", h1, d1, sala1);

		try {
			this.r1 = new Reserva(c1, s1, EstadoReserva.RESERVADA);
		} catch (Exception e) {
			fail("No debería haber lanzado excepción: " + e.getMessage());
		}
	}

	@Test
	public void testReserva(){
		assertEquals(c1, r1.getCliente());
		assertEquals(s1, r1.getServicio());
		assertEquals(EstadoReserva.RESERVADA, r1.getEstado());
	}

	@Test
	public void testReservaException() {
		/* Suspender a un cliente */
		for (int i = 0; i < Cliente.getMAX_FALTAS(); i++) {
			c3.addFalta();
		}
		if (!c3.isSuspendido()) {
			fail("El cliente no está suspendido");
		}

		Exception exception = assertThrows(ClienteSuspendidoException.class, () -> {
			r2 = new Reserva(c3, s1, EstadoReserva.RESERVADA);
		});
		assertEquals("El cliente Diego está suspendido", exception.getMessage());
	}

	@Test
	public void testSetGetCliente() {
		assertEquals(c1, r1.getCliente());
		r1.setCliente(c2);
		assertEquals(c2, r1.getCliente());
	}

	@Test
	public void testSetGetServicio() {
		assertEquals(s1, r1.getServicio());
		r1.setServicio(s2);
		assertEquals(s2, r1.getServicio());
	}

	@Test
	public void testSetGetEstado() {
		assertEquals(EstadoReserva.RESERVADA, r1.getEstado());
		r1.setEstado(EstadoReserva.PAGADO);
		assertEquals(EstadoReserva.PAGADO, r1.getEstado());
		r1.setEstado(EstadoReserva.LISTA_ESPERA);
		assertEquals(EstadoReserva.LISTA_ESPERA, r1.getEstado());
	}

	@Test
	public void testPagarReserva() {
		r1.pagarReserva();
		assertEquals(EstadoReserva.PAGADO, r1.getEstado());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, r1.compareTo(r1));

		try {
			r2 = new Reserva(c2, s2, EstadoReserva.RESERVADA);
			assertNotEquals(0, r1.compareTo(r2));
		} catch (Exception e) {
			fail("No debería haber lanzado excepción, c2 s2");
		}

		try {
			r2 = new Reserva(c2, s1, EstadoReserva.RESERVADA);
			assertNotEquals(0, r1.compareTo(r2));
		} catch (Exception e) {
			fail("No debería haber lanzado excepción, c2 s1");
		}

		try {
			r2 = new Reserva(c1, s2, EstadoReserva.PAGADO);
			assertNotEquals(0, r1.compareTo(r2));
		} catch (Exception e) {
			fail("No debería haber lanzado excepción, c1 s2");
		}
	}

	@Test
	public void testEquals(){
		assertTrue(r1.equals(r1));
	}

	@Test
	public void testHashCode(){
		int expected = 11 * r1.getCliente().hashCode() * r1.getServicio().hashCode();
		assertEquals(expected, r1.hashCode());
	}

	@Test
	public void testDevolverCobroCliente(){
		assertTrue(r1.devolverCobroCliente());
	}

	@Test
	public void testReservaCancelada(){
		assertTrue(r1.reservaCancelada());
	}

	@Test
	public void testReservaCanceladaPorCliente(){
		assertFalse(r1.reservaCanceladaPorCliente(c1));
	}

	@Test
	public void testReservaCanceladaPorServicio(){
		assertFalse(r1.reservaCanceladaPorServicio(s1));
	}

	@Test
	public void testGetCoste(){
		assertEquals(r1.getServicio().getPrecio(), r1.getCoste(), 0);
	}

	@Test
	public void testgetCosteDevolucion(){
		assertEquals(r1.getCliente().getTarifa().calcularDevolucionCancPorMonitor(r1.getServicio()), r1.getCosteDevolucion(), 0);
	}

}
