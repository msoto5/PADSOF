/**
 * Implementación de la clase TarjetaBancaria
 */
package usuario.cliente;

import java.io.Serializable;
import java.time.LocalDate;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

/**
 * Clase que representa una tarjeta bancaria de un cliente
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class TarjetaBancaria implements Serializable {

    /** Numero de la tarjeta */
    private final String numeroTarjeta;

    /** Fecha de caducidad de la tarjeta */
    private final LocalDate fechaCaducidad;

    /** Cvv de la tarjeta */
    private final String cvv;

    /**
     * Constructor de la clase TarjetaBancaria
     * 
     * @param numeroTarjeta  Numero de la tarjeta
     * @param fechaCaducidad Fecha de caducidad de la tarjeta
     * @param cvv            Cvv de la tarjeta
     * @throws InvalidCardNumberException el numero de la tarjeta no es valido
     * @throws IllegalArgumentException   la fecha de caducidad es anterior a la
     *                                    fecha actual o el cvv no tiene 3 digitos
     */
    public TarjetaBancaria(String numeroTarjeta, LocalDate fechaCaducidad, String cvv)
            throws InvalidCardNumberException, IllegalArgumentException {
        if (!TeleChargeAndPaySystem.isValidCardNumber(numeroTarjeta)) {
            throw new InvalidCardNumberException("El numero de la tarjeta no es valido");
        } else if (fechaCaducidad.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de caducidad de la tarjeta no puede ser anterior a la fecha actual");
        } else if (cvv.length() != 3) {
            throw new IllegalArgumentException("El cvv de la tarjeta debe tener 3 digitos");
        }

        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.cvv = cvv;
    }

    /**
     * Devuelve el numero de la tarjeta
     * 
     * @return el numero de la tarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Devuelve la fecha de caducidad de la tarjeta
     * 
     * @return la fecha de caducidad de la tarjeta
     */
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Devuelve el cvv de la tarjeta
     * 
     * @return el cvv de la tarjeta
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Comprueba si la fecha de caducidad de la tarjeta es valida
     * 
     * @return true si la fecha de caducidad es valida, false en caso contrario
     */
    public boolean fechaCaducidadValida() {
        return fechaCaducidad.isAfter(LocalDate.now());
    }

    /**
     * Carga una cantidad en la tarjeta
     * 
     * @param cantidad    Cantidad a cargar
     * @param descripcion Descripcion de la carga
     * @param trace       Si se quiere hacer un seguimiento de la carga que imprime
     *                    por pantalla
     * @return true si la carga se ha realizado correctamente, false en caso
     *         contrario
     */
    public boolean cobrar(double cantidad, String descripcion, boolean trace) {
        if (fechaCaducidadValida()) {
            try {
                TeleChargeAndPaySystem.charge(numeroTarjeta, descripcion, cantidad, trace);
                return true;
            } catch (InvalidCardNumberException e) {
                System.err.println(
                        "Error al cobrar la tarjeta: " + e.getMessage() + " " + e.getCardNumberString() + "Incorrecta");
            } catch (FailedInternetConnectionException e) {
                System.err.println("Error al cobrar la tarjeta: " + e.getMessage() + " " + e.getCardNumberString()
                        + "Conexion fallida");
            } catch (OrderRejectedException e) {
                System.err.println("Error al cobrar la tarjeta: " + e.getMessage() + " " + e.getCardNumberString()
                        + "Orden rechazada");
            }
        }
        System.err.println("Error al cobrar la tarjeta: Tarjeta caducada");
        return false;
    }

    /**
     * Carga una cantidad en la tarjeta sin hacer un seguimiento de la carga
     * 
     * @param cantidad    Cantidad a cargar
     * @param descripcion Descripcion de la carga
     * @return true si la carga se ha realizado correctamente, false en caso
     *         contrario
     */
    public boolean cobrar(double cantidad, String descripcion) {
        return cobrar(cantidad, descripcion, false);
    }

    @Override
    public String toString() {
        return "TarjetaBancaria [numeroTarjeta=" + numeroTarjeta + ", fechaCaducidad=" + fechaCaducidad + ", cvv=" + cvv
                + "]";
    }
}
