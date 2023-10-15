package servicioTest.actividadTest;

import static org.junit.Assert.*;

import java.time.*;

import servicio.actividad.*;
import utiles.Horario;
import sala.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.Assert;

class ActividadTest {
	private static Actividad a;
	private static String nombre = "Pesas";
	private static String descripcion = "ejercicios pesas";
	private static Horario h1;
	private static LocalDate fecha;
	private static Sala s1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		h1 = new Horario(LocalTime.of(18,0,0), LocalTime.of(19,0,0));
		fecha = LocalDate.of(2023, 12, 03);
		s1 = new Sala("Principal", 20, "Yoga");
		a = new EntrenamientoLibre(nombre, descripcion, h1, fecha, s1);
	}

	@Test
	void testActividad() {
		Assert.assertEquals(nombre, a.getNombre());
		Assert.assertEquals(descripcion, a.getDescripcion());
		Assert.assertEquals(h1, a.getHorario());
		Assert.assertEquals(fecha, a.getFecha());
		Assert.assertEquals(s1, a.getSala());
	}

	@Test
	void testSetGetHorario(){
		a.setHorario(h1);
		assertEquals(h1, a.getHorario());
	}

	@Test
	void testSetGetFecha(){
		a.setFecha(fecha);
		assertEquals(fecha, a.getFecha());
	}

	@Test
	void testSetSala(){
		assertTrue(a.setSala(s1));
	}

	@Test
	void testGetSala(){
		a.setSala(s1);
		assertEquals(s1, a.getSala());
	}

	@Test
	void testAforoDisponible() {
		int expected = s1.getAforo() - a.getReservas().size();
		Assert.assertEquals(expected, a.aforoDisponible());
	}

}
