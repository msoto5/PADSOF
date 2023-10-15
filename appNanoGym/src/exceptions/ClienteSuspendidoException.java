package exceptions;

import usuario.cliente.Cliente;

/**
 * Excepción que se lanza cuando se intenta mover un servicio de la lista de
 * espera a la lista de reservas y la lista de espera está vacía.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class ClienteSuspendidoException extends Exception {

    /** Serial version UID */
    private static final long serialVersionUID = 8560514165422717985L;

    /** Cliente suspendido que ha provocado el error */
    private Cliente cliente;

    /**
     * Constructor de la clase ClienteSuspendidoException
     * 
     * @param cl Cliente que ha provocado la excepcion
     */
    public ClienteSuspendidoException(Cliente cl) {
        super("El cliente " + cl.getNombreCompleto() + " está suspendido");
        cliente = cl;
    }

    /**
     * Devuelve el cliente suspendido que ha provocado el error
     * 
     * @return Cliente suspendido que ha provocado el error
     */
    public Cliente getCliente() {
        return cliente;
    }
}
