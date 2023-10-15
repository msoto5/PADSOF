/**
 * Clase que representa una actividad de la aplicación.
 * @file Actividad.java
 * @package servicio.Actividad
 */

package servicio.actividad;

import java.time.LocalDate;

import exceptions.SalaNoCompatibleException;
import servicio.Servicio;
import utiles.Horario;
import sala.Sala;

/**
 * Clase abstracta que representa una actividad de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public abstract class Actividad extends Servicio {
    /** Horario en el que transcurre la actividad **/
    private Horario horario;

    /** Fecha en la que tiene lugar la actividad **/
    private LocalDate fecha;

    /** Sala donde se realiza la Actividad */
    private Sala sala;

    /**
     * Constructor de la clase Actividad
     * 
     * @param nombre      Nombre del servicio de clase Actividad
     * @param descripcion Descripción del servicio de clase Actividad
     * @param horario     Horario en el que transcurre la actividad
     * @param fecha       Fecha en la que tiene lugar la actividad
     * @param sala        Sala donde se realiza la Actividad
     */
    public Actividad(String nombre, String descripcion, Horario horario, LocalDate fecha, Sala sala)
            throws IllegalArgumentException {
        super(nombre, descripcion);

        if (horario == null) {
            throw new IllegalArgumentException("El horario no puede ser nulo");
        } else if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        } else if (sala == null) {
            throw new IllegalArgumentException("La sala no puede ser nula");
        }

        this.horario = horario;
        this.fecha = fecha;
        this.sala = sala;
    }

    /**
     * Establece el horario en el que transcurre la actividad
     * 
     * @param horario horario en el que transcurre la actividad
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    /**
     * Devuelve el horario en el que transcurre la actividad
     * 
     * @return horario en el que transcurre la actividad
     */
    public Horario getHorario() {
        return this.horario;
    }

    /**
     * Establece la fecha en la que tiene lugar la actividad
     * 
     * @param fecha fecha en la que tiene lugar la actividad
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la fecha en la que tiene lugar la actividad
     * 
     * @return fecha en la que tiene lugar la actividad
     */
    public LocalDate getFecha() {
        return this.fecha;
    }

    /**
     * Establece la sala donde se realiza la Actividad
     * 
     * @param sala Sala donde se realiza la Actividad
     * @return true si se ha establecido la sala correctamente, false en caso
     * @throws IllegalArgumentException Si la sala es nula
     */
    public boolean setSala(Sala sala) throws IllegalArgumentException {
        boolean ret = true;
        if (sala == null) {
            throw new IllegalArgumentException("La sala no puede ser nula");
        }
        Actividad a = sala.getActividad(fecha, horario);
        if (a != this && a != null) {
            throw new IllegalArgumentException("La sala ya tiene una actividad en ese horario");
        } else {
            Sala s = this.sala;
            this.sala = sala;
            if (a == null) {
                try {
                    ret = sala.addActividad(this);
                } catch (SalaNoCompatibleException e) {
                    this.sala = s;
                    return false;
                }
            }
            return ret;
        }
    }

    /**
     * Devuelve la sala donde se realiza la Actividad
     * 
     * @return Sala donde se realiza la Actividad
     */
    public Sala getSala() {
        return this.sala;
    }

    /**
     * Devuelve una cadena de caracteres con la información de la actividad
     * 
     * @return Cadena de caracteres con la información de la actividad
     */
    @Override
    public String toString() {
        return "Actividad " + super.toString()
                + "\n -Horario: " + horario
                + "\n -Fecha: " + fecha
                + "\n -Sala: " + sala
                + "\n";
    }

    /**
     * Devuelve el aforo disponible de la actividad
     * 
     * @return Aforo disponible de la actividad
     */
    public int aforoDisponible() {
        return sala.getAforo() - this.getReservas().size();
    }
}
