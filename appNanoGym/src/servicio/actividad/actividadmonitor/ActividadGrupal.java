/**
 * Clase que representa una actividad grupal de la aplicación.
 * @file ActividadGrupal.java
 * @package servicio.actividad.actividadMonitor
 */

package servicio.actividad.actividadmonitor;

import java.time.LocalDate;

import exceptions.ListaEsperaVaciaException;
import exceptions.ServicioAtMaximunCapacityException;
import usuario.Monitor;
import utiles.Horario;
import utiles.ListaEspera;
import utiles.Reserva;
import utiles.EstadoReserva;
import sala.Sala;
import servicio.EntrenamientoPersonalizado;
import servicio.Servicio;
import servicio.actividad.EntrenamientoLibre;

/**
 * Clase que representa una actividad grupal de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class ActividadGrupal extends ActividadMonitor {

	/** Serial Version UID */
	private static final long serialVersionUID = -1605623110344521324L;

	/** Tipo de actividad grupal */
	private TipoActividad tipo;

	/** Lista de espera de la actividad grupal */
	private ListaEspera listaEspera;

	/** Precio de la actividad grupal */
	private static double PRECIO = 15.0;

	/**
	 * Constructor de la clase ActividadGrupal
	 * 
	 * @param nombre        Nombre del servicio de clase ActividadGrupal
	 * @param descripcion   Descripción del servicio de clase ActividadGrupal
	 * @param horario       Horario en el que transcurre la actividad grupal
	 * @param fecha         Fecha en la que tiene lugar la actividad grupal
	 * @param sala          Sala en la que se realiza la actividad grupal
	 * @param monitor       Monitor que realiza la actividad grupal
	 * @param tipoActividad tipo de actividad grupal
	 * @throws IllegalArgumentException si el tipo de actividad es nulo
	 */
	public ActividadGrupal(String nombre, String descripcion, Horario horario, LocalDate fecha, Sala sala,
			Monitor monitor, TipoActividad tipoActividad) throws IllegalArgumentException {
		super(nombre, descripcion, horario, fecha, sala, monitor);

		if (tipoActividad == null) {
			throw new IllegalArgumentException("El tipo de actividad no puede ser nulo");
		}
		this.tipo = tipoActividad;
		this.listaEspera = new ListaEspera(this);
	}

	/**
	 * Devuelve el tipo de actividad grupal
	 * 
	 * @return tipo de actividad grupal
	 */
	public TipoActividad getTipoActividad() {
		return tipo;
	}

	/**
	 * Establece el tipo de actividad grupal
	 * 
	 * @param tipo tipo de actividad grupal
	 */
	public void setTipo(TipoActividad tipo) throws IllegalArgumentException {
		if (tipo == null) {
			throw new IllegalArgumentException("El tipo de actividad no puede ser nulo");
		}

		this.tipo = tipo;
	}

	/**
	 * Establece la lista de espera de la actividad grupal
	 * 
	 * @param listaEspera lista de espera de la actividad grupal
	 */
	public void setListaEspera(ListaEspera listaEspera) throws IllegalArgumentException {
		if (listaEspera == null) {
			throw new IllegalArgumentException("La lista de espera no puede ser nula");
		}

		this.listaEspera = listaEspera;
	}

	/**
	 * Devuelve la lista de espera de la actividad grupal
	 * 
	 * @return lista de espera de la actividad grupal
	 */
	public ListaEspera getListaEspera() {
		return listaEspera;
	}

	/**
	 * Añade una reserva a la lista de espera del entrenamiento personalizado
	 * 
	 * @param r reserva a añadir. Debe tener el estado en lista de espera
	 * @return true si se añade correctamente, false en caso contrario
	 * @throws IllegalArgumentException si la reserva es nula
	 */
	public boolean addReservaListaEspera(Reserva r) throws IllegalArgumentException {
		return this.listaEspera.addReserva(r);
	}

	/**
	 * Elimina una reserva de la lista de espera del entrenamiento personalizado
	 * 
	 * @param r reserva a eliminar. Debe tener el estado en lista de espera
	 * @return true si se elimina correctamente, false en caso contrario
	 * @throws IllegalArgumentException si la reserva es nula
	 */
	public boolean removeReservaListaEspera(Reserva r) throws IllegalArgumentException {
		return listaEspera.removeReserva(r);
	}

	/**
	 * Añade una reserva a la actividad grupal
	 * 
	 * @param reserva reserva a añadir
	 * @return true si se añade correctamente, false en caso contrario
	 */
	@Override
	public boolean addReserva(Reserva reserva) {
		try {
			return super.addReserva(reserva);
		} catch (ServicioAtMaximunCapacityException e) {
			reserva.setEstado(EstadoReserva.LISTA_ESPERA);
			this.addReservaListaEspera(reserva);
			return true;
		}
	}

	/**
	 * Elimina una reserva de la actividad grupal
	 * 
	 * @param reserva reserva a eliminar
	 * @return true si se elimina correctamente, false en caso contrario
	 */
	@Override
	public boolean removeReserva(Reserva reserva) {
		if (reserva.getEstado() == EstadoReserva.LISTA_ESPERA) {
			return this.removeReservaListaEspera(reserva);
		} else {
			return super.removeReserva(reserva);
		}
	}

	/**
	 * Establece el precio de la actividad grupal
	 * 
	 * @param precio precio de la actividad grupal
	 */
	public static void setPRECIO(double precio) {
		ActividadGrupal.PRECIO = precio;
	}

	/**
	 * Devuelve el precio de la actividad grupal
	 * 
	 * @return precio de la actividad grupal
	 */
	public static double getPRECIO() {
		return ActividadGrupal.PRECIO;
	}

	/**
	 * Devuelve el precio de la actividad grupal
	 * 
	 * @return precio de la actividad grupal
	 */
	@Override
	public double getPrecio() {
		return ActividadGrupal.getPRECIO();
	}

	/**
	 * Devuelve un string con la información de la actividad grupal
	 * 
	 * @return string con la información de la actividad grupal
	 */
	public String toString() {
		return "Actividad Grupal: " + super.toString() + "\n-Precio:" + ActividadGrupal.PRECIO;
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
		r.setEstado(EstadoReserva.RESERVADA);
		this.addReserva(r);
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
	 * Devuelve si la actividad grupal tiene info grupal
	 * 
	 * @return true
	 */
	@Override
	public boolean imprimirInfoGrupal() {
		return true;
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
	 * Devuelve el string de búsqueda de la actividad grupal
	 * 
	 * @return string de búsqueda de la actividad grupal
	 */
	@Override
	public String getBusquedaString() {
		return "Actividad grupal " + this.tipo.getNombre() + " " + getFecha() + " " + getHorario();
	}

	/**
	 * Compara la actividad grupal con otro servicio
	 * 
	 * @param serv servicio con el que comparar
	 * @return 0 si son iguales, menos que 0 si la actividad grupal es menor,
	 *         mas que 0 si la
	 *         actividad grupal es mayor
	 */
	@Override
	public int compareTo(Servicio serv) {
		int ret = super.compareTo(serv);
		if (ret == 0 && serv instanceof ActividadGrupal) {
			ActividadGrupal act = (ActividadGrupal) serv;
			ret = this.tipo.compareTo(act.tipo);
		} else if (ret == 0 && serv instanceof EntrenamientoPersonalizado) {
			ret = -1; // Act. G < Ent. P
		} else if (ret == 0 && serv instanceof EntrenamientoLibre) {
			ret = 1; // Act. G > Ent. L
		}
		return ret;
	}

	/**
	 * Devuelve si la actividad grupal es igual a otro objeto
	 * 
	 * @param obj objeto con el que comparar
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		if (obj instanceof ActividadGrupal) {
			ActividadGrupal act = (ActividadGrupal) obj;
			ret = super.equals(obj) && this.tipo.equals(act.tipo);
		}
		return ret;
	}

	/**
	 * Devuelve el hashcode de la actividad grupal
	 * 
	 * @return hashcode de la actividad grupal
	 */
	@Override
	public int hashCode() {
		return super.hashCode() * this.tipo.hashCode();
	}
}
