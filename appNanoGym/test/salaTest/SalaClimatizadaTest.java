package salaTest;

import org.junit.*;
import static org.junit.Assert.*;

import exceptions.SalaConAforoMaximoException;
import exceptions.SalaNoCompatibleException;
import sala.*;
import utiles.Horario;

import java.time.*;

public class SalaClimatizadaTest {
	private Horario h = new Horario(LocalTime.now().minusHours(5), 
									LocalTime.now().minusHours(1));
	private SalaClimatizada sPadre = new SalaClimatizada(h, "sHijo", 5, "des_sHijo");
	private SalaClimatizada sHijo;
	private Sala sala;

	@Before
	public void setUp() {
		try {
			sHijo = sPadre.crearSalaHija(h, "sHijo", 1, "des_sHijo");
			sala = new Sala("sala", 1, "des_sala");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSalaHabilitada() {
		assertFalse(sPadre.habilitada());
	}

	@Test
	public void testSetHijo() {
		try {
			sPadre.setHijo(sHijo);
			assertTrue(sHijo.getPadre().equals(sPadre));
		} catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
			fail(e.getMessage());
		}
		assertThrows(SalaNoCompatibleException.class, () -> sPadre.setHijo(sala));
	}
}
