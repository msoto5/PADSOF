/**
 * Implementa la clase Usuario
 */
package usuario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import nanoGym.NanoGym;
import utiles.GestorNotificaciones;

/**
 * Clase abstracta que representa un usuario del sistema
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public abstract class Usuario implements Serializable, Comparable<Usuario> {

    /** Nombre de usuario */
    private String username;

    /** Contraseña */
    private String contraseña;

    /** Gestor de notificaciones */
    private GestorNotificaciones gN;

    /** Fichero BackUp */
    private static final String FICHERO_BACKUP = "./src/backUp/usuarios.ser";

    /**
     * Constructor de la clase Usuario
     * 
     * @param username   Nombre de usuario
     * @param contraseña Contraseña
     * 
     * @throws IllegalArgumentException si alguno de los argumentos son nulos o
     *                                  vacíos
     */
    public Usuario(String username, String contraseña) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío.");
        } else if (contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
        }

        this.username = username;
        this.contraseña = contraseña;
        this.gN = new GestorNotificaciones();
    }

    /**
     * Devuelve el nombre de usuario
     * 
     * @return nombre de usuario
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Establece el nombre de usuario
     * 
     * @param newUsername nuevo nombre de usuario
     */
    public void setUsername(String newUsername) {
        if (newUsername == null || newUsername.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío.");
        }

        this.username = newUsername;
    }

    /**
     * Establece la contraseña
     * 
     * @param newContraseña nueva contraseña
     */
    public void setContraseña(String newContraseña) {
        if (newContraseña == null || newContraseña.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
        }

        this.contraseña = newContraseña;
    }

    /**
     * Devuelve la contraseña
     * 
     * @return contraseña
     */
    public String getContraseña() {
        return this.contraseña;
    }

    /**
     * Devuelve la información del usuario en un String
     * 
     * @return String con la información del usuario
     */
    @Override
    public String toString() {
        return "- Usuario: " + this.username
                + "\nContraseña: " + this.contraseña;
    }

    /**
     * Función para mostrar menu de los diferentes tipos de usuarios
     */
    public abstract void mostrarMenuPrincipal();

    /**
     * Función switch para cambiar de menu dependiendo del tipo de usuario
     * 
     * @param res respuesta del usuario
     * @param ng  objeto NanoGym
     */
    public abstract void switchMenuPrincipal(int res, NanoGym ng);

    /**
     * Devuelve si el usuario es cliente. Método abstracto.
     * 
     * @return true si es cliente, false si no
     */
    public abstract boolean esCliente();

    /**
     * Devuelve si el usuario es Monitor. Método abstracto.
     * 
     * @return true si es monitor, false si no
     */
    public abstract boolean esMonitor();

    /**
     * Devuelve el gestor de notificaciones
     * 
     * @return gestor de notificaciones
     */
    protected GestorNotificaciones getGN() {
        return gN;
    }

    /**
     * Obtiene las notificaciones de un usuario, pero no la marca como leídas
     * 
     * @return notificaciones del usuario
     */
    public List<String> getNotificaciones() {
        return gN.getNotificaciones();
    }

    /**
     * Obtiene las notificaciones de un usuario y las marca como leídas
     * 
     * @return notificaciones del usuario
     */
    public List<String> readNotificaciones() {
        return gN.readNotificaciones();
    }

    /**
     * Añade una notificación al cliente
     * 
     * @param not Notificación a añadir
     */
    public void addNotificacion(String not) {
        getGN().addNotificacion(not);
    }

    /**
     * Almacena la Usuario en el Fichero de backup
     * 
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public boolean exportarUsuario() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_BACKUP));
            oos.writeObject(this);
            oos.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero");
            return false;
        }
    }

    /**
     * Almacena todas las Usuarios de un mapa en el fichero de backup
     * 
     * @param map mapa de los usuarios
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public static boolean exportarAllSalas(Map<String, Usuario> map) {
        try {
            FileOutputStream fos = new FileOutputStream(FICHERO_BACKUP);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Usuario usr : map.values()) {
                oos.writeObject(usr);
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
     * Lee un Usuario desde el fichero de backup
     * 
     * @return Usuario leido o null en caso de error
     */
    public static Usuario importarUsuario() {
        Usuario ret = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_BACKUP));
            ret = (Usuario) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return ret;
    }

    /**
     * Lee todas las Usuarios del fichero de backup
     * 
     * @return lista de Usuarios leidos
     */
    public static HashMap<String, Usuario> importarAllUsuarios() {
        HashMap<String, Usuario> map = new HashMap<>();
        Usuario usr;
        try {
            FileInputStream fis = new FileInputStream(FICHERO_BACKUP);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                usr = (Usuario) ois.readObject();
                map.put(usr.getUsername(), usr);
            }

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return map;
    }

    /**
     * Compara dos usuarios por su nombre de usuario
     * 
     * @param o usuario a comparar
     * @return 0 si son iguales, 1 si el usuario es mayor, -1 si el usuario es menor
     */
    @Override
    public int compareTo(Usuario o) {
        return this.username.compareTo(o.getUsername());
    }

    /**
     * Comprueba si dos usuarios son iguales
     * 
     * @param o objeto a comparar
     * @return true si son iguales, false si no
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Usuario) {
            Usuario u = (Usuario) o;
            return this.username.equals(u.getUsername());
        }
        return false;
    }

    /**
     * Devuelve el hashcode del usuario
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return 11 * this.username.hashCode();
    }
}
