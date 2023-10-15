/**
 * Implementación de la tarifa de pago por uso.
 */
package usuario.cliente.tarifa;

/**
 * Clase que representa una tarifa de pago por uso.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class TarifaPagoUso extends Tarifa {

    /** Serial Version UID */
    private static final long serialVersionUID = -6981306568290243395L;

    /**
     * Constructor de la clase TarifaPagoUso (Implementado por warning de Javadoc)
     */
    public TarifaPagoUso() {
    }

    /**
     * Devuelve en un String los datos de la tarifa
     * 
     * @return String con los datos de la tarifa
     */
    @Override
    public String toString() {
        return "TarifaPagoUso ";
    }

    /**
     * Devuelve si hay que cobrar al registrarse
     * 
     * @return false
     */
    @Override
    public boolean cobrarAlRegistrarse() {
        return false;
    }

    @Override
    public double getDevolucionCancelacionPorMonitor() {
        return Tarifa.getDEVOLUCION_MONITOR();
    }

    @Override
    public double getDevolucionCancelacionPorCliente() {
        return Tarifa.getDEVOLUCION_CLIENTE();
    }

    @Override
    public double renovar(int duracion) {
        return 0;
    }

    @Override
    public String toStringInterfaz() {
        return this.toString();
    }
}
