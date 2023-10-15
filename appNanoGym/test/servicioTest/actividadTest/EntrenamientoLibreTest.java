package servicioTest.actividadTest;

import java.time.*;

import servicio.actividad.*;
import utiles.Horario;
import sala.*;

import org.junit.*;
import static org.junit.Assert.*;

public class EntrenamientoLibreTest {

	private static Horario h1;
	private static Sala s1;
	private static LocalDate fecha;
	private static EntrenamientoLibre el;
	private static String nombre = "Pesas";
	private static String descripcion = "ejercicios pesas";

	@Before
	public void setUpBeforeClass() throws Exception {
		h1 = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));
		s1 = new Sala("Principal", 20, "Yoga");
		fecha = LocalDate.of(2023, 12, 03);
		el = new EntrenamientoLibre(nombre, descripcion, h1, fecha, s1);
	}

	@Test
	public void testEntrenamientoLibre() {
		Assert.assertEquals(nombre, el.getNombre());
		Assert.assertEquals(descripcion, el.getDescripcion());
		Assert.assertEquals(h1, el.getHorario());
		Assert.assertEquals(fecha, el.getFecha());
		Assert.assertEquals(s1, el.getSala());
	}

	@Test
	public void testSetGetPRECIO() {
		EntrenamientoLibre.setPRECIO(10);
		assertEquals(10, EntrenamientoLibre.getPRECIO(), 0);
	}

	@Test
	public void testGetPrecio(){
		assertEquals(EntrenamientoLibre.getPRECIO(), el.getPrecio(), 0);
	}

	@Test
	public void testImprimirInfoGrupal(){
		assertFalse(el.imprimirInfoGrupal());
	}

	@Test
	public void testGetTipo(){
		String expected = "Entrenamiento libre";
		assertTrue(el.getBusquedaString().contains(expected));
	}

}
