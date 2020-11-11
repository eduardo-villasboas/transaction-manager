package com.transactionmanager.time;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

/**
 * Implementação simples do papel {@link Clock}.
 * 
 * @author eduardo
 *
 */
@Service
public class ClockService implements Clock {

	/**
	 * Retorna uma instancia de {@link ZonedDateTime}
	 * 
	 * @return uma instancia da hora atual para o horário atual considerando o
	 *         timezone UTC
	 */
	@Override
	public ZonedDateTime getUtcZonedDateTime() {
		return ZonedDateTime.now(ZoneOffset.UTC);
	}

}
