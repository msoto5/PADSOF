package utilesTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.PriorityQueue;

import org.junit.*;
import static org.junit.Assert.*;

import sala.Sala;
import servicio.actividad.EntrenamientoLibre;
import usuario.cliente.Cliente;
import usuario.cliente.tarifa.TarifaPagoUso;
import utiles.EstadoReserva;
import utiles.Horario;
import utiles.ListaEspera;
import utiles.Reserva;

public class ListaEsperaTest {
	LocalDate f1, f2;
	TarifaPagoUso t1 = new TarifaPagoUso();
	Cliente c1, c2;
	Reserva r1, r2;
	EntrenamientoLibre el1, el2;
	Horario h1;
	Sala sala1, sala2;
	ListaEspera le1, le2;
	PriorityQueue<Reserva> listaEspera;

	@Before
	public void setUp() throws Exception {
		LocalDate f1 = LocalDate.of(1999, 12, 12);
		LocalDate f2 = LocalDate.of(2023, 12, 12);
		this.h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
		
		this.c1 = new Cliente("ms", "1234", "Miguel Soto", f1);
		this.c2 = new Cliente("nv", "1234", "Nicolas Victorino", f1);
		this.sala1 = new Sala("sala1", 10, "salita");

		this.el1 = new EntrenamientoLibre("el1", "el1_desc", h1, f2, sala1);
		this.el2 = new EntrenamientoLibre("el2", "el2_desc", h1, f2, sala1);
		this.r1 = new Reserva(c1, el1, EstadoReserva.LISTA_ESPERA);
		this.r2 = new Reserva(c2, el1, EstadoReserva.LISTA_ESPERA);

		this.le1 = new ListaEspera(this.el1);

		this.listaEspera = new PriorityQueue<Reserva>();
	}

	@Test
	public void testListaEspera(){
		assertEquals(this.el1, this.le1.getServicio());
	}

	@Test
	public void testSetGetServicio() {
		this.le1.setServicio(this.el2);
		assertEquals(this.el2, this.le1.getServicio());
	}

	@Test
	public void testSetGetListaEspera(){
		this.le1.setListaEspera(listaEspera);
		assertEquals(this.listaEspera, this.le1.getListaEspera());
	}

	@Test
	public void testOperacionesReserva() {
		this.r1.setEstado(EstadoReserva.LISTA_ESPERA);
		this.r2.setEstado(EstadoReserva.LISTA_ESPERA);
		this.le1.addReserva(this.r1);
		this.le1.addReserva(this.r2);
		assertEquals(2, this.le1.size());
		this.le1.removeReserva(this.r1);
		assertEquals(1, this.le1.size());
	}

	@Test
	public void testOperacionesColaPrioridad() {
		this.le1.addReserva(r1);
		assertEquals(r1, le1.pollReserva());
		assertEquals(0, le1.size());
		assertEquals(true, le1.isEmpty());
		this.le1.addReserva(r2);
		assertEquals(r2, le1.peekReserva());
	}

	@Test
	public void testIsNextReserva() {
		this.le1.addReserva(r2);
		this.le1.addReserva(r1);
		assertEquals(true, le1.isNextReserva(r1));
		assertEquals(false, le1.isNextReserva(r2));
	}
}
