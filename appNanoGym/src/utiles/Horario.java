package utiles;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Clase que representa un horario, con una hora inicial y otra final
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Horario implements Serializable, Comparable<Horario> {

    /** Serial Version UID */
    private static final long serialVersionUID = 7894267669036574157L;

    /** Hora Inicial */
    private LocalTime horaInicio;

    /** Hora Final */
    private LocalTime horaFin;

    /**
     * Constructor de la clase Horario.
     * 
     * @param horaInicio Hora de inicio del horario
     * @param horaFin    Hora de fin del horario. No puede ser ni igual ni anterior
     *                   a la hora de inicio.
     * @throws IllegalArgumentException Si la hora de fin es anterior o igual a la
     *                                  hora de inicio.
     */
    public Horario(LocalTime horaInicio, LocalTime horaFin) throws IllegalArgumentException {
        if (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin no puede ser anterior o igual a la hora de inicio");
        }
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /**
     * Modifica la hora de inicio del horario.
     * 
     * @param horaInicio Hora de inicio del horario. No puede ser ni igual ni
     *                   posterior a la hora de fin.
     * @throws IllegalArgumentException Si la hora de inicio es posterior o igual a
     *                                  la hora de fin.
     */
    public void setHoraInicio(LocalTime horaInicio) throws IllegalArgumentException {
        if (horaInicio.isAfter(this.horaFin) || horaInicio.equals(this.horaFin)) {
            throw new IllegalArgumentException("La hora de inicio no puede ser posterior o igual a la hora de fin");
        }
        this.horaInicio = horaInicio;
    }

    /**
     * Devuelve la hora de inicio del horario.
     * 
     * @return Hora de inicio del horario.
     */
    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    /**
     * Modifica la hora de fin del horario.
     * 
     * @param horaFin Hora final del horario. No puede ser ni igual ni anterior a la
     *                hora de inicio.
     * @throws IllegalArgumentException Si la hora de fin es anterior o igual a la
     *                                  hora de inicio.
     */
    public void setHoraFin(LocalTime horaFin) throws IllegalArgumentException {
        if (horaFin.isBefore(this.horaInicio)) {
            throw new IllegalArgumentException("La hora de fin no puede ser anterior o igual a la hora de inicio");
        }
        this.horaFin = horaFin;
    }

    /**
     * Devuelve la hora de fin del horario.
     * 
     * @return Hora de fin del horario.
     */
    public LocalTime getHoraFin() {
        return this.horaFin;
    }

    /**
     * Devuelve un String con la información del horario.
     * 
     * @return String con la información del horario.
     */
    public String toString() {
        return this.horaInicio + " - " + this.horaFin;
    }

    /**
     * Comprueba si un horario está incluido en otro.
     * 
     * @param h1 Horario que se quiere comprobar si está incluido dentro de this.
     * @return True si el h1 está incluido, false en caso contrario.
     */
    public boolean horarioIncluido(Horario h1) {
        return (this.horaInicio.isBefore(h1.horaInicio) || this.horaInicio.equals(h1.horaInicio))
                && (this.horaFin.isAfter(h1.horaFin) || this.horaFin.equals(h1.horaFin));
    }

    /**
     * Comprueba si una hora está incluida en el horario.
     * 
     * @param h Hora que se quiere comprobar si está incluida dentro de this.
     * @return True si la hora está incluida, false en caso contrario.
     */
    public boolean horaIncluida(LocalTime h) {
        return (this.horaInicio.isBefore(h) || this.horaInicio.equals(h))
                && (this.horaFin.isAfter(h) || this.horaFin.equals(h));
    }

    /**
     * Comprueba si un horario se solapa con otro.
     * 
     * @param h1 Horario que se quiere comprobar si se solapa con this.
     * @return True si se solapan, false en caso contrario.
     */
    public boolean horarioSolapan(Horario h1) {
        if ((this.horaInicio.isBefore(h1.horaInicio) || this.horaInicio.equals(h1.horaInicio)) && (this.horaFin.isAfter(h1.horaFin) || this.horaFin.equals(h1.horaFin))){
            return true;
        } else {
            return false;
        } 

    }

    /**
     * Compara dos horarios.
     * 
     * @param h1 Horario con el que se quiere comparar.
     * @return -1 si el horario actual es anterior a h1, 1 si es posterior y 0 si
     *         son iguales.
     */
    public int compareTo(Horario h1) {
        if (this.horaInicio.isBefore(h1.horaInicio)) {
            return -1;
        } else if (this.horaInicio.isAfter(h1.horaInicio)) {
            return 1;
        } else {
            if (this.horaFin.isBefore(h1.horaFin)) {
                return -1;
            } else if (this.horaFin.isAfter(h1.horaFin)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Horario nulo
     * @return Horario nulo
     */
    public static Horario horarioNULL() {
        return new Horario(LocalTime.of(0, 0), LocalTime.of(0, 1));
    }
}
