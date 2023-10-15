package utiles;

/**
 * Enumerado que representa los estados de una reserva
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public enum EstadoReserva {
    /** Reserva pagada */
    PAGADO(0),

    /** Reserva en lista de espera */
    LISTA_ESPERA(1),

    /** Reserva reservada */
    RESERVADA(2),
    
    /** Reserva cancelada */
    CANCELADA(3);

    /** Valor del estado */
    private int estado;

    /**
     * Constructor de la enumeración
     * 
     * @param estado Valor del estado
     */
    EstadoReserva(int estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el valor del estado
     * 
     * @return Valor del estado
     */
    public int getEstado() {
        return this.estado;
    }

    /**
     * Establece el valor del estado
     * 
     * @param estado Valor del estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
}