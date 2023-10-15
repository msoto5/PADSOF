package usuarioTest.clienteTest;

import java.time.LocalDate;

import org.junit.*;
import static org.junit.Assert.*;

import usuario.cliente.Cliente;
import usuario.cliente.Suspension;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.TarifaPagoUso;
import usuario.cliente.tarifa.TarifaPlana;
import utiles.EstadoReserva;
import utiles.Horario;
import utiles.Reserva;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.TipoActividad;

import java.time.LocalTime;
import java.util.LinkedList;

import sala.Sala;

public class ClienteTest {
	LocalDate f1, f2;
	TarifaPagoUso t1 = new TarifaPagoUso();
	Cliente c0, c1, c2;
	TarjetaBancaria tb1, tb2;
	Reserva r1, r2;
	EntrenamientoLibre el1, el2;
	TipoActividad ta1;
	Horario h1;
	Sala sala1, sala2;

	@Before
	public void setUp() throws Exception {
		this.ta1 = new TipoActividad("Pilates");
		this.h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));

		this.f1 = LocalDate.of(1999, 12, 12);
		this.f2 = LocalDate.of(2000, 12, 12);

		this.c1 = new Cliente("ms", "1234", "Miguel Soto", f1);
		this.c2 = new Cliente("nv", "1234", "Nicolas Victorino", f1);

		this.sala1 = new Sala("sala1", 10, "salita");

		this.el1 = new EntrenamientoLibre("el1", "el1_desc", h1, f1, sala1);
		this.el2 = new EntrenamientoLibre("el2", "el2_desc", h1, f1, sala1);

		this.r1 = new Reserva(c1, el1, EstadoReserva.RESERVADA);
		this.r2 = new Reserva(c1, el2, EstadoReserva.RESERVADA);

		String numTarjeta = "1234567890123456";
		try {
			LocalDate f2 = LocalDate.of(2030, 12, 12);
			this.tb1 = new TarjetaBancaria(numTarjeta, f2, "123");
			LocalDate f3 = LocalDate.of(2020, 12, 12);
			this.tb2 = new TarjetaBancaria(numTarjeta, f3, "123");
		} catch (Exception e) {}
	}

	@Test
	public void testCliente() {
		this.c0 = new Cliente("ms", "1234", "Miguel Soto", f1);
		assertEquals("ms", c1.getUsername());
		assertEquals("1234", c1.getContraseña());
		assertEquals("Miguel Soto", c1.getNombreCompleto());
		assertEquals(f1, c1.getFechaNacimiento());
		assertEquals(0, c1.getNumFaltas());
		assertEquals(0, c1.getGastoTotal(), 0);
		assertEquals(null, c1.getTarjetaBancaria());
		assertEquals(0, c1.getReservas().size());
		assertEquals(null, c1.getSuspension());
	}

	@Test
	public void testSetGetNombreCompleto() {
		String nombre = "Nicolas Victorino";
		c1.setNombreCompleto(nombre);
		assertEquals(nombre, c1.getNombreCompleto());
	}

	@Test
	public void testSetGetFechaNacimiento() {
		c1.setFechaNacimiento(f2);
		assertEquals(f2, c1.getFechaNacimiento());
	}

	@Test
	public void testSetGetNumFaltas() {
		int numF = 2;
		c1.setNumFaltas(numF);
		assertEquals(numF, c1.getNumFaltas());
	}

	@Test
	public void testAddFalta(){
		int numF = 1;
		c1.setNumFaltas(numF);
		c1.addFalta();
		int expected = 2;
		assertEquals(expected, c1.getNumFaltas());
	}

	@Test
	public void testResetFalta(){
		c1.resetFaltas();
		assertEquals(0, c1.getNumFaltas());
	}

	@Test
	public void testSetGetGastoTotal() {
		c1.setGastoTotal(100);
		assertEquals(100, c1.getGastoTotal(), 0);
	}

	@Test
	public void testAddGasto(){
		c1.setGastoTotal(100);
		c1.addGasto(100);
		int expected = 200;
		assertEquals(expected, c1.getGastoTotal(), 0);
	}

	@Test
	public void testSetGetTarjetaBancaria() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> c1.setTarjetaBancaria(tb2));
		assertEquals("La tarjeta bancaria no puede ser nula o estar caducada.", e.getMessage());

		c1.setTarjetaBancaria(tb1);
		assertEquals(tb1, c1.getTarjetaBancaria());
	}

	@Test
	public void testSetGetTarifa() {
		TipoActividad ta = new TipoActividad("Pilates");
		TarifaPlana tp = new TarifaPlana(3, ta);
		c1.setTarifa(tp);
		assertEquals(tp, c1.getTarifa());
	}

	@Test
	public void testRenovarTarifa(){

	}

	@Test
	public void testSetGetReservas() {
		LinkedList<Reserva> reservas = new LinkedList<Reserva>();
		reservas.add(r1);
		reservas.add(r2);
		c1.setReservas(reservas);
		assertEquals(reservas, c1.getReservas());
	}

	@Test
	public void testAddRemoveReserva(){
		c1.addReserva(r1);
		assertEquals(true, c1.getReservas().contains(r1));
		c1.removeReserva(r1);
		assertEquals(false, c1.getReservas().contains(r1));
	}

	@Test
	public void testSetGetSuspension() {
		Suspension sus1 = new Suspension();
		c1.setSuspension(sus1);
		assertEquals(sus1, c1.getSuspension());
	}

	@Test
	public void testOperacionesSuspender() {
		c1.suspender();
		assertEquals(true, c1.isSuspendido());
		c1.eliminarSuspension();
		assertEquals(false, c1.isSuspendido());
	}

	@Test
	public void testSetGetMAX_FALTAS() {
		int before = Cliente.getMAX_FALTAS();
		Cliente.setMAX_FALTAS(before + 2);
		assertEquals(before + 2, Cliente.getMAX_FALTAS());
	}

	@Test
	public void testOperacionesCobrar() {
		this.c1.setTarifa(t1);
		this.c1.setTarjetaBancaria(tb1);
		double gasto = 10.0;
		String desc = "Pago de reserva";
		double gBefore = c1.getGastoTotal();
		c1.cobrar(gasto, desc);
		assertEquals(gasto + gBefore, c1.getGastoTotal(), 0);
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> c1.cobrar(-1, desc));
		assertEquals("La cantidad a cobrar no puede ser negativa. Si quieres devolver un cobro, usa el método devolverCobro", e.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> c1.devolverCobro((-1) * gasto, desc));
		assertEquals("La cantidad a devolver no puede ser negativa. Si quieres cobrar, usa el método cobrar", e2.getMessage());

		c1.devolverCobro(gasto, "Devolucion " + desc);
		assertEquals(gBefore, c1.getGastoTotal(), 0);
	}

	@Test
	public void testOperacionesReserva() {
		this.c1 = new Cliente("ms", "1234", "Miguel Soto", f1);
		assertEquals(0, c1.getReservas().size());
		this.c1.addReserva(r1);
		assertEquals(1, c1.getReservas().size());
		try {
			this.r2 = new Reserva(c2, el2, EstadoReserva.RESERVADA);
			this.c2.addReserva(r2);
			assertEquals(1, c2.getReservas().size());
		} catch (Exception e) {
			fail("No debe dar error" + e.getMessage());
		}
		
		this.c1.removeReserva(r1);
		assertEquals(0, c1.getReservas().size());
	}

	@Test
	public void testOperacionesFalta() {
		c1.setNumFaltas(0);
		for (int i = 0; i < Cliente.getMAX_FALTAS(); i++) {
			c1.addFalta();
		}
		assertEquals(Cliente.getMAX_FALTAS(), c1.getNumFaltas());
		assertEquals(true, c1.isSuspendido());
		c1.resetFaltas();
		assertEquals(0, c1.getNumFaltas());
	}

	@Test
	public void testOperacionesGasto() {
		double gasto = c1.getGastoTotal();
		c1.addGasto(200);
		assertEquals(200 + gasto, c1.getGastoTotal(), 0);
	}

	@Test
	public void testOperacionesTarifa() {
		int dur1 = 3;
		TipoActividad ta = new TipoActividad("Pilates");
		TarifaPlana tp = new TarifaPlana(dur1, ta);
		c1.setTarifa(tp);
		assertEquals(tp, c1.getTarifa());
		c1.renovarTarifa(1);
		assertEquals(dur1 + 1, ((TarifaPlana) c1.getTarifa()).getDuracionMes());
	}

	@Test
	public void testEsCliente(){
		assertTrue(c1.esCliente());
	}

	@Test
	public void testEsMonitor(){
		assertFalse(c1.esMonitor());
	}

}
