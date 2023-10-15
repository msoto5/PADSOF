package utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un gestor de notificaciones
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class GestorNotificaciones implements Serializable {

    /** Lista de Notificaciones no leidas */
    private List<String> noLeidas = new ArrayList<String>();

    /**
     * Constructor de la clase GestorNotificaciones
     */
    public GestorNotificaciones() {
    }

    /**
     * Devuelve la lista de notificaciones no leidas
     * 
     * @return Lista de notificaciones no leidas
     */
    public List<String> getNotificaciones() {
        return noLeidas;
    }

    /**
     * Añade una notificacion a la lista de notificaciones no leidas
     * 
     * @param not Notificacion a añadir
     * @return true si se ha añadido correctamente, false en caso contrario
     */
    public boolean addNotificacion(String not) {
        return noLeidas.add(not);
    }

    /**
     * Lee las notificaciones no leidas. Una vez leidas, se eliminan de la lista.
     * 
     * @return Lista de notificaciones no leidas
     */
    public List<String> readNotificaciones() {
        List<String> aux = new ArrayList<String>(noLeidas);
        noLeidas.clear();
        return aux;
    }

    /**
     * Devuelve la información de un gestor de notificaciones como un String
     * 
     * @return Información del gestor de notificaciones
     */
    @Override
    public String toString() {
        return "GestorNotificaciones [noLeidas=" + noLeidas + "]";
    }
}
