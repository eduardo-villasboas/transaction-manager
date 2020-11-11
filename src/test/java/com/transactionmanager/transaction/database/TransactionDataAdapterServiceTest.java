package com.transactionmanager.transaction.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.transactionmanager.transaction.entity.TransactionDto;
import com.transactionmanager.transaction.usecase.OperationType;

class TransactionDataAdapterServiceTest {

	private static final ZonedDateTime CURRENT_TIME = ZonedDateTime.now(ZoneId.systemDefault());
	private static final UUID TRANSACTION_ID = UUID.randomUUID();
	private static final UUID ACCOUNT_ID = UUID.randomUUID();
	private TransactionDataAdapterService transactionDataAdapterService;
	private TransactionRepository transactionRepository;

	@BeforeEach
	void setup() {
		transactionRepository = mock(TransactionRepository.class);
		transactionDataAdapterService = new TransactionDataAdapterService(transactionRepository);
	}

	@Test
	void testWhenThenSaveThenCreateAnTransactionFromDtoAndSaveUsingRepository() {

		final TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionId(UUID.randomUUID());
		transactionDto.setAccountId(UUID.randomUUID());
		transactionDto.setAmmount(new BigDecimal("-34.11"));
		transactionDto.setOperationType(OperationType.COMPRA_A_VISTA);
		transactionDto.setEventDate(CURRENT_TIME);

		transactionDataAdapterService.createTransaction(transactionDto);

		ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
		verify(transactionRepository, times(1)).saveAndFlush(transactionCaptor.capture());
		final Transaction savedTransaction = transactionCaptor.getValue();
		assertThat(savedTransaction.getTransactionId()).as("Transaction id is invalid")
				.isEqualTo(transactionDto.getTransactionId());
		assertThat(savedTransaction.getAccountId()).as("account was not mapped")
				.isEqualTo(transactionDto.getAccountId());
		assertThat(savedTransaction.getAmmount()).as("ammount was not mapped").isEqualTo(transactionDto.getAmmount());
		assertThat(savedTransaction.getOperationType()).as("type was not mapped")
				.isEqualTo(transactionDto.getOperationType().getOperationTypeId());
		assertThat(savedTransaction.getEventDate()).as("EventDate is invalid").isEqualTo(transactionDto.getEventDate());
	}

	@Test
	void testWhenFindByIdThenFindTransactionAndMapToTransactionDto() {
		final Transaction transaction = createTransaction();
		when(transactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(transaction));

		final TransactionDto transactionDto = transactionDataAdapterService.findById(TRANSACTION_ID);

		verify(transactionRepository, times(1)).findById(eq(TRANSACTION_ID));
		assertThat(transactionDto.getTransactionId()).as("transaction id was not mapped")
				.isEqualTo(transaction.getTransactionId());
		assertThat(transactionDto.getAccountId()).as("account was not mapped").isEqualTo(transaction.getAccountId());
		assertThat(transactionDto.getAmmount()).as("Ammount was not mapped").isEqualTo(transaction.getAmmount());
		assertThat(transactionDto.getOperationType()).as("operationType was not mapped")
				.isSameAs(OperationType.COMPRA_A_VISTA);
		assertThat(transactionDto.getEventDate()).as("eventDate was not mapped").isEqualTo(CURRENT_TIME);
	}

	private Transaction createTransaction() {
		final Transaction transaction = new Transaction();
		transaction.setTransactionId(TRANSACTION_ID);
		transaction.setAccountId(ACCOUNT_ID);
		transaction.setAmmount(new BigDecimal("-4561.11"));
		transaction.setOperationType((short) 1);
		transaction.setEventDate(CURRENT_TIME);
		return transaction;
	}

}
