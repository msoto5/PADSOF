package exceptions;

import sala.Sala;

/**
 * Excepción que se lanza cuando se intenta añadir un hijo a una sala que ya
 * tiene el aforo máximo
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class SalaConAforoMaximoException extends Exception {

    /** Sala que ha provocado el error */
    private Sala sala;

    /**
     * Constructor de la clase SalaConAforoMaximoException
     * 
     * @param sala Sala con aforo máximo que ha provocado el error
     */
    public SalaConAforoMaximoException(Sala sala) {
        super("La sala " + sala.getNombre() + " ya tiene el aforo máximo");
        this.sala = sala;
    }

    /**
     * Devuelve la sala cpn aforo máximo que ha provocado el error
     * 
     * @return Sala con aforo máximo que ha provocado el error
     */
    public Sala getSala() {
        return this.sala;
    }
}
