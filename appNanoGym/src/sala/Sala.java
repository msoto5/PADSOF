package sala;

import java.util.*;

import exceptions.SalaConAforoMaximoException;
import exceptions.SalaNoCompatibleException;
import servicio.actividad.*;
import utiles.Horario;

import java.io.*;
import java.io.Serializable;
import java.time.*;

/**
 * Clase Sala
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Sala implements Serializable {
    /** SerialVersionUID */
    private static final long serialVersionUID = -4100959309961258597L;

    /** Número de salas */
    private static int NUMSALAS = 0;

    /** Id sala */
    private final int id;

    /** Nombre de la sala */
    private String nombre;

    /** Aforo de la sala */
    private int aforo;

    /** Descripción de la sala */
    private String descripcion;

    /** Sala padre */
    private final Sala padre;

    /** Hijos de la sala */
    private ArrayList<Sala> hijos = new ArrayList<Sala>();

    /** Actividades de la sala */
    private ArrayList<Actividad> actividades = new ArrayList<Actividad>();

    /** Fichero con backup de las salas */
    private static final String FICHERO_BACKUP = "./src/backUp/salas.ser";

    /**
     * Constructor de la clase Sala sin padre
     * 
     * @param nombre       Nombre de la sala
     * @param aforo        Aforo de la sala
     * @param descricpcion Descripción de la sala
     */
    public Sala(String nombre, int aforo, String descricpcion) {
        this.id = Sala.NUMSALAS;
        Sala.NUMSALAS++;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descricpcion;
        this.padre = null;
    }

    /**
     * Constructor de la clase Sala con padre
     * 
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param padre       Sala padre
     * @param descripcion Descripción de la sala
     * @throws SalaConAforoMaximoException si el aforo de la sala es mayor que
     *                                     el aforo disponible de la sala padre
     * @throws SalaNoCompatibleException   si la sala padre no es compatible
     */
    public Sala(String nombre, int aforo, Sala padre, String descripcion)
            throws SalaConAforoMaximoException, SalaNoCompatibleException {
        this.id = Sala.NUMSALAS;
        Sala.NUMSALAS++;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.padre = padre;

        padre.setHijo(this);
    }

    /**
     * Devuelve el id de la sala
     * 
     * @return aforo disponible de la sala
     */
    public int getId() {
        return this.id;
    }

    /**
     * Establece el nombre de la sala
     * 
     * @param nombre nombre de la sala
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre de la sala
     * 
     * @return nombre de la sala
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el aforo de la sala
     * 
     * @param aforo aforo de la sala
     */
    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    /**
     * Devuelve el aforo de la sala
     * 
     * @return aforo de la sala
     */
    public int getAforo() {
        return this.aforo;
    }

    /**
     * Establece el aforo disponible de la sala
     * 
     * @param descripcion descripcion de la sala
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el aforo disponible de la sala
     * 
     * @return aforo disponible de la sala
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Devuelve la sala padre
     * 
     * @return sala padre
     */
    public Sala getPadre() {
        return this.padre;
    }

    /**
     * Añade un hijo a la sala
     * 
     * @param hijo hijo de la sala
     * @throws SalaConAforoMaximoException si el aforo disponible de la sala es
     *                                     menor que el aforo de la nueva sala hija
     * @throws SalaNoCompatibleException   si la sala padre no es compatible
     */
    public void setHijo(Sala hijo) throws SalaConAforoMaximoException, SalaNoCompatibleException {
        if (this.aforoDisponible() >= hijo.aforo) {
            this.hijos.add(hijo);
        } else {
            throw new SalaConAforoMaximoException(this);
        }
    }

    /**
     * Devuelve las subsalas de la sala
     * 
     * @return subsalas de la sala
     */
    public ArrayList<Sala> getHijos() {
        return this.hijos;
    }

    /**
     * Establece la actividad de la sala
     * 
     * @param activ actividad de la sala
     */
    public void setActividad(Actividad activ) {
        this.actividades.add(activ);
    }

    /**
     * Devuelve todas las actividades de la sala
     * 
     * @return actividades de la sala
     */
    public ArrayList<Actividad> getActividades() {
        return this.actividades;
    }

    /**
     * Devuelve todas las actividades de la sala ordenadas
     * 
     * @return actividades de la sala ordenadas
     */
    public ArrayList<Actividad> getActividadesOrdenadas() {
        this.ordenarActividades();
        return this.actividades;
    }

    /**
     * Devuelve la información de la sala en un String
     * 
     * @return String con la información de la sala
     */
    @Override
    public String toString() {
        String ret = this.nombre + "(" + this.id + "): " + this.descripcion + " .Aforo: " + this.aforo + "\n";
        if (this.padre != null) {
            ret += "Sala hija de: " + this.padre.nombre + "(" + this.padre.id + ")\n";
        }

        if (!(this.hijos.isEmpty())) {
            ret += "Hijos:\n";
            for (Sala i : this.hijos) {
                ret += "\t" + i.nombre + "(" + i.id + ")\n";
            }
        }
        return ret;
    }

    /**
     * Crea una sala hija
     * 
     * @param nombre      Nombre de la sala
     * @param aforo       Aforo de la sala
     * @param descripcion Descripción de la sala
     * @return Sala creada
     */
    public Sala crearSalaHija(String nombre, int aforo, String descripcion) {
        try {
            return new Sala(nombre, aforo, this, descripcion);
        } catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
            return null;
        }
    }

    /**
     * Crea una sala climatizada hija climatizada
     * 
     * @param h           horario de la sala
     * @param nombre      nombre de la sala
     * @param aforo       aforo de la sala
     * @param descripcion descripcion de la sala
     * @return SalaClimatizada creada
     */
    public SalaClimatizada crearSalaClimatizada(Horario h, String nombre, int aforo, String descripcion) {
        try {
            return new SalaClimatizada(h, nombre, aforo, this, descripcion);
        } catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
            return null;
        }
    }

    /**
     * Devuelve si es hoja
     * 
     * @return true si es hoja, false si no
     */
    public boolean esHoja() {
        if (this.hijos.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Devuelve las actividades que se realiza en la sala en la fecha indicada
     * 
     * @param fecha fecha de la actividad
     * @return actividad que se realiza en la sala en la fecha indicada o null si no
     *         hay ninguna
     */
    public List<Actividad> getActividadesFecha(LocalDate fecha) {
        List<Actividad> ret = new ArrayList<Actividad>();

        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha)) {
                ret.add(a);
            }
        }

        return ret;
    }

    /**
     * Devuelve la primera actividad que se realiza en la sala en la fecha indicada
     * 
     * @param fecha fecha de la actividad
     * @return actividad que se realiza en la sala en la fecha indicada o null si no
     */
    public Actividad getActividadFecha(LocalDate fecha) {
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha)) {
                return a;
            }
        }

        return null;
    }

    /**
     * Devuelve las actividades que se realiza en la sala en la fecha y horario
     * indicados
     * 
     * @param fecha   fecha de la actividad
     * @param horario horario de la actividad
     * @return actividad que se realiza en la sala en la fecha y horario indicados o
     *         null si no hay ninguna
     * @throws IllegalArgumentException si la fecha o el horario son nulos
     */
    public List<Actividad> getActividades(LocalDate fecha, Horario horario) throws IllegalArgumentException {
        if (fecha == null || horario == null) {
            throw new IllegalArgumentException("Fecha o horario nulos");
        }
        List<Actividad> ret = new ArrayList<Actividad>();
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha) && horario.horarioIncluido(a.getHorario())) {
                ret.add(a);
            }
        }

        return ret;
    }

    /**
     * Devuelve la primera actividad que se realiza en la sala en la fecha y horario
     * indicados
     * 
     * @param fecha   fecha de la actividad
     * @param horario horario de la actividad
     * @return actividad que se realiza en la sala en la fecha y horario indicados o
     *         null si no hay ninguna
     * @throws IllegalArgumentException si la fecha o el horario son nulos
     */
    public Actividad getActividad(LocalDate fecha, Horario horario) throws IllegalArgumentException {
        if (fecha == null || horario == null) {
            throw new IllegalArgumentException("Fecha o horario nulos");
        }
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha) && horario.horarioIncluido(a.getHorario())) {
                return a;
            }
        }

        return null;
    }

    /**
     * Añade una actividad a la sala.
     * Comprobación con horarioSolapan.
     * 
     * @param a actividad a añadir
     * @return true si la actividad se añade correctamente, false si ya hay una
     *         actividad en la sala en la misma fecha y horario
     * @throws IllegalArgumentException  si la actividad es nula
     * @throws SalaNoCompatibleException si la sala no es hoja o la sala de la
     *                                   actividad no es esta
     */
    public boolean addActividad(Actividad a) throws IllegalArgumentException, SalaNoCompatibleException {
        if (a == null) {
            throw new IllegalArgumentException("Actividad nula");
        } else if (!this.esHoja()) {
            throw new SalaNoCompatibleException(this, "La sala no es hoja");
        } else if (!a.getSala().equals(this)) {
            throw new SalaNoCompatibleException(this, "La sala de la actividad no es esta");
        }

        for (Actividad i : this.actividades) {
            if (i.getFecha().equals(a.getFecha()) && i.getHorario().horarioSolapan((a.getHorario()))) {
                return false;
            }
        }

        return this.actividades.add(a);
    }

    /**
     * Elimina una actividad de la sala
     * 
     * @param a actividad a eliminar
     * @return true si la actividad se elimina correctamente, false si no se
     *         encuentra la actividad
     */
    public boolean eliminarActividad(Actividad a) {
        return this.actividades.remove(a);
    }

    /**
     * Devuelve si está ocupada la sala en una hora. (Si hay una actividad en la
     * fecha y hora indicadas)
     * Con horaIncluida
     * 
     * @param fecha fecha a comprobar
     * @param hora  hora a comprobar
     * @return true si está ocupada, false si no
     */
    public boolean ocupada(LocalDate fecha, LocalTime hora) {
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha) && a.getHorario().horaIncluida(hora)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve si está ocupada la sala en la hora actual. (Si hay una actividad en
     * la
     * fecha y hora indicadas)
     * Con horaIncluida
     * 
     * @return true si está ocupada, false si no
     */
    public boolean ocupada() {
        return this.ocupada(LocalDate.now(), LocalTime.now());
    }

    /**
     * Devuelve si está ocupada la sala en el horario indicado. (Si hay una
     * actividad en la
     * fecha y hora indicadas)
     * Con horarioIncluido
     * 
     * @param fecha fecha a comprobar
     * @param h     horario a comprobar
     * @return true si está ocupada, false si no
     */
    public boolean ocupada(LocalDate fecha, Horario h) {
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha) && a.getHorario().horarioIncluido(h)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve si la sola esta disponible en el horario h. (Si se puede usar
     * para una actividad en la fecha y hora indicadas)
     * Con horarioSolapan.
     * 
     * @param fecha fecha a comprobar
     * @param h     horario a comprobar
     * @return true si está disponible, false si no
     */
    public boolean habilitada(LocalDate fecha, Horario h) {
        for (Actividad a : this.actividades) {
            if (a.getFecha().equals(fecha) && a.getHorario().horarioSolapan(h)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve si la sala esta disponible en la hora indicada. (Si se puede usar
     * para una actividad en la fecha y hora indicadas)
     * 
     * @param fecha fecha a comprobar
     * @param hora  hora a comprobar
     * @return true si está disponible, false si no
     */
    public boolean habilitada(LocalDate fecha, LocalTime hora) {
        return !this.ocupada(fecha, hora);
    }

    /**
     * Devuelve si la sala esta disponible en la hora actual. (Si se puede usar
     * para una actividad en la fecha y hora indicadas)
     * 
     * @return true si está disponible, false si no
     */
    public boolean habilitada() {
        return !this.ocupada();
    }

    /**
     * Devuelve la proxima actividad de la sala
     * 
     * @return proxima actividad de la sala
     */
    public Actividad nextActividad() {
        LocalTime comp = LocalTime.of(23, 59, 59); /* Elemento para comparar horarios de proximas clases */
        LocalDate compd = LocalDate.now().plusMonths(12);
        Actividad ret = null;

        for (Actividad a : this.actividades) {
            System.out.println(a);
            if (a.getFecha().compareTo(compd) < 0 && a.getHorario().getHoraInicio().compareTo(comp) < 0) {
                comp = a.getHorario().getHoraInicio();
                ret = a;
            }
        }

        return ret;
    }

    /**
     * Devuelve el aforo de los hijos de la sala
     * 
     * @return aforo total de las salas hijos
     */
    public int aforoHijos() {
        int aforo = 0;

        if (this.hijos.isEmpty()) {
            return aforo;
        }

        for (Sala s : this.hijos) {
            aforo += s.aforo;
        }

        return aforo;
    }

    /**
     * Devuelve el aforo disponible de la sala
     * 
     * @return aforo disponible de la sala
     */
    public int aforoDisponible() {
        int aforo = this.aforo - this.aforoHijos();

        return aforo;
    }

    /**
     * Comprueba si el aforo de los hijos es menor o igual que el aforo de la sala
     * 
     * @return true si el aforo de los hijos es menor o igual que el aforo de la
     *         sala, false si no
     *         False mal, true bien
     */
    public boolean comprobarAforoHijos() {
        if (this.aforo >= this.aforoHijos()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ordena las actividades de la sala por fecha y hora
     */
    public void ordenarActividades() {
        Collections.sort(this.actividades);
    }

    /**
     * Almacena la sala en el Fichero de backup
     * 
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public boolean exportarSala() {
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
     * Almacena todas las salas de un mapa en el fichero de backup
     * 
     * @param map mapa de salas a almacenar
     * @return true si se ha almacenado, false si ha habido algún error
     */
    public static boolean exportarAllSalas(Map<Integer, Sala> map) {
        try {
            FileOutputStream fos = new FileOutputStream(FICHERO_BACKUP);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Sala sala : map.values()) {
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
     * Lee una sala desde el fichero de backup
     * 
     * @return sala leida o null en caso de error
     */
    public static Sala importarSala() {
        Sala ret = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_BACKUP));
            ret = (Sala) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return ret;
    }

    /**
     * Lee todas las salas del fichero de backup
     * 
     * @return mapa de salas leidas
     */
    public static HashMap<Integer, Sala> importarAllSalas() {
        HashMap<Integer, Sala> map = new HashMap<>();
        Sala sala;
        try {
            FileInputStream fis = new FileInputStream(FICHERO_BACKUP);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                sala = (Sala) ois.readObject();
                map.put(sala.getId(), sala);
            }

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero");
        }
        return map;
    }

    /**
     * Comprueba si una saña es igual a otra
     * 
     * @param o objeto a comparar
     * @return true si son iguales, false si no
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sala)) {
            return false;
        }
        if (this.id == ((Sala) o).getId()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Devuelve el hashcode de la sala
     * 
     * @return hashcode de la sala
     */
    @Override
    public int hashCode() {
        return this.id * 11;
    }

    /**
     * Devuelve si la sala es climatizada o no
     * 
     * @return true si la sala es climatizada, false si no
     */
    public boolean esClimatizada() {
        return false;
    }
}
