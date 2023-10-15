package servicioTest.actividadTest.actividadMonitorTest;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import servicio.actividad.actividadmonitor.TipoActividad;

public class TipoActividadTest {
    private static String nombre = "tipo";
    private static TipoActividad ta;

    @BeforeAll
	static void setUpBeforeClass() throws Exception {
        ta = new TipoActividad(nombre);
    }

    @Test
    void testTipoActividad(){
        Assert.assertEquals(nombre, ta.getNombre());
    }

    @Test
    void testSetGetNombre(){
        ta.setNombre(nombre);
        assertEquals(nombre, ta.getNombre());
    }
}
