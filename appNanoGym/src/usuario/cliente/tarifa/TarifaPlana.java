/**
 * Implementa la tarifa plana
 */
package usuario.cliente.tarifa;

import java.time.LocalDate;

import servicio.EntrenamientoPersonalizado;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.TipoActividad;

/**
 * Clase que representa una tarifa plana
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class TarifaPlana extends Tarifa {

    /** Serial Version UID */
    private static final long serialVersionUID = -7256583486145213529L;

    /** Fecha Inicial de la Tarifa Plana */
    private final LocalDate fInicial;

    /** Duración en Meses de la Tarifa Plana */
    private int duracionMes;

    /** Tipo de Actividad Grupal incluida en la Tarifa Plana */
    private final TipoActividad tipoActividad;

    /** Precio Base de la Tarifa Plana */
    private static double PRECIO_BASE = 50.0;

    /** Descuento de la Tarifa Plana en los entrenamientos personalizados */
    private static double DESC_ENT_PRESONALIZADO = 0.1;

    /** Descuento de la Tarifa Plana en los entrenamientos grupales */
    private static double DESC_ENT_LIBRE = 0.1;

    /**
     * Constructor de la clase TarifaPlana
     * 
     * @param duracion      Duración en Meses de la Tarifa Plana
     * @param tipoActividad Tipo de Actividad Grupal incluida en la Tarifa Plana
     * @throws IllegalArgumentException Si la duración es menor que 1 o el tipo de
     *                                  actividad es nulo
     */
    public TarifaPlana(int duracion, TipoActividad tipoActividad) throws IllegalArgumentException {
        if (duracion < 1) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0");
        } else if (tipoActividad == null) {
            throw new IllegalArgumentException("El tipo de actividad no puede ser nulo");
        }

        this.fInicial = LocalDate.now();
        this.duracionMes = duracion;
        this.tipoActividad = tipoActividad;
    }

    /**
     * Devuelve la duración en Meses de la Tarifa Plana
     * 
     * @return la duración en Meses de la Tarifa Plana
     */
    public LocalDate getfInicial() {
        return fInicial;
    }

    /**
     * Devuelve la duración en Meses de la Tarifa Plana
     * 
     * @return la duración en Meses de la Tarifa Plana
     */
    public int getDuracionMes() {
        return duracionMes;
    }

    /**
     * Establece la duración en Meses de la Tarifa Plana
     * 
     * @param duracionMes la duración en Meses de la Tarifa Plana
     * @throws IllegalArgumentException Si la duración es menor que 1
     */
    public void setDuracionMes(int duracionMes) throws IllegalArgumentException {
        if (duracionMes < 1) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0");
        }
        this.duracionMes = duracionMes;
    }

    /**
     * Devuelve el precio Base de la Tarifa Plana
     * 
     * @return el precio Base de la Tarifa Plana
     */
    public static double getPrecioBase() {
        return PRECIO_BASE;
    }

    /**
     * Establece el precio Base de la Tarifa Plana
     * 
     * @param precioBase el precio Base de la Tarifa Plana
     * @throws IllegalArgumentException Si el precio base es menor que 0
     */
    public static void setPrecioBase(double precioBase) throws IllegalArgumentException {
        if (precioBase < 0.0) {
            throw new IllegalArgumentException("El precio base debe ser mayor o igual que 0");
        }
        PRECIO_BASE = precioBase;
    }

    /**
     * Devuelve el descuento de la Tarifa Plana en los entrenamientos personalizados
     * 
     * @return el descuento de la Tarifa Plana en los entrenamientos personalizados
     */
    public static double getDescEntPersonalizado() {
        return DESC_ENT_PRESONALIZADO;
    }

    /**
     * Establece el descuento de la Tarifa Plana en los entrenamientos
     * personalizados
     * 
     * @param descEntPersonalizado el descuento de la Tarifa Plana en los
     *                             entrenamientos personalizados
     * @throws IllegalArgumentException Si el descuento es menor que 0 o mayor que 1
     */
    public static void setDescEntPersonalizado(double descEntPersonalizado) throws IllegalArgumentException {
        if (descEntPersonalizado < 0.0 || descEntPersonalizado > 1.0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
        }
        DESC_ENT_PRESONALIZADO = descEntPersonalizado;
    }

    /**
     * Devuelve el descuento de la Tarifa Plana en los entrenamientos grupales
     * 
     * @return el descuento de la Tarifa Plana en los entrenamientos grupales
     */
    public static double getDescEntLibre() {
        return DESC_ENT_LIBRE;
    }

    /**
     * Establece el descuento de la Tarifa Plana en los entrenamientos grupales
     * 
     * @param descEntLibre el descuento de la Tarifa Plana en los entrenamientos
     *                     grupales
     * @throws IllegalArgumentException Si el descuento es menor que 0 o mayor que 1
     * 
     */
    public static void setDescEntLibre(double descEntLibre) throws IllegalArgumentException {
        if (descEntLibre < 0.0 || descEntLibre > 1.0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
        }
        DESC_ENT_LIBRE = descEntLibre;
    }

    /**
     * Devuelve el tipo de Actividad Grupal incluida en la Tarifa Plana
     * 
     * @return el tipo de Actividad Grupal incluida en la Tarifa Plana
     */
    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    /**
     * Devuelve los detalles de la tarifa en un String
     * 
     * @return los detalles de la tarifa en un String
     */
    @Override
    public String toString() {
        return "Tarifa Plana [Fecha Inicial=" + fInicial + ", Duración="
                + duracionMes
                + ", Tipo Actividad=" + tipoActividad + "]";
    }

    /**
     * Renovar la tarifa plana
     * 
     * @param duracion Aumento de la duración en Meses de la Tarifa Plana
     * @return el precio de la renovación
     * @throws IllegalArgumentException Si la duración es menor que 1
     */
    public double renovar(int duracion) {
        if (duracion < 1) {
            throw new IllegalArgumentException("La duración debe ser mayor que 0");
        }

        this.duracionMes += duracion;
        return PRECIO_BASE * duracion;
    }

    /**
     * Calcula la devolución de la cancelación por parte del cliente de una
     * Actividad
     * Grupal
     * 
     * @return Devolución de la cancelación por parte del cliente
     */
    @Override
    public double getDevolucionCancelacionPorCliente() {
        return 0.0;
    }

    /**
     * Calcula la devolución de la cancelación por parte del Monitor de una
     * Actividad Grupal
     * 
     * @return Devolución de la cancelación por parte del Monitor
     */
    @Override
    public double getDevolucionCancelacionPorMonitor() {
        return 0.0;
    }

    /**
     * Obtiene el precio a pagar al reservar una actividad grupal
     * 
     * @param ag Actividad Grupal a reservar
     * @return Precio a pagar al reservar
     */
    @Override
    public double getPrecioReserva(ActividadGrupal ag) {
        if (ag.getTipoActividad().getNombre().toLowerCase().equals(this.tipoActividad.getNombre().toLowerCase())) {
            return 0.0;
        } else {
            return super.getPrecioReserva(ag);
        }
    }

    /**
     * Obtiene el precio a pagar al reservar un entrenamiento personalizado
     * 
     * @param ep Entrenamiento Personalizado a reservar
     * @return Precio a pagar al reservar
     */
    @Override
    public double getPrecioReserva(EntrenamientoPersonalizado ep) {
        return super.getPrecioReserva(ep) * (1 - DESC_ENT_PRESONALIZADO);
    }

    /**
     * Obtiene el precio a pagar al reservar un entrenamiento libre
     * 
     * @param el Entrenamiento Libre a reservar
     * @return Precio a pagar al reservar
     */
    @Override
    public double getPrecioReserva(EntrenamientoLibre el) {
        return super.getPrecioReserva(el) * (1 - DESC_ENT_LIBRE);
    }

    /**
     * Obtiene el mensaje de cobro de la tarifa
     * 
     * @return Mensaje de cobro de la tarifa
     */
    public String getMensajeCobro() {
        return "Cobro por Tarifa Plana en actividad" + this.tipoActividad + " con duración de " + this.duracionMes;
    }

    /**
     * Índica si se cobra al registrarse en la aplicación
     */
    @Override
    public boolean cobrarAlRegistrarse() {
        return true;
    }

    @Override
    public String toStringInterfaz() {
        return "Tarifa Plana (" + tipoActividad.toString() + "). Inicio: " + this.fInicial + "(" + this.duracionMes
                + " meses)";
    }
}
