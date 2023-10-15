package nanoGymTest;

import org.junit.Test;
import static org.junit.Assert.*;

import sala.*;
import usuario.*;
import usuario.cliente.*;
import utiles.Horario;

import java.time.*;
import nanoGym.NanoGym;

public class NanoGymTest {
	
	public static Sala s1;
	public static Usuario c1;
	public static NanoGym ng;

	public NanoGymTest() {
		s1 = new Sala("Principal", 20, "Pesas");
		c1 = new Cliente("juan", "123", "Juan Luis", LocalDate.of(2000, 12, 1));
		ng = new NanoGym();
	}

	@Test
	public void testGetCurrentUser() {
		ng.setCurrentUser(c1);
		assertEquals(c1, ng.getCurrentUser());
	}

	@Test
	public void testLeerSalas() {
		ng.addSala(s1);
		try {
			ng.generateBackUp();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(s1.getId(), ng.getSalas().get(s1.getId()).getId());
	}

	@Test
	public void testGuardarSalas() {
		ng.addSala(s1);
		try {
			ng.generateBackUp();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(s1.getId(), ng.getSalas().get(s1.getId()).getId());
	}

	@Test
	public void testAddSala() {
		ng.addSala(s1);
		assertEquals(s1, ng.getSalas().get(s1.getId()));
	}

	@Test
	public void testRemoveSala() {
		ng.addSala(s1);
		ng.removeSala(s1);
		assertEquals(null, ng.getSalas().get(s1.getId()));
	}

	@Test
	public void testLeerUsuarios() {
		ng.addUsuario(c1);
		try {
			ng.generateBackUp();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			fail("Exception" + e.getMessage());
		}
		assertEquals(c1.getUsername(), ng.getUsuarios().get(c1.getUsername()).getUsername());
	}

	@Test
	public void testGuardarUsuarios() {
		ng.addUsuario(c1);
		try {
			ng.generateBackUp();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(c1.getUsername(), ng.getUsuarios().get(c1.getUsername()).getUsername());
	}

	@Test
	public void testAddUsuario() {
		ng.addUsuario(c1);
		assertEquals(c1.getUsername(), ng.getUsuarios().get(c1.getUsername()).getUsername());
	}

	@Test
	public void testRemoveServicios() {
		ng.addUsuario(c1);
		ng.removeUsuario(c1);
		assertEquals(null, ng.getUsuarios().get(c1.getUsername()));
	}

	@Test
	public void testLogOut() {
		ng.setCurrentUser(c1);
		ng.logOut();
		assertEquals(null, ng.getCurrentUser());
	}

	@Test
	public void testBackUpAll() {
		Sala s = new Sala("sala 1", 10, "sala de pesas");
		Monitor m = new Monitor("moni", "12345", "Monitor Prueba", "monitor-prueba@gmail.com", "12345678V");

		Horario horario = new Horario(LocalTime.of(12,0), LocalTime.of(13,0));
        LocalDate fecha = LocalDate.now().plusDays(2);
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        
        horario = new Horario(LocalTime.of(14,0), LocalTime.of(15,0));
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(14,0), LocalTime.of(15,0));
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(16,0), LocalTime.of(17,0));
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(18,0), LocalTime.of(19,0));
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);
        horario = new Horario(LocalTime.of(20,0), LocalTime.of(21,0));
        ng.addEntrenamientoLibre("el", "Entrenamiento Libre para mejorar tu forma tu mismo", horario, fecha, s);

        ng.addActividadGrupal("Yoga", "Yoga para mejorar la mente y el cuerpo", horario, fecha, s, m, "Yoga");

		ng.generateBackUp();

		NanoGym ng2 = new NanoGym();
		ng2.loadBackUp();

		assertEquals(ng.getServicios().size(), ng2.getServicios().size());
		assertEquals(ng.getUsuarios().size(), ng2.getUsuarios().size());
		assertEquals(ng.getSalas().size(), ng2.getSalas().size());
	}
}
