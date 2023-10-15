/**
 * Clase Reserva
 */
package utiles;

import java.io.Serializable;

import exceptions.ClienteSuspendidoException;
import servicio.Servicio;
import usuario.cliente.*;

/**
 * Clase que implementa una reserva de un servicio por parte de un cliente
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Reserva implements Serializable, Comparable<Reserva> {

    /** Cliente que tiene la reserva */
    Cliente cliente;

    /** Servicio reservado */
    Servicio servicio;

    /** Estado de la reserva */
    EstadoReserva estado;

    /**
     * Constructor de la clase Reserva
     * 
     * @param cliente  Cliente que tiene la reserva
     * @param servicio Servicio reservado
     * @param estado   Estado de la reserva
     * @throws ClienteSuspendidoException Si el cliente está suspendido
     */
    public Reserva(Cliente cliente, Servicio servicio, EstadoReserva estado) throws ClienteSuspendidoException {
        if (cliente.isSuspendido() == true) {
            throw new ClienteSuspendidoException(cliente);
        } else {
            this.cliente = cliente;
        }
        this.servicio = servicio;
        this.estado = estado;
    }

    /**
     * Devuelve el cliente que tiene la reserva
     * 
     * @return Cliente que tiene la reserva
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Establece el cliente que tiene la reserva
     * 
     * @param cliente Cliente que tiene la reserva
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve el servicio reservado
     * 
     * @return Servicio reservado
     */
    public Servicio getServicio() {
        return this.servicio;
    }

    /**
     * Establece el servicio reservado
     * 
     * @param servicio Servicio reservado
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Devuelve el estado de la reserva
     * 
     * @return Estado de la reserva
     */
    public EstadoReserva getEstado() {
        return this.estado;
    }

    /**
     * Establece el estado de la reserva
     * 
     * @param estado Estado de la reserva
     */
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
        this.cliente.addNotificacion("Ha cambiado el estado de su reserva de "
                + this.servicio.getNombre() + " a "
                + estado.toString());
    }

    /**
     * Cambia el estado de la reserva a PAGADO
     */
    public void pagarReserva() {
        setEstado(EstadoReserva.PAGADO);
    }

    /**
     * Compara dos reservas por el servicio y luego por el cliente
     * 
     * @param r2 Reserva a comparar
     * @return 0 si son iguales, 1 si es mayor y -1 si es menor
     */
    @Override
    public int compareTo(Reserva r2) {
        int ret = this.servicio.compareTo(r2.getServicio());
        if (ret == 0) {
            ret = this.cliente.compareTo(r2.getCliente());
        }
        return ret;
    }

    /**
     * Devuelve una cadena con la información de la reserva
     * 
     * @return Cadena con la información de la reserva
     */
    @Override
    public String toString() {
        return "Reserva [cliente=" + this.cliente.toString2() + ", servicio=" + this.servicio + ", estado="
                + this.estado + "]";
    }

    /**
     * Compara dos reservas. Dos reservas son iguales si el cliente y el servicio
     * son iguales.
     * El estado no se tiene en cuenta.
     * 
     * @param o Reserva a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Reserva)) {
            return false;
        }
        Reserva r = (Reserva) o;
        return this.cliente.equals(r.getCliente()) && this.servicio.equals(r.getServicio());
    }

    /**
     * Devuelve el hashCode de la reserva
     * 
     * @return hashCode de la reserva
     */
    @Override
    public int hashCode() {
        return 11 * this.cliente.hashCode() * this.servicio.hashCode();
    }

    /**
     * Cancelar reserva. Únicamente la establece como cancelada.
     */
    private void cancelar() {
        setEstado(EstadoReserva.CANCELADA);
        // this.estado = EstadoReserva.CANCELADA;
        this.servicio = null;
        this.cliente = null;
    }

    /**
     * Devuelve el coste de la reserva
     * 
     * @return true si se ha devuelto el cobro, false en caso contrario
     */
    public boolean devolverCobroCliente() {
        boolean ret = true;
        if (this.estado == EstadoReserva.PAGADO) {
            ret = this.cliente.devolverCobro(this.getCoste(), "Cancelación de reserva");
            if (!ret) {
                System.err.println("ERROR: No se ha podido devolver el cobro al cliente");
                return false;
            }
            setEstado(EstadoReserva.CANCELADA);
        }

        return ret;
    }

    /**
     * Procedimiento al cancelar una reserva.
     * 
     * @return true si se ha cancelado la reserva, false en caso contrario
     */
    public boolean reservaCancelada() {
        boolean ret = true;
        if (!this.devolverCobroCliente()) {
            return false;
        }
        this.cancelar();
        return ret;
    }

    /**
     * Procedimiento al cancelar una reserva por parte del cliente
     * 
     * @param s Cliente que cancela la reserva
     * @return true si se ha cancelado la reserva, false en caso contrario
     */
    public boolean reservaCanceladaPorCliente(Cliente s) {
        boolean ret = true;
        if (!this.cliente.equals(s)) {
            return false;
        }
        ret = this.servicio.cancelarReservaPorCliente(this);
        if (!ret) {
            System.err.println("ERROR: No se ha podido cancelar la reserva en el servicio");
        }
        if (!this.reservaCancelada()) {
            return false;
        }

        return ret;
    }

    /**
     * Procedimiento al cancelar una reserva por parte del servicio
     * 
     * @param s Servicio que cancela la reserva
     * @return true si se ha cancelado la reserva, false en caso contrario
     */
    public boolean reservaCanceladaPorServicio(Servicio s) {
        boolean ret = true;
        if (!this.servicio.equals(s)) {
            return false;
        }
        ret = this.cliente.cancelarReservaPorServicio(this);
        if (!ret) {
            System.err.println("ERROR: No se ha podido cancelar la reserva en el servicio");
        }
        if (!this.reservaCancelada()) {
            return false;
        }
        return ret;
    }

    /**
     * Devuelve el coste de la reserva
     * 
     * @return Coste de la reserva
     */
    public double getCoste() {
        return this.servicio.getPrecio();
    }

    /**
     * Devuelve la devolución que se le hace al cliente por la cancelación de la
     * reserva
     * 
     * @return Devolución que se le hace al cliente por la cancelación de la reserva
     */
    public double getCosteDevolucion() {
        return this.cliente.getTarifa().calcularDevolucionCancPorMonitor(this.servicio);
    }

    /**
     * Procedimiento al salir de la lista de espera
     * 
     * @return true si se ha realizado la operación correctamente, false en caso
     *         contrario
     */
    public boolean saleDeListaDeEspera() {

        String desc = "Pago servicio " + this.servicio.getNombre() + ". Fecha: " + this.servicio.getFecha();

        boolean ret = this.cliente.cobrar(getCoste(), desc);
        if (ret) {
            this.setEstado(EstadoReserva.PAGADO);
        }

        return ret;
    }
}
