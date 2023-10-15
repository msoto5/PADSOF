package usuarioTest.clienteTest.tarifaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sala.Sala;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.cliente.tarifa.TarifaPlana;
import utiles.Horario;

import org.junit.Assert;

public class TarifaPlanaTest {
    private TarifaPlana tp;
    private int duracion = 3;
    private TipoActividad ta = new TipoActividad("Pilates");
    Horario h1 = new Horario(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
    LocalDate f1 = LocalDate.of(1999, 12, 12);
    Sala s1 = new Sala("sala1", 10, "salita");
    EntrenamientoLibre el = new EntrenamientoLibre("el1", "el1_desc", h1, f1, s1);
    
    @BeforeEach
	void setUpBeforeClass() throws Exception {
        tp = new TarifaPlana(duracion, ta);
	}

    @Test
    void testTarifaPlana(){
        Assert.assertEquals(duracion, tp.getDuracionMes());
        Assert.assertEquals(ta, tp.getTipoActividad());
    }

    @Test
    void testSetGetDuracionMes(){
        tp.setDuracionMes(4);
        assertEquals(4, tp.getDuracionMes());
    }

    @Test
    void testSetGetPrecioBase(){
        TarifaPlana.setPrecioBase(10);
        assertEquals(10, TarifaPlana.getPrecioBase());
    }

    @Test
    void testSetGetDescEntPersonalizado(){
        TarifaPlana.setDescEntPersonalizado(0.3);
        assertEquals(0.3, TarifaPlana.getDescEntPersonalizado());
    }

    @Test
    void testSetGetDescEntLibre(){
        TarifaPlana.setDescEntLibre(0.3);
        assertEquals(0.3, TarifaPlana.getDescEntLibre());
    }

    @Test
    void testRenovar() {
        int d = 3;
        TarifaPlana tp2 = new TarifaPlana(duracion, ta);
        tp2.renovar(d);
        assertEquals(tp2.getDuracionMes(), tp.getDuracionMes() + d);
        assertEquals(tp2.getTipoActividad(), tp.getTipoActividad());
        assertEquals(tp2.getfInicial(), tp.getfInicial());
    }

    @Test
    void testGetDevolucionCancelacionPorCliente(){
        assertEquals(0.0, tp.getDevolucionCancelacionPorCliente());
    }

    @Test
    void testGetDevolucionCancelacionPorMonitor(){
        assertEquals(0.0, tp.getDevolucionCancelacionPorMonitor());
    }

    @Test
    void testGetPrecioReserva() {
        assertEquals(el.getPrecio() * (1 - TarifaPlana.getDescEntLibre()), tp.getPrecioReserva(el));
    }

    @Test
    void testCobrarAlRegistrarse(){
        assertTrue(tp.cobrarAlRegistrarse());
    }
}
