package com.transactionmanager.account.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.transactionmanager.account.entity.AccountDto;

class AccountCreatorServiceTest {

	private static final Long DOCUMENT_NUMBER = 88779003L;
	private AccountCreatorService accountCreatorService;
	private AccountDataAdapter accountDataAdapter;

	@BeforeEach
	void setup() {

		accountDataAdapter = mock(AccountDataAdapter.class);
		accountCreatorService = new AccountCreatorService(accountDataAdapter);

	}

	@Test
	void testVerifyInteractionsWithDatabase() {
		final AccountDto accountDtoToSave = createAccountDto();

		final UUID accountID = accountCreatorService.createNewAccount(accountDtoToSave);

		verify(accountDataAdapter, times(1)).createAccount(same(accountDtoToSave));
		assertThat(accountDtoToSave.getAccountId()).as("UUID should not be null").isNotNull()
				.as("Id should be equals entity Id").isEqualTo(accountID);
	}

	private AccountDto createAccountDto() {
		final AccountDto accountDto = new AccountDto();
		accountDto.setDocumentNumber(DOCUMENT_NUMBER);
		return accountDto;
	}

}
