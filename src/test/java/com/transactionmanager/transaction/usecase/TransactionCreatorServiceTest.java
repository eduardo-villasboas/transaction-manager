package com.transactionmanager.transaction.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.transactionmanager.account.error.exception.DataIntegrityViolationException;
import com.transactionmanager.account.usecase.AccountDataAdapter;
import com.transactionmanager.time.Clock;
import com.transactionmanager.transaction.entity.TransactionDto;

class TransactionCreatorServiceTest {

	private static final ZonedDateTime CURRENT_TIME = ZonedDateTime.now(ZoneId.systemDefault());

	private TransactionCreator transactionCreator;

	private TransactionDataAdapter transactionDataAdapter;

	private AccountDataAdapter accountDataAdapter;

	private Clock clock;

	@BeforeEach
	void setup() {
		transactionDataAdapter = mock(TransactionDataAdapter.class);
		accountDataAdapter = mock(AccountDataAdapter.class);
		clock = mock(Clock.class);
		when(clock.getUtcZonedDateTime()).thenReturn(CURRENT_TIME);

		transactionCreator = new TransactionCreatorService(transactionDataAdapter, accountDataAdapter, clock);
	}

	@Test
	void testWhenCreateNewANewTransactionWithCompraThenSaveATransactionWithNegativeAmmount() {
		final TransactionDto transactionDto = new TransactionDto();
		transactionDto.setOperationType(OperationType.COMPRA_A_VISTA);
		transactionDto.setAccountId(UUID.randomUUID());
		transactionDto.setAmmount(new BigDecimal("78.54"));
		when(accountDataAdapter.exists(any())).thenReturn(true);

		final UUID transactionId = transactionCreator.createNewTransaction(transactionDto);

		assertThat(transactionDto.getTransactionId()).as("TransactionId should be not null").isNotNull()
				.as("TransactionId should be equals entity Id").isEqualTo(transactionId);
		assertThat(transactionDto.getAmmount()).as("Ammount should be negative for a compra.")
				.isEqualTo(new BigDecimal("-78.54"));
		assertThat(transactionDto.getEventDate()).as("Event date shoud be correctly configured")
				.isEqualTo(CURRENT_TIME);
		verify(transactionDataAdapter, times(1)).createTransaction(transactionDto);
	}

	@Test
	void testWhenAccountNotExistsInDatabaseThenThrowsAnException() {
		final TransactionDto transactionDto = new TransactionDto();
		final UUID accountId = UUID.randomUUID();
		transactionDto.setAccountId(accountId);
		when(accountDataAdapter.exists(accountId)).thenReturn(false);

		final DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
				() -> transactionCreator.createNewTransaction(transactionDto), "An exception should be throw.");

		assertThat(exception.getMessage()).as("Invalid message on exception").isEqualTo(
				String.format("Error acccount with id %s not found. Transaction wasn't created.", accountId));

	}

}
