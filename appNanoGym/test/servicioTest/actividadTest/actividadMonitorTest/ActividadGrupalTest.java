package servicioTest.actividadTest.actividadMonitorTest;

import java.time.*;

import utiles.*;
import sala.*;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.*;
import usuario.cliente.Cliente;

import org.junit.*;
import static org.junit.Assert.*;

public class ActividadGrupalTest {

	private static Horario h1;
	private static Sala s1;
	private static LocalDate fecha;
	private static Monitor m1;
	private static ActividadGrupal ag;
	private static Reserva r;
	private static TipoActividad ta;
	private static String nombre = "Spinning";
	private static String nombreTA = "tipoActividad";
	private static String descripcion = "ciclismo indoor";
	private static ListaEspera le;
	private static Cliente c;
	private static EstadoReserva er = EstadoReserva.LISTA_ESPERA;

	@Before
	public void setUpBeforeClass() throws Exception {
		h1 = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));
		s1 = new Sala("Principal", 20, "Yoga");
		fecha = LocalDate.of(2023, 12, 03);
		m1 = new Monitor("Fernando", "33", "Fernando Alonso", "FA33@email.com", "33333333A");
		ta = new TipoActividad(nombreTA);
		c = new Cliente("ms", "1234", "Miguel Soto", LocalDate.of(1999, 12, 12));
		ag = new ActividadGrupal(nombre, descripcion, h1, fecha, s1, m1, ta);
		r = new Reserva(c, ag, er);
		le = new ListaEspera(ag);
	}

	@Test
	public void testActividadGrupal() {
		Assert.assertEquals(nombre, ag.getNombre());
		Assert.assertEquals(descripcion, ag.getDescripcion());
		Assert.assertEquals(h1, ag.getHorario());
		Assert.assertEquals(fecha, ag.getFecha());
		Assert.assertEquals(s1, ag.getSala());
		Assert.assertEquals(m1, ag.getMonitor());
		Assert.assertEquals(ta, ag.getTipoActividad());
	}

	@Test
	public void testSetGetTipoActividad(){
		ag.setTipo(ta);
		assertEquals(ta, ag.getTipoActividad());
	}

	@Test
	public void testSetGetListaEspera() {
		ag.setListaEspera(le);
		assertEquals(le, ag.getListaEspera());
	}

	@Test
	public void testAddReservaListaEspera() {
		r.setEstado(EstadoReserva.LISTA_ESPERA);
		assertEquals(ag.getListaEspera().addReserva(r), ag.addReservaListaEspera(r));
	}

	@Test
	public void testRemoveReservaListaEspera() {
		r.setEstado(EstadoReserva.LISTA_ESPERA);
		ag.addReservaListaEspera(r);
		assertTrue(ag.removeReservaListaEspera(r));
	}

	@Test
	public void testAddReserva(){
		assertTrue(ag.addReserva(r));
	}

	@Test
	public void testRemoveReserva(){
		r.setEstado(EstadoReserva.PAGADO);
		ag.addReserva(r);
		assertTrue(ag.removeReserva(r));
	}

	@Test
	public void testGetPRECIO() {
		ActividadGrupal.setPRECIO(10);
		if (ActividadGrupal.getPRECIO() == 10) {
			assertTrue(true);
		} else {
			fail("No se ha podido cambiar el precio");
		}
		
	}

	@Test
	public void testGetPrecio(){
		assertEquals(ActividadGrupal.getPRECIO(), ag.getPrecio(), 0);
	}

	@Test
	public void testMoverReservaListaEspera() {
		Assert.assertNotEquals(r, ag.getListaEspera());
	}

}
