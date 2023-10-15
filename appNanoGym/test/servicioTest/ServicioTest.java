package servicioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.ServicioAtMaximunCapacityException;
import sala.Sala;
import servicio.*;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.Monitor;
import usuario.cliente.Cliente;
import utiles.*;

import org.junit.Assert;

class ServicioTest {

	private Servicio s;
	private PriorityQueue<Reserva> r;
	private Reserva r1;
	private Monitor m1;
	private List<ActividadMonitor> ams1 = new ArrayList<ActividadMonitor>();
	private Cliente c1;
	private LocalDate f1;
	private ActividadGrupal ag1;
	private Horario h1;
	private Sala sala1;
	private TipoActividad ta1;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		ta1 = new TipoActividad("Pilates");
		sala1 = new Sala("sala1", 10, "salita");
		h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
		f1 = LocalDate.of(1999, 12, 12);
		m1 = new Monitor("ic", "1234", "Iker Casillas", "iker@gmail.com", "12345678A");
		ag1 = new ActividadGrupal("ag1", "ag1_desc", h1, f1, sala1, m1, ta1);
		ams1.add(ag1);
		s = new EntrenamientoPersonalizado("ep2", "ep2_desc", m1, ams1, "Mejorar salud");
		r = new PriorityQueue<Reserva>();
		c1 = new Cliente("ms", "1234", "Miguel Soto", f1);
		r1 = new Reserva(c1, s, EstadoReserva.RESERVADA);
		
	}

	@Test
	void testServicio() {
		assertEquals("ep2", s.getNombre());
		assertEquals("ep2_desc", s.getDescripcion());
	}

	@Test
	void testSetGetNombre(){
		s.setNombre("servicio");
		assertEquals("servicio", s.getNombre());
	}

	@Test
	void testSetGetDescripcion(){
		s.setDescripcion("desc");
		assertEquals("desc", s.getDescripcion());
	}

	@Test
	void testSetGetReservas() throws IllegalArgumentException, ServicioAtMaximunCapacityException {
		s.setReservas(r);
		assertEquals(r, s.getReservas());
	}

	@Test
	void testAddReserva() throws IllegalArgumentException, ServicioAtMaximunCapacityException {
		assertTrue(s.addReserva(r1));
	}

	@Test
	void testRemoveReserva() {
		s.removeReserva(r1);
		Assert.assertNotEquals(r1, s.getReservas());
	}

	@Test
	void testExportarImportarServicio() {
		s.exportarServicio();
		Servicio s2 = Servicio.importarServicio();
		assertEquals(s, s2);
	}
}
