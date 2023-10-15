package usuarioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.Usuario;
import usuario.cliente.Cliente;
import utiles.GestorNotificaciones;

public class UsuarioTest {
    private static String username = "user";
    private static String contraseña = "1234";
    private static Usuario u;
    private static LocalDate f1;
    private static GestorNotificaciones gN;

    @BeforeEach
    void setUp() {
        f1 = LocalDate.of(1999, 12, 12);
        u = new Cliente(username, contraseña, "Fernando", f1);
        gN = new GestorNotificaciones();
    }

    @Test
    void testUsuario(){
        assertEquals(username, u.getUsername());
        assertEquals(contraseña, u.getContraseña());
    }

    @Test
    void testSetGetUsername(){
        u.setUsername("usuario");
        assertEquals("usuario", u.getUsername());
    }

    @Test
    void testSetGetContraseña(){
        u.setContraseña("12345");
        assertEquals("12345", u.getContraseña());
    }

    @Test
    void testGetNotificaciones(){
        assertEquals(gN.getNotificaciones(), u.getNotificaciones());
    }

    @Test
    void testReadNotificaciones(){
        assertEquals(gN.readNotificaciones(), u.readNotificaciones());
    }

    @Test
    void testAddNotificacion(){
        String not = "not";
        assertTrue(gN.addNotificacion(not));
    }

    @Test
	void testExportarUsuario() {
		assertTrue(u.exportarUsuario());
	}

    @Test
    void testImportarAllUsuarios(){
        HashMap<String, Usuario> map = Usuario.importarAllUsuarios();
        assertNotEquals(map, null);
    }

    @Test
    void testCompareTo(){
        Usuario o = new Cliente("username", "contraseña", "Fernando", LocalDate.of(1998, 12, 12));
        u.getUsername().compareTo(o.getUsername());
    }

    @Test
    void testEquals(){
        assertTrue(u.equals(u));
    }

    @Test
    void testHashCode(){
        int expected = 11 * u.getUsername().hashCode();
        assertEquals(expected, u.hashCode());
    }
}
