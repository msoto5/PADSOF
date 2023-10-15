package utilesTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import utiles.GestorNotificaciones;

class GestorNotificacionesTest {

	private GestorNotificaciones gN;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		gN = new GestorNotificaciones();
	}

	@Test
	void testGetAddNotificacion() {
		assertTrue(gN.getNotificaciones().isEmpty());
		gN.addNotificacion("Not 1");
		gN.addNotificacion("Not 2");
		assertEquals(2, gN.getNotificaciones().size());
	}

	@Test
	void testReadNotificaciones() {
		gN.addNotificacion("Not 1");
		gN.addNotificacion("Not 2");
		assertEquals(2, gN.getNotificaciones().size());
		List<String> not = gN.readNotificaciones();
		assertTrue(gN.getNotificaciones().isEmpty());
		assertEquals(2, not.size());
	}
}
