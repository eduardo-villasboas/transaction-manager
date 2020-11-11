package com.transactionmanager.transaction.usecase;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Classe responsável pela serialização do tipo {@link OperationType}
 * 
 * @author eduardo
 *
 */
class OperationTypeSerialize extends JsonSerializer<OperationType> {

	/**
	 * Serializa o operationType utilizando o seu operationTypeId
	 * {@link JsonParser}
	 * 
	 */
	@Override
	public void serialize(OperationType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.getOperationTypeId());
	}

}
