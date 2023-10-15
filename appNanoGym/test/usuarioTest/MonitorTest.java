package usuarioTest;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.*;
import static org.junit.Assert.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.time.Month;

import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.cliente.Cliente;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.TarifaPagoUso;
import utiles.EstadoReserva;
import utiles.Horario;
import utiles.Reserva;
import usuario.Monitor;

public class MonitorTest {
	String nif = "12345678A";

	LocalDate f1, f2, f3, f4;
	TarifaPagoUso t1 = new TarifaPagoUso();
	Cliente c0, c1, c2;
	TarjetaBancaria tb1, tb2;
	Reserva r1, r2;
	EntrenamientoPersonalizado ep1, ep2;
	ActividadGrupal ag1, ag2, ag3, ag4;
	TipoActividad ta1;
	Horario h1;
	Sala sala1, sala2;
	Monitor m0, m1, m2;
	LinkedList<ActividadMonitor> actividades = new LinkedList<>();
	ActividadMonitor am;
	String nombre = "Spinning";
	String descripcion = "ciclismo indoor";
	LocalDate fecha;
	TipoActividad ta;
	LinkedList<EntrenamientoPersonalizado> entrenosPersonalizado = new LinkedList<>();
	HashMap<Integer, Integer> horasTrabajadas;

	Collection<ActividadMonitor> ams1 = new ArrayList<ActividadMonitor>();
	Collection<ActividadMonitor> ams2 = new ArrayList<ActividadMonitor>();

