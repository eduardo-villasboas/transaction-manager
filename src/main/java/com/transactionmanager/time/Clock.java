package com.transactionmanager.time;

import java.time.ZonedDateTime;

/**
 * interface define o papel responsável por buscar dados temporais.
 * 
 * @author eduardo
 *
 */
public interface Clock {

	/**
	 * Retorna uma instancia de {@link ZonedDateTime}
	 * 
	 * @return uma instancia da hora atual para o horário atual considerando o
	 *         timezone UTC
	 */
	ZonedDateTime getUtcZonedDateTime();

}
