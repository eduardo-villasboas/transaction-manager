package com.transactionmanager.transaction.usecase;

import java.math.BigDecimal;
/**
 * Classe de implementação do papel {@link StrategyFactor}
 * 
 * @author eduardo
 *
 */
final class NegativeStrategyFactory implements StrategyFactor {

	private static NegativeStrategyFactory INSTANCE = new NegativeStrategyFactory();
	
	private NegativeStrategyFactory() {
	}
	
	/**
	 * Aplica um fator de multiplicação negativo ao parâmetro informado. Ou seja,
	 * multiplica o valor passado por -1
	 * 
	 * @param ammount valor ao qual o fator deve ser aplicado.
	 * @return uma nova instância de {@link BigDecimal} com o fator aplicado. 
	 */
	@Override
	public BigDecimal apply(BigDecimal ammount) {
		return ammount.multiply(new BigDecimal(-1));
	}

	/**
	 * Cria uma instância única 
	 * 
	 * @return uma instância de {@link NegativeStrategyFactory}
	 */
	public static NegativeStrategyFactory instance() {
		return INSTANCE;
	}

}
