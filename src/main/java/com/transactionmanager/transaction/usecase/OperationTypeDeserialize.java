package com.transactionmanager.transaction.usecase;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Classe responsável pela deserialização do tipo {@link OperationType}
 * 
 * @author eduardo
 *
 */
class OperationTypeDeserialize extends JsonDeserializer<OperationType> {

	/**
	 * Deserializa o operationType baseado no operationId dentro do
	 * {@link JsonParser}
	 * 
	 * @return operationType para o operationId
	 */
	@Override
	public OperationType deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		if (p.currentToken() == JsonToken.VALUE_NUMBER_INT) {
			final short operationTypeId = (short)p.getIntValue();
			if (OperationType.existThis(operationTypeId)) {
				return OperationType.getBy(operationTypeId);
			}
			throw new IllegalStateException(String.format(
					"Error when deserialize OperationType by operationTypeId for id equals %d", operationTypeId));
		}

		return null;
	}

}
