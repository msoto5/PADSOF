package exceptions;

import sala.Sala;

/**
 * Excepcion que se provoca cuando una sala no es compatible en una acción
 * 
 * @author Miguel Soto, Nicolas VIctorino y DIego Gonzalez
 */
public class SalaNoCompatibleException extends Exception {
    /** Sala que ha provocado el error */
    private Sala sala;

    /** Motivo de la excepcion */
    private String motivo;

    /**
     * Constructor de la clase SalaNoCompatibleException
     * 
     * @param sala   Sala que ha provocado el error
     * @param motivo Motivo porque no son compatibles
     */
    public SalaNoCompatibleException(Sala sala, String motivo) {
        super("La sala " + sala.getNombre() + " no es compatible porque " + motivo);
        this.sala = sala;
        this.motivo = motivo;
    }

    /**
     * Devuelve la sala que ha provocado el error
     * 
     * @return Sala que ha provocado el error
     */
    public Sala getSala() {
        return this.sala;
    }

    /**
     * Devuelve el motivo de la excepción
     * 
     * @return Motivo de la excepcion.
     */
    public String getMotivo() {
        return motivo;
    }
}
