/**
 * Enumeración que representa los tipos de actividades de la aplicación.
 * @file TipoActividad.java
 * @package servicio.actividad.actividadMonitor
 */

package servicio.actividad.actividadmonitor;

import java.io.Serializable;

/**
 * Enumeración que representa los tipos de actividades de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class TipoActividad implements Serializable, Comparable<TipoActividad> {

    /** Nombre tipo Actividad */
    private String nombre;

    /**
     * Constructor de la clase TipoActividad
     * 
     * @param nombre Nombre del tipo de actividad
     */
    public TipoActividad(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del tipo de actividad
     * 
     * @return nombre del tipo de actividad
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del tipo de actividad
     * 
     * @param nombre nombre del tipo de actividad
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve un string con la información del tipo de actividad
     * 
     * @return string con la información del tipo de actividad
     */
    public String toString() {
        return nombre;
    }

    /**
     * Compara dos tipos de actividad
     * 
     * @param tipoActividad tipo de actividad a comparar
     * @return 0 si son iguales, 1 si el tipo de actividad es mayor, -1 si el tipo
     *         de actividad es menor
     */
    @Override
    public int compareTo(TipoActividad tipoActividad) {
        return this.nombre.compareTo(tipoActividad.getNombre());
    }

    /**
     * Compara dos tipos de actividad
     * 
     * @param obj tipo de actividad a comparar
     * @return true si son iguales, false si no lo son
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TipoActividad) {
            TipoActividad tipoActividad = (TipoActividad) obj;
            return this.nombre.equals(tipoActividad.getNombre());
        }
        return false;
    }

    /**
     * Devuelve el hashcode del tipo de actividad
     * 
     * @return hashcode del tipo de actividad
     */
    @Override
    public int hashCode() {
        return 11 * this.nombre.hashCode();
    }
}