	@Before
	public void setUp() throws Exception {
		this.ta1 = new TipoActividad("Pilates");
		this.h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));

		this.f1 = LocalDate.of(1999, 12, 12);
		this.f2 = LocalDate.of(2000, 12, 12);
		this.f3 = LocalDate.of(2023, 12, 12);
		this.f4 = LocalDate.of(2024, 12, 12);


		this.c1 = new Cliente("ms", "1234", "Miguel Soto", f1);
		this.c2 = new Cliente("nv", "1234", "Nicolas Victorino", f1);

		this.m1 = new Monitor("ic", "1234", "Iker Casillas", "iker@gmail.com", nif);
		this.m2 = new Monitor("dg", "1234", "Ah", "Ah@no.com", nif);

		this.sala1 = new Sala("sala1", 10, "salita");
		this.sala2 = new Sala("sala2", 10, "salita");

		this.ag1 = new ActividadGrupal("ag1", "ag1_desc", h1, f3, sala1, m1, ta1);
		this.h1 = new Horario(LocalTime.of(19, 0, 0), LocalTime.of(20, 0, 0));
		this.ag2 = new ActividadGrupal("ag2", "ag2_desc", h1, f4, sala1, m1, ta1);
		this.h1 = new Horario(LocalTime.of(20, 0, 0), LocalTime.of(21, 0, 0));
		this.ag3 = new ActividadGrupal("ag3", "ag3_desc", h1, f3, sala2, m1, ta1);
		this.h1 = new Horario(LocalTime.of(21, 0, 0), LocalTime.of(22, 0, 0));
		this.ag4 = new ActividadGrupal("ag4", "ag4_desc", h1, f4, sala2, m1, ta1);

		this.ams1.add(ag1);
		this.ams1.add(ag2);
		this.ams2.add(ag3);
		this.ams2.add(ag4);

		this.ep1 = new EntrenamientoPersonalizado("ep1", "ep1_desc", m1, ams1, "Mejorar salud");
		this.ep2 = new EntrenamientoPersonalizado("ep2", "ep2_desc", m2, ams2, "Mejorar salud");

		this.r1 = new Reserva(c1, ep1, EstadoReserva.RESERVADA);
		this.r2 = new Reserva(c1, ep2, EstadoReserva.RESERVADA);

		this.ta =new TipoActividad("tipo");
		this.fecha = LocalDate.of(2023, 12, 03);
		this.am = new ActividadGrupal(nombre, descripcion, h1, fecha, sala1, m1, ta);

		this.horasTrabajadas = new HashMap<Integer, Integer>();
		this.horasTrabajadas.put(10, 20);

		String numTarjeta = "1234567890123456";
		try {
			LocalDate f2 = LocalDate.of(2030, 12, 12);
			this.tb1 = new TarjetaBancaria(numTarjeta, f2, "123");
			LocalDate f3 = LocalDate.of(2020, 12, 12);
			this.tb2 = new TarjetaBancaria(numTarjeta, f3, "123");
		} catch (Exception e) {}
	}

	@Test
	public void testMonitor(){
		assertEquals("ic", m1.getUsername());
		assertEquals("1234", m1.getContraseña());
		assertEquals("Iker Casillas", m1.getNombreCompleto());
		assertEquals("iker@gmail.com", m1.getEmail());
		assertEquals(nif, m1.getNif());
	}

	@Test
	public void testSetGetNombreCompleto() {
		this.m1.setNombreCompleto("Fernando Alonso");
		assertEquals("Fernando Alonso", this.m1.getNombreCompleto());
	}

	@Test
	public void testSetGetEmail() {
		this.m1.setEmail("moni_monitor@estudiohoynomañana.com");
		assertEquals("moni_monitor@estudiohoynomañana.com", this.m1.getEmail());
	}

	@Test
	public void testSetHorasTrabajadas(){
		this.m1.setHorasTrabajadas(horasTrabajadas);
		assertNotEquals(horasTrabajadas, null);
	}

	@Test
	public void testGetAddHorasTrabajadas() {
		int horas = 1000000000;
		Month m = Month.MARCH;
		Year y = Year.of(2023);
		this.m1.addHorasTrabajadas(m, y, horas);
		assertEquals(horas, this.m1.getHorasTrabajadas(m, y));
	}

	

	@Test
	public void testSetGetActividades(){
		this.m1.setActividades(actividades);
		assertEquals(actividades, this.m1.getActividades());
	}

	@Test
	public void testAddActividad(){
		this.m1.addActividad(ag1);
		assertEquals(true, this.m1.getActividades().contains(ag1));
	}

	@Test
	public void testRemoveActividad(){
		this.m2.addActividad(ag1);
		this.m2.removeActividad(ag1);
		assertEquals(false, this.m2.getActividades().contains(ag1));
	}

	@Test
	public void testSetGetEntrenosPersonalizado(){
		this.m1.setEntrenosPersonalizado(entrenosPersonalizado);
		assertEquals(entrenosPersonalizado, this.m1.getEntrenosPersonalizado());
	}

	@Test
	public void testAddRemoveEntrenamientoPersonalizado() {
		this.m1.addEntrenamientoPersonalizado(ep1);
		assertEquals(true, this.m1.getEntrenosPersonalizado().contains(ep1));
		this.m1.removeEntrenamientoPersonalizado(ep1);
		assertEquals(false, this.m1.getEntrenosPersonalizado().contains(ep1));
	}

	@Test
	public void testSetGetNOMINA_BASE() {
		Monitor.setNOMINA_BASE(100);
		assertEquals(100, Monitor.getNOMINA_BASE(), 0);
	}

	@Test
	public void testSetGetEXTRA_SUELDO_SESION() {
		Monitor.setEXTRA_SUELDO_SESION(100);
		assertEquals(100, Monitor.getEXTRA_SUELDO_SESION(), 0);
	}

	@Test
	public void testGetName(){
		assertEquals("Iker Casillas", this.m1.getName());
	}

	@Test
	public void testGetNif() {
		assertEquals(nif, this.m1.getNif());
	}

	@Test
	public void testGenerarPDF() {
		Month m = Month.JANUARY;
		Year y = Year.of(2023);
		m1.addHorasTrabajadas(m, y, 100);
		assertEquals(false, this.m1.generarPDF(m1, m, y));
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	}

	@Test
	public void testDisponible1(){
		LocalTime hora = LocalTime.of(12, 30, 0); 
		assertTrue(this.m1.disponible(f1, hora));
	}

	@Test
	public void testDisponible2(){
		assertTrue(this.m1.disponible(f1, h1));
	}

	@Test
	public void testEsCliente(){
		assertFalse(m1.esCliente());
	}

	@Test
	public void testEsMonitor(){
		assertTrue(m1.esMonitor());
	}

	@Test
	public void testCancelarActividadMonitor(){
		this.m1.addActividad(ag1);
		assertTrue(this.m1.cancelarActividadMonitor(ag1));
		this.m1.removeActividad(ag1);
		assertFalse(this.m1.cancelarActividadMonitor(ag1));
	}

	@Test
	public void testCancelarEntrenamientoPersonalizado(){
		this.m1.addEntrenamientoPersonalizado(ep1);
		assertTrue(this.m1.cancelarEntrenamientoPersonalizado(ep1));
		this.m1.removeEntrenamientoPersonalizado(ep1);
		assertFalse(this.m1.removeEntrenamientoPersonalizado(ep1));
	}

	@Test
	public void testEquals(){
		assertTrue(this.m1.equals(this.m1));
	}

	@Test
	public void testHashCode(){
		assertEquals(Objects.hash(nif), this.m1.hashCode());
	}

}
