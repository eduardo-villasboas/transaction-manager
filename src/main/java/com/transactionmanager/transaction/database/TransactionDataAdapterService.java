package com.transactionmanager.transaction.database;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.transactionmanager.transaction.entity.TransactionDto;
import com.transactionmanager.transaction.usecase.OperationType;
import com.transactionmanager.transaction.usecase.TransactionDataAdapter;

/**
 * Classe de implementação do papel {@link TransactionDataAdapter} que tem a
 * responstabilidade de acessar dados de transação.
 * 
 * @author eduardo
 *
 */
@Service
class TransactionDataAdapterService implements TransactionDataAdapter {

	private final TransactionRepository transactionRepository;

	TransactionDataAdapterService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Cria uma {@link Transaction} utilizando os dados de {@link TransactionDto} e
	 * persiste os dados no banco.
	 * 
	 * @param transactionDto dados da transação a ser persistida no banco.
	 */
	@Override
	public void createTransaction(TransactionDto transactionDto) {
		final Transaction transaction = new Transaction();
		transaction.setTransactionId(transactionDto.getTransactionId());
		transaction.setAccountId(transactionDto.getAccountId());
		transaction.setAmmount(transactionDto.getAmmount());
		transaction.setOperationType(getOperationId(transactionDto));
		transaction.setEventDate(transactionDto.getEventDate());
		transactionRepository.saveAndFlush(transaction);
	}

	private static short getOperationId(TransactionDto transactionDto) {
		return getOperationType(transactionDto).getOperationTypeId();
	}

	private static OperationType getOperationType(TransactionDto transactionDto) {
		return transactionDto.getOperationType();
	}

	/**
	 * Busca uma transação associada ao id informado.
	 * 
	 * @param transactionId id da transação que vai ser recuperada.
	 * @return a transação associada ao parâmetro transactionId.
	 * @apiNote este método só foi adicionado para que a criação dos testes de
	 *          integração não quebrasse o encapsulamento do sistema. por isso não
	 *          tem testes para esse método quando ele lança exceptions e também o
	 *          método get() do {@link Optional} não foi verificado
	 */
	@Override
	public TransactionDto findById(UUID transactionId) {
		final Transaction transaction = transactionRepository.findById(transactionId).get();
		final TransactionDto transactionDto = new TransactionDto();
		transactionDto.setTransactionId(transaction.getTransactionId());
		transactionDto.setAccountId(transaction.getAccountId());
		transactionDto.setAmmount(transaction.getAmmount());
		final short operationType = transaction.getOperationType();
		transactionDto.setOperationType(OperationType.getBy(operationType));
		transactionDto.setEventDate(transaction.getEventDate());
		return transactionDto;
	}

}
