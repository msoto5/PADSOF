/**
 * Implementa la clase Usuario
 */
package usuario.cliente;

import java.time.LocalDate;
import java.util.*;

import exceptions.ClienteSuspendidoException;
import exceptions.ServicioAtMaximunCapacityException;
import nanoGym.NanoGym;
import servicio.Servicio;
import usuario.Usuario;
import usuario.cliente.tarifa.Tarifa;
import usuario.cliente.tarifa.TarifaPagoUso;
import utiles.EstadoReserva;
import utiles.Reserva;

/**
 * Clase que representa un cliente del sistema
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Cliente extends Usuario {

	/** Serial Version UID */
	private static final long serialVersionUID = -4067215005699253373L;

	/** Nombre completo del Cliente */
	private String nombreCompleto;

	/** Fecha de nacimiento */
	private LocalDate fechaNacimiento;

	/** Número de faltas */
	private int numFaltas;

	/** Gasto total */
	private double gastoTotal;

	/** Tarjeta bancaria */
	private TarjetaBancaria tarjetaBancaria;

	/** Tarifa */
	private Tarifa tarifa;

	/** Lista de reservas */
	private LinkedList<Reserva> reservas;

	/** Características de la Suspensión del Cliente, si tiene */
	private Suspension suspension;

	/** Máximo de faltas permitidas antes de la suspension */
	private static int MAX_FALTAS = 3;

	/**
	 * Constructor de la clase Cliente.
	 * Inicializa un cliente con 0 faltas, el gasto total a 0, sin tarjeta bancaria,
	 * la tarifa a TarifaPagoUso, sin reservas y sin suspension.
	 * 
	 * @param username    Nombre de usuario del cliente
	 * @param contraseña  Contraseña del cliente
	 * @param nombre      Nombre completo del cliente
	 * @param fNacimiento Fecha de nacimiento del cliente
	 * @throws IllegalArgumentException si alguno de los argumentos son nulos o
	 *                                  vacíos
	 */
	public Cliente(String username, String contraseña, String nombre, LocalDate fNacimiento)
			throws IllegalArgumentException {
		super(username, contraseña);
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre completo no puede ser nulo o vacío.");
		} else if (fNacimiento == null || fNacimiento.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
		}

		this.nombreCompleto = nombre;
		this.fechaNacimiento = fNacimiento;
		this.numFaltas = 0;
		this.gastoTotal = 0;
		this.tarjetaBancaria = null;
		this.tarifa = new TarifaPagoUso();
		this.reservas = new LinkedList<>();
		this.suspension = null;
	}

	/**
	 * Devuelve el nombre completo del cliente
	 * 
	 * @return el nombre completo del cliente
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Establece el nombre completo del cliente
	 * 
	 * @param nombreCompleto el nombre completo del cliente
	 * @throws IllegalArgumentException si el nombre completo es nulo o vacío
	 */
	public void setNombreCompleto(String nombreCompleto) {
		if (nombreCompleto == null || nombreCompleto.isEmpty()) {
			throw new IllegalArgumentException("El nombre completo no puede ser nulo o vacío.");
		}
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Devuelve la fecha de nacimiento del cliente
	 * 
	 * @return la fecha de nacimiento del cliente
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento del cliente
	 * 
	 * @param fechaNacimiento la fecha de nacimiento del cliente
	 * @throws IllegalArgumentException si la fecha de nacimiento es nula
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
		}
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Devuelve el número de faltas del cliente
	 * 
	 * @return el número de faltas del cliente
	 */
	public int getNumFaltas() {
		return numFaltas;
	}

	/**
	 * Establece el número de faltas del cliente
	 * 
	 * @param numFaltas el número de faltas del cliente
	 * @throws IllegalArgumentException si el número de faltas es negativo
	 */
	public void setNumFaltas(int numFaltas) {
		if (numFaltas < 0) {
			throw new IllegalArgumentException("El número de faltas no puede ser negativo.");
		}
		this.numFaltas = numFaltas;
	}

	/**
	 * Añade una falta al cliente. En caso de que el número de faltas supere el
	 * máximo permitido, el cliente será suspendido.
	 */
	public void addFalta() {
		this.numFaltas++;
		if (this.numFaltas >= MAX_FALTAS) {
			this.suspender();
		}
	}

	/**
	 * Suspende al cliente
	 */
	public void resetFaltas() {
		this.numFaltas = 0;
	}

	/**
	 * Devuelve el gasto total del cliente
	 * 
	 * @return el gasto total del cliente
	 */
	public double getGastoTotal() {
		return gastoTotal;
	}

	/**
	 * Establece el gasto total del cliente
	 * 
	 * @param gastoTotal el gasto total del cliente
	 * @throws IllegalArgumentException si el gasto total es negativo
	 */
	public void setGastoTotal(double gastoTotal) {
		if (gastoTotal < 0) {
			throw new IllegalArgumentException("El gasto total no puede ser negativo.");
		}
		this.gastoTotal = gastoTotal;
	}

	/**
	 * Añade un gasto al cliente
	 * 
	 * @param gasto el gasto a añadir
	 * @throws IllegalArgumentException si el gasto es negativo
	 */
	public void addGasto(double gasto) {
		if (gasto < 0) {
			throw new IllegalArgumentException("El gasto no puede ser negativo.");
		}
		this.gastoTotal += gasto;
	}

	/**
	 * Devuelve la tarjeta bancaria del cliente
	 * 
	 * @return la tarjeta bancaria del cliente
	 */
	public TarjetaBancaria getTarjetaBancaria() {
		return tarjetaBancaria;
	}

	/**
	 * Establece la tarjeta bancaria del cliente
	 * 
	 * @param tarjetaBancaria la tarjeta bancaria del cliente
	 * @throws IllegalArgumentException si la tarjeta bancaria es nula o está
	 *                                  caducada
	 */
	public void setTarjetaBancaria(TarjetaBancaria tarjetaBancaria) {
		if (tarjetaBancaria == null || !tarjetaBancaria.fechaCaducidadValida()) {
			throw new IllegalArgumentException("La tarjeta bancaria no puede ser nula o estar caducada.");
		}
		this.tarjetaBancaria = tarjetaBancaria;
	}

	/**
	 * Devuelve la tarifa del cliente
	 * 
	 * @return la tarifa del cliente
	 */
	public Tarifa getTarifa() {
		return tarifa;
	}

	/**
	 * Establece la tarifa del cliente
	 * 
	 * @param tarifa la tarifa del cliente
	 * @throws IllegalArgumentException si la tarifa es nula
	 */
	public void setTarifa(Tarifa tarifa) {
		if (tarifa == null) {
			throw new IllegalArgumentException("La tarifa no puede ser nula.");
		}
		this.tarifa = tarifa;
	}

	/**
	 * Renueva la tarifa del cliente
	 * 
	 * @param duracion la duración de la tarifa
	 */
	public void renovarTarifa(int duracion) {
		this.addGasto(tarifa.renovar(duracion));
		this.getGN().addNotificacion("Has renovado tu tarifa por " + duracion + " días.");
	}

	/**
	 * Devuelve la lista de reservas del cliente
	 * 
	 * @return la lista de reservas del cliente
	 */
	public LinkedList<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * Establece la lista de reservas del cliente
	 * 
	 * @param reservas la lista de reservas del cliente
	 * @throws IllegalArgumentException si la lista de reservas es nula
	 */
	public void setReservas(LinkedList<Reserva> reservas) {
		if (reservas == null) {
			throw new IllegalArgumentException("La lista de reservas no puede ser nula.");
		}
		this.reservas = reservas;
	}

	/**
	 * Añade una reserva a la lista de reservas del cliente
	 * 
	 * @param reserva la reserva a añadir
	 * @return true si la reserva se añade correctamente, false en caso contrario
	 * @throws IllegalArgumentException si la reserva es nula
	 */
	public boolean addReserva(Reserva reserva) {
		if (reserva == null) {
			throw new IllegalArgumentException("La reserva no puede ser nula.");
		}
		if (this.reservas.contains(reserva)) {
			return false;
		}

		this.reservas.add(reserva);
		Collections.sort(this.reservas);

		this.getGN().addNotificacion("Se te ha asignado la reserva de "
				+ reserva.getServicio().getNombre() + ".");

		return true;
	}

	/**
	 * Elimina una reserva de la lista de reservas del cliente
	 * 
	 * @param reserva la reserva a eliminar
	 * @return true si la reserva se elimina correctamente, false en caso contrario
	 * @throws IllegalArgumentException si la reserva es nula
	 */
	public boolean removeReserva(Reserva reserva) {
		if (reserva == null) {
			throw new IllegalArgumentException("La reserva no puede ser nula.");
		}

		this.getGN().addNotificacion("Se te ha eliminado la reserva de "
				+ reserva.getServicio().getNombre() + ".");
		return this.reservas.remove(reserva);
	}

	/**
	 * Devuelve la suspension del cliente
	 * 
	 * @return la suspension del cliente
	 */
	public Suspension getSuspension() {
		if (suspension == null) {
			return null;
		} else if (suspension.isSuspendido()) {
			return suspension;
		} else {
			this.suspension = null;
			return null;
		}
	}

	/**
	 * Establece la suspension del cliente
	 * 
	 * @param suspension la suspension del cliente
	 * @throws IllegalArgumentException si la suspension es nula
	 */
	public void setSuspension(Suspension suspension) {
		this.suspension = suspension;
	}

	/**
	 * Suspende al cliente
	 * 
	 * @return la suspension del cliente
	 */
	public Suspension suspender() {
		this.suspension = new Suspension();
		getGN().addNotificacion("Tu cuenta ha sido suspendida durante "
				+ Suspension.getDiasSuspension() + "días. No podrás realizar reservas.");

		return this.suspension;
	}

	/**
	 * Elimina la suspension del cliente
	 */
	public void eliminarSuspension() {
		getGN().addNotificacion("Tu cuenta ha sido reactivada. Ya puedes realizar reservas.");
		this.suspension = null;
	}

	/**
	 * Devuelve si el cliente está suspendido
	 * 
	 * @return si el cliente está suspendido
	 */
	public boolean isSuspendido() {
		if (getSuspension() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Devuelve el máximo de faltas permitidas
	 * 
	 * @return el máximo de faltas permitidas
	 */
	public static int getMAX_FALTAS() {
		return MAX_FALTAS;
	}

	/**
	 * Establece el máximo de faltas permitidas
	 * 
	 * @param maxFaltas el máximo de faltas permitidas
	 * @throws IllegalArgumentException si el máximo de faltas es negativo
	 */
	public static void setMAX_FALTAS(int maxFaltas) {
		if (maxFaltas < 0) {
			throw new IllegalArgumentException("El máximo de faltas no puede ser negativo.");
		}
		MAX_FALTAS = maxFaltas;
	}

	/**
	 * Devuelve los datos del cliente en un String
	 * 
	 * @return los datos del cliente en un String
	 */
	@Override
	public String toString() {
		if (this.suspension != null) {
			return "Cliente: \n-Nombre: " + nombreCompleto + "\n-Fecha de nacimiento: " + fechaNacimiento
					+ "\n-Numero de faltas: " + numFaltas + "\n-Gasto total: " + gastoTotal
					+ "\n-Tarjeta bancaria: " + tarjetaBancaria + "\n-Tarifa: " + tarifa + "\n-Reservas: " + reservas
					+ "\n-Suspension: " + getSuspension();
		} else {
			return "Cliente: \n-Nombre: " + nombreCompleto + "\n-Fecha de nacimiento: " + fechaNacimiento
					+ "\n-Numero de faltas: " + numFaltas + "\n-Gasto total: " + gastoTotal
					+ "\n-Tarjeta bancaria: " + tarjetaBancaria + "\n-Tarifa: " + tarifa + "\n-Reservas: " + reservas;
		}
	}

	/**
	 * Otra forma de devolver los datos del cliente en un String
	 * 
	 * @return los datos del cliente en un String
	 */
	public String toString2() {
		if (this.suspension != null) {
			return "Cliente: \n-Nombre: " + nombreCompleto + "\n-Fecha de nacimiento: " + fechaNacimiento
					+ "\n-Numero de faltas: " + numFaltas + "\n-Gasto total: " + gastoTotal
					+ "\n-Tarjeta bancaria: " + tarjetaBancaria + "\n-Tarifa: " + tarifa + "]" + "\n-Suspension: "
					+ getSuspension();
		} else {
			return "Cliente: \n-Nombre: " + nombreCompleto + "\n-Fecha de nacimiento: " + fechaNacimiento
					+ "\n-Numero de faltas: " + numFaltas + "\n-Gasto total: " + gastoTotal
					+ "\n-Tarjeta bancaria: " + tarjetaBancaria + "\n-Tarifa: " + tarifa;
		}

	}

	/**
	 * Cobra al cliente una cantidad determinada.
	 * Para devolver el cobro se usa el método devolverCobro.
	 * 
	 * @param cantidad    cantidad a cobrar. No puede ser negativa.
	 * @param descripcion descripción del cobro
	 * @return true si el cobro se realiza correctamente, false en caso contrario
	 * @throws IllegalArgumentException si la cantidad a cobrar es negativa
	 */
	public boolean cobrar(double cantidad, String descripcion) throws IllegalArgumentException {
		if (cantidad < 0) {
			throw new IllegalArgumentException(
					"La cantidad a cobrar no puede ser negativa. Si quieres devolver un cobro, usa el método devolverCobro");
		}
		//System.out.println("Cobrando " + cantidad + "€ por " + descripcion);
		if (this.getTarjetaBancaria().cobrar(cantidad, descripcion) == true) {
			this.gastoTotal += cantidad;
			this.getGN().addNotificacion("Se te ha cobrado " + cantidad + "€ por " + descripcion);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve el cobro al cliente una cantidad determinada.
	 * Para devolver el cobro se usa el método devolverCobro.
	 * 
	 * @param cantidad    cantidad a devolver. No puede ser negativa.
	 * @param descripcion descripción del cobro
	 * @return true si el cobro se realiza correctamente, false en caso contrario
	 */
	public boolean devolverCobro(double cantidad, String descripcion) {
		if (cantidad < 0) {
			throw new IllegalArgumentException(
					"La cantidad a devolver no puede ser negativa. Si quieres cobrar, usa el método cobrar");
		}
		if (this.getTarjetaBancaria().cobrar((-1) * cantidad, descripcion) == true) {
			this.gastoTotal -= cantidad;
			this.getGN().addNotificacion("Se te ha devuelto " + cantidad + "€ por " + descripcion);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve el dinero al cliente por haber cancelado una reserva
	 * 
	 * @param r la reserva cancelada. Debe tener EstadoReserva = PAGADO
	 * @return true si se ha devuelto el dinero correctamente, false en caso
	 *         contrario
	 */
	public boolean devolverCobro(Reserva r) {
		if (r.getEstado() != EstadoReserva.PAGADO) {
			return false;
		}
		r.setEstado(EstadoReserva.CANCELADA);
		return this.devolverCobro(r.getCosteDevolucion(), "Devolución por cancelación de reserva");
	}

	/**
	 * Muestra el menú principal del cliente
	 */
	@Override
	public void mostrarMenuPrincipal() {
		System.out.println("MENU DE CLIENTE");
		System.out.println("--------------------------------");
		System.out.println("1. LogOut");
		System.out.println("2. Consultar actividades");
		System.out.println("3. Consultar reservas");
		System.out.println("4. Consultar perfil");
		System.out.println("5. Consultar notificaciones");
		System.out.println("6. Renovar tarifa");
		System.out.println("--------------------------------");
	}

	/**
	 * Interpreta las opciones del cliente en el menú
	 * 
	 * @param ret opción elegida
	 * @param ng  NanoGym
	 */
	@Override
	public void switchMenuPrincipal(int ret, NanoGym ng) {
		if (ret < 1 || ret > 5) {
			System.out.println("Numero invalido");
			return;
		} else if (ret == 1 || ret == 2) {
			return;
		}
		ret -= 2;
		switch (ret) {
			case 1: /* Reservas */
				this.consultarReservas();
				break;
			case 2: /* Perfil */
				this.mostrarPerfil();
				break;
			case 3: /* Notificaciones */
				break;
			case 4: /* Renovar tarifa */
				this.menuRenovarTarifa();
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
	 * Muestra el menú de renovación de tarifa
	 */
	private void menuRenovarTarifa() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("RENOVAR TARIFA");
		System.out.println("=============================================");
		System.out.println("Introduzca el numero de meses que quiere renovar:");
		System.out.println("1. Mensual");
		System.out.println("2. Trimestral");
		System.out.println("3. Anual");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		NanoGym.leer.nextLine();
		if (ret < 1 || ret > 3) {
			System.out.println("Numero invalido");
			return;
		}
		switch (ret) {
			case 1: /* Mensual */
				this.renovarTarifa(1);
				break;
			case 2: /* Trimestral */
				this.renovarTarifa(3);
				break;
			case 3: /* Anual */
				this.renovarTarifa(12);
				break;
			default:
				System.out.println("Opción incorrecta.");
				break;
		}

		System.out.println("Tarifa renovada correctamente");
	}

	/**
	 * Muestra el menú del perfil del cliente
	 */
	public void mostrarPerfil() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println(this.toString());
	}

	/**
	 * Muestra las reservas del cliente
	 */
	public void consultarReservas() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		int i = 0;
		if (this.getReservas().isEmpty()) {
			System.out.println("No hay reservas");
		} else {
			for (Reserva r : this.reservas) {
				i++;
				System.out.println("---------------------------------");
				System.out.println("Reserva " + i + ":");
				System.out.println(r.toString());
			}
		}

		int nres = 0;
		do {
			System.out
					.println("======================================================================================");
			System.out.println("(Introduce el número de una reserva si desea cancelarla o enter para volver al menu)");
			System.out
					.println("======================================================================================");
			String reserv = NanoGym.leer.nextLine();
			if (reserv.length() != 0) {
				nres = Integer.parseInt(reserv);

				if (nres > 0 && nres <= i) {
					try {
						if (this.cancelarReserva(this.getReservas().get(i - 1)) == true) {
							System.out.print("\033[H\033[2J");
							System.out.flush();
							System.out.println("Reserva cancelada correctamente.");
							NanoGym.leer.nextLine();
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Error: Opcion no valida. Vuelve a intentarlo");
				}
			} else {
				break;
			}
		} while (nres <= 0 || nres > i);
	}

	/**
	 * Reserva un servicio
	 * 
	 * @param s         Servicio a reservar
	 * @param flag_pago true si se quiere pagar la reserva, false en caso contrario
	 * @return true si la reserva se ha realizado correctamente, false en caso
	 *         contrario
	 * @throws ClienteSuspendidoException         si el cliente está suspendido
	 * @throws ServicioAtMaximunCapacityException si el servicio está lleno y no
	 *                                            acepta más capacidad
	 */
	public boolean reservarServicio(Servicio s, boolean flag_pago)
			throws ClienteSuspendidoException, ServicioAtMaximunCapacityException {
		boolean ret = false;

		if (s == null) { /* Control de errores */
			throw new IllegalArgumentException("El servicio no puede ser nulo.");
		} else if (this.isSuspendido()) {
			throw new ClienteSuspendidoException(this);
		} else if (!s.aceptaNuevaReserva()) {
			throw new ServicioAtMaximunCapacityException(s);
		}

		/* Crear reserva */
		Reserva r = new Reserva(this, s, EstadoReserva.RESERVADA);

		/* Añadir reserva al servicio */
		try {
			ret = s.addReserva(r);
		} catch (ServicioAtMaximunCapacityException e) {
			throw new ServicioAtMaximunCapacityException(s);
		}

		if (!ret) {
			return false;
		}

		/* Añadir reserva al cliente */
		ret = this.addReserva(r);
		if (!ret) {
			s.removeReserva(r);
			return false;
		}

		/* Pago */
		if (flag_pago) {
			String desc = "Pago servicio " + s.getNombre() + ". Fecha: " + s.getFecha().toString();
			System.out.println("Cobrando " + this.getTarifa().getPrecioReserva(s) + "€ por " + desc);
			ret = this.cobrar(this.getTarifa().getPrecioReserva(s), desc);
			if (!ret) {
				s.removeReserva(r);
				this.removeReserva(r);
				return false;
			}

			r.setEstado(EstadoReserva.PAGADO);
		}
		return true;
	}

	/**
	 * Reserva un servicio
	 * 
	 * @param s Servicio a reservar
	 * @return true si la reserva se ha realizado correctamente, false en caso
	 *         contrario
	 * @throws ClienteSuspendidoException         si el cliente está suspendido
	 * @throws ServicioAtMaximunCapacityException si el servicio está lleno y no
	 *                                            acepta más capacidad
	 */
	public boolean reservarServicio(Servicio s) throws ClienteSuspendidoException, ServicioAtMaximunCapacityException {
		return this.reservarServicio(s, true);
	}

	/**
	 * Cancela una reserva
	 * 
	 * @param s Reserva a cancelar
	 * @return true si la reserva se ha cancelado correctamente, false en caso
	 *         contrario
	 */
	public boolean cancelarReserva(Reserva s) {
		boolean ret = this.removeReserva(s);
		if (!ret) {
			return false;
		}
		this.addFalta();
		s.reservaCanceladaPorCliente(this);

		return ret;
	}

	/**
	 * Cancela un servicio
	 * 
	 * @param s Servicio a cancelar
	 * @return true si el servicio se ha cancelado correctamente, false en caso
	 *         contrario
	 */
	public boolean cancelarServicio(Servicio s) {
		for (Reserva r : reservas) {
			if (r.getServicio().equals(s)) {
				return this.cancelarReserva(r);
			}
		}
		return false;
	}

	/**
	 * Cancela una reserva por parte del servicio
	 * 
	 * @param r Reserva a cancelar
	 * @return true si la reserva se ha cancelado correctamente, false en caso
	 *         contrario
	 */
	public boolean cancelarReservaPorServicio(Reserva r) {
		return this.removeReserva(r);
	}

	/**
	 * Índica que el cliente puede reservar
	 * 
	 * @return true
	 */
	@Override
	public boolean esCliente() {
		return true;
	}

	/**
	 * Índica que el cliente no puede dar clase
	 * 
	 * @return false
	 */
	@Override
	public boolean esMonitor() {
		return false;
	}

	/**
	 * Devuelve los servicios de las reservas
	 * 
	 * @return los servicios de las reservas
	 */
	public ArrayList<Servicio> getServiciosReservas() {
		ArrayList<Servicio> ret = new ArrayList<>();
		for (Reserva r : this.reservas) {
			ret.add(r.getServicio());
		}
		return ret;
	}
}
