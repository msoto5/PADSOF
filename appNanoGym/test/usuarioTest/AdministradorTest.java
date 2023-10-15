package usuarioTest;

import org.junit.*;
import static org.junit.Assert.*;

import usuario.*;

public class AdministradorTest {
	private static Administrador a1;
	private static String username = "admin";
	private static String contraseña = "1234";

	@Before
	public void setUpBeforeClass() throws Exception {
		a1 = new Administrador(username, contraseña);
	}

	@Test
	public void testAdministrador() {
		assertEquals(username, a1.getUsername());
		assertEquals(contraseña, a1.getContraseña());
	}

	@Test
	public void testEsCliente(){
		assertFalse(a1.esCliente());
	}

	@Test
	public void testEsMonitor(){
		assertFalse(a1.esMonitor());
	}
	
}
