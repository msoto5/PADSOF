package nanoGym;

import java.util.*;
import java.util.Map.Entry;
import java.time.*;

import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import es.uam.eps.padsof.payrolls.ICompanyInfo;

import sala.*;
import servicio.*;
import servicio.actividad.EntrenamientoLibre;
import servicio.actividad.actividadmonitor.ActividadGrupal;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import servicio.actividad.actividadmonitor.EntrenamientoMonitor;
import servicio.actividad.actividadmonitor.TipoActividad;
import usuario.*;
import usuario.cliente.TarjetaBancaria;
import usuario.cliente.*;
import usuario.cliente.tarifa.*;
import utiles.Horario;

/**
 * Clase que representa el sistema NanoGym
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class NanoGym implements ICompanyInfo {

    /** Salas del gimnasio */
    private HashMap<Integer, Sala> salas = new HashMap<Integer, Sala>();

    /** Usuario del gimnasio */
    private HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();

    /** Servicios del gimnasio */
    private ArrayList<Servicio> servicios = new ArrayList<Servicio>();

    /** Tipos de Actividades disponibles */
    private Set<TipoActividad> tiposDeActividad = new HashSet<TipoActividad>();

    /** Usuario actualmente logeado */
    private Usuario currentUser;

    /** Nombre del Gimnasio */
    private String name = "NanoGym";

    /** Logo del Gimnasio */
    private String logo = "./src/nanoGym/logo.png";

    /** Cif del Gimnasio */
    private String cif = "ES-00877893X";

    /** Scanner */
    public final static Scanner leer = new Scanner(System.in);

    /**
     * Constructor de la clase NanoGym
     */
    public NanoGym() {
        /*
         * this.leerSalas();
         * this.leerServicios();
         * this.leerUsuarios();
         */
    }

    /**************************************************************************/
    /* Funciones salas */
    /**************************************************************************/

    /**
     * Lee las salas del fichero de backup de salas
     */
    public void leerSalas() {
        salas = Sala.importarAllSalas();
    }

    /**
     * Almacena las salas en el fichero de backup de salas
     */
    public void guardarSalas() {
        if (Sala.exportarAllSalas(salas) == false) {
            System.out.println("Error al guardar las salas");
        }
    }

    /**
     * Devuelve las salas del gimnasio
     * 
     * @return salas
     */
    public HashMap<Integer, Sala> getSalas() {
        return this.salas;
    }

    /**
     * Devuelve las salas del gimnasio en forma de lista
     * 
     * @return salas
     */
    public List<Sala> getSalasList() {
        List<Sala> salasList = new ArrayList<Sala>();
        for (Entry<Integer, Sala> entry : salas.entrySet()) {
            salasList.add(entry.getValue());
        }
        return salasList;
    }

    /**
     * Devuelve las salas del gimnasio que son hojas
     * 
     * @return salas hojas
     */
    public List<Sala> getSalasHojas() {
        List<Sala> salasList = new ArrayList<Sala>();
        for (Sala entry : salas.values()) {
            if (entry.esHoja() == true) {
                salasList.add(entry);
            }
        }
        return salasList;
    }

    /**
     * Añade una sala al gimnasio
     * 
     * @param s sala a añadir
     */
    public void addSala(Sala s) {
        if (!salas.containsKey(s.getId())) {
            salas.put(s.getId(), s);
            this.generateBackUp();
        }
    }

    /**
     * Elimina una sala del gimnasio
     * 
     * @param s sala a eliminar
     */
    public void removeSala(Sala s) {
        this.salas.remove(s.getId());
        this.generateBackUp();
    }

    /**************************************************************************/
    /* Funciones usuarios */
    /**************************************************************************/
    /**
     * Lee los usuarios del fichero de backup de usuarios
     */
    public void leerUsuarios() {
        usuarios = Usuario.importarAllUsuarios();
    }

    /**
     * Almacena los usuarios en el fichero de backup de usuarios
     */
    public void guardarUsuarios() {
        if (Usuario.exportarAllSalas(usuarios) == false) {
            System.out.println("Error al guardar las salas");
        }
    }

    /**
     * Devuelve todos los usuarios del gimansio
     * 
     * @return usuarios
     */
    public HashMap<String, Usuario> getUsuarios() {
        return this.usuarios;
    }

    /**
     * Devuelve los clientes del gimnasio
     * 
     * @return clientes
     */
    public List<Cliente> getClientes() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (Usuario usr : usuarios.values()) {
            if (usr.esCliente()) {
                clientes.add((Cliente) usr);
            }
        }

        return clientes;
    }

    /**
     * Devuelve los monitores del gimnasio
     * 
     * @return monitores
     */
    public ArrayList<Monitor> getMonitores() {
        ArrayList<Monitor> monitores = new ArrayList<Monitor>();
        for (Usuario usr : usuarios.values()) {
            if (usr.esMonitor()) {
                monitores.add((Monitor) usr);
            }
        }

        return monitores;
    }

    /**
     * Devuelve un monitor dado su nif
     * 
     * @param nif nif del monitor
     * @return monitor
     */
    public Monitor getMonitor(String nif) {
        for (Usuario usr : usuarios.values()) {
            if (usr.esMonitor()) {
                if (((Monitor) usr).getNif().equals(nif)) {
                    return (Monitor) usr;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve los administradores del gimnasio
     * 
     * @return administradores
     */
    public List<Administrador> getAdministradores() {
        List<Administrador> administradores = new ArrayList<Administrador>();
        for (Usuario usr : usuarios.values()) {
            if (!usr.esCliente() && !usr.esMonitor()) {
                administradores.add((Administrador) usr);
            }
        }

        return administradores;
    }

    /**
     * Añade un usuario al gimnasio
     * 
     * @param u usuario a añadir
     */
    public void addUsuario(Usuario u) {
        if (!usuarios.containsKey(u.getUsername())) {
            usuarios.put(u.getUsername(), u);
            this.generateBackUp();
        }
    }

    /**
     * Eliminar un usuario del gimnasio
     * 
     * @param u usuario a eliminar
     */
    public void removeUsuario(Usuario u) {
        this.usuarios.remove(u.getUsername());
        this.generateBackUp();
    }

    /**
     * Devuelve el usuario actual
     * 
     * @return usuario actual
     */
    public Usuario getCurrentUser() {
        return this.currentUser;
    }

    /**************************************************************************/
    /* Funciones servicios */
    /**************************************************************************/
    /**
     * Lee los servicios del fichero de backup de servicios
     */
    public void leerServicios() {
        servicios = Servicio.importarAllServicios();
    }

    /**
     * Almacena los servicios en el fichero de backup de servicios
     */
    public void guardarServicios() {
        if (Servicio.exportarAllServicios(servicios) == false) {
            System.out.println("Error al guardar las salas");
        }
    }

    /**
     * Devuelve los servicios del gimnasio
     * 
     * @return servicios
     */
    public ArrayList<Servicio> getServicios() {
        return this.servicios;
    }

    /**
     * Devuelve los servicios que se producen en la proxima semana
     * 
     * @return arraylist de servicios
     */
    public ArrayList<Servicio> getServiciosSiguientes() {
        ArrayList<Servicio> ret = new ArrayList<Servicio>();
        for (Servicio s : this.servicios) {
            if (s.getFecha().isAfter(LocalDate.now()) && s.getFecha().isBefore(LocalDate.now().plusDays(7))) {
                ret.add(s);
            }
        }
        return ret;
    }

    /**
     * Devuelve los servicios de tipo actividad Monitor
     * 
     * @return arraylist de servicios
     */
    public ArrayList<ActividadMonitor> getServiciosActividadMonitor() {
        ArrayList<ActividadMonitor> ret = new ArrayList<ActividadMonitor>();
        for (Servicio s : this.servicios) {
            if (s instanceof ActividadMonitor) {
                if (s instanceof ActividadGrupal && s.aforoDisponible() > 0)
                    ret.add((ActividadGrupal) s);
                else if (s instanceof EntrenamientoMonitor
                        && ((EntrenamientoMonitor) s).getMonitor().equals(currentUser)
                        && ((EntrenamientoMonitor) s).getEntPers() == null)
                    ret.add((EntrenamientoMonitor) s);
            }
        }
        return ret;
    }

    /**
     * Añade un servicio al gimnasio
     * 
     * @param s servicio a añadir
     */
    public void addServicio(Servicio s) {
        servicios.add(s);
        this.generateBackUp();
    }

    /**
     * Crea un Entrenamiento Libre y lo añade al sistema
     * 
     * @param nombre      Nombre del Entrenamiento Libre
     * @param descripcion Descripcion del Entrenamiento Libre
     * @param h           Horario del Entrenamiento Libre
     * @param fecha       Fecha del Entrenamiento Libre
     * @param sala        Sala del Entrenamiento Libre
     * @return Entrenamiento Libre creado
     */
    public EntrenamientoLibre addEntrenamientoLibre(String nombre, String descripcion, Horario h, LocalDate fecha,
            Sala sala) {
        EntrenamientoLibre el = new EntrenamientoLibre(nombre, descripcion, h, fecha, sala);
        this.addServicio(el);
        return el;
    }

    /**
     * Crea un Actividad Grupal y lo añade al sistema
     * 
     * @param nombre        Nombre del Actividad Grupal
     * @param descripcion   Descripcion del Actividad Grupal
     * @param h             Horario del Actividad Grupal
     * @param fecha         Fecha del Actividad Grupal
     * @param sala          Sala del Actividad Grupal
     * @param monitor       Monitor del Actividad Grupal
     * @param tipoActividad Tipo de Actividad del Actividad Grupal
     * @return Actividad Grupal creado
     */
    public ActividadGrupal addActividadGrupal(String nombre, String descripcion, Horario h, LocalDate fecha, Sala sala,
            Monitor monitor, String tipoActividad) {
        TipoActividad ta = new TipoActividad(tipoActividad);
        ActividadGrupal el = new ActividadGrupal(nombre, descripcion, h, fecha, sala, monitor, ta);
        monitor.addActividad(el);
        this.addServicio(el);
        return el;
    }

    /**
     * Elimina un servicio del gimnasio
     * 
     * @param s servicio a eliminar
     */
    public void removeServicio(Servicio s) {
        this.servicios.remove(s);
    }

    /**
     * Busca los servicios dado un nombre
     * 
     * @param nombre nombre del servicio
     * @return servicios con ese nombre
     */
    public ArrayList<Servicio> buscarServiciosNombre(String nombre) {
        ArrayList<Servicio> serviciosEncontrados = new ArrayList<Servicio>();
        for (Servicio servicio : this.servicios) {
            if (servicio.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                serviciosEncontrados.add(servicio);
            }
        }
        return serviciosEncontrados;
    }

    /**
     * Busca los servicios de un tipo concreto
     * 
     * @param tipo tipo de servicio
     * @return servicios de un tipo concreto
     */
    public ArrayList<Servicio> buscarServicios(String tipo) {
        ArrayList<Servicio> serviciosEncontrados = new ArrayList<Servicio>();
        for (Servicio servicio : this.servicios) {
            if (servicio.getBusquedaString().toLowerCase().contains(tipo.toLowerCase())) {
                serviciosEncontrados.add(servicio);
            }
        }
        return serviciosEncontrados;
    }

    /**
     * Dada una busqueda inicial realiza un AND con una busqueda nueva
     * 
     * @param ls   lista de servicios de la busqueda anterior
     * @param tipo busqueda nueva
     * @return lista de servicios con la busqueda nueva AND la anterior
     */
    public ArrayList<Servicio> busquedasAND(ArrayList<Servicio> ls, String tipo) {
        ArrayList<Servicio> serviciosEncontrados = new ArrayList<Servicio>();
        for (Servicio servicio : ls) {
            if (servicio.getBusquedaString().toLowerCase().contains(tipo.toLowerCase())) {
                serviciosEncontrados.add(servicio);
            }
        }
        return serviciosEncontrados;
    }

    /**
     * Dada una busqueda inicial realiza un OR con una busqueda nueva
     * 
     * @param ls   lista de servicios con la busqueda anterior
     * @param tipo busqueda nueva
     * @return lista de servicios con la busqueda nueva OR la anterior
     */
    public ArrayList<Servicio> busquedasOR(ArrayList<Servicio> ls, String tipo) {
        for (Servicio servicio : servicios) {
            if (servicio.getBusquedaString().toLowerCase().contains(tipo.toLowerCase()) && !ls.contains(servicio)) {
                // System.out.println("añado " + servicio.getBusquedaString());
                ls.add(servicio);
            } else {
                // System.out.println("no añado " + servicio.getBusquedaString());
            }
        }
        return ls;
    }

    /**
     * Busca los servicios de tipo Entrenamiento Libre
     * 
     * @return servicios de tipo Entrenamiento Libre
     */
    public List<Servicio> buscarEntLibre() {
        return this.buscarServicios("Entrenamiento libre");
    }

    /**
     * Busca los servicios de tipo Entrenamiento Personalizado
     * 
     * @return servicios de tipo Entrenamiento Personalizado
     */
    public List<Servicio> buscarEntPersonalizado() {
        return this.buscarServicios("Entrenamiento personalizado");
    }

    /**
     * Busca los servicios de tipo Actividad Grupal
     * 
     * @return servicios de tipo Actividad Grupal
     */
    public List<Servicio> buscarActividadGrupal() {
        return this.buscarServicios("Actividad grupal");
    }

    /**
     * Busca los servicios de tipo Actividad Grupal de un tipo concreto
     * 
     * @param tipo tipo de actividad
     * @return servicios de tipo Actividad Grupal de un tipo concreto
     */
    public List<Servicio> buscarActividadGrupal(String tipo) {
        return this.buscarServicios("Actividad grupal " + tipo);
    }

    /**************************************************************************/
    /* Funciones para manejar los tipos de Actividad */
    /**************************************************************************/
    /**
     * Añade un tipo de actividad al gimnasio
     * 
     * @param tipo tipo de actividad a añadir
     */
    public void addTipoActividad(TipoActividad tipo) {
        this.tiposDeActividad.add(tipo);
    }

    /**
     * Elimina un tipo de actividad del gimnasio
     * 
     * @param tipo tipo de actividad a eliminar
     */
    public void removeTipoActividad(TipoActividad tipo) {
        this.tiposDeActividad.remove(tipo);
    }

    /**
     * Devuelve los tipos de actividad del gimnasio
     * 
     * @return tipos de actividad
     */
    public Set<TipoActividad> getTiposDeActividad() {
        return this.tiposDeActividad;
    }

    /**
     * Establece los tipos de actividad del gimnasio
     * 
     * @param tipos tipos de actividad
     */
    public void setTipoActividad(Set<TipoActividad> tipos) {
        this.tiposDeActividad = tipos;
    }

    /**
     * Devuelve información del gimnasio en un String
     * 
     * @return información del gimnasio
     */
    @Override
    public String toString() {
        return "NanoGym [salas=" + salas + "\nusuarios=" + usuarios + "\nservicios=" + servicios + "\n, name=" + name
                + ", logo=" + logo + ", cif=" + cif + "]";
    }

    /**************************************************************************/
    /* Funciones para hacer y cargar backUps */
    /**************************************************************************/
    /**
     * Guarda un backup del sistema
     */
    public void generateBackUp() {
        this.guardarSalas();
        this.guardarUsuarios();
        this.guardarServicios();
    }

    /**
     * Carga el backUp del sistema
     */
    public void loadBackUp() {
        this.leerSalas();
        this.leerServicios();
        this.leerUsuarios();
    }

    /**************************************************************************/
    /* Funciones para generar PDFs */
    /**************************************************************************/
    /**
     * Devuelve el nombre del gimnasio
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Devuelve el logo del gimnasio
     * 
     * @return logo del gimnasio
     */
    @Override
    public String getLogo() {
        return this.logo;
    }

    /**
     * Devuelve el cif del gimnasio
     * 
     * @return cif del gimnasio
     */
    @Override
    public String getCif() {
        return this.cif;
    }

    /**
     * Devuelve el sueldo base de un monitor
     * 
     * @return sueldo base de un monitor
     */
    @Override
    public double getBaseSalaryPerMonth() {
        return Monitor.getNOMINA_BASE();
    }

    /**
     * Devuelve el sueldo por hora de un monitor
     * 
     * @return sueldo por hora de un monitor
     */
    @Override
    public double getRateHour() {
        return Monitor.getEXTRA_SUELDO_SESION();
    }

    /**************************************************************************/
    /* Funciones para registro(cliente), logIn y logOut */
    /**************************************************************************/
    /**
     * Añade un cliente al sistema comprobando que los datos sean correctos
     * 
     * @param nick           nombre de usuario
     * @param contraseña     contraseña del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param nacimiento     fecha de nacimiento del usuario
     * @param tb             tarjeta bancaria del usuario
     * @param tarifa         tarifa del usuario
     * @return cliente creado
     * @throws IllegalArgumentException si alguno de los datos es incorrecto
     */
    public Cliente aniadirCliente(String nick, String contraseña, String nombreCompleto, LocalDate nacimiento,
            TarjetaBancaria tb, Tarifa tarifa) throws IllegalArgumentException {
        /* Comprobacion de parametros */
        if (this.usuarios.containsKey(nick)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nickname.");
        }
        if (contraseña.length() < 5) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 5 caracteres.");
        }
        if (tb.fechaCaducidadValida() == false) {
            throw new IllegalArgumentException("La tarjeta bancaria ha caducado.");
        }
        if (TeleChargeAndPaySystem.isValidCardNumber(tb.getNumeroTarjeta()) == false) {
            throw new IllegalArgumentException("El numero de tarjeta bancaria no es valido.");
        }
        if (tb.getCvv().length() != 3) {
            throw new IllegalArgumentException("El codigo de seguridad de la tarjeta bancaria no es valido.");
        }

        Cliente c = new Cliente(nick, contraseña, nombreCompleto, nacimiento);

        c.setTarjetaBancaria(tb);

        if (tarifa.cobrarAlRegistrarse() == true) {
            if (c.cobrar(TarifaPlana.getPrecioBase() * ((TarifaPlana) tarifa).getDuracionMes(),
                    ((TarifaPlana) tarifa).getMensajeCobro()) == true) {
                c.setTarifa(tarifa);
            } else {
                throw new IllegalArgumentException("No se ha podido cobrar la tarifa.");
            }
        }

        this.usuarios.put(nick, c);
        this.generateBackUp();

        return c;
    }

    /**
     * Recoge los datos del cliente por terminal y lo añade al sistema
     * 
     * @return cliente creado
     * @throws IllegalArgumentException si alguno de los datos es incorrecto
     */
    public Cliente registrarCliente() throws IllegalArgumentException {
        Cliente c = null;
        String nick = "", contraseña = "";
        int anio = 0, mes = 0, dia = 0;
        LocalDate nacimiento = null;
        Tarifa tarifa = null;
        TarjetaBancaria tb = null;

        /* Recogo datos del cliente */

        do {
            System.out.println("Introduce tu nickname: ");
            nick = leer.nextLine();

            if (this.usuarios.containsKey(nick)) {
                System.out.println("Ya existe un usuario con ese nickname.");
            }

        } while (this.usuarios.containsKey(nick));

        do {
            System.out.println("Introduce tu contraseña(minimo 5 caracteres): ");
            contraseña = leer.nextLine();

            if (contraseña.length() < 5) {
                System.out.println("Contraseña invalida. Menos de 5 caracteres.");
            }
        } while (contraseña.length() < 5);

        System.out.println("Introduce tu nombre completo: ");
        String nombreCompleto = leer.nextLine();

        do {
            System.out.println("Introduce tu fecha de nacimiento(YYYY MM DD): ");
            anio = leer.nextInt();
            mes = leer.nextInt();
            dia = leer.nextInt();
            leer.nextLine();

            try {
                nacimiento = LocalDate.of(anio, mes, dia);
            } catch (Exception e) {
                System.out.println("Fecha invalida.");
            }

        } while (nacimiento == null);

        do {
            System.out.println("Introduce tu numero de tarjeta bancaria(16 numeros): ");
            String numeroTarjeta = leer.nextLine();

            System.out.println("Introduce tu fecha de caducidad de la tarjeta bancaria(YYYY MM DD): ");
            int anioCaducidad = leer.nextInt();
            int mesCaducidad = leer.nextInt();
            int diaCaducidad = leer.nextInt();
            LocalDate fechaCaducidad = LocalDate.of(anioCaducidad, mesCaducidad, diaCaducidad);
            leer.nextLine();

            System.out.println("Introduce el codigo de seguridad de la tarjeta bancaria(CVV): ");
            String cvv = leer.nextLine();

            try {
                tb = new TarjetaBancaria(numeroTarjeta, fechaCaducidad, cvv);
            } catch (InvalidCardNumberException e) {
                System.out.println("La tarjeta bancaria no es valida. (Numero incorrecto)");
            } catch (Exception d) {
                System.out.println("La tarjeta bancaria no es valida.");
            }

        } while (tb == null);

        System.out.println("Introduce el tipo de tarifa que quieres(valor 1 o 2): ");
        System.out.println("1. Tarifa Plana");
        System.out.println("2. Tarifa Pago Por Uso");
        int tipoTarifa = Integer.parseInt(leer.nextLine());
        if (tipoTarifa == 1) {
            System.out.println("Introduce la duracion del mes de la tarifa plana: ");
            int duracionMes = Integer.parseInt(leer.nextLine());
            /* Hacer esto */
            System.out.println("Introduce la actividad de la tarifa plana: ");
            String tipoActividad = leer.nextLine();
            TipoActividad ta = new TipoActividad(tipoActividad);
            tarifa = new TarifaPlana(duracionMes, ta); /* Cambiar forma de meter tipo actividad */
        } else if (tipoTarifa == 2) {
            tarifa = new TarifaPagoUso();
        }

        /* Añado cliente */
        c = this.aniadirCliente(nick, contraseña, nombreCompleto, nacimiento, tb, tarifa);

        return c;
    }

    /**
     * Función logIn por terminal
     * 
     * @return true si se ha logeado correctamente, false en caso contrario
     */
    public boolean logIn() {
        String nick, contraseña;

        System.out.println("Introduce tu nick: ");
        nick = leer.nextLine();
        System.out.println("Introduce tu contraseña: ");
        contraseña = leer.nextLine();

        if (this.usuarios.containsKey(nick)) {
            if (this.usuarios.get(nick).getContraseña().equals(contraseña)) {
                this.currentUser = this.usuarios.get(nick);
                return true;
            } else {
                throw new IllegalArgumentException("La contraseña no es correcta.");
            }
        } else {
            throw new IllegalArgumentException("No existe un usuario con ese nickname.");
        }
    }

    /**
     * Inicia sesión a un usuario
     * 
     * @param nick       nombre de usuario
     * @param contrasena contraseña del usuario
     * @return El usuario creado
     * @throws IllegalArgumentException si el usuario no existe o la contraseña
     *                                  es incorrecta
     */
    public Usuario logIn(String nick, String contrasena) throws IllegalArgumentException {

        if (this.usuarios.containsKey(nick)) {
            if (this.usuarios.get(nick).getContraseña().equals(contrasena)) {
                this.currentUser = this.usuarios.get(nick);
                return this.usuarios.get(nick);
            } else {
                throw new IllegalArgumentException("La contraseña no es correcta.");
            }
        } else {
            throw new IllegalArgumentException("No existe un usuario con ese nickname.");
        }

    }

    /**
     * Cierra la sesion del usuario actual
     * 
     * @return true si se ha cerrado la sesion correctamente, false en caso
     *         contrario
     */
    public boolean logOut() {
        if (this.currentUser != null) {
            this.currentUser = null;
            return true;
        } else {
            throw new IllegalArgumentException("No hay ningun usuario logeado.");
        }
    }

    /**************************************************************************/
    /* Funciones para visualización */
    /**************************************************************************/
    /**
     * Muestra el menu de inicio por terminal
     */
    public void mostrarMenuInicio() {
        System.out.println("--------------------------------------------------");
        System.out.println("Bienvenido a NanoGym!\n");
        System.out.println("1. Registrarse");
        System.out.println("2. LogIn (debe de estar registrado previamente)");
        System.out.println("--------------------------------------------------");

    }

    /**************************************************************************/
    /* Funciones para visualización */
    /**************************************************************************/
    /**
     * Crea un entrenamiento personalizado
     * 
     * @param nombre       nombre del entrenamiento personalizado
     * @param descripcion  descripcion del entrenamiento personalizado
     * @param monitor      monitor del entrenamiento personalizado
     * @param actividadesM actividades del entrenamiento personalizado
     * @param obj          objetivo del entrenamiento personalizado
     * @return true si se ha creado correctamente, false en caso contrario
     */
    public boolean crearEntrenamientoPersonalizado(String nombre, String descripcion, Monitor monitor,
            Collection<ActividadMonitor> actividadesM, String obj) {

        try {
            EntrenamientoPersonalizado ep = new EntrenamientoPersonalizado(nombre, descripcion, monitor, actividadesM,
                    obj);

            this.servicios.add(ep);
            this.generateBackUp();
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    /**
     * Crea un entrenamiento con monitor
     * 
     * @param nombre      nombre del entrenamiento con monitor
     * @param descripcion descripcion del entrenamiento con monitor
     * @param monitor     monitor del entrenamiento con monitor
     * @param sala        sala del entrenamiento con monitor
     * @param fecha       fecha del entrenamiento con monitor
     * @param h           horario del entrenamiento con monitor
     * @return true si se ha creado correctamente, false en caso contrario
     */
    public EntrenamientoMonitor crearEntrenamientoMonitor(String nombre, String descripcion, Monitor monitor, Sala sala,
            LocalDate fecha, Horario h) {

        try {
            EntrenamientoMonitor ep = new EntrenamientoMonitor(nombre, descripcion, h, sala, monitor, fecha);

            this.servicios.add(ep);
            this.generateBackUp();
            return ep;
        } catch (Exception e) {

            return null;
        }

    }

    /**
     * Muestra las actividades grupales
     */
    public void mostrarActividadesGrupales() {
        int i = 1;
        int flag = 0;
        for (Servicio a : this.servicios) {
            if (a.imprimirInfoGrupal() == true) {
                System.out.println("-------------------------------------");
                System.out.println("Actividad " + i + ":");
                System.out.println(a.toString());
                flag++;
            }
            i++;
        }
        if (flag == 0) {
            System.out.println("No hay actividades grupales");
        }
    }

    /**
     * Calcula el balance actual del gimnasio y lo imprime por pantalla
     */
    public void balanceActual() {
        double gastos = 0;
        double ingresos = 0;

        Iterator<Entry<String, Usuario>> usuariosIterator = this.usuarios.entrySet().iterator();
        while (usuariosIterator.hasNext()) {
            Map.Entry<String, Usuario> set = (Map.Entry<String, Usuario>) usuariosIterator.next();
            if (set.getValue().esCliente() == true) {
                ingresos += ((Cliente) set.getValue()).getGastoTotal();
            } else if (set.getValue().esMonitor() == true) {
                gastos += ((Monitor) set.getValue()).getHorasTrabajadas(LocalDate.now().getMonth(),
                        Year.of(LocalDate.now().getYear())) * Monitor.getEXTRA_SUELDO_SESION()
                        + Monitor.getNOMINA_BASE();
            }
        }

        System.out.println("BALANCE DE NANOGYM:");
        System.out.println("=============================");
        System.out.println("Gastos: " + gastos);
        System.out.println("Ingresos:" + ingresos);
        System.out.println("--------------------------");
        System.out.println("Balance: " + (ingresos - gastos));

    }

    /**
     * Devuelve los gastos del gimnasio
     * 
     * @return gastos del gimnasio
     */
    public double getGastos() {
        double gastos = 0;

        Iterator<Entry<String, Usuario>> usuariosIterator = this.usuarios.entrySet().iterator();
        while (usuariosIterator.hasNext()) {
            Map.Entry<String, Usuario> set = (Map.Entry<String, Usuario>) usuariosIterator.next();
            if (set.getValue().esMonitor() == true) {
                gastos += ((Monitor) set.getValue()).getHorasTrabajadas(LocalDate.now().getMonth(),
                        Year.of(LocalDate.now().getYear())) * Monitor.getEXTRA_SUELDO_SESION()
                        + Monitor.getNOMINA_BASE();
            }
        }

        return gastos;
    }

    /**
     * Devuelve los ingresos del gimnasio
     * 
     * @return ingresos del gimnasio
     */
    public double getIngresos() {
        double ingresos = 0;

        Iterator<Entry<String, Usuario>> usuariosIterator = this.usuarios.entrySet().iterator();
        while (usuariosIterator.hasNext()) {
            Map.Entry<String, Usuario> set = (Map.Entry<String, Usuario>) usuariosIterator.next();
            if (set.getValue().esCliente() == true) {
                ingresos += ((Cliente) set.getValue()).getGastoTotal();
            }
        }

        return ingresos;
    }

    /**
     * Devuelve el balance del gimnasio
     * 
     * @return balance del gimnasio
     */
    public double getBalance() {
        return this.getIngresos() - this.getGastos();
    }

    /**
     * Establece el usuario actual
     * 
     * @param u usuario actual
     */
    public void setCurrentUser(Usuario u) {
        this.currentUser = u;
    }

}
