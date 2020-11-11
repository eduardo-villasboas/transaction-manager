package com.transactionmanager.transaction.usecase;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Enumeração com todos os tipos de operaçãoes relacionados a 
 * transações.
 * 
 * @author eduardo
 *
 */
@JsonDeserialize(using = OperationTypeDeserialize.class)
@JsonSerialize(using = OperationTypeSerialize.class)
public enum OperationType {

	COMPRA_A_VISTA((short) 1, NegativeStrategyFactory.instance()), 
	COMPRA_PARCELADA((short) 2, NegativeStrategyFactory.instance()), 
	SAQUE((short)3, NegativeStrategyFactory.instance()), 
	PAGAMENTO((short)4, PositiveStrategyFactory.instance());

	private final short operationTypeId;
	private final StrategyFactor strategyFactor;
	
	/**
	 * {@link Map} que guarda os códigos associados 
	 * as contantes do enum 
	 * 
	 */
	private static final Map<Short, OperationType> operationsMap;
	static {
		operationsMap = Arrays.asList(OperationType.values()).stream()
				.collect(toMap(operationType -> operationType.getOperationTypeId(), operationType -> operationType));
	}
	
	/**
	 * Construtor para a enumeração do tipo {@link OperationType}
	 * 
	 * @param operationTypeId id associado ao tipo de operação.
	 * @param strategyFactor estratégia de aplicação de fator a valores do tipo
	 * {@link BigDecimal} 
	 * @see StrategyFactor
	 */
	OperationType(short operationTypeId, StrategyFactor strategyFactor) {
		this.operationTypeId = operationTypeId;
		this.strategyFactor = strategyFactor;

	}

	public short getOperationTypeId() {
		return operationTypeId;
	}

	/**
	 * Utiliza a um {@link StrategyFactor} para aplicar um fator ao valor informado
	 * @param ammount valor ao qual deve ser aplicado o fator.
	 * @return uma nova instância de {@link BigDecimal} com o fator aplicado.
	 */
	BigDecimal applyStrategyFactor(BigDecimal ammount) {
		return strategyFactor.apply(ammount);
	}

	/**
	 * Verifica se existe alguma constante com o operationType informado.
	 * 
	 * @param operationType tipo da operação.
	 * @return true existir alguma constante que possui o operationType informado ou false
	 * no caso contrário.
	 */
	public static boolean existThis(short operationType) {
		return operationsMap.containsKey(operationType);
	}

	/**
	 * Retorna a constante associada ao operationType informado.
	 * 
	 * @param operationType tipo da operação.
	 * @return A constante associda ao tipo de operação.
	 */
	public static OperationType getBy(short operationType) {
		return operationsMap.get(operationType);
	}
	
}
