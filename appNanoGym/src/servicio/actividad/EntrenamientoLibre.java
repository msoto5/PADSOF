/**
 * Clase que representa un entrenamiento libre de la aplicación.
 * @file EntrenamientoLibre.java
 * @package servicio.actividad
 */

package servicio.actividad;

import java.time.LocalDate;
import utiles.Horario;
import sala.Sala;
import servicio.Servicio;

/**
 * Clase que representa un entrenamiento personalizado de la aplicación.
 * 
 * @author Miguel Soto, Nicolas Victorino y Diego González
 */
public class EntrenamientoLibre extends Actividad {

	/** Serial Version UID */
	private static final long serialVersionUID = -6425575686946636132L;

	/** Precio por 1 hora de entrenamiento libre */
	private static double PRECIO = 10.0;

	/**
	 * Constructor de la clase EntrenamientoLibre
	 * 
	 * @param nombre      Nombre del servicio de clase EntrenamientoLibre
	 * @param descripcion Descripción del servicio de clase EntrenamientoLibre
	 * @param horario     Horario en el que transcurre el EntrenamientoLibre
	 * @param fecha       Fecha en la que tiene lugar el EntrenamientoLibre
	 * @param sala        Sala en la que se realiza el EntrenamientoLibre
	 */
	public EntrenamientoLibre(String nombre, String descripcion, Horario horario, LocalDate fecha, Sala sala) {
		super(nombre, descripcion, horario, fecha, sala);
	}

	/**
	 * Establece el precio del entrenamiento libre
	 * 
	 * @param precio precio del entrenamiento libre
	 */
	public static void setPRECIO(double precio) {
		EntrenamientoLibre.PRECIO = precio;
	}

	/**
	 * Devuelve el precio del entrenamiento libre
	 * 
	 * @return precio del entrenamiento libre
	 */
	public static double getPRECIO() {
		return EntrenamientoLibre.PRECIO;
	}

	/**
	 * Devuelve el precio del entrenamiento libre
	 * 
	 * @return precio del entrenamiento libre
	 */
	@Override
	public double getPrecio() {
		return EntrenamientoLibre.getPRECIO();
	}

	/**
	 * Devuelve un string con la información del entrenaminto libre
	 * 
	 * @return string con la información del entrenaminto libre
	 */
	@Override
	public String toString() {
		return "Entrenamiento libre: " + super.toString()
				+ "\n -Precio:" + EntrenamientoLibre.PRECIO;
	}

	/**
	 * Devuelve si hay información grupal del entrenamiento libre
	 * 
	 * @return false
	 */
	@Override
	public boolean imprimirInfoGrupal() {
		return false;
	}

	/**
	 * Devuelve el String de busqueda del entrenamiento libre
	 * 
	 * @return String de busqueda del entrenamiento libre
	 */
	@Override
	public String getBusquedaString() {
		return "Entrenamiento libre " + getFecha() + " " + getHorario();
	}

	/**
	 * Compara el Ent.Libre con un servicio
	 * 
	 * @param serv servicio a comparar
	 * @return 0 si son iguales, -1 si el Ent.Libre es menor, 1 si el Ent.Libre es
	 *         mayor
	 */
	@Override
	public int compareTo(Servicio serv) {
		int ret = super.compareTo(serv);
		if (ret == 0 && !(serv instanceof EntrenamientoLibre)) {
			ret = -1; // Ent libre < Act Monitor < Ent Pers
		} else if (ret == 0) {
			ret = 0;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && obj instanceof EntrenamientoLibre;
	}
}
