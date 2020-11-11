package com.transactionmanager.transaction.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionFinderServiceTest {

	private TransactionFinderService transactionFinderService;
	private TransactionDataAdapter transactionDataAdapter;
	
	@BeforeEach
	void setup() {
		transactionDataAdapter = mock(TransactionDataAdapter.class);
		transactionFinderService = new TransactionFinderService(transactionDataAdapter);
	}
	
	@Test
	void testVerifyInteractionWithDatabase() {
		final UUID transactionId = UUID.randomUUID();
		
		transactionFinderService.findById(transactionId);
		
		verify(transactionDataAdapter, times(1)).findById(transactionId);
	}

}
