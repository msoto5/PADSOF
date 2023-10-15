package exceptions;

import servicio.Servicio;

/**
 * Excepción que se lanza cuando se intenta mover un servicio de la lista de
 * espera a la lista de reservas y la lista de espera está vacía.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class ListaEsperaVaciaException extends Exception {

    /** Serial version UID */
    private static final long serialVersionUID = 6679888379019280999L;

    /** Servicio que ha provocado el error */
    private Servicio serv;

    /**
     * Constructor de la clase ListaEsperaVaciaException
     * 
     * @param serv servicio que ha provocado la excepcion
     */
    public ListaEsperaVaciaException(Servicio serv) {
        super("El servicio " + serv.getNombre() + " no tiene reservas en lista de espera");
        this.serv = serv;
    }

    /**
     * Devuelve el servicio con la lista de espera vacía que ha provocado el error
     * 
     * @return Servicio con lista de espera vacía que ha provocado el error
     */
    public Servicio getServicio() {
        return serv;
    }
}
