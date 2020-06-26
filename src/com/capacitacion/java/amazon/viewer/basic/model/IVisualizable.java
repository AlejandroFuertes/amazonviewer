package com.capacitacion.java.amazon.viewer.basic.model;

import java.util.Date;

/**
 * <h1>IVisualizable</h1>
 */
public interface IVisualizable {

	/**
	 * Este metodo captura el tiempo exacto de inicio de visualizacion.
	 * 
	 * @param dataI Es un objeto de tipo Date con el tiempo de inicio exacto.
	 * @return Devuelve la fecha y hora capturada
	 */
	Date startToSee(Date dateI);

	/**
	 * Este metodo captura el tiempo exacto de inicio y final de visualizacion.
	 * 
	 * @param dataI Es un objeto de tipo Date con el tiempo de inicio exacto
	 * @param dataF Es un objeto de tipo Date con el tiempo de fin exacto
	 * 
	 */
	void stopToSee(Date dateI, Date dateF);

}
