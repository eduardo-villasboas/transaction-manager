package com.transactionmanager.transaction.usecase;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanager.account.error.exception.DataIntegrityViolationException;
import com.transactionmanager.account.usecase.AccountDataAdapter;
import com.transactionmanager.time.Clock;
import com.transactionmanager.transaction.entity.TransactionDto;


/**
 * classe de implementação do papel {@link TransactionCreator} 
 * que tem a responstabilidade criar novas transações.
 * @author eduardo
 *
 */
@Service
class TransactionCreatorService implements TransactionCreator {

	private final TransactionDataAdapter transactionDataAdapter;
	private final AccountDataAdapter accountDataAdapter;
	private final Clock clock;

	public TransactionCreatorService(TransactionDataAdapter transactionDataAdapter,
			AccountDataAdapter accountDataAdapter, Clock clock) {
		this.transactionDataAdapter = transactionDataAdapter;
		this.accountDataAdapter = accountDataAdapter;
		this.clock = clock;
	}

	/**
	 * Cria uma nova transação com o valor baseado no tipo de transação. Para
	 * transações de compra e saque os valores são negativos e para transações de
	 * pagamentos os valores são positivos.
	 * 
	 * @param transactionDto dados da transação a ser criada.
	 * @return id da transação criada.
	 * 
	 */
	@Transactional
	@Override
	public UUID createNewTransaction(TransactionDto transactionDto) {
		throwAnExceptionIfAccountNotExists(transactionDto.getAccountId());
		final UUID transactionId = UUID.randomUUID();
		transactionDto.setTransactionId(transactionId);
		final OperationType operationType = transactionDto.getOperationType();
		final BigDecimal newAmmount = operationType.applyStrategyFactor(transactionDto.getAmmount());
		transactionDto.setAmmount(newAmmount);
		transactionDto.setEventDate(clock.getUtcZonedDateTime());
		transactionDataAdapter.createTransaction(transactionDto);
		return transactionId;
	}

	private void throwAnExceptionIfAccountNotExists(UUID accountId) {
		if (!accountDataAdapter.exists(accountId)) {
			throw new DataIntegrityViolationException(
					String.format("Error acccount with id %s not found. Transaction wasn't created.", accountId));
		}
	}

}
