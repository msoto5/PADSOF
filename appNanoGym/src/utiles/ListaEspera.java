/**
 * @file ListaEspera.java
 * @package utiles
 */
package utiles;

import java.io.Serializable;
import java.util.PriorityQueue;

import servicio.Servicio;

/**
 * Clase que representa una lista de espera de un servicio.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class ListaEspera implements Serializable {

    /** Serial Version UID */
    private static final long serialVersionUID = -5695225329213933700L;

    /** Servicio al que pertenece la lista de espera */
    private Servicio serv;

    /** Reservas en lista de espera */
    private PriorityQueue<Reserva> listaEspera;

    /**
     * Constructor de la clase ListaEspera
     * 
     * @param serv servicio al que pertenece la lista de espera
     */
    public ListaEspera(Servicio serv) {
        this.serv = serv;
        listaEspera = new PriorityQueue<Reserva>();
    }

    /**
     * Devuelve el servicio al que pertenece la lista de espera
     * 
     * @return servicio al que pertenece la lista de espera
     */
    public Servicio getServicio() {
        return serv;
    }

    /**
     * Establece el servicio al que pertenece la lista de espera
     * 
     * @param serv servicio al que pertenece la lista de espera
     * @throws IllegalArgumentException si el servicio es nulo
     */
    public void setServicio(Servicio serv) throws IllegalArgumentException {
        if (serv == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }
        this.serv = serv;
    }

    /**
     * Devuelve la lista de espera del servicio
     * 
     * @return lista de espera del servicio
     */
    public PriorityQueue<Reserva> getListaEspera() {
        return listaEspera;
    }

    /**
     * Establece una nueva lista de espera del servicio
     * 
     * @param listaEspera lista de espera del servicio
     * @throws IllegalArgumentException si la lista de espera es nula
     */
    public void setListaEspera(PriorityQueue<Reserva> listaEspera) throws IllegalArgumentException {
        if (listaEspera == null) {
            throw new IllegalArgumentException("La lista de espera no puede ser nula");
        }
        this.listaEspera = listaEspera;
    }

    /**
     * Añade una reserva a la lista de espera
     * 
     * @param r reserva a añadir. Debe tener el estado en lista de espera
     * @return true si se añade correctamente, false en caso contrario
     * @throws IllegalArgumentException si la reserva es nula
     */
    public boolean addReserva(Reserva r) throws IllegalArgumentException {
        if (r == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        } else if (r.getServicio() != this.serv) {
            throw new IllegalArgumentException("La reserva no pertenece a este servicio");
        } else if (r.getEstado() != EstadoReserva.LISTA_ESPERA) {
            throw new IllegalArgumentException("La reserva no está en lista de espera");
        }
        return listaEspera.add(r);
    }

    /**
     * Elimina una reserva de la lista de espera
     * 
     * @param r reserva a eliminar. Debe tener el estado en lista de espera
     * @return true si se elimina correctamente, false en caso contrario
     * @throws IllegalArgumentException si la reserva es nula
     */
    public boolean removeReserva(Reserva r) throws IllegalArgumentException {
        if (r == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        } else if (r.getServicio() != this.serv) {
            throw new IllegalArgumentException("La reserva no pertenece a este servicio");
        } else if (r.getEstado() != EstadoReserva.LISTA_ESPERA) {
            throw new IllegalArgumentException("La reserva no está en lista de espera");
        }
        return listaEspera.remove(r);
    }

    /**
     * Elimina la reserva de la lista de espera y la devuelve
     * 
     * @return reserva eliminada de la lista de espera
     */
    public Reserva pollReserva() {
        return listaEspera.poll();
    }

    /**
     * Devuelve la reserva de la lista de espera sin eliminarla
     * 
     * @return reserva de la lista de espera
     */
    public Reserva peekReserva() {
        return listaEspera.peek();
    }

    /**
     * Devuelve true si la lista de espera está vacía, false en caso contrario
     * 
     * @return true si la lista de espera está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return listaEspera.isEmpty();
    }

    /**
     * Devuelve el número de reservas en la lista de espera
     * 
     * @return número de reservas en la lista de espera
     */
    public int size() {
        return listaEspera.size();
    }

    /**
     * Devuelve si la reserva introducida es la siguiente en la lista de espera
     * 
     * @param r reserva a comprobar
     * @return true si la reserva es la siguiente en la lista de espera, false en
     *         caso contrario
     */
    public boolean isNextReserva(Reserva r) {
        return r.compareTo(listaEspera.peek()) == 0;
    }

    /**
     * Devuelve una cadena con la información de la lista de espera
     * 
     * @return cadena con la información de la lista de espera
     */
    @Override
    public String toString() {
        return "ListaEspera [serv=" + serv + ", listaEspera=" + listaEspera + "]";
    }
}
