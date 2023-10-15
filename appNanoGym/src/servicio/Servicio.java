/**
 * Clase que representa un servicio de la aplicación.
 */

package servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.*;

import utiles.EstadoReserva;
import utiles.Horario;
import utiles.Reserva;

import exceptions.ServicioAtMaximunCapacityException;

/**
 * Clase abstracta que representa un servicio del gimnasio.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public abstract class Servicio implements Comparable<Servicio>, Serializable {

    /** Nombre del servicio **/
    private String nombre;

    /** Descripción del servicio **/
    private String descripcion;

    /** Reservas del servicio **/
    private PriorityQueue<Reserva> reservas;

    /** Fichero de BackUp */
    private static final String FICHERO_BACKUP = "./src/backUp/reservas.ser";

    /**
     * Constructor de la clase Servicio
     * 
     * @param nombre      Nombre del servicio
     * @param descripcion Descripcion del servicio
     * @throws IllegalArgumentException Si el nombre o la descripción son nulos
     */
    public Servicio(String nombre, String descripcion) throws IllegalArgumentException {
        if (nombre == null || descripcion == null) {
            throw new IllegalArgumentException("El nombre y la descripción no pueden ser nulos");
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.reservas = new PriorityQueue<Reserva>();
    }

    /**
     * Establece el nombre del servicio
     * 
     * @param nombre Nombre del servicio
     * @throws IllegalArgumentException Si el nombre es nulo
     */
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del servicio
     * 
     * @return Nombre del servicio
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece la descripción del servicio
     * 
     * @param descripcion Descripción del servicio
     * @throws IllegalArgumentException Si la descripción es nula
     */
    public void setDescripcion(String descripcion) throws IllegalArgumentException {
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción no puede ser nula");
        }
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la descripción del servicio
     * 
     * @return Descripción del servicio
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Establece las reservas del servicio
     * 
     * @param reservas Reservas del servicio
     * @throws IllegalArgumentException           Si las reservas son nulas
     * @throws ServicioAtMaximunCapacityException Si el servicio ya está al aforo
     *                                            máximo
     */
    public void setReservas(PriorityQueue<Reserva> reservas)
            throws IllegalArgumentException, ServicioAtMaximunCapacityException {
        if (reservas == null) {
            throw new IllegalArgumentException("Las reservas no pueden ser nulas");
        }
        if (reservas.size() > this.aforoDisponible()) {
            throw new ServicioAtMaximunCapacityException(this);
        }
        this.reservas = reservas;
    }

    /**
     * Devuelve las reservas del servicio
     * 
     * @return Reservas del servicio
     */
    public PriorityQueue<Reserva> getReservas() {
        return this.reservas;
    }

    /**
     * Añade una reserva al servicio
     * 
     * @param reserva Reserva a añadir
     * @return True si se ha añadido correctamente, false en caso contrario
     * @throws IllegalArgumentException           Si la reserva es nula
     * @throws ServicioAtMaximunCapacityException Si el servicio ya está al aforo
     *                                            máximo
     */
    public boolean addReserva(Reserva reserva) throws IllegalArgumentException, ServicioAtMaximunCapacityException {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        } else if (reserva.getServicio() != this) {
            throw new IllegalArgumentException("La reserva no pertenece a este servicio");
        } else if (this.reservas.contains(reserva)) {
            return false;
        } else if (this.aforoDisponible() == 0) {
            throw new ServicioAtMaximunCapacityException(this);
        }
        reserva.setEstado(EstadoReserva.RESERVADA);
        return this.reservas.add(reserva);
    }

    /**
     * Elimina una reserva del servicio
     * 
     * @param reserva Reserva a eliminar
     * @return True si se ha eliminado correctamente, false en caso contrario
     * @throws IllegalArgumentException Si la reserva es nula
     */
    public boolean removeReserva(Reserva reserva) throws IllegalArgumentException {
        if (reserva == null)
            throw new IllegalArgumentException("La reserva no puede ser nula");
        if (!this.reservas.contains(reserva))
            return false;

        return this.reservas.remove(reserva);
    }

    /**
     * Devuelve una cadena de caracteres con la información del servicio
     * 
     * @return Cadena de caracteres con la información del servicio
     */
    @Override
    public String toString() {
        return "-Nombre: " + this.nombre
                + "\n-Descripción: " + this.descripcion
                + "\n-Aforo disponible: " + this.aforoDisponible()
                + "\n-Reservas: " + this.reservas.toString();
    }

    /**
     * Método abstracto que devuelve el aforo disponible del servicio
     * 
     * @return Aforo disponible del servicio
     */
    public abstract int aforoDisponible();

    /**
     * Devuelve el aforo total del servicio
     * @return Aforo total del servicio
     */
    public int aforoTotal() {
        return this.aforoDisponible() + this.reservas.size();
    }

    /**
     * Devuelve la fecha cuando empieza el servicio
     * 
     * @return Fecha cuando empieza el servicio
     */
    public abstract LocalDate getFecha();

    /**
     * Devuelve el horario del servicio
     * 
     * @return Horario del servicio
     */
    public abstract Horario getHorario();

    /**
     * Devuelve el precio del servicio. Método abstracto
     * 
     * @return Precio del servicio
     */
    public abstract double getPrecio();

    /**
     * Método abstracto que devuelve true si el servicio acepta nuevas reservas
     * 
     * @return True si el servicio acepta nuevas reservas, false en caso contrario
     */
    public boolean aceptaNuevaReserva() {
        return aforoDisponible() > 0;
    }

    /**
     * Procedimiento que se ejecuta cuando se cancela una reserva por parte del
     * cliente
     * 
     * @param reserva Reserva cancelada
     * @return True si se ha cancelado correctamente, false en caso contrario
     */
    public boolean cancelarReservaPorCliente(Reserva reserva) {
        return this.removeReserva(reserva);
    }

    /**
     * Procedimiento que se ejecuta cuando se cancela un servicio
     * 
     * @return True si se ha cancelado correctamente, false en caso contrario
     */
    public boolean cancelar() {
        for (Reserva reserva : this.reservas) {
            return reserva.reservaCanceladaPorServicio(this);
        }
        this.reservas.clear();
        return true;
    }

    /**
     * Método abstracto que devuelve si hay información grupal del servicio
     * 
     * @return True si se ha impreso correctamente, false en caso contrario
     */
    public abstract boolean imprimirInfoGrupal();

    /**
     * Método abstracto que devuelve la información de la búsqueda del servicio
     * 
     * @return Información de la búsqueda del servicio
     */
    public abstract String getBusquedaString();

    /**
     * Indica si el servicio tiene lista de espera. Por defecto es false.
     * @return True si tiene lista de espera, false en caso contrario
     */
    public boolean tieneListaEspera() {
        return false;
    }

    /**
     * Por defecto, no tiene monitor
     * @return Cadena vacía
     */
    public String getMonitorNombre() {
        return "";
    }

    /**
     * Almacena el servicio en el Fichero de backup
     * 
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public boolean exportarServicio() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_BACKUP));
            oos.writeObject(this);
            oos.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Almacena todos los servicios de un list en el fichero de backup
     * 
     * @param servs lista de salas a almacenar
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public static boolean exportarAllServicios(ArrayList<Servicio> servs) {
        try {
            FileOutputStream fos = new FileOutputStream(FICHERO_BACKUP);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Servicio sala : servs) {
                oos.writeObject(sala);
            }

            oos.close();
            fos.close();

        } catch (IOException e) {
            System.err.println("Error al escribir el fichero");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Lee un servicio desde el fichero de backup
     * 
     * @return servicio o null en caso de error
     */
    public static Servicio importarServicio() {
        Servicio ret = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_BACKUP));
            ret = (Servicio) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return ret;
    }

    /**
     * Lee todos los servicios del fichero de backup
     * 
     * @return lista de servicios leidos
     */
    public static ArrayList<Servicio> importarAllServicios() {
        ArrayList<Servicio> list = new ArrayList<>();
        Servicio serv;
        try {
            FileInputStream fis = new FileInputStream(FICHERO_BACKUP);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                serv = (Servicio) ois.readObject();
                list.add(serv);
            }

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return list;
    }

    /**
     * Compara dos servicios
     * 
     * @param servicio Servicio a comparar
     * @return 0 si son iguales, 1 si el servicio pasado por parámetro es mayor, -1
     */
    @Override
    public int compareTo(Servicio servicio) {
        if (servicio == null) {
            return 1;
        }
        int ret = this.getFecha().compareTo(servicio.getFecha());
        if (ret == 0) {
            ret = this.getNombre().compareTo(servicio.getNombre());
            if (ret == 0) {
                ret = this.getBusquedaString().compareTo(servicio.getBusquedaString());
            }
        }
        return ret;
    }

    /**
     * Compara si dos Servicios son iguales
     * 
     * @param obj Objeto a comparar
     * @return True si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Servicio) {
            return (compareTo((Servicio) obj)) == 0;
        }
        return false;
    }

    /**
     * Devuelve el hashcode del servicio
     * 
     * @return Hashcode del servicio
     */
    @Override
    public int hashCode() {
        return 35 * this.getFecha().hashCode() * this.getNombre().hashCode() * this.getBusquedaString().hashCode();
    }
}
