package allTest;

import nanoGymTest.*;
import usuarioTest.*;
import usuarioTest.clienteTest.*;
import servicioTest.*;
import servicioTest.actividadTest.actividadMonitorTest.*;
import servicioTest.actividadTest.*;
import salaTest.*;
import utilesTest.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({ NanoGymTest.class,
SalaTest.class,
SalaClimatizadaTest.class,
AdministradorTest.class,
MonitorTest.class,
ClienteTest.class,
ActividadMonitorTest.class,
EntrenamientoLibreTest.class,
EntrenamientoPersonalizadoTest.class,
ActividadGrupalTest.class,
EstadoReservaTest.class,
HorarioTest.class,
ListaEsperaTest.class,
ReservaTest.class,
EntrenamientoMonitorTest.class
})
public class allTests {
}