/**
 * Implementa la clase Tarifa
 */
package usuario.cliente.tarifa;

import java.io.Serializable;

import servicio.EntrenamientoPersonalizado;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.Servicio;

/**
 * Clase que representa una tarifa
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public abstract class Tarifa implements Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID = -5868316768312045504L;

    /** Devolución al cliente si cancela el monitor */
    private static double DEVOLUCION_CANCELACION_POR_MONITOR = 1.0;

    /** Devolución al cliente si cancela el cliente */
    private static double DEVOLUCION_CANCELACION_POR_CLIENTE = 0.9;

    /**
     * Constructor de la clase Tarifa (Implementado por warning de Javadoc)
     */
    public Tarifa() {
    }

    /**
     * Devuelve el procentaje de devolución al cliente si cancela el monitor
     * 
     * @return Devolución al cliente si cancela el monitor
     */
    public abstract double getDevolucionCancelacionPorMonitor();

    /**
     * Establece la devolución por monitor
     * 
     * @param devCancMonitor Devolución por monitor
     * @throws IllegalArgumentException Si la devolución por monitor debe estar
     *                                  comprendida entre 0 y 1
     */
    public static void setDevolucionCancelacionPorMonitor(double devCancMonitor) throws IllegalArgumentException {
        if (devCancMonitor < 0 || devCancMonitor > 1) {
            throw new IllegalArgumentException("La devolución por monitor debe estar comprendida entre 0 y 1");
        }

        DEVOLUCION_CANCELACION_POR_MONITOR = devCancMonitor;
    }

    /**
     * Devuelve el procentaje de devolución al cliente si cancela el cliente
     * 
     * @return Devolución al cliente si cancela el cliente
     */
    public static double getDEVOLUCION_CLIENTE() {
        return DEVOLUCION_CANCELACION_POR_CLIENTE;
    }

    /**
     * Devuelve el procentaje de devolución al cliente si cancela el monitor
     * 
     * @return Devolución al cliente si cancela el monitor
     */
    public static double getDEVOLUCION_MONITOR() {
        return DEVOLUCION_CANCELACION_POR_MONITOR;
    }

    /**
     * Devuelve el procentaje de devolución al cliente si cancela el cliente
     * 
     * @return Devolución al cliente si cancela el cliente
     */
    public abstract double getDevolucionCancelacionPorCliente();

    /**
     * Establece la devolución por cliente
     * 
     * @param devCancCliente Devolución por cliente
     * @throws IllegalArgumentException Si la devolución por cliente debe estar
     *                                  comprendida entre 0 y 1
     */
    public static void setDevolucionCancelacionPorCliente(double devCancCliente) throws IllegalArgumentException {
        if (devCancCliente < 0 || devCancCliente > 1) {
            throw new IllegalArgumentException("La devolución por cliente debe estar comprendida entre 0 y 1");
        }

        DEVOLUCION_CANCELACION_POR_CLIENTE = devCancCliente;
    }

    /**
     * Calcula la devolución al cliente si cancela el monitor de un Entrenamiento
     * Personalizado
     * 
     * @param ep Entrenamiento Personalizado cancelado
     * @return Devolución al cliente si cancela el monitor
     */
    public double calcularDevolucionCancPorMonitor(EntrenamientoPersonalizado ep) {
        return EntrenamientoPersonalizado.getPRECIO() * DEVOLUCION_CANCELACION_POR_MONITOR;
    }

    /**
     * Calcula la devolución al cliente si cancela el monitor de un Entrenamiento
     * Libre
     * 
     * @param el Entrenamiento Libre cancelado
     * @return Devolución al cliente si cancela el monitor
     */
    public double calcularDevolucionCancPorMonitor(EntrenamientoLibre el) {
        return EntrenamientoLibre.getPRECIO() * DEVOLUCION_CANCELACION_POR_MONITOR;
    }

    /**
     * Calcula la devolución al cliente si cancela el monitor de una Actividad
     * Grupal
     * 
     * @param ag Actividad Grupal cancelada
     * @return Devolución al cliente si cancela el monitor
     */
    public double calcularDevolucionCancPorMonitor(ActividadGrupal ag) {
        return ActividadGrupal.getPRECIO() * DEVOLUCION_CANCELACION_POR_MONITOR;
    }

    /**
     * Calcula la devolución al cliente si cancela el cliente de un Entrenamiento
     * Personalizado
     * 
     * @param ep Entrenamiento Personalizado cancelado
     * @return Devolución al cliente si cancela el cliente
     */
    public double calcularDevolucionCancPorCliente(EntrenamientoPersonalizado ep) {
        return EntrenamientoPersonalizado.getPRECIO() * DEVOLUCION_CANCELACION_POR_CLIENTE;
    }

    /**
     * Calcula la devolución al cliente si cancela el cliente de un Entrenamiento
     * Libre
     * 
     * @param el Entrenamiento Libre cancelado
     * @return Devolución al cliente si cancela el cliente
     */
    public double calcularDevolucionCancPorCliente(EntrenamientoLibre el) {
        return EntrenamientoLibre.getPRECIO() * DEVOLUCION_CANCELACION_POR_CLIENTE;
    }

    /**
     * Calcula la devolución al cliente si cancela el cliente de una Actividad
     * Grupal
     * 
     * @param ag Actividad Grupal cancelada
     * @return Devolución al cliente si cancela el cliente
     */
    public double calcularDevolucionCancPorCliente(ActividadGrupal ag) {
        return ActividadGrupal.getPRECIO() * DEVOLUCION_CANCELACION_POR_CLIENTE;
    }

    /**
     * Obtiene el precio a pagar al reservar un entrenamiento personalizado
     * 
     * @param ep Entrenamiento Personalizado a reservar
     * @return Precio a pagar al reservar un servicio
     */
    public double getPrecioReserva(EntrenamientoPersonalizado ep) {
        return EntrenamientoPersonalizado.getPRECIO();
    }

    /**
     * Obtiene el precio a pagar al reservar un entrenamiento libre
     * 
     * @param el Entrenamiento Libre a reservar
     * @return Precio a pagar al reservar un servicio
     */
    public double getPrecioReserva(EntrenamientoLibre el) {
        return EntrenamientoLibre.getPRECIO();
    }

    /**
     * Obtiene el precio a pagar al reservar una actividad grupal
     * 
     * @param ag Actividad Grupal a reservar
     * @return Precio a pagar al reservar un servicio
     */
    public double getPrecioReserva(ActividadGrupal ag) {
        return ActividadGrupal.getPRECIO();
    }

    /**
     * Obtiene el precio a pagar al reservar un servicio
     * 
     * @param s Servicio a reservar
     * @return Precio a pagar al reservar un servicio
     */
    public double getPrecioReserva(Servicio s) {
        return s.getPrecio();
    }

    /**
     * Define si hay que cobrar la tarifa al registrarse
     * 
     * @return true si hay que cobrar la tarifa al registrarse, false en caso
     *         contrario
     */
    public abstract boolean cobrarAlRegistrarse();

    /**
     * Devuelve en un String los datos de la tarifa
     * 
     * @return String con los datos de la tarifa
     */
    @Override
    public abstract String toString();

    /**
     * Devuelve el precio a pagar por renovar una tarifa
     * 
     * @param duracion Duración adicional de la tarifa
     * @return Precio a pagar por renovar la tarifa
     */
    public abstract double renovar(int duracion);

    /**
     * Calcula la devolución al cliente si cancela el monitor el servicio
     * 
     * @param s Servicio cancelado
     * @return Devolución al cliente si cancela el cliente
     */
    public double calcularDevolucionCancPorMonitor(Servicio s) {
        return s.getPrecio() * DEVOLUCION_CANCELACION_POR_MONITOR;
    }

    /**
     * Devuelva la tarifa como un toString para la interfaz
     * 
     * @return String con los datos de la tarifa
     */
    public abstract String toStringInterfaz();
}
