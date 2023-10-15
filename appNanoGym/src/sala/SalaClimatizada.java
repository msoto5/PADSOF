package sala;

import java.time.*;

import exceptions.SalaConAforoMaximoException;
import exceptions.SalaNoCompatibleException;
import utiles.Horario;

/**
 * Clase SalaClimatizada
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class SalaClimatizada extends Sala {
    /** SerialVersionUID */
    private static final long serialVersionUID = 5245789868461551735L;

    /** Horario de la climatización de la sala */
    private Horario horario;

    /**
     * Constructor de la clase SalaClimatizada que no tiene padre
     * 
     * @param horario     Horario de la climatización de la sala
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param descripcion Descripción de la sala
     */
    public SalaClimatizada(Horario horario, String nombre, int aforo, String descripcion) {
        super(nombre, aforo, descripcion);
        this.horario = horario;
    }

    /**
     * Constructor de la clase SalaClimatizada que tiene padre
     * 
     * @param horario     Horario de la climatización de la sala
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param padre       Sala padre
     * @param descripcion Descripción de la sala
     * @throws SalaNoCompatibleException   si la sala padre no es una sala
     *                                     climatizada
     * @throws SalaConAforoMaximoException si el aforo disponible de la sala es
     *                                     menor que el aforo de la nueva sala hija
     */
    public SalaClimatizada(Horario horario, String nombre, int aforo, Sala padre, String descripcion)
            throws SalaConAforoMaximoException, SalaNoCompatibleException {
        super(nombre, aforo, padre, descripcion);
        this.horario = horario;
    }

    /**
     * Establece el horario de la Sala Climatizada
     * 
     * @param horario Horario de la climatización de la sala
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    /**
     * Devuelve el horario de la Sala Climatizada
     * 
     * @return Horario de la climatización de la sala
     */
    public Horario getHorario() {
        return this.horario;
    }

    /**
     * Comprueba si la sala se puede usar en el horario h
     * 
     * @param fecha Fecha a comprobar
     * @param h     Horario a comprobar
     * @return true si la sala se puede usar en el horario h, false si no se puede
     *         usar
     */
    @Override
    public boolean habilitada(LocalDate fecha, Horario h) {
        if (super.habilitada(fecha, h) && horario.horarioIncluido(h)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si la sala se puede usar en la hora hora
     * 
     * @param fecha Fecha a comprobar
     * @param hora  Hora a comprobar
     * @return true si la sala se puede usar en la hora hora, false si no se puede
     *         usar
     */
    @Override
    public boolean habilitada(LocalDate fecha, LocalTime hora) {
        if (super.habilitada(fecha, hora) && horario.horaIncluida(hora)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si la sala se puede usar en la hora actual
     * 
     * @return true si la sala se puede usar ahora, false si no se puede usar
     */
    @Override
    public boolean habilitada() {
        return habilitada(LocalDate.now(), LocalTime.now());
    }

    /**
     * Establece la sala hija de la sala climatizada
     * 
     * @param hijo nueva sala hija
     * @throws SalaConAforoMaximoException si el aforo disponible de la sala es
     *                                     menor que el aforo de la nueva sala hija
     * @throws IllegalArgumentException    si la sala
     */
    @Override
    public void setHijo(Sala hijo) throws SalaConAforoMaximoException, SalaNoCompatibleException {
        if (hijo instanceof SalaClimatizada) {
            super.setHijo(hijo);
        } else {
            throw new SalaNoCompatibleException(hijo, "No es Sala Climatizada");
        }
    }

    /**
     * Devuelve el horario en un string
     * 
     * @return Horario en un string
     */
    @Override
    public String toString() {
        return super.toString() + " Horario: " + this.horario;
    }

    /**
     * Crea una sala hija.
     * 
     * @param h           Horario de la sala
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param descripcion Descripción de la sala
     * @return Sala creada o null si ha habido alguna excepción.
     */
    public SalaClimatizada crearSalaHija(Horario h, String nombre, int aforo, String descripcion) {
        try {
            if (!horario.horarioIncluido(h)) {
                throw new SalaNoCompatibleException(this, "El horario no está incluido en el horario de la sala");
            }
            return new SalaClimatizada(h, nombre, aforo, this, descripcion);
        } catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
            return null;
        }
    }

    /**
     * Crea una sala hija climatizada con el mismo horaio que la sala padre.
     * 
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param descripcion Descripción de la sala
     * @return Sala creada o null si ha habido alguna excepción.
     */
    @Override
    public SalaClimatizada crearSalaHija(String nombre, int aforo, String descripcion) {
        return crearSalaHija(this.horario, nombre, aforo, descripcion);
    }

    @Override
    public boolean esClimatizada() {
        return true;
    }

    // Como no puede haber dos salas con el mismo id, el equal y hashCode de sala
    // funcionan.
}
