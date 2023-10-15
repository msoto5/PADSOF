package salaTest;

import sala.*;
import servicio.actividad.EntrenamientoLibre;
import java.time.*;

import utiles.Horario;
import servicio.actividad.*;

import org.junit.*;
import static org.junit.Assert.*;

import exceptions.SalaNoCompatibleException;

public class SalaTest {
	private Sala sPadre = new Sala("sPadre", 20, "des_sPadre");
	private Sala sHijo;
	private Horario h = new Horario(LocalTime.of(9, 30, 0),
									LocalTime.of(11, 30, 0));
	private Horario h2 = new Horario(LocalTime.now(),
									 LocalTime.now().plusHours(2));
	private EntrenamientoLibre el;
	private EntrenamientoLibre el2, el3;

	@Before
	public void setUp() {
		try {
			sHijo = sPadre.crearSalaHija("sHijo", 5, "des_sHijo");
			el = new EntrenamientoLibre("se1", "des_se1", h, LocalDate.now(), sHijo);
			el2 = new EntrenamientoLibre("se2", "des_se2", h2, LocalDate.now(), sHijo);
			el3 = new EntrenamientoLibre("se3", "des_se3", h2, LocalDate.now(), sHijo);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetHijos() {
		assertEquals(sHijo, sPadre.getHijos().get(0));
	}

	@Test
	public void testCrearSalaHija() {
		Sala s = sPadre.crearSalaHija("sPadre_hija", 25, "des_sPadre_hija");
		assertEquals(null, s);
	}

	@Test
	public void testEsHoja() {
		assertFalse(sPadre.esHoja());
		assertTrue(sHijo.esHoja());
	}

	@Test
	public void testGetActividadFecha() {
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		Actividad a = sHijo.getActividadFecha(LocalDate.now());
		assertEquals(el.getNombre(), a.getNombre());
	}

	@Test
	public void testGetActividad() {
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		assertEquals(el, sHijo.getActividad(LocalDate.now(), h));
	}

	@Test
	public void testAddActividad() {
		try {
			assertTrue(sHijo.addActividad(el));
			assertTrue(sHijo.getActividades().contains(el));
			assertTrue(sHijo.addActividad(el2));
			assertTrue(sHijo.getActividades().contains(el2));
			assertFalse(sHijo.addActividad(el3));
			assertFalse(sHijo.getActividades().contains(el3));
			assertThrows(SalaNoCompatibleException.class, () -> sPadre.addActividad(el3));
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		
	}

	@Test
	public void testEliminarActividad() {
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		
		sHijo.eliminarActividad(el);
		assertFalse(sHijo.getActividades().contains((el)));
	}

	@Test
	public void testNextActividad() {
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}

		assertEquals(el, sHijo.nextActividad());
	}

	@Test
	public void testOcupadaAhora() {
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		assertFalse(sHijo.ocupada());
		
		try {
			sHijo.addActividad(el);
		} catch (IllegalArgumentException | SalaNoCompatibleException e) {
			e.printStackTrace();
			fail("No deberia lanzar excepcion");
		}
		assertFalse(sHijo.ocupada());
	}

	@Test
	public void testAforoHijos() {
		assertEquals(5, sPadre.aforoHijos());
	}

	@Test
	public void testComprobarAforoHijos() {
		assertTrue(sPadre.comprobarAforoHijos());
	}

	@Test
	public void testExportarSala() {
		assertTrue(sPadre.exportarSala());
	}

	@Test
	public void testImportarSala() {
		assertTrue(sPadre.exportarSala());
		Sala s = Sala.importarSala();
		assertTrue(sPadre.equals(s));
		assertEquals(sPadre.hashCode(), s.hashCode());
		assertEquals(sPadre.toString(), s.toString());
	}
}
