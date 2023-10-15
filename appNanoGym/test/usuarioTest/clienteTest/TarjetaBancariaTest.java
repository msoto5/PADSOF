package usuarioTest.clienteTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.cliente.TarjetaBancaria;

class TarjetaBancariaTest {

	String numTarjeta = "1234567890123456";
	LocalDate f1 = LocalDate.of(2022, 12, 12);
	LocalDate fCaducidad = LocalDate.of(2023, 12, 12);
	String cvv = "cvv";
	TarjetaBancaria tb;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		//fCaducidad = LocalDate.of(2023, 30, 12);
		tb = new TarjetaBancaria(numTarjeta, fCaducidad, cvv);
	}

	@Test
	void testTarjetaBancaria() {
		assertEquals(numTarjeta, tb.getNumeroTarjeta());
		assertEquals(fCaducidad, tb.getFechaCaducidad());
		assertEquals(cvv, tb.getCvv());
	}

	@Test
	void testFechaCaducidadValida(){
		assertTrue(tb.fechaCaducidadValida());
	}

	@Test
	void testCobrarDoubleStringBoolean() {
		boolean trace = true;
		double cantidad = 12.3;
		String descripcion = "descripcion_test";
		assertTrue(tb.cobrar(cantidad, descripcion, trace));
	}

	@Test
	void testCobrarDoubleString() {
		double cantidad = 12.3;
		String descripcion = "descripcion_test";
		assertTrue(tb.cobrar(cantidad, descripcion));
	}
}
