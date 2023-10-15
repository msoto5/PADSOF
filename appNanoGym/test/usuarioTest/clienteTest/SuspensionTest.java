package usuarioTest.clienteTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.cliente.Cliente;
import usuario.cliente.Suspension;

class SuspensionTest {
	LocalDate f1;
	Suspension s;

	@BeforeEach
	void setUp() throws Exception {
		this.f1 = LocalDate.of(1999, 12, 12);
		this.s = new Suspension();
	}

	@Test
	void testSuspension(){
		assertEquals(s.getfInicial(), LocalDate.now());
	}

	@Test
	void testSetGetDiasSuspension(){
		Suspension.setDiasSuspension(10);
		assertEquals(10, Suspension.getDiasSuspension());
	}

	@Test
	void testIsSuspendido() {
		Suspension s = new Suspension();
		assertEquals(true, s.isSuspendido());
		Cliente c1 = new Cliente("ms", "1234", "Miguel Soto", LocalDate.of(1999, 12, 12));
		for (int i = 0; i < Cliente.getMAX_FALTAS(); i++) {
			c1.addFalta();
		}
		assertEquals(true, c1.isSuspendido());
	}
}

