package exceptions;

import servicio.Servicio;

/**
 * Excepción que se lanza cuando se intenta añadir una reserva a un servicio que
 * ya tiene el afoto al máximo.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class ServicioAtMaximunCapacityException extends Exception {

    /** Serial version UID */
    private static final long serialVersionUID = 4402652682677076176L;

    /** Servicio que ha provocado el error */
    private Servicio serv;

    /**
     * Constructor de la clase ServicioAtMaximunCapacityException
     * 
     * @param serv Mensaje de la excepción
     */
    public ServicioAtMaximunCapacityException(Servicio serv) {
        super("El servicio " + serv.getNombre() + " ya tiene el aforo máximo");
        this.serv = serv;
    }

    /**
     * Devuelve el servicio con el aforo máximo que ha provocado el error
     * 
     * @return Servicio con aforo máximo que ha provocado el error
     */
    public Servicio getServicio() {
        return serv;
    }
}