package usuarioTest.clienteTest.tarifaTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.cliente.tarifa.Tarifa;
import usuario.cliente.tarifa.TarifaPagoUso;

public class TarifaPagoUsoTest {
    TarifaPagoUso tpu;
    Tarifa t;
    
    @BeforeEach
    void setUpBeforeClass() throws Exception {
        tpu = new TarifaPagoUso();
        t = new TarifaPagoUso();
    }

    @Test
    void testTarifaPagoUso(){

    }

    @Test
    void testCobrarAlRegistrarse(){
        assertFalse(tpu.cobrarAlRegistrarse());
    }

    @Test
    void testGetDevolucionCancelacionPorCliente(){
        assertEquals(t.getDevolucionCancelacionPorCliente(), tpu.getDevolucionCancelacionPorCliente());
    }

    @Test
    void testGetDevolucionCancelacionPorMonitor(){
        assertEquals(t.getDevolucionCancelacionPorMonitor(), tpu.getDevolucionCancelacionPorMonitor());
    }

    @Test
    void testRenovar(){
        assertEquals(0, tpu.renovar(1));
    }
    
}
