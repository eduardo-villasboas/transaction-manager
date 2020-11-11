package com.transactionmanager.account.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.error.exception.NotFoundEntityException;

class AccountDataAdapterServiceTest {

	private AccountDataAdapterService accountDataAdapterService;

	private AccountRepository accountRepository;

	private static final UUID ACCOUNT_ID = UUID.randomUUID();

	private static final Long DOCUMENT_NUMBER = 54313240L;

	@BeforeEach
	void setup() {
		accountRepository = mock(AccountRepository.class);
		accountDataAdapterService = new AccountDataAdapterService(accountRepository);
	}

	@Test
	void testWhenFindByIdThenFindAccountAndMapToAccountDto() {
		when(accountRepository.findById(ACCOUNT_ID))
				.thenReturn(Optional.of(createAccount(ACCOUNT_ID, DOCUMENT_NUMBER)));

		final AccountDto accountDto = accountDataAdapterService.findById(ACCOUNT_ID);

		verify(accountRepository, times(1)).findById(eq(ACCOUNT_ID));
		assertThat(accountDto).as("AccountDto should not be null").isNotNull()
				.as("AccountDto Should be equals expected")
				.isEqualTo(createAccountDto(ACCOUNT_ID, DOCUMENT_NUMBER));
	}

	@Test
	void testWhenFindByIdReturnsThenThrowsAnException() {
		when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

		final NotFoundEntityException assertThrows = assertThrows(NotFoundEntityException.class,
				() -> accountDataAdapterService.findById(ACCOUNT_ID));

		assertThat(assertThrows.getMessage()).as("Icorrect exception description")
				.isEqualTo(String.format("Error. Account not found for id %s", ACCOUNT_ID));
	}
	
	@Test
	void testWhenSaveThenCreateAccountFromDtoAndSaveUsingRepositoy() {
		
		accountDataAdapterService.createAccount(createAccountDto(ACCOUNT_ID, DOCUMENT_NUMBER));
		
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		verify(accountRepository, times(1)).save(accountCaptor.capture());
		
		final Account savedAccount = accountCaptor.getValue();
		assertThat(savedAccount.getAccountId()).as("Invalid value for accountId").isEqualTo(ACCOUNT_ID);
		assertThat(savedAccount.getDocumentNumber()).as("Invalid value for document number").isEqualTo(DOCUMENT_NUMBER);
	}

	private AccountDto createAccountDto(UUID accountId, Long documentNumber) {
		final AccountDto accountDto = new AccountDto();
		accountDto.setAccountId(accountId);
		accountDto.setDocumentNumber(documentNumber);
		return accountDto;
	}

	private Account createAccount(UUID accountId, Long documentNumber) {
		final Account accounty = new Account();
		accounty.setAccountId(accountId);
		accounty.setDocumentNumber(documentNumber);
		return accounty;
	}

}
