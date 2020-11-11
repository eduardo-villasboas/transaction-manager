package com.transactionmanager.account.usecase;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountFinderServiceTest {

	private AccountFinderService accountFinderService;
	
	private AccountDataAdapter accountDataAdapter;
	
	@BeforeEach
	void setup() {
		accountDataAdapter = mock(AccountDataAdapter.class);
		accountFinderService = new AccountFinderService(accountDataAdapter);
	}
	
	@Test
	void testVerifyInteractionsWithDatabase() {
		
		final UUID accountId = UUID.randomUUID();
		accountFinderService.findById(accountId);
		
		verify(accountDataAdapter, times(1)).findById(eq(accountId));
		
	}
	
}
