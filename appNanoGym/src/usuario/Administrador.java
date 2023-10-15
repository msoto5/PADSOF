/**
 * Implementa la clase Administrador
 */
package usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.SalaConAforoMaximoException;
import exceptions.SalaNoCompatibleException;
import nanoGym.*;

import usuario.cliente.tarifa.*;
import utiles.Horario;
import usuario.cliente.*;
import servicio.actividad.*;
import servicio.actividad.actividadmonitor.*;
import sala.*;

/**
 * Clase que representa un administrador del sistema
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class Administrador extends Usuario {

	/** Serial version UID */
	private static final long serialVersionUID = 5920686746542919410L;

	/** NanoGym */
	private NanoGym ng;

	/**
	 * Constructor de la clase Administrador
	 * 
	 * @param username   Nombre de usuario del administrador
	 * @param contraseña Contraseña del administrador
	 * @param ng         NanoGym
	 */
	public Administrador(String username, String contraseña, NanoGym ng) {
		super(username, contraseña);
		this.ng = ng;
	}

	/**
	 * Constructor de la clase Administrador sin NanoGym
	 * 
	 * @param username   Nombre de usuario del administrador
	 * @param contraseña Contraseña del administrador
	 */
	public Administrador(String username, String contraseña) {
		super(username, contraseña);
	}

	/**
	 * Devuelve una representación en String del administrador
	 * 
	 * @return representación en String del administrador
	 */
	@Override
	public String toString() {
		return "Administrador -> " + super.toString();
	}

	@Override
	public void mostrarMenuPrincipal() {
		System.out.println("MENU DE ADMINISTRADOR");
		System.out.println("--------------------------------");
		System.out.println("1. LogOut");
		System.out.println("2. Consultar proximas actividades");
		System.out.println("3. Modificar parametros");
		System.out.println("4. Crear actividad");
		System.out.println("5. Crear sala");
		System.out.println("6. Registrar monitor");
		System.out.println("7. Generar balance");
		System.out.println("--------------------------------");
	}

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
				this.mostrarParametros();
				break;
			case 2: /* Perfil */
				this.menuCrearActividad();
				break;
			case 3:
				this.menuCrearSala();
				break;
			case 4:
				this.registrarMonitor();
				break;
			case 5:
				this.menuGenerarBalance();
				break;
			default:
				System.out.println("Opción incorrecta.");
				break;
		}

		// System.out.println("====================================");
		// System.out.println("(presiona enter para volver al menu)");
		// System.out.println("====================================");
		// NanoGym.leer.nextLine();
	}

	/**
	 * Parte del menu de terminal para generar el balance
	 */
	private void menuGenerarBalance() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		ng.balanceActual();
		System.out.println("====================================");
		System.out.println("(presiona enter para volver al menu)");
		System.out.println("====================================");
		NanoGym.leer.nextLine();
	}

	/**
	 * Parte del menu del monitor para crear una actividad
	 */
	private void menuCrearActividad() {
		int ret = 0;
		do {
			System.out.println("CREAR ACTIVIDAD");
			System.out.println("--------------------------------");
			System.out.println("1. Actividad grupal");
			System.out.println("2. Entrenamiento libre");
			System.out.println("--------------------------------");
			System.out.println("(presiona un numero para elegir o enter para volver al menu)");
			System.out.println("--------------------------------");
			String res = NanoGym.leer.nextLine();
			if (res.length() < 1) {
				return;
			}
			ret = Integer.parseInt(res);
			if (ret < 1 || ret > 2) {
				System.out.println("Numero invalido");
			}

		} while (ret == 0);

		switch (ret) {
			case 1:
				this.menuCrearActividadGrupal();
				break;
			case 2:
				this.menuCrearEntrenamientoLibre();
				break;
			default:
				System.out.println("Opción incorrecta.");
				break;
		}

		if (ret != 0) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.println("Actividad cread correctamente");
			System.out.println("====================================");
			System.out.println("(presiona enter para volver al menu)");
			System.out.println("====================================");
			NanoGym.leer.nextLine();
		}
	}

	/**
	 * Parte del menu del administrador para crear un entrenamiento libre
	 */
	private void menuCrearEntrenamientoLibre() {
		System.out.print("\033[H\033[2J");
		System.out.flush();

		System.out.println("CREAR ENTRENAMIENTO LIBRE");
		System.out.println("--------------------------------");
		System.out.println("Introduce el nombre del entrenamiento");
		String nombre = NanoGym.leer.nextLine();
		System.out.println("Introduce la descripcion del entrenamiento");
		String descripcion = NanoGym.leer.nextLine();

		System.out.println("Introduce la fecha (MM DD)");
		int mes = NanoGym.leer.nextInt();
		int dia = NanoGym.leer.nextInt();

		LocalDate fecha = LocalDate.of(LocalDate.now().getYear(), mes, dia);

		System.out.println("Introduce la hora de inicio (HH MM)");
		int horaInicio = NanoGym.leer.nextInt();
		int minutoInicio = NanoGym.leer.nextInt();
		System.out.println("Introduce la hora de fin (HH MM)");
		int horaFin = NanoGym.leer.nextInt();
		int minutoFin = NanoGym.leer.nextInt();

		Horario h = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("SALAS:");
		System.out.println("==================================");
		Iterator<Entry<Integer, Sala>> salasIterator = ng.getSalas().entrySet().iterator();
		while (salasIterator.hasNext()) {
			Map.Entry<Integer, Sala> pair = (Map.Entry<Integer, Sala>) salasIterator.next();
			if (pair.getValue().ocupada(fecha, h) == false) {
				System.out.println(pair.getValue());
			}
		}
		System.out.println("==================================");
		System.out.println("Introduce el numero de la sala");
		int id = NanoGym.leer.nextInt();

		Sala sala = ng.getSalas().get(id);

		EntrenamientoLibre el = new EntrenamientoLibre(nombre, descripcion, h, fecha, sala);
		ng.getServicios().add(el);
	}

	/**
	 * Crea un entrenamiento libre
	 * 
	 * @param nombre      nombre del entrenamiento libre
	 * @param descripcion descripcion del entrenamiento libre
	 * 
	 * @param h           horario del entrenamiento libre
	 * @param fecha       fecha del entrenamiento libre
	 * @param sala        sala del entrenamiento libre
	 * 
	 */
	public void crearEntrenaminetoLibre(String nombre, String descripcion, Horario h, LocalDate fecha, Sala sala) {
		EntrenamientoLibre el = new EntrenamientoLibre(nombre, descripcion, h, fecha, sala);
		ng.getServicios().add(el);
	}

	/**
	 * Parte del menu del admin para crear una actividad grupal
	 */
	private void menuCrearActividadGrupal() {
		System.out.print("\033[H\033[2J");
		System.out.flush();

		System.out.println("CREAR ACTIVIDAD GRUPAL");
		System.out.println("--------------------------------");
		System.out.println("Introduce el nombre de la actividad");
		String nombre = NanoGym.leer.nextLine();
		System.out.println("Introduce la descripcion de la actividad");
		String descripcion = NanoGym.leer.nextLine();

		System.out.println("Introduce la fecha (MM DD)");
		int mes = NanoGym.leer.nextInt();
		int dia = NanoGym.leer.nextInt();

		LocalDate fecha = LocalDate.of(LocalDate.now().getYear(), mes, dia);

		System.out.println("Introduce la hora de inicio (HH MM)");
		int horaInicio = NanoGym.leer.nextInt();
		int minutoInicio = NanoGym.leer.nextInt();
		System.out.println("Introduce la hora de fin (HH MM)");
		int horaFin = NanoGym.leer.nextInt();
		int minutoFin = NanoGym.leer.nextInt();

		Horario h = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));

		System.out.println("Introduce el tipo de actividad grupal (EN MAYUSCULAS)");
		String ta = NanoGym.leer.nextLine();
		TipoActividad t = new TipoActividad(ta);

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("SALAS:");
		System.out.println("==================================");
		Iterator<Entry<Integer, Sala>> salasIterator = ng.getSalas().entrySet().iterator();
		while (salasIterator.hasNext()) {
			Map.Entry<Integer, Sala> set = (Map.Entry<Integer, Sala>) salasIterator.next();
			if (set.getValue().ocupada(fecha, h) == false) {
				System.out.println(set.getValue());
			}
		}

		System.out.println("=================================================================");
		System.out.println("Ingrese el id de la sala que quiere usar(numero despues de nombre)");
		System.out.println("=================================================================");
		int idPadre = Integer.parseInt(NanoGym.leer.nextLine());

		Sala sala = ng.getSalas().get(idPadre);

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("MONITORES:");
		System.out.println("==================================");
		Iterator<Entry<String, Usuario>> usuariosIterator = ng.getUsuarios().entrySet().iterator();
		while (usuariosIterator.hasNext()) {
			Map.Entry<String, Usuario> set = (Map.Entry<String, Usuario>) usuariosIterator.next();
			if (set.getValue().esMonitor() == true) {
				System.out.println(set.getValue());
			}
		}
		System.out.println("========================================================");
		System.out.println("Ingrese el nick del monitor que desea utilizar (usuario)");
		System.out.println("========================================================");
		String nick = NanoGym.leer.nextLine();

		Monitor monitor = (Monitor) ng.getUsuarios().get(nick);

		ActividadGrupal a = new ActividadGrupal(nombre, descripcion, h, fecha, sala, monitor, t);

		ng.getServicios().add(a);
	}

	/**
	 * Actividad grupal que crea el adminsitrador
	 * 
	 * @param nombre      nombre de la actividad
	 * @param descripcion descripcion de la actividad
	 * @param h           horario de la actividad
	 * @param fecha       fecha de la actividad
	 * @param sala        sala de la actividad
	 * @param moni        monitor de la actividad
	 * @param t           tipo de actividad de la actividad
	 */
	public void crearActividadGrupal(String nombre, String descripcion, Horario h, LocalDate fecha, Sala sala,
			Monitor moni, TipoActividad t) {

		ActividadGrupal a = new ActividadGrupal(nombre, descripcion, h, fecha, sala, moni, t);
		ng.getServicios().add(a);
	}

	/**
	 * Muestra los parametros del gimansio
	 */
	public void mostrarParametros() {
		String res;
		int ret = 0;
		do {
			System.out.println("PARAMETROS");
			System.out.println("--------------------------------");
			System.out.println("1. Modificar precio de tarifa plana");
			System.out.println("2. Modificar descuento tarifa plana en entrenamiento personalizado");
			System.out.println("3. Modificar descuento tarifa plana en entrenamiento libre");
			System.out.println("4. Modificar numero maximo de faltas");
			System.out.println("5. Modificar duracion de una suspension");
			System.out.println("6. Modificar precio actividad grupal");
			System.out.println("7. Modificar precio entrenamiento libre");
			System.out.println("8. Modificar sueldo base monitores");
			System.out.println("9. Modificar sueldo extra por clase de monitores");
			System.out.println("10. Modificar devolucion cancelacion cliente");
			System.out.println("11. Modificar devolucion cancelacion monitor");

			System.out.println("================================================================");
			System.out.println("(presiona un numero para modificar o enter para volver al menu)");
			System.out.println("================================================================");

			res = NanoGym.leer.nextLine();
			if (res.length() < 1) {
				break;
			}
			ret = Integer.parseInt(res);

			if (ret < 1 || ret > 11) {
				System.out.println("Numero invalido");
			}
		} while (ret < 1 || ret > 11);

		switch (ret) {
			case 0:

				break;
			case 1:
				this.modificarPrecioTarifaPlana();
				break;
			case 2:
				this.modificarDescuentoTarifaPlanaPersonalizado();
				break;
			case 3:
				this.modificarDescuentoTarifaPlanaLibre();
				break;
			case 4:
				this.modificarMaxFaltas();
				break;
			case 5:
				this.modificarDuracionSuspension();
				break;
			case 6:
				this.modificarPrecioActividadGrupal();
				break;
			case 7:
				this.modificarPrecioEntrenamientoLibre();
				break;
			case 8:
				this.modificarSueldoBaseMonitores();
				break;
			case 9:
				this.modificarSueldoExtraClaseMonitores();
				break;
			case 10:
				this.modificarDevolucionCancelacionCliente();
				break;
			case 11:
				this.modificarDevolucionCancelacionMonitor();
				break;
			default:
				System.out.println("Opción incorrecta.");
				break;
		}

		if (ret != 0) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.println("Modificado correctamente");
		}

	}

	/**
	 * Para modificar el precio de la tarifa plana por terminal
	 */
	public void modificarPrecioTarifaPlana() {
		System.out.println("Ingrese el nuevo precio de la tarifa plana");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			TarifaPlana.setPrecioBase(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el precio de la tarifa plana
	 * 
	 * @param valor nuevo valor de la tarifa plana
	 */
	public void modificarPrecioTarifaPlana(Double valor) {
		try {
			TarifaPlana.setPrecioBase(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el descuento de la tarifa plana a los entrenos personalizados
	 * por terminal
	 */
	public void modificarDescuentoTarifaPlanaPersonalizado() {
		System.out.println("Ingrese el nuevo precio de la tarifa plana");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			TarifaPlana.setDescEntPersonalizado(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el descuento de la tarifa plana a los entrenos personalizados
	 * 
	 * @param valor nuevo valor del descuento
	 */
	public void modificarDescuentoTarifaPlanaPersonalizado(Double valor) {
		try {
			TarifaPlana.setDescEntPersonalizado(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el descuento de la tarifa plana a los entrenos libres por terminal
	 */
	public void modificarDescuentoTarifaPlanaLibre() {
		System.out.println("Ingrese el nuevo precio de la tarifa plana");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			TarifaPlana.setPrecioBase(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el descuento de la tarifa plana a los entrenos libres
	 * 
	 * @param valor nuevo valor del descuento
	 */
	public void modificarDescuentoTarifaPlanaLibre(Double valor) {
		try {
			TarifaPlana.setDescEntLibre(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el numero maximo de faltas por terminal
	 */
	public void modificarMaxFaltas() {
		System.out.println("Ingrese el nuevo nuevo numero de faltas");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			Cliente.setMAX_FALTAS(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el numero maximo de faltas
	 * 
	 * @param valor nuevo valor del numero maximo de faltas
	 */
	public void modificarMaxFaltas(int valor) {
		try {
			Cliente.setMAX_FALTAS(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica la duracion de una suspension por terminal
	 */
	public void modificarDuracionSuspension() {
		System.out.println("Ingrese la nueva duracion");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			Suspension.setDiasSuspension(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica la duracion de una suspension
	 * 
	 * @param valor nuevo valor de la duracion de una suspension
	 */
	public void modificarDuracionSuspension(int valor) {
		try {
			Suspension.setDiasSuspension(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el precio de la actividad grupal por terminal
	 */
	public void modificarPrecioActividadGrupal() {
		System.out.println("Ingrese el nuevo precio de la actividad grupal");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			ActividadGrupal.setPRECIO(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el precio de la actividad grupal
	 * 
	 * @param valor nuevo valor del precio de la actividad grupal
	 */
	public void modificarPrecioActividadGrupal(int valor) {
		try {
			ActividadGrupal.setPRECIO(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el precio del entrenamiento libre por terminal
	 */
	public void modificarPrecioEntrenamientoLibre() {
		System.out.println("Ingrese el nuevo precio del entrenamiento libre");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		EntrenamientoLibre.setPRECIO(ret);

	}

	/**
	 * Modifica el precio del entrenamiento libre
	 * 
	 * @param valor nuevo valor del precio del entrenamiento libre
	 */
	public void modificarPrecioEntrenamientoLibre(Double valor) {
		EntrenamientoLibre.setPRECIO(valor);
	}

	/**
	 * Modifica el sueldo base de los monitores por terminal
	 */
	public void modificarSueldoBaseMonitores() {
		System.out.println("Ingrese el nuevo sueldo base de los monitores");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			Monitor.setNOMINA_BASE(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el sueldo base de los monitores
	 * 
	 * @param valor nuevo valor del sueldo base de los monitores
	 */
	public void modificarSueldoBaseMonitores(Double valor) {
		try {
			Monitor.setNOMINA_BASE(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el sueldo extra por clase de los monitores por terminal
	 */
	public void modificarSueldoExtraClaseMonitores() {
		System.out.println("Ingrese el nuevo sueldo extra por clase de los monitores");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		Monitor.setEXTRA_SUELDO_SESION(ret);
	}

	/**
	 * Modifica el sueldo extra por clase de los monitores
	 * 
	 * @param valor nuevo valor del sueldo extra por clase de los monitores
	 */
	public void modificarSueldoExtraClaseMonitores(Double valor) {
		Monitor.setEXTRA_SUELDO_SESION(valor);
	}

	/**
	 * Modifica el porcentaje de devolucion por cancelacion de un cliente por
	 * terminal
	 */
	public void modificarDevolucionCancelacionCliente() {
		System.out.println("Ingrese el nuevo porcentaje de devolucion por cancelacion de un cliente");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			Tarifa.setDevolucionCancelacionPorCliente(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el porcentaje de devolucion por cancelacion de un cliente
	 * 
	 * @param valor nuevo valor del porcentaje de devolucion por cancelacion de un
	 *              cliente
	 */
	public void modificarDevolucionCancelacionCliente(Double valor) {
		try {
			Tarifa.setDevolucionCancelacionPorCliente(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el porcentaje de devolucion por cancelacion de un monitor por
	 * terminal
	 */
	public void modificarDevolucionCancelacionMonitor() {
		System.out.println("Ingrese el nuevo porcentaje de devolucion por cancelacion de un monitor");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		try {
			Tarifa.setDevolucionCancelacionPorMonitor(ret);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Modifica el porcentaje de devolucion por cancelacion de un monitor
	 * 
	 * @param valor nuevo valor del porcentaje de devolucion por cancelacion de un
	 *              monitor
	 */
	public void modificarDevolucionCancelacionMonitor(Double valor) {
		try {
			Tarifa.setDevolucionCancelacionPorMonitor(valor);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Devuelve si es un cliente o no
	 * 
	 * @return false
	 */
	@Override
	public boolean esCliente() {
		return false;
	}

	/**
	 * Menu para crear sala
	 */
	public void menuCrearSala() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("Desea que la sala sea hija?");
		System.out.println("1. Si");
		System.out.println("2. No");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());
		if (ret == 1) {
			this.menuCrearSalaHija();
		} else if (ret == 2) {
			this.menuCrearSalaPadre();
		} else {
			System.out.println("Opción incorrecta.");
		}

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("Sala creada correctamente");
		System.out.println("==========================");
		System.out.println("Pulse enter para continuar");
		System.out.println("==========================");
	}

	/**
	 * Menu para crear sala padre
	 */
	private void menuCrearSalaPadre() {
		Sala s1;
		System.out.print("\033[H\033[2J");
		System.out.flush();

		System.out.println("Introduzca el nombre de la sala:");
		String nombre = NanoGym.leer.nextLine();
		System.out.println("Introduzca la descripcion de la sala:");
		String descripcion = NanoGym.leer.nextLine();
		System.out.println("Introduzca el aforo de la sala:");
		int aforo = Integer.parseInt(NanoGym.leer.nextLine());

		System.out.println("===================================");
		System.out.println("Desea que la sala este climatizada?");
		System.out.println("1. Si");
		System.out.println("2. No");
		System.out.println("===================================");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());

		if (ret == 1) {

			System.out.println("=======================================");
			System.out.println("Introduzca hora de inicio climatizacion (HH MM)");
			int horaInicio = NanoGym.leer.nextInt();
			int minutoInicio = NanoGym.leer.nextInt();
			System.out.println("Introduzca hora de fin climatizacion (HH MM)");
			int horaFin = NanoGym.leer.nextInt();
			int minutoFin = NanoGym.leer.nextInt();

			Horario h = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));

			s1 = new SalaClimatizada(h, nombre, aforo, descripcion);
		} else {
			s1 = new Sala(nombre, aforo, descripcion);
		}

		ng.getSalas().put(s1.getId(), s1);
	}

	/**
	 * Menu para crear sala hija
	 */
	public void menuCrearSalaHija() {
		Sala s1;
		System.out.print("\033[H\033[2J");
		System.out.flush();

		System.out.println("Introduzca el nombre de la sala:");
		String nombre = NanoGym.leer.nextLine();
		System.out.println("Introduzca la descripcion de la sala:");
		String descripcion = NanoGym.leer.nextLine();
		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("SALAS:");
		System.out.println("==================================");
		Iterator<Entry<Integer, Sala>> salasIterator = ng.getSalas().entrySet().iterator();
		while (salasIterator.hasNext()) {
			Map.Entry<Integer, Sala> set = (Map.Entry<Integer, Sala>) salasIterator.next();
			System.out.println(set.getValue());
		}

		System.out.println("===========================================================================");
		System.out.println("Ingrese el id de la sala que quiere que sea padre(numero despues de nombre)");
		System.out.println("===========================================================================");
		int idPadre = Integer.parseInt(NanoGym.leer.nextLine());

		Sala salaPadre = ng.getSalas().get(idPadre);

		System.out.println("Introduce aforo de la sala: (menor que " + salaPadre.aforoDisponible() + ")");
		int aforo = Integer.parseInt(NanoGym.leer.nextLine());

		System.out.println("===================================");
		System.out.println("Desea que la sala este climatizada?");
		System.out.println("1. Si");
		System.out.println("2. No");
		System.out.println("===================================");
		int ret = Integer.parseInt(NanoGym.leer.nextLine());

		if (ret == 1) {

			System.out.println("=======================================");
			System.out.println("Introduzca hora de inicio climatizacion (HH MM)");
			int horaInicio = NanoGym.leer.nextInt();
			int minutoInicio = NanoGym.leer.nextInt();
			System.out.println("Introduzca hora de fin climatizacion (HH MM)");
			int horaFin = NanoGym.leer.nextInt();
			int minutoFin = NanoGym.leer.nextInt();

			Horario h = new Horario(LocalTime.of(horaInicio, minutoInicio), LocalTime.of(horaFin, minutoFin));

			try {
				s1 = new SalaClimatizada(h, nombre, aforo, salaPadre, descripcion);
				ng.getSalas().put(s1.getId(), s1);
			} catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
				e.printStackTrace();
			}
		} else {
			try {
				s1 = new Sala(nombre, aforo, salaPadre, descripcion);
				ng.getSalas().put(s1.getId(), s1);
			} catch (SalaConAforoMaximoException | SalaNoCompatibleException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Devuelve si es monitor o no
	 * 
	 * @return false
	 */
	@Override
	public boolean esMonitor() {
		return false;
	}

	/**
	 * Metodo para registrar un monitor al sistema por parte de un administrador
	 * 
	 * @param nick       Nombre de usuario del monitor
	 * @param contraseña Contraseña del monitor
	 * @param nombre     Nombre completo del monitor
	 * @param email      Email del monitor
	 * @param nif        NIF del monitor
	 */
	public void registrarMonitor(String nick, String contraseña, String nombre, String email, String nif) {
		Monitor m = new Monitor(nick, contraseña, nombre, email, nif);

		ng.addUsuario(m);
	}

	/**
	 * Registra a un monitor por terminal
	 */
	public void registrarMonitor() {
		String nick = "", contraseña = "", nombre = "", email = "", nif = "";
		System.out.print("\033[H\033[2J");
		System.out.flush();

		/* Recogo datos del cliente */

		do {
			System.out.println("Introduce nickname: ");
			nick = NanoGym.leer.nextLine();

			if (ng.getUsuarios().containsKey(nick)) {
				System.out.println("Ya existe un usuario con ese nickname.");
			}

		} while (ng.getUsuarios().containsKey(nick));

		do {
			System.out.println("Introduce contraseña(minimo 5 caracteres): ");
			contraseña = NanoGym.leer.nextLine();

			if (contraseña.length() < 5) {
				System.out.println("Contraseña invalida. Menos de 5 caracteres.");
			}
		} while (contraseña.length() < 5);

		System.out.println("Introduce nombre completo: ");
		nombre = NanoGym.leer.nextLine();

		System.out.println("Introduce email: ");
		email = NanoGym.leer.nextLine();

		do {
			System.out.println("Introduce el NIF (9 caracteres): ");
			nif = NanoGym.leer.nextLine();

			if (nif.length() != 9) {
				System.out.println("NIF invalido.");
			}
		} while (nif.length() != 9);

		/* Añado cliente */
		Monitor m = new Monitor(nick, contraseña, nombre, email, nif);

		ng.addUsuario(m);

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.println("Monitor registrado correctamente");
		System.out.println("==========================");
		System.out.println("Pulse enter para continuar");
		System.out.println("==========================");
		NanoGym.leer.nextLine();
	}

	/**
	 * Obtiene los gastos del gym
	 * 
	 * @return gastos del gym
	 */
	public double getGastos() {
		return ng.getGastos();
	}

	/**
	 * Obtiene los ingresos del gym
	 * 
	 * @return ingresos del gym
	 */
	public double getIngresos() {
		return ng.getIngresos();
	}

	/**
	 * Obtiene el balance del gym
	 * 
	 * @return balance del gym
	 */
	public double getBalance() {
		return ng.getBalance();
	}
}
