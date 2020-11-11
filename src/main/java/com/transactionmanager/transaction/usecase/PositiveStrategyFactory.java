package com.transactionmanager.transaction.usecase;

import java.math.BigDecimal;

/**
 * Classe de implementação do papel {@link StrategyFactor}
 * 
 * @author eduardo
 *
 */
final class PositiveStrategyFactory implements StrategyFactor {

	private static final PositiveStrategyFactory INSTANCE = new PositiveStrategyFactory();

	private PositiveStrategyFactory() {
	}

	/**
	 * Aplica um fator de multiplicação positivo ao parâmetro informado. Ou seja,
	 * multiplica o valor passado por 1
	 * 
	 * @param ammount valor ao qual o fator deve ser aplicado.
	 * @return uma nova instância de {@link BigDecimal} com o fator aplicado. Note que 
	 * neste caso o valor é o mesmo, porém foi escolhido mesmo assim retornar uma nova instância
	 * para manter o contrato que o papel {@link StrategyFactor} define. 
	 */
	@Override
	public BigDecimal apply(BigDecimal ammount) {
		return ammount.multiply(new BigDecimal(1));
	}

	/**
	 * Cria uma instância única 
	 * 
	 * @return uma instância de {@link PositiveStrategyFactory}
	 */
	public static PositiveStrategyFactory instance() {
		return INSTANCE;
	}

}
