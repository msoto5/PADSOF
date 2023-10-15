/**
 * Clase que representa un entrenamiento con monitor de la aplicación.
 * @file EntrenamientoMonitor.java
 * @package servicio.actividad.actividadMonitor
 */

package servicio.actividad.actividadmonitor;

import java.time.LocalDate;

import utiles.Horario;
import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import usuario.Monitor;

/**
 * Clase que representa un entrenamiento con monitor de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class EntrenamientoMonitor extends ActividadMonitor {

    /** Serial Version UID */
    private static final long serialVersionUID = -6897819324588761590L;

    /** Aforo máximo del entrenamiento con monitor **/
    private static int AFORO_MAX = 1;

    /** Entrenamiento personalizado del entrenamiento con monitor **/
    private EntrenamientoPersonalizado ep;

    /**
     * Constructor de la clase EntrenamientoMonitor
     * 
     * @param nombre      Nombre del servicio de clase EntrenamientoMonitor
     * @param descripcion Descripción del servicio de clase EntrenamientoMonitor
     * @param horario     Horario en el que transcurre el entrenamiento con monitor
     * @param sala        Sala en la que se realiza el entrenamiento con monitor
     * @param monitor     Monitor que realiza el entrenamiento con monitor
     * @param fecha       Fecha en la que tiene lugar el entrenamiento con monitor
     * @throws IllegalArgumentException si el monitor es nulo
     */
    public EntrenamientoMonitor(String nombre, String descripcion, Horario horario, Sala sala, Monitor monitor,
            LocalDate fecha) throws IllegalArgumentException {
        super(nombre, descripcion, horario, fecha, sala, monitor);
    }

    /**
     * Establece el entrenamiento personalizado del entrenamiento con monitor
     * @param ep entrenamiento personalizado del entrenamiento con monitor
     */
    public void setEntPers(EntrenamientoPersonalizado ep) {
        this.ep = ep;
    }

    /**
     * Devuelve el entrenamiento personalizado del entrenamiento con monitor
     * @return entrenamiento personalizado del entrenamiento con monitor
     */
    public EntrenamientoPersonalizado getEntPers() {
        return this.ep;
    }

    /**
     * Establece el aforo máximo del entrenamiento con monitor
     * 
     * @param aforoMax aforo máximo del entrenamiento con monitor
     */
    public static void setAFORO_MAX(int aforoMax) {
        EntrenamientoMonitor.AFORO_MAX = aforoMax;
    }

    /**
     * Devuelve el aforo máximo del entrenamiento con monitor
     * 
     * @return doube que indica el aforo máximo del entrenamiento con monitor
     */
    public static double getAFORO_MAX() {
        return EntrenamientoMonitor.AFORO_MAX;
    }

    /**
     * Devuelve un string con la información del entrenamiento con monitor
     * 
     * @return string con la información del entrenamiento con monitor
     */
    public String toString() {
        return "AFORO MÁXIMO: " + EntrenamientoMonitor.AFORO_MAX;
    }

    /**
     * Devuelve el aforo disponible del entrenamiento con monitor
     * 
     * @return int que indica el aforo disponible del entrenamiento con monitor
     */
    @Override
    public int aforoDisponible() {
        if (ep == null) {
            return EntrenamientoMonitor.AFORO_MAX;
        }
        int aforo = EntrenamientoMonitor.AFORO_MAX - ep.getReservas().size();
        return (aforo < 0) ? 0 : aforo;
    }

    /**
     * Devuelve el precio del entrenamiento con monitor
     * 
     * @return 0
     */
    public double getPrecio() {
        return 0;
    }

    /**
     * Devuelve si hay que imprimir info grupal
     * 
     * @return false
     */
    @Override
    public boolean imprimirInfoGrupal() {
        return false;
    }

    /**
     * Devuelve el String para buscar
     * 
     * @return String para buscar
     */
    @Override
    public String getBusquedaString() {
        return "Entrenamiento con monitor";
    }
}
