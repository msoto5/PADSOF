package servicioTest;

import org.junit.*;
import static org.junit.Assert.*;

import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import servicio.actividad.actividadmonitor.*;
import usuario.Monitor;
import usuario.cliente.Cliente;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.tarifa.TarifaPagoUso;
import utiles.*;
import java.time.*;
import java.util.*;

public class EntrenamientoPersonalizadoTest {
	String nombre = "ep1";
	String descripcion = "ep1_desc";
	String objetivo = "Mejorar salud";
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
	ListaEspera le;
	Set<String> objetivosEp;

	List<ActividadMonitor> ams1 = new ArrayList<ActividadMonitor>();
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

		this.ep1 = new EntrenamientoPersonalizado("ep1", "ep1_desc", m1, ams1, objetivo);
		this.ep2 = new EntrenamientoPersonalizado("ep2", "ep2_desc", m2, ams2, "Mejorar salud");
		//System.out.println(ep1.getActividades());
		//System.out.println(ep2.getActividades());

		this.r1 = new Reserva(c1, ep1, EstadoReserva.RESERVADA);
		this.r2 = new Reserva(c1, ep2, EstadoReserva.RESERVADA);

		this.le = new ListaEspera(ep1);

		this.objetivosEp = new HashSet<String>(Arrays.asList("Perder peso", "Ganar masa muscular", "Mejorar rendimiento deportivo", "Mejorar salud", "Mejorar estado físico", "Mejorar estado mental"));

		String numTarjeta = "1234567890123456";
		try {
			LocalDate f2 = LocalDate.of(2030, 12, 12);
			this.tb1 = new TarjetaBancaria(numTarjeta, f2, "123");
			LocalDate f3 = LocalDate.of(2020, 12, 12);
			this.tb2 = new TarjetaBancaria(numTarjeta, f3, "123");
		} catch (Exception e) {}
	}

	@Test
	public void testEntrenamientoPersonalizado() {
		assertEquals(nombre, ep1.getNombre());
		assertEquals(descripcion, ep1.getDescripcion());
		assertEquals(m1, ep1.getMonitor());
		assertEquals("Mejorar salud", ep1.getObjetivo());
	}

	@Test
	public void testGetMonitor() {
		assertEquals(m1, ep1.getMonitor());
	}

	@Test
	public void testGetActividades(){
		assertEquals(ams1, ep1.getActividades());
	}

	@Test
	public void testGetActividadM(){
		int classNumber = 2;
		assertEquals(ams1.get(classNumber - 1), ep1.getActividadM(classNumber));
	}

	@Test
	public void testAddActividad() {
		assertTrue(ep1.addActividad(ag2));
	}

	@Test
	public void testRemoveActividad() {
		assertTrue(ep1.removeActividad(ag2));
	}

	@Test
	public void testGetObjetivo(){
		assertEquals(objetivo, ep1.getObjetivo());
	}

	@Test
	public void testAddReserva(){
		assertTrue(ep1.addReserva(r1));
	}

	@Test
	public void testRemoveReserva(){
		ep1.addReserva(r1);
		assertTrue(ep1.removeReserva(r1));
	}

	@Test
	public void testGetListaEspera(){
		ep1.setListaEspera(le);
		assertEquals(le, ep1.getListaEspera());
	}

	@Test
	public void testAddReservaListaEspera(){
		r1.setEstado(EstadoReserva.LISTA_ESPERA);
		assertTrue(ep1.addReservaListaEspera(r1));
	}

	@Test
	public void testRemoveReservaListaEspera(){
		r1.setEstado(EstadoReserva.LISTA_ESPERA);
		ep1.addReservaListaEspera(r1);
		assertTrue(ep1.removeReservaListaEspera(r1));
	}

	@Test
	public void testGetPRECIO(){
		EntrenamientoPersonalizado.setPRECIO(10.0);
		assertEquals(10.0, EntrenamientoPersonalizado.getPRECIO(), 0);
	}

	@Test
	public void testGetAFORO_MAX(){
		EntrenamientoPersonalizado.setAFORO_MAX(1);
		assertEquals(1, EntrenamientoPersonalizado.getAFORO_MAX(), 0);
	}

	@Test
	public void testGetObjetivoEp(){
		EntrenamientoPersonalizado.setObjetivos(objetivosEp);
		assertEquals(objetivosEp, EntrenamientoPersonalizado.getObjetivosEp());
	}

	@Test
	public void testAddObjetivo() {
		assertTrue(EntrenamientoPersonalizado.addObjetivo("PerderPeso"));
	}

	@Test
	public void testRemoveObjetivo() {
		assertTrue(EntrenamientoPersonalizado.removeObjetivo("PerderPeso"));
	}

	@Test
	public void testGetFecha() {
		assertEquals(ep1.getActividadM(1).getFecha(), ep1.getFecha());
	}

	@Test
	public void testNumSesionesTotal() {
		assertEquals(ep1.getActividades().size(), ep1.numSesionesTotal());
	}

	@Test
	public void testAforoDisponible() {
		int expected = (int) (EntrenamientoPersonalizado.getAFORO_MAX() - ep1.getReservas().size());
		assertEquals(expected, ep1.aforoDisponible());
	}

	
	@Test
	public void testGetDateInicial() {
		assertEquals(ep1.getFecha(), ep1.getDateInicial());
	}

	@Test
	public void testGetDateFinal(){
		assertEquals(ams1.get(ams1.size()-1).getFecha(), ep1.getDateFinal());
	}

	@Test
	public void testMoverReservaListaEspera() {
		assertNotEquals(r1, ep1.getListaEspera());
	}

}
