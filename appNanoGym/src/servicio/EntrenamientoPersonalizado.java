/**
 * Clase que representa un entrenamiento personalizado de la aplicación.
 * @file EntrenamientoPersonalizado.java
 * @package servicio
 */

package servicio;

import java.time.LocalDate;
import java.util.*;

import exceptions.ClienteSuspendidoException;
import exceptions.ListaEsperaVaciaException;
import exceptions.ServicioAtMaximunCapacityException;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import servicio.actividad.actividadmonitor.EntrenamientoMonitor;
import usuario.Monitor;
import utiles.EstadoReserva;
import utiles.Horario;
import utiles.ListaEspera;
import utiles.Reserva;

/**
 * Clase que representa un entrenamiento personalizado de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class EntrenamientoPersonalizado extends Servicio {

    /** Monitor del Entrenamiento Personalizado */
    private final Monitor monitor;

    /** Se guardan en orden según el número de clase */
    private List<ActividadMonitor> actividadesM;

    /** Objetivo del entrenamiento personalizado */
    private String objetivo;

    /** Lista de Espera del Entrenamiento Personalizado */
    private ListaEspera listaEspera;

    /** Aforo máximo */
    private static int AFORO_MAX = 1;

    /** Precio del entrenamiento personalizado */
    private static double PRECIO = 20.0;

    /** Colección de Objetivos del entrenamiento personalizado */
    private static Set<String> objetivosEp = new HashSet<String>(Arrays.asList("Perder peso", "Ganar masa muscular",
            "Mejorar rendimiento deportivo", "Mejorar salud", "Mejorar estado físico", "Mejorar estado mental"));

    /**
     * Constructor de la clase EntrenamientoPersonalizado
     * 
     * @param nombre       Nombre del servicio de clase entrenamiento personalizado
     * @param descripcion  Descripcion del servicio de tipo entrenamiento
     *                     personalizado
     * @param monitor      Monitor del entrenamiento personalizado
     * @param actividadesM ActividadesMonitor del entrenamiento personalizado. Debe
     *                     haber al menos 1 actividad.
     * @param obj          Objetivo del entrenamiento personalizado
     * @throws IllegalArgumentException si los argumentos son nulos o incorrectos.
     */
    public EntrenamientoPersonalizado(String nombre, String descripcion, Monitor monitor,
            Collection<ActividadMonitor> actividadesM, String obj) {
        super(nombre, descripcion);
        if (monitor == null) {
            throw new IllegalArgumentException("El monitor no puede ser nulo");
        } else if (actividadesM == null || actividadesM.isEmpty()) {
            throw new IllegalArgumentException(
                    "Los actividadesMonitor no pueden ser nulos y deben haber al menos una actividad");
        } else if (obj == null) {
            throw new IllegalArgumentException(
                    "El objetivo no puede ser nulo y debe estar en la colección de objetivos posibles");
        }

        this.monitor = monitor;
        this.actividadesM = new LinkedList<ActividadMonitor>();
        if (this.actividadesM.addAll(actividadesM) == false) {
            throw new IllegalArgumentException("No se han podido añadir las actividades");
        }
        actividadesM.removeAll(Collections.singleton(null)); // Eliminar actividades nulas
        Collections.sort(this.actividadesM); // Ordenar las actividades por fecha
        this.objetivo = obj;
        this.listaEspera = new ListaEspera(this);

        if (monitor.addEntrenamientoPersonalizado(this) == false) {
            throw new IllegalArgumentException("No se ha podido añadir el entrenamiento personalizado al monitor");
        }

        for (ActividadMonitor am : this.actividadesM) {
            if (am instanceof EntrenamientoMonitor) {
                ((EntrenamientoMonitor) am).setEntPers(this);
            }
        }
    }

    /**
     * Devuelve el monitor del entrenamiento personalizado
     * 
     * @return monitor del entrenamiento personalizado
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * Devuelve las actividades del entrenamiento personalizado
     * 
     * @return lista de actividades del entrenamiento personalizado
     */
    public List<ActividadMonitor> getActividades() {
        return actividadesM;
    }

    /**
     * Devuelve la actividad classNumber del entrenamiento personalizado
     * 
     * @param classNumber número de clase. La primera es la 1.
     * @return las actividades por número de clase
     */
    public ActividadMonitor getActividadM(int classNumber) {
        if (classNumber < 1 || classNumber > actividadesM.size())
            return null;

        return actividadesM.get(classNumber - 1);
    }

    /**
     * Establece las actividades del entrenamiento personalizado
     * 
     * @param actividades lista de actividades del entrenamiento personalizado
     * @throws IllegalArgumentException si las actividades son nulas
     */
    public void setActividades(List<ActividadMonitor> actividades) throws IllegalArgumentException {
        if (actividades == null) {
            throw new IllegalArgumentException("Las actividades no pueden ser nulas");
        }
        this.actividadesM = actividades;
        Collections.sort(this.actividadesM); // Ordenar las actividades por fecha
        // this.actividadesM.sort(Comparator.comparing(ActividadMonitor::getFecha));
    }

    /**
     * Añade una actividad al entrenamiento personalizado
     * 
     * @param act actividad a añadir
     * @return true si se añade correctamente, false en caso contrario
     */
    public boolean addActividad(ActividadMonitor act) {
        boolean ret = false;
        if (act == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        ret = actividadesM.add(act);
        if (ret) {
            Collections.sort(this.actividadesM); // Ordenar las actividades por fecha
        }
        // this.actividadesM.sort(Comparator.comparing(ActividadMonitor::getFecha));
        return ret;
    }

    /**
     * Elimina una actividad al entrenamiento personalizado
     * 
     * @param a actividad que debe ser eliminada
     * @return true si se ha eliminado correctamente o false en caso contrario
     * @throws IllegalArgumentException si la actividad es nula
     */
    public boolean removeActividad(ActividadMonitor a) throws IllegalArgumentException {
        if (a == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        return actividadesM.remove(a);
    }

    /**
     * Devuelve el objetivo del entrenamiento personalizado
     * 
     * @return objetivo del entrenamiento personalizado
     */
    public String getObjetivo() {
        return this.objetivo;
    }

    /**
     * Establece el objetivo del entrenamiento personalizado
     * 
     * @param obj objetivo del entrenamiento personalizado
     * @throws IllegalArgumentException si el objetivo es nulo
     */
    public void setObjetivo(String obj) throws IllegalArgumentException {
        if (obj == null || !objetivosEp.contains(obj)) {
            throw new IllegalArgumentException(
                    "El objetivo no puede ser nulo y debe estar en la colección de objetivos posibles");
        }
        this.objetivo = obj;
    }

    /**
     * Añade una reserva a la lista de espera del entrenamiento personalizado
     * 
     * @param reserva reserva a añadir
     * @return true si se añade correctamente, false en caso contrario
     */
    @Override
    public boolean addReserva(Reserva reserva) throws IllegalArgumentException {
        try {
            for (ActividadMonitor am : actividadesM) {
                try {
                    reserva.getCliente().reservarServicio(am, false);
                } catch (ServicioAtMaximunCapacityException | ClienteSuspendidoException e) {
                    continue; // Si no se puede reservar, se pasa a la siguiente
                }
            }
            boolean ret = super.addReserva(reserva);

            return ret;
        } catch (ServicioAtMaximunCapacityException e) {
            reserva.setEstado(EstadoReserva.LISTA_ESPERA);
            this.addReservaListaEspera(reserva);
            return true;
        }
    }

    /**
     * Elimina una reserva del entrenamiento personalizado
     * 
     * @param r reserva a eliminar
     * @return true si se elimina correctamente, false en caso contrario
     */
    @Override
    public boolean removeReserva(Reserva r) {
        if (r.getEstado() == EstadoReserva.LISTA_ESPERA) {
            return this.removeReservaListaEspera(r);
        } else {
            boolean ret = super.removeReserva(r);
            if (ret) {
                for (ActividadMonitor am : actividadesM) {
                    r.getCliente().cancelarServicio(am);
                }
            }
            try {
                moverReservaListaEspera();
            } catch (ListaEsperaVaciaException | ServicioAtMaximunCapacityException e) {
            }

            return ret;
        }
    }

    /**
     * Devuelve la lista de espera del entrenamiento personalizado
     * 
     * @return lista de espera del entrenamiento personalizado
     */
    public ListaEspera getListaEspera() {
        return listaEspera;
    }

    /**
     * Establece la lista de espera del entrenamiento personalizado
     * 
     * @param listaEspera lista de espera del entrenamiento personalizado
     * @throws IllegalArgumentException si la lista de espera es nula
     */
    public void setListaEspera(ListaEspera listaEspera) throws IllegalArgumentException {
        if (listaEspera == null) {
            throw new IllegalArgumentException("La lista de espera no puede ser nula");
        }
        this.listaEspera = listaEspera;
    }

    /**
     * Añade una reserva a la lista de espera del entrenamiento personalizado
     * 
     * @param r reserva a añadir. Debe tener el estado en lista de espera
     * @return true si se añade correctamente, false en caso contrario
     * @throws IllegalArgumentException si la reserva es nula
     */
    public boolean addReservaListaEspera(Reserva r) throws IllegalArgumentException {
        return listaEspera.addReserva(r);
    }

    /**
     * Elimina una reserva de la lista de espera del entrenamiento personalizado
     * 
     * @param r reserva a eliminar
     * @return true si se elimina correctamente, false en caso contrario
     * @throws IllegalArgumentException si la reserva es nula
     */
    public boolean removeReservaListaEspera(Reserva r) throws IllegalArgumentException {
        return listaEspera.removeReserva(r);
    }

    /**
     * Establece el precio del entrenamiento personalizado
     * 
     * @param precio precio del entrenamiento personalizado
     */
    public static void setPRECIO(double precio) {
        EntrenamientoPersonalizado.PRECIO = precio;
    }

    /**
     * Devuelve el precio del entrenamiento personalizado
     * 
     * @return precio del entrenamiento personalizado
     */
    public static double getPRECIO() {
        return EntrenamientoPersonalizado.PRECIO;
    }

    public double getPrecio() {
        return EntrenamientoPersonalizado.getPRECIO();
    }

    /**
     * Establece el aforo máximo del entrenamiento personalizado
     * 
     * @param aforoMax aforo maximo del entrenamiento personalizado
     */
    public static void setAFORO_MAX(int aforoMax) {
        EntrenamientoPersonalizado.AFORO_MAX = aforoMax;
    }

    /**
     * Devuelve el aforo máximo del entrenamiento personalizado
     * 
     * @return aforo máximo del entrenamiento personalizado
     */
    public static double getAFORO_MAX() {
        return EntrenamientoPersonalizado.AFORO_MAX;
    }

    /**
     * Devuelve el set de los objetivos del entrenamiento personalizado
     * 
     * @return los objetivos posibles del entrenamiento personalizado
     */
    public static Set<String> getObjetivosEp() {
        return objetivosEp;
    }

    /**
     * Establece la lista de objetivos del entrenamiento personalizado
     * 
     * @param objetivos los objetivos del entrenamiento personalizado
     * @throws IllegalArgumentException si los objetivos son nulos
     */
    public static void setObjetivos(Set<String> objetivos) throws IllegalArgumentException {
        if (objetivos == null) {
            throw new IllegalArgumentException("Los objetivos no pueden ser nulos");
        }
        EntrenamientoPersonalizado.objetivosEp = objetivos;
    }

    /**
     * Añade un objetivo a la colección de los Entrenamiento Personalizado
     * 
     * @param objetivo objetivo a añadir
     * @return true si se añade correctamente, false en caso contrario
     * @throws IllegalArgumentException si el objetivo es nulo
     */
    public static boolean addObjetivo(String objetivo) throws IllegalArgumentException {
        if (objetivo == null) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo");
        }
        return objetivosEp.add(objetivo);
    }

    /**
     * Elimina un objetivo de la colección de los Entrenamiento Personalizado
     * 
     * @param obj objetivo a eliminar
     * @return true si se elimina correctamente, false en caso contrario
     * @throws IllegalArgumentException si el objetivo es nulo o vacío
     */
    public static boolean removeObjetivo(String obj) throws IllegalArgumentException {
        if (obj == null || obj.isEmpty()) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo ni vacío");
        }
        return objetivosEp.remove(objetivosEp.iterator().next());
    }

    /**
     * Método abstracto de Servicio que devuelve la fecha inicial del
     * entrenamiento personalizado
     * 
     * @return fecha inicial del entrenamiento personalizado
     */
    @Override
    public LocalDate getFecha() {
        return this.getActividadM(1).getFecha();
    }

    /**
     * Ent Pers. no tiene horario
     * 
     * @return nul
     */
    @Override
    public Horario getHorario() {
        return Horario.horarioNULL();
    }

    /**
     * Devuelve un string con la información del entrenaminto personalizado
     * 
     * @return string con la información del entrenaminto personalizado
     */
    public String toString() {
        return "Entrenamiento Personalizado: " + super.toString() + " -Monitor: " + monitor.getName()
                + " -ActividadesM: " + actividadesM + " -Reservas: " + this.getReservas() + " -Aforo: "
                + EntrenamientoPersonalizado.AFORO_MAX + " -Objetivo: " + objetivo + " -Precio: "
                + EntrenamientoPersonalizado.PRECIO;
    }

    /**
     * Devuelve un entero que indica el número de sesiones totales del entrenamiento
     * personalizado
     * 
     * @return entero que indica el número de sesiones totales
     */
    public int numSesionesTotal() {
        return this.actividadesM.size();
    }

    /**
     * Devuelve un entero que indica el aforo disponible del entrenamiento
     * personalizado.
     * Puede devolver 0 si no hay aforo disponible en ninguna actividad.
     * 
     * @return entero que indica el aforo disponible
     */
    public int aforoDisponible() {
        int disp = 0;
        for (ActividadMonitor am : actividadesM) {
            disp += am.aforoDisponible();
        }
        if (disp == 0) { // Ninguna actividad tiene aforo disponible
            return 0;
        }

        return EntrenamientoPersonalizado.AFORO_MAX - this.getReservas().size();
    }

    /**
     * Devuelve la fecha de inicio del entrenamiento personalizado
     * 
     * @return fecha de inicio del entrenamiento personalizado
     */
    public LocalDate getDateInicial() {
        return this.getFecha();
    }

    /**
     * Devuelve la fecha de fin del entrenamiento personalizado
     * 
     * @return fecha de fin del entrenamiento personalizado
     */
    public LocalDate getDateFinal() {
        return this.actividadesM.get(this.actividadesM.size() - 1).getFecha();
    }

    /**
     * Mueve la primera reserva que estuviera en lista de espera a la reserva normal
     * 
     * @return la reserva cambiada
     * @throws ListaEsperaVaciaException          si la lista de espera está vacía
     * @throws ServicioAtMaximunCapacityException si el servicio está lleno
     */
    public Reserva moverReservaListaEspera() throws ListaEsperaVaciaException, ServicioAtMaximunCapacityException {
        if (this.listaEspera.isEmpty()) {
            throw new ListaEsperaVaciaException(this);
        } else if (this.aforoDisponible() == 0) {
            throw new ServicioAtMaximunCapacityException(this);
        }

        Reserva r = this.listaEspera.pollReserva();
        if (r.saleDeListaDeEspera()) {
            this.addReserva(r);
        } else {
            System.err.println("Error al mover la reserva de lista de espera");
            moverReservaListaEspera(); // Volver a intentar
        }

        return r;
    }

    /**
     * Devuelve si la actividad grupal acepta nuevas reservas.
     * Cómo tiene lista de espera, siempre acepta.
     * 
     * @return true
     */
    @Override
    public boolean aceptaNuevaReserva() {
        return true;
    }

    /**
     * Procedimiento si el entrenamiento personalizado es cancelado por el monitor
     * 
     * @param m monitor que cancela el entrenamiento personalizado
     * @return true si se cancela correctamente, false en caso contrario
     */
    public boolean cancelarPorMonitor(Monitor m) {
        if (this.monitor.equals(m)) {
            return this.cancelar();
        }
        return false;
    }

    @Override
    public boolean cancelar() {
        if (super.cancelar()) {
            // Cancelar reservas lista espera
            for (Reserva r : listaEspera.getListaEspera()) {
                r.reservaCanceladaPorServicio(this);
            }

            // Cancelar actividades ep que tengan el mismo monitor
            for (ActividadMonitor am : this.actividadesM) {
                am.cancelarPorMonitor(monitor); // Solo se cancela si es el mismo monitor
            }
            return true;
        }
        return false;
    }

    /**
     * Devuelve si debe imprimir infoGrupal
     * 
     * @return false
     */
    @Override
    public boolean imprimirInfoGrupal() {
        return false;
    }

    /**
     * Devuelve el string de busqueda del entrenamiento personalizado
     * 
     * @return string de busqueda del entrenamiento personalizado
     */
    @Override
    public String getBusquedaString() {
        return "Entrenamiento personalizado " + getNombre() + " " + objetivo;
    }

    /**
     * Indica que si que tiene lista de espera
     * 
     * @return true
     */
    @Override
    public boolean tieneListaEspera() {
        return true;
    }

    /**
     * Compara el entrenamiento personalizado con otro servicio
     * 
     * @param serv servicio con el que comparar
     * @return 0 si son iguales, 1 si el entrenamiento personalizado es mayor, -1 si
     *         el entrenamiento personalizado es menor
     */
    @Override
    public int compareTo(Servicio serv) {
        int ret = super.compareTo(serv);
        if (ret == 0 && !(serv instanceof EntrenamientoPersonalizado)) {
            ret = 1; // Ep > Actividad
        } else if (ret == 0) {
            EntrenamientoPersonalizado ep = (EntrenamientoPersonalizado) serv;
            ret = this.monitor.compareTo(ep.monitor);
        }

        return ret;
    }

    /**
     * Devuelve si el entrenamiento personalizado es igual a otro objeto
     * 
     * @param o objeto con el que comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        boolean ret = super.equals(o);
        if (ret && o instanceof EntrenamientoPersonalizado) {
            EntrenamientoPersonalizado ep = (EntrenamientoPersonalizado) o;
            ret = this.monitor.equals(ep.monitor);
        }

        return ret;
    }

    /**
     * Devuelve el hashcode del entrenamiento personalizado
     * 
     * @return hashcode del entrenamiento personalizado
     */
    @Override
    public int hashCode() {
        return super.hashCode() * this.monitor.hashCode();
    }
}
