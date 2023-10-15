package servicioTest.actividadTest.actividadMonitorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import utiles.*;
import sala.*;
import servicio.actividad.actividadmonitor.EntrenamientoMonitor;
import usuario.*;

import org.junit.*;
import static org.junit.Assert.*;

public class EntrenamientoMonitorTest {

	private static Horario h1;
	private static Sala s1;
	private static LocalDate fecha;
	private static Monitor m1;
	private static EntrenamientoMonitor em;
	private static String nombre = "entrenamiento_monitor";
	private static String descripcion = "descripcionEM";

	@Before
	public void setUpBeforeClass() throws Exception {
		h1 = new Horario(LocalTime.of(18,0), LocalTime.of(19,0));
		s1 = new Sala("Principal", 20, "Yoga");
		fecha = LocalDate.of(2023, 03, 30);
		m1 = new Monitor("Fernando", "33", "Fernando Alonso", "FA33@email.com", "33333333A");
		em = new EntrenamientoMonitor(nombre, descripcion, h1, s1, m1, fecha);
	}

	@Test
	public void testEntrenamientoMonitor() {
		Assert.assertEquals(nombre, em.getNombre());
		Assert.assertEquals(descripcion, em.getDescripcion());
		Assert.assertEquals(h1, em.getHorario());
		Assert.assertEquals(s1, em.getSala());
		Assert.assertEquals(m1, em.getMonitor());
		Assert.assertEquals(fecha, em.getFecha());
	}

	@Test
	public void testSetGetAFORO_MAX() {
		EntrenamientoMonitor.setAFORO_MAX(1);
		Assert.assertEquals(EntrenamientoMonitor.getAFORO_MAX(), 1, 0);
	}

	@Test
	public void testAforoDisponible() {
		assertEquals(1, em.aforoDisponible());
	}

	@Test
	public void testGetPrecio(){
		Assert.assertEquals(0, em.getPrecio(), 0);
	}

	@Test
	public void testGetTipo(){
		String expected = "Entrenamiento con monitor";
		Assert.assertTrue(em.getBusquedaString().contains(expected));
	}

}
