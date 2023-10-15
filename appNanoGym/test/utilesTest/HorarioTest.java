package utilesTest;

import org.junit.*;
import static org.junit.Assert.*;

import utiles.Horario;
import java.time.LocalTime;

public class HorarioTest {
	LocalTime t1 = LocalTime.of(18,0,0);
	LocalTime t2 = LocalTime.of(19,0,0);
	LocalTime t3 = LocalTime.of(20,0,0);
	LocalTime t0 = LocalTime.of(17,0,0);
	Horario h1 = new Horario(t1, t2);
	Horario h2;
	LocalTime hora = LocalTime.of(18, 30, 0); 

	@Test
	public void testHorario(){
		assertEquals(t1, h1.getHoraInicio());
		assertEquals(t2, h1.getHoraFin());
	}

	@Test
	public void testHorarioException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			h2 = new Horario(t2, t1);
		});
		assertEquals("La hora de fin no puede ser anterior o igual a la hora de inicio", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			Horario h2 = new Horario(t2, t2);
		});
		assertEquals("La hora de fin no puede ser anterior o igual a la hora de inicio", exception.getMessage());
	}

	@Test
	public void testSetGetHoraInicio() {
		h1.setHoraInicio(t0);
		assertEquals(t0, h1.getHoraInicio());
	}

	@Test
	public void testSetGetHoraInicioException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			h1.setHoraInicio(t3);
		});
		assertEquals("La hora de inicio no puede ser posterior o igual a la hora de fin", exception.getMessage());
	}

	@Test
	public void testSetGetHoraFin() {
		h1.setHoraFin(t3);
		assertEquals(t3, h1.getHoraFin());
	}

	@Test
	public void testSetGetHoraFinException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			h1.setHoraFin(t0);
		});
		assertEquals("La hora de fin no puede ser anterior o igual a la hora de inicio", exception.getMessage());
	}

	@Test
	public void testToString() {
		assertEquals(t1 + " - " + t2, h1.toString());
	}

	@Test
	public void testHorarioIncluido(){
		assertTrue(h1.horarioIncluido(h1));
	}

	@Test
	public void testHoraIncluida(){
		assertTrue(h1.horaIncluida(hora));
	}

	@Test
	public void testHorarioSeSolapan(){
		assertTrue(h1.horarioSolapan(h1));
	}

	@Test
	public void testCompareTo(){
		LocalTime hora = LocalTime.of(10,0,0);
		LocalTime hora2 = LocalTime.of(11,0,0);
		Horario h = new Horario(hora, hora2);
		assertEquals(1, h1.compareTo(h));
	}

}
