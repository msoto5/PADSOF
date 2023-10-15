package usuario.cliente;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase que representa una suspension de un cliente
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Suspension implements Serializable {
    private static final long serialVersionUID = 6648517350030800461L;

    /** Duración en días de las suspensiones */
    private static int DIAS_SUSPENSION = 3;

    /** Fecha Inicial de la suspensión */
    private final LocalDate fInicial;

    /**
     * Constructor de la clase Suspension.
     * Establece la fecha inicial de la suspensión a la fecha actual
     */
    public Suspension() {
        this.fInicial = LocalDate.now();
    }

    /**
     * Devuelve la fecha inicial de la suspensión
     * 
     * @return LocalDate con la fecha inicial de la suspensión
     */
    public LocalDate getfInicial() {
        return fInicial;
    }

    /**
     * Devuelve la fecha final de la suspensión
     * 
     * @return LocalDate la fecha final de la suspensión
     */
    public static int getDiasSuspension() {
        return DIAS_SUSPENSION;
    }

    /**
     * Establece la duración en días de las suspensiones
     * 
     * @param DiasSuspension Duración en días de las suspensiones
     */
    public static void setDiasSuspension(int DiasSuspension) {
        DIAS_SUSPENSION = DiasSuspension;
    }

    /**
     * Devuelve true si el cliente está suspendido
     * 
     * @return true si el cliente está suspendido
     */
    public boolean isSuspendido() {
        return fInicial.plusDays(DIAS_SUSPENSION).isAfter(LocalDate.now());
    }

    /**
     * Devuelve la representación en String de la clase Suspension
     * 
     * @return String con la representación en String de la clase Suspension
     */
    @Override
    public String toString() {
        return "Suspension [fInicial=" + fInicial + "]";
    }
}
