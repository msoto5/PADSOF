/**
 * Clase que representa una actividad impartida por un monitor de la aplicación.
 * @file ActividadMonitor.java
 * @package servicio.actividad.actividadMonitor
 */

package servicio.actividad.actividadmonitor;

import java.time.LocalDate;

import usuario.Monitor;
import servicio.Servicio;
import servicio.actividad.Actividad;
import utiles.Horario;
import sala.Sala;

/**
 * Clase abstracta que representa una actividad impartida por un monitor de la
 * aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public abstract class ActividadMonitor extends Actividad {

    /** Serial Version UID */
    private static final long serialVersionUID = -3278837391787857380L;

    /** Monitor que imparte la actividad */
    private Monitor monitor;

    /**
     * Constructor de la clase ActividadMonitor
     * 
     * @param nombre      Nombre del servicio de clase ActividadMonitor
     * @param descripcion Descripción del servicio de clase ActividadMonitor
     * @param horario     Horario en el que transcurre la actividad impartida por un
     *                    monitor
     * @param fecha       Fecha en la que tiene lugar la actividad impartida por un
     *                    monitor
     * @param sala        Sala en la que tiene lugar la actividad impartida por un
     *                    monitor
     * @param monitor     Monitor que imparte la actividad
     * @throws IllegalArgumentException si el monitor es nulo o no está disponible
     */
    public ActividadMonitor(String nombre, String descripcion, Horario horario, LocalDate fecha, Sala sala,
            Monitor monitor) throws IllegalArgumentException {
        super(nombre, descripcion, horario, fecha, sala);
        if (monitor == null) {
            throw new IllegalArgumentException("El monitor no puede ser nulo");
        } else if (!monitor.disponible(fecha, horario)) {
            throw new IllegalArgumentException("El monitor no está disponible" +
                    " en el horario indicado");
        }

        this.monitor = monitor;
        monitor.addActividad(this);
    }

    /**
     * Devuelve el monitor que imparte la actividad
     * 
     * @return monitor que imparte la actividad
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * Establece el monitor que imparte la actividad
     * 
     * @param monitor monitor que imparte la actividad
     * @throws IllegalArgumentException si el monitor es nulo o ya imparte la
     *                                  actividad
     */
    public void setMonitor(Monitor monitor) throws IllegalArgumentException {
        if (monitor == null) {
            throw new IllegalArgumentException("El monitor no puede ser nulo");
        } else if (monitor.getActividades().contains(this)) {
            throw new IllegalArgumentException("El monitor ya imparte esta actividad");
        } else if (!monitor.disponible(getFecha(), getHorario())) {
            throw new IllegalArgumentException("El monitor no está disponible" +
                    "en el horario indicado");
        }
        this.monitor = monitor;
    }

    /**
     * Devuelve el nombre del monitor que imparte la actividad
     * 
     * @return nombre del monitor que imparte la actividad
     */
    @Override
    public String getMonitorNombre() {
        return monitor.getName();
    }

    /**
     * Devuelve la información de la actividad
     * 
     * @return información de la actividad
     */
    @Override
    public String toString() {
        return super.toString() + "\n -Monitor: " + monitor;
    }

    /**
     * Procedimiento al cancelar la actividad por parte del Monitor
     * 
     * @param m Monitor que cancela la actividad
     * @return true si se ha cancelado la actividad, false en caso contrario
     */
    public boolean cancelarPorMonitor(Monitor m) {
        if (m.equals(monitor)) {
            return this.cancelar();
        }
        return false;
    }

    /**
     * Compara la actividad de un monitor con un servicio
     * 
     * @param serv servicio a comparar
     * @return 0 si son iguales, 1 si la actividad es mayor, -1 si la actividad es
     *         menor
     */
    @Override
    public int compareTo(Servicio serv) {
        int ret = super.compareTo(serv);
        if (ret == 0 && serv instanceof ActividadMonitor) {
            ActividadMonitor act = (ActividadMonitor) serv;
            ret = monitor.compareTo(act.monitor);
        }
        return ret;
    }

    /**
     * Compara la actividad de un monitor con otro objeto
     * 
     * @param obj objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActividadMonitor) {
            ActividadMonitor act = (ActividadMonitor) obj;
            return super.equals(obj) && monitor.equals(act.monitor);
        }
        return false;
    }

    /**
     * Devuelve el hashcode de la actividad de un monitor
     * 
     * @return hashcode de la actividad de un monitor
     */
    @Override
    public int hashCode() {
        return super.hashCode() * monitor.hashCode();
    }
}
