package servicioTest.actividadTest.actividadMonitorTest;

import java.time.*;

import org.junit.*;
import static org.junit.Assert.*;

import sala.Sala;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.Monitor;
import utiles.Horario;


public class ActividadMonitorTest {
	private static ActividadMonitor am;
	private static String nombre = "Spinning";
	private static String descripcion = "ciclismo indoor";
	private static Horario h1;
	private static LocalDate fecha;
	private static Sala s1;
	private static Monitor m1;
	private static TipoActividad ta;
	private static String nombreTA = "tipoActividad";


	@Before
	public void setUpBeforeClass() throws Exception {
		h1 = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));
		s1 = new Sala("Principal", 20, "Yoga");
		fecha = LocalDate.of(2023, 12, 03);
		m1 = new Monitor("Fernando", "33", "Fernando Alonso", "FA33@email.com", "33333333A");
		ta = new TipoActividad(nombreTA);
		am = new ActividadGrupal(nombre, descripcion, h1, fecha, s1, m1, ta);
	}

	@Test
	public void testActividadMonitor() {
		Assert.assertEquals(nombre, am.getNombre());
		Assert.assertEquals(descripcion, am.getDescripcion());
		Assert.assertEquals(h1, am.getHorario());
		Assert.assertEquals(fecha, am.getFecha());
		Assert.assertEquals(s1, am.getSala());
		Assert.assertEquals(m1, am.getMonitor());
	}

	@Test
	public void testSetGetMonitor() {
		assertEquals(m1, am.getMonitor());
	}

	@Test
	public void testCancelarPorMonitor(){
		assertTrue(am.cancelarPorMonitor(m1));
	}
	
}
