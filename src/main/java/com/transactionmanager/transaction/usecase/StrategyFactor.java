package com.transactionmanager.transaction.usecase;

import java.math.BigDecimal;

/**
 * Inteface que define o papel de aplicação de fatores em uma instancia de
 * {@link BigDecimal}.
 * 
 * @author eduardo
 *
 */
public interface StrategyFactor {

	/**
	 * Aplica um fator de multiplicação negativo ao parâmetro informado.
	 * 
	 * @param ammount valor ao qual o fator deve ser aplicado.
	 * @return uma nova instância de {@link BigDecimal} com o fator aplicado.
	 */
	BigDecimal apply(BigDecimal ammount);

}
