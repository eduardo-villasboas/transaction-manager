package com.transactionmanager.transaction.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

class OperationTypeDeserializeTest {

	private OperationTypeDeserialize operationTypeDeserialize;

	@BeforeEach
	void setup() {
		operationTypeDeserialize = new OperationTypeDeserialize();
	}

	@Test
	void testWhenDeserializeThenDeserializeBasedOnOperationTypeId() throws JsonProcessingException, IOException {
		final JsonParser p = mock(JsonParser.class);
		final DeserializationContext ctxtDummy = null;
		when(p.currentToken()).thenReturn(JsonToken.VALUE_NUMBER_INT);
		when(p.getIntValue()).thenReturn(1);

		final OperationType operationTypeOfCompra = operationTypeDeserialize.deserialize(p, ctxtDummy);

		assertThat(operationTypeOfCompra).as("Should be OperationType.COMPRA").isSameAs(OperationType.COMPRA_A_VISTA);

		when(p.getIntValue()).thenReturn(2);

		final OperationType operationTypeOfCompraParcelada = operationTypeDeserialize.deserialize(p, ctxtDummy);

		assertThat(operationTypeOfCompraParcelada).as("Should be OperationType.COMPRA_PARCELADA")
				.isSameAs(OperationType.COMPRA_PARCELADA);
	}

	@Test
	void testWhenDeserializeThenThrowsAnExcetionIfOperationIdIsOutOfRange() throws IOException {

		final JsonParser p = mock(JsonParser.class);
		final DeserializationContext ctxtDummy = null;
		when(p.currentToken()).thenReturn(JsonToken.VALUE_NUMBER_INT);
		when(p.getIntValue()).thenReturn(99);

		final IllegalStateException exception = assertThrows(IllegalStateException.class,
				() -> operationTypeDeserialize.deserialize(p, ctxtDummy), "Should throws an exception");

		assertThat(exception.getMessage()).as("Expected message is wrong.")
				.isEqualTo("Error when deserialize OperationType by operationTypeId for id equals 99");
	}
	
	@Test
	void testWhenDeserializeThenReturnsNullBecaseTokenIsDiferentOfInteger() throws JsonProcessingException, IOException {
		
		final JsonParser p = mock(JsonParser.class);
		final DeserializationContext ctxtDummy = null;
		when(p.currentToken()).thenReturn(JsonToken.VALUE_NULL);

		assertThat(operationTypeDeserialize.deserialize(p, ctxtDummy)).as("Should returns null").isNull();
		
	}

}
