/**
 * Clase que representa a un monitor de la aplicación.
 * @file Monitor.java
 * @package Usuario
 */

package usuario;

import java.time.*;
import java.util.*;
import java.util.Map.Entry;

import nanoGym.*;
import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import servicio.Servicio;
import servicio.actividad.actividadmonitor.*;
import utiles.Horario;
import es.uam.eps.padsof.payrolls.*;
import es.uam.eps.padsof.payrolls.exceptions.*;
import es.uam.eps.padsof.payrolls.IEmployeeInfo;

/**
 * Clase que representa a un monitor de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Monitor extends Usuario implements IEmployeeInfo {

    /** Serial Version UID */
    private static final long serialVersionUID = -1585169386210255506L;

    /** Nombre completo del monitor */
    private String nombreCompleto;

    /** Nif del monitor */
    private final String nif;

    /** PDFPath para crear nominas */
    private static String PDFPath = "./resources/nominasMonitor/";

    /** Email del monitor */
    private String email;

    /** Horas trabajadas del Monitor al mes */
    private HashMap<Integer, Integer> horasTrabajadas;

    /** Actividades que imparte el monitor */
    private LinkedList<ActividadMonitor> actividades = new LinkedList<>();

    /** Entrenamientos personalizados que imparte el monitor */
    private LinkedList<EntrenamientoPersonalizado> entrenosPersonalizado = new LinkedList<>();

    /** Nomina base de los monitores */
    private static double NOMINA_BASE = 500.0;

    /** Extra de sueldo de los monitores por sesion impartida */
    private static double EXTRA_SUELDO_SESION = 7.0;

    /**
     * Constructor de la clase Monitor
     * 
     * @param username       Nombre de usuario del monitor
     * @param contraseña     Contraseña del monitor
     * @param nombreCompleto Nombre completo del monitor
     * @param email          Email del monitor
     * @param nif            Nif del monitor
     * 
     * @throws IllegalArgumentException Si alguno de los argumentos es nulo o el nif
     */
    public Monitor(String username, String contraseña, String nombreCompleto,
            String email, String nif) throws IllegalArgumentException {
        super(username, contraseña);
        if (nombreCompleto == null || email == null || nif == null) {
            throw new IllegalArgumentException("El nombre completo, el email y el nif no pueden ser nulos");
        } else if (nif.length() != 9) {
            throw new IllegalArgumentException("El nif debe tener 9 caracteres");
        }

        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.nif = nif;
        this.horasTrabajadas = new HashMap<Integer, Integer>();
    }

    /**
     * Establece el nombre completo del monitor
     * 
     * @param nombreCompleto Nombre completo del monitor
     * @throws IllegalArgumentException Si el nombre completo es nulo
     */
    public void setNombreCompleto(String nombreCompleto) throws IllegalArgumentException {
        if (nombreCompleto == null) {
            throw new IllegalArgumentException("El nombre completo no puede ser nulo");
        }
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Devuelve el nombre completo del monitor
     * 
     * @return Nombre completo del monitor
     */
    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    /**
     * Establece el email del monitor
     * 
     * @param email Email del monitor
     * @throws IllegalArgumentException Si el email es nulo
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }
        this.email = email;
    }

    /**
     * Devuelve el email del monitor
     * 
     * @return Email del monitor
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Establece las horas trabajadas del monitor
     * 
     * @param horasTrabajadas Horas trabajadas del monitor
     * @throws IllegalArgumentException Si las horas trabajadas son negativas
     */
    public void setHorasTrabajadas(HashMap<Integer, Integer> horasTrabajadas) throws IllegalArgumentException {
        if (horasTrabajadas.isEmpty()) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden estar vacias");
        }
        this.horasTrabajadas = horasTrabajadas;
    }

    /**
     * Añade horas trabajadas al monitor
     * 
     * @param mo    Mes en el que se trabajan las horas
     * @param y     Año en el que se trabajan las horas
     * @param hours Horas trabajadas
     * @throws IllegalArgumentException Si las horas trabajadas son negativas
     */
    public void addHorasTrabajadas(Month mo, Year y, int hours) throws IllegalArgumentException {
        if (hours < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas");
        }
        int key = y.getValue() * 100 + mo.getValue();
        if (this.horasTrabajadas.containsKey(key)) {
            this.horasTrabajadas.put(key, this.horasTrabajadas.get(mo.getValue()) + hours);
        } else {
            this.horasTrabajadas.put(key, hours);
        }
    }

    /**
     * Devuelve las horas trabajadas del monitor
     * 
     * @param mo Mes en el que se trabajan las horas
     * @param y  Año en el que se trabajan las horas
     * @return Horas trabajadas del monitor
     */
    public int getHorasTrabajadas(Month mo, Year y) {
        int key = y.getValue() * 100 + mo.getValue();

        if (this.horasTrabajadas.containsKey(key)) {
            return this.horasTrabajadas.get(key);
        } else {
            return 0;
        }
    }

    /**
     * Establece las actividades que imparte el monitor
     * 
     * @param actividades Actividades que imparte el monitor
     * @throws IllegalArgumentException Si las actividades son nulas
     */
    public void setActividades(LinkedList<ActividadMonitor> actividades) throws IllegalArgumentException {
        if (actividades == null) {
            throw new IllegalArgumentException("Las actividades no pueden ser nulas");
        }
        this.actividades = actividades;
    }

    /**
     * Devuelve las actividades que imparte el monitor
     * 
     * @return Actividades que imparte el monitor
     */
    public LinkedList<ActividadMonitor> getActividades() {
        return this.actividades;
    }

    /**
     * Añade una actividad al monitor. Es obligatorio comprobar si devuelve false,
     * ya que puede no añadirse si el monitor está ocupado en ese momento
     * 
     * @param actividad Actividad que se añade al monitor
     * @return true si se ha añadido la actividad, false en caso contrario
     * @throws IllegalArgumentException Si la actividad es nula
     */
    public boolean addActividad(ActividadMonitor actividad) throws IllegalArgumentException {
        if (actividad == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        } else if (!disponible(actividad.getFecha(), actividad.getHorario())) {
            return false;
        } else if (this.actividades.contains(actividad)) {
            return false;
        }

        this.actividades.add(actividad);
        Collections.sort(this.actividades);
        this.getGN().addNotificacion("Se te ha asignado la actividad "
                + actividad.getNombre() + " el " + actividad.getFecha().toString()
                + " con horario: " + actividad.getHorario());

        return true;
    }

    /**
     * Elimina una actividad del monitor
     * 
     * @param actividad Actividad que se elimina del monitor
     * @return true si se ha eliminado la actividad, false en caso contrario
     * @throws IllegalArgumentException Si la actividad es nula
     */
    public boolean removeActividad(ActividadMonitor actividad) throws IllegalArgumentException {
        if (actividad == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }

        boolean ret = this.actividades.remove(actividad);
        if (ret) {
            this.getGN().addNotificacion("Se te ha eliminado la actividad "
                    + actividad.getNombre() + " el " + actividad.getFecha().toString()
                    + " con horario: " + actividad.getHorario());
        }
        return ret;
    }

    /**
     * Establece los entrenamientos personalizados que imparte el monitor
     * 
     * @param ep Entrenamientos personalizados que imparte el monitor
     * @throws IllegalArgumentException Si los entrenamientos personalizados son
     *                                  nulos
     */
    public void setEntrenosPersonalizado(LinkedList<EntrenamientoPersonalizado> ep) throws IllegalArgumentException {
        if (ep == null) {
            throw new IllegalArgumentException("Los entrenamientos personalizados no pueden ser nulos");
        }
        this.entrenosPersonalizado = ep;
    }

    /**
     * Devuelve los entrenamientos personalizados que imparte el monitor
     * 
     * @return Entrenamientos personalizados que imparte el monitor
     */
    public LinkedList<EntrenamientoPersonalizado> getEntrenosPersonalizado() {
        return this.entrenosPersonalizado;
    }

    /**
     * Añade un entrenamiento personalizado al monitor. Es obligatorio comprobar
     * si devuelve false, ya que puede no añadirse si el monitor está ocupado en
     * ese momento
     * 
     * @param ep Entrenamiento personalizado que se añade al monitor
     * @return true si se ha añadido el entrenamiento personalizado, false en caso
     *         contrario
     * @throws IllegalArgumentException Si el entrenamiento personalizado es nulo
     */
    public boolean addEntrenamientoPersonalizado(EntrenamientoPersonalizado ep) throws IllegalArgumentException {
        if (ep == null || ep.getMonitor() != this) {
            throw new IllegalArgumentException("El entrenamiento personalizado no puede ser nulo");
        } else if (this.entrenosPersonalizado.contains(ep)) {
            return false;
        }

        for (ActividadMonitor am : ep.getActividades()) {
            //System.out.println("Monitor am = " + am.getMonitor());
            if (!am.getMonitor().equals(this) && !disponible(am.getFecha(), am.getHorario())) {
                //System.out.println("falseee :()");
                return false;
            }
        }

        this.entrenosPersonalizado.add(ep);
        Collections.sort(this.entrenosPersonalizado);

        // Añade todas las actividades del ent. personalizado al monitor
        this.actividades.addAll(ep.getActividades());
        Collections.sort(this.actividades);
        //System.out.println("trueee :()");

        this.getGN().addNotificacion("Se te ha asignado el entrenamiento personalizado "
                + ep.getNombre() + " el " + ep.getFecha().toString());
        //System.out.println("trueee 2");
        return true;
    }

    /**
     * Elimina un entrenamiento personalizado del monitor
     * 
     * @param ep Entrenamiento personalizado que se elimina del monitor
     * @return true si se ha eliminado el entrenamiento personalizado, false en caso
     *         contrario
     * @throws IllegalArgumentException Si el entrenamiento personalizado es nulo
     */
    public boolean removeEntrenamientoPersonalizado(EntrenamientoPersonalizado ep) throws IllegalArgumentException {
        if (ep == null) {
            throw new IllegalArgumentException("El entrenamiento personalizado no puede ser nulo");
        }
        boolean ret = this.entrenosPersonalizado.remove(ep);
        if (ret) {
            for (ActividadMonitor am : ep.getActividades()) {
                this.actividades.remove(am);
            }

            this.getGN().addNotificacion("Se te ha eliminado el entrenamiento personalizado "
                    + ep.getNombre() + " el " + ep.getFecha().toString());
        }

        return ret;
    }

    /**
     * Establece el sueldo base de los monitores
     * 
     * @param nominaBase Sueldo base de los monitores
     * @throws IllegalArgumentException Si la nomina base es negativa
     */
    public static void setNOMINA_BASE(double nominaBase) throws IllegalArgumentException {
        if (nominaBase < 0) {
            throw new IllegalArgumentException("La nomina base no puede ser negativa");
        }
        Monitor.NOMINA_BASE = nominaBase;
    }

    /**
     * Devuelve la nomina base del monitor
     * 
     * @return Nomina base del monitor
     */
    public static double getNOMINA_BASE() {
        return Monitor.NOMINA_BASE;
    }

    /**
     * Establece el extra de sueldo de los monitores por sesion impartida
     * 
     * @param extraSesion Extra de sueldo de los monitores por sesion impartida
     */
    public static void setEXTRA_SUELDO_SESION(double extraSesion) {
        if (extraSesion > 0) {
            Monitor.EXTRA_SUELDO_SESION = extraSesion;
        }
    }

    /**
     * Devuelve el extra de sueldo de los monitores por sesion impartida
     * 
     * @return Extra de sueldo de los monitores por sesion impartida
     */
    public static double getEXTRA_SUELDO_SESION() {
        return Monitor.EXTRA_SUELDO_SESION;
    }

    /**
     * Devuelve un string con la información del monitor
     * 
     * @return string con la información del monitor
     */
    @Override
    public String toString() {
        return "Monitor: "
                + "\n" + super.toString()
                + "\n- Nombre Completo: " + this.nombreCompleto
                + "\n- Email: " + this.email
                + "\n- nº actividades: " + this.actividades.size()
                + "\n- nº entrenamientos personalizados: " + this.entrenosPersonalizado.size()
                + "\n- nº nominas: " + this.horasTrabajadas.size() + "\n";
    }

    /**
     * Devuelve el nombre
     * 
     * @return String con el nombre
     */
    @Override
    public String getName() {
        return this.nombreCompleto;
    }

    /**
     * Devuelve el nif
     * 
     * @return String con el nif
     */
    @Override
    public String getNif() {
        return this.nif;
    }

    /**
     * Genera un pdf con la nomina del monitor
     * 
     * @param m  monitor del que se genera la nomina
     * @param mo mes del que se genera la nomina
     * @param y  año del que se genera la nomina
     * @return true si se ha generado el pdf, false en caso contrario
     */
    public boolean generarPDF(Monitor m, Month mo, Year y) {
        try {
            NanoGym ng = new NanoGym();
            PayrollSystem.createPayroll(ng, m, mo, y, m.getHorasTrabajadas(mo, y),
                    Monitor.PDFPath);
            return true;
        } catch (NonExistentFileException e) {
            System.err.println("Error de archivo inexistente");
        } catch (UnsupportedImageTypeException i) {
            System.err.println("Error con la imagen");
        } catch (InvalidPeriod p) {
            System.err.println("Periodo de tiempo " + LocalDate.now().getMonth() + " - " + LocalDate.now().getYear()
                    + " invalido");
        }

        System.err.println("Error generando el pdf\n");
        return false;
    }

    /**
     * Devuelve si el monitor está disponible a una fecha y hora
     * 
     * @param f fecha a la que se comprueba si el monitor está disponible
     * @param h hora a la que se comprueba si el monitor está disponible
     * @return true si el monitor está disponible, false en caso contrario
     */
    public boolean disponible(LocalDate f, LocalTime h) {
        for (ActividadMonitor a : this.actividades) {
            if (a.getFecha().equals(f) && a.getHorario().horaIncluida(h)) {
                return false;
            }
        }
        // Como las actividades de los ep se añaden a actividades, no hace falta
        // comprobar si el monitor tiene un ep en esa fecha y hora

        return true;
    }

    /**
     * Devuelve si el monitor está disponible a una fecha y horario
     * 
     * @param f fecha a la que se comprueba si el monitor está disponible
     * @param h horario a la que se comprueba si el monitor está disponible
     * @return true si el monitor está disponible, false en caso contrario
     */
    public boolean disponible(LocalDate f, Horario h) {
        for (ActividadMonitor a : this.actividades) {
            if (a.getFecha().equals(f) && a.getHorario().horarioSolapan(h)) {
                return false;
            }
        }
        // Como las actividades de los ep se añaden a actividades, no hace falta
        // comprobar si el monitor tiene un ep en esa fecha y hora

        return true;
    }

    /**
     * Muestra el menú principal del Monitor
     */
    @Override
    public void mostrarMenuPrincipal() {
        System.out.println("MENU DE MONITOR");
        System.out.println("--------------------------------");
        System.out.println("1. LogOut");
        System.out.println("2. Consultar proximas actividades");
        System.out.println("3. Consultar proximas clases");
        System.out.println("4. Consultar perfil");
        System.out.println("5. Consultar notificaciones");
        System.out.println("6. Generar PDF"); /* falta */
        System.out.println("7. Crear entrenamiento personalizado"); /* falta */
        System.out.println("--------------------------------");
    }

    /**
     * Interpreta la acción del monitor en su mení principal
     * 
     * @param ret Opcion seleccionada por el usuario
     * @param ng  NanoGym
     */
    @Override
    public void switchMenuPrincipal(int ret, NanoGym ng) {
        if (ret < 1 || ret > 7) {
            System.out.println("Numero invalido");
            return;
        } else if (ret == 1 || ret == 2) {
            return;
        }
        ret -= 2;
        switch (ret) {
            case 1: /* Reservas */
                this.consultarProximasActividades();
                break;
            case 2: /* Perfil */
                this.mostrarPerfil();
                break;
            case 3: /* Notificaciones */
                this.mostrarNotificaciones();
                break;
            case 4: /* Generar PDF */
                this.llamarAGenerarPDF();
                break;
            case 5: /* Crear entrenamiento personalizado */
                this.crearEntrenamientoPersonalizado(ng);
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }

        System.out.println("====================================");
        System.out.println("(presiona enter para volver al menu)");
        System.out.println("====================================");

        NanoGym.leer.nextLine();
    }

    /**
     * Crea un nuevo entrenamiento personalizado
     * 
     * @param ng NanoGym
     */
    private void crearEntrenamientoPersonalizado(NanoGym ng) {

        LinkedList<ActividadMonitor> am = new LinkedList<ActividadMonitor>();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Crear entrenamiento personalizado");
        System.out.println("====================================");
        System.out.println("Introduce el nombre del entrenamiento");
        String nombre = NanoGym.leer.nextLine();
        System.out.println("Introduce la descripcion del entrenamiento");
        String descripcion = NanoGym.leer.nextLine();
        System.out.println("Introduce el numero de sesiones del entrenamiento");
        int sesiones = NanoGym.leer.nextInt();
        NanoGym.leer.nextLine();
        System.out.println("Introduce el objetivo del entrenamiento");
        String objetivo = NanoGym.leer.nextLine();
        System.out.println("Quiere añadir alguna actividad grupal? (s/n)");
        String res = NanoGym.leer.nextLine();
        if (res.equals("s")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("ACTIVIDADES GRUPALES:");
            System.out.println("====================================");
            ng.mostrarActividadesGrupales();
            System.out.println(
                    "===============================================================================================================");
            System.out.println(
                    "Introduce el numero de la actividad que quieres añadir al entrenamiento, y cuando haya terminado presione enter");

            String v;
            int nres = 0;
            do {
                v = NanoGym.leer.nextLine();
                if (v.length() >= 1) {
                    nres = Integer.parseInt(v);
                    if (nres < 1 || nres > ng.getServicios().size()) {
                        System.out.println("Numero invalido");
                    } else {
                        if (ng.getServicios().get(nres - 1).imprimirInfoGrupal() == true) {
                            ActividadMonitor ag = (ActividadMonitor) ng.getServicios().get(nres - 1);
                            am.add(ag);
                        } else {
                            System.out.println("Numero invalido");
                        }
                    }
                }
            } while (v.length() >= 1);

            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("CREAR ENTRENAMIENTOS MONITOR:");
            System.out.println("====================================");
            for (int i = 0; i < sesiones; i++) {
                // String nombre, String descripcion, Horario horario, Sala sala, Monitor
                // monitor, LocalDate fecha
                System.out.println("Introduce el nombre de la sesion:");
                String nombreSesion = NanoGym.leer.nextLine();
                System.out.println("Introduce la descripcion de la sesion:");
                String descripcionSesion = NanoGym.leer.nextLine();
                System.out.println("Introduce la hora de inicio de la sesion: (HH MM)");
                int horaInicio = NanoGym.leer.nextInt();
                int minutoInicio = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                System.out.println("Introduce la hora de fin de la sesion: (HH MM)");
                int horaFin = NanoGym.leer.nextInt();
                int minutoFin = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                System.out.println("Introduce el dia de la sesion: (MM DD)");
                int mes = NanoGym.leer.nextInt();
                int dia = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                Iterator<Entry<Integer, Sala>> salasIterator = ng.getSalas().entrySet().iterator();
                while (salasIterator.hasNext()) {
                    Map.Entry<Integer, Sala> set = (Map.Entry<Integer, Sala>) salasIterator.next();
                    System.out.println(set.getValue());
                }
                System.out.println("Introduce el id de la sala de la sesion:");
                int numSala = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                Sala sala = ng.getSalas().get(numSala);

                Horario horario = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));
                LocalDate fecha = LocalDate.of(LocalDate.now().getYear(), mes, dia);

                EntrenamientoMonitor actividad = new EntrenamientoMonitor(nombreSesion, descripcionSesion, horario,
                        sala, this, fecha);
                am.add(actividad);
            }

            ng.crearEntrenamientoPersonalizado(nombre, descripcion, this, am, objetivo);

        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("CREAR ENTRENAMIENTOS MONITOR:");
            System.out.println("====================================");
            for (int i = 0; i < sesiones; i++) {
                // String nombre, String descripcion, Horario horario, Sala sala, Monitor
                // monitor, LocalDate fecha
                System.out.println("Introduce el nombre de la sesion:");
                String nombreSesion = NanoGym.leer.nextLine();
                System.out.println("Introduce la descripcion de la sesion:");
                String descripcionSesion = NanoGym.leer.nextLine();
                System.out.println("Introduce la hora de inicio de la sesion: (HH MM)");
                int horaInicio = NanoGym.leer.nextInt();
                int minutoInicio = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                System.out.println("Introduce la hora de fin de la sesion: (HH MM)");
                int horaFin = NanoGym.leer.nextInt();
                int minutoFin = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                System.out.println("Introduce el dia de la sesion: (MM DD)");
                int mes = NanoGym.leer.nextInt();
                int dia = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                Iterator<Entry<Integer, Sala>> salasIterator = ng.getSalas().entrySet().iterator();
                while (salasIterator.hasNext()) {
                    Map.Entry<Integer, Sala> set = (Map.Entry<Integer, Sala>) salasIterator.next();
                    System.out.println(set.getValue());
                }
                System.out.println("Introduce el id de la sala de la sesion:");
                int numSala = NanoGym.leer.nextInt();
                NanoGym.leer.nextLine();
                Sala sala = ng.getSalas().get(numSala);

                Horario horario = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));
                LocalDate fecha = LocalDate.of(LocalDate.now().getYear(), mes, dia);

                EntrenamientoMonitor actividad = new EntrenamientoMonitor(nombreSesion, descripcionSesion, horario,
                        sala, this, fecha);
                am.add(actividad);
            }

            ng.crearEntrenamientoPersonalizado(nombre, descripcion, this, am, objetivo);
        }

        System.out.print("Entrenamiento creado correctamente");

    }

    /**
     * Muestra el perfil del monitor
     */
    public void mostrarPerfil() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(this.toString());
    }

    /**
     * Muestra las proximas actividades del monitor
     */
    public void consultarProximasActividades() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Actividades del monitor " + this.nombreCompleto);
        System.out.println("====================================");
        if (this.actividades.isEmpty()) {
            System.out.println("No hay actividades");
            return;
        }
        int i = 0;
        for (ActividadMonitor a : this.actividades) {
            i++;
            System.out.println("Actividad " + i);
            System.out.println(a.toString());
        }
        int nres = 0;
        do {
            System.out
                    .println("======================================================================================");
            System.out
                    .println("(Introduce el número de una actividad si desea cancelarla o enter para volver al menu)");
            System.out
                    .println("======================================================================================");
            String reserv = NanoGym.leer.nextLine();
            if (reserv.length() != 0) {
                nres = Integer.parseInt(reserv);

                if (nres > 0 && nres <= i) {
                    try {
                        if (this.cancelarActividadMonitor(this.getActividades().get(i - 1)) == true) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Actividad cancelada correctamente.");
                            NanoGym.leer.nextLine();
                        } else {
                            System.out.println("Error: No se ha podido cancelar la actividad.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                break;
            }
        } while (nres <= 0 || nres > i);
    }

    /**
     * Muestra las notificaciones del monitor
     */
    public void mostrarNotificaciones() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("No tienes notificaciones");
    }

    /**
     * Se prepara para llamar al metodo de generarPDF
     */
    public void llamarAGenerarPDF() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Generar PDF");
        System.out.println("====================================");
        System.out.println("Introduce el mes y el año para generar el PDF");
        System.out.println("Mes: ");
        int mes = Integer.parseInt(NanoGym.leer.nextLine());
        System.out.println("Año: ");
        int anio = Integer.parseInt(NanoGym.leer.nextLine());
        NanoGym.leer.nextLine();
        Month m = Month.of(mes);
        Year y = Year.of(anio);
        this.generarPDF(this, m, y);
    }

    /**
     * Índica si puede reservar
     * 
     * @return false
     */
    @Override
    public boolean esCliente() {
        return false;
    }

    /**
     * Índica si puede dar clase
     * 
     * @return true
     */
    @Override
    public boolean esMonitor() {
        return true;
    }

    /**
     * Cancela una actividad
     * 
     * @param am Actividad a cancelar
     * @return true si se ha cancelado correctamente, false en caso contrario
     */
    public boolean cancelarActividadMonitor(ActividadMonitor am) {
        boolean ret = this.removeActividad(am);
        if (!ret) {
            return false;
        }
        return am.cancelarPorMonitor(this);
    }

    /**
     * Cancela un entrenamiento personalizado
     * 
     * @param e Entrenamiento a cancelar
     * @return true si se ha cancelado correctamente, false en caso contrario
     */
    public boolean cancelarEntrenamientoPersonalizado(EntrenamientoPersonalizado e) {
        boolean ret = this.removeEntrenamientoPersonalizado(e);
        if (!ret) {
            return false;
        }
        return e.cancelarPorMonitor(this);
    }

    /**
     * Comprueba si dos monitores son iguales
     * 
     * @param o Monitor a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monitor)) {
            return false;
        }
        Monitor monitor = (Monitor) o;
        return Objects.equals(nif, monitor.nif);
    }

    /**
     * Devuelve el hash de un monitor
     * 
     * @return hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }

    /**
     * Devuelve todos los servicios del monitor
     * 
     * @return ArrayList con los servicios del monitor
     */
    public ArrayList<Servicio> getServicios() {
        ArrayList<Servicio> servicios = new ArrayList<Servicio>();
        for (ActividadMonitor a : this.actividades) {
            servicios.add(a);
        }
        for (EntrenamientoPersonalizado e : this.entrenosPersonalizado) {
            servicios.add(e);
        }
        return servicios;
    }
}
