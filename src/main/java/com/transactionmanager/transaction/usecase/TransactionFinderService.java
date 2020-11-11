package com.transactionmanager.transaction.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanager.account.error.exception.NotFoundEntityException;
import com.transactionmanager.transaction.entity.TransactionDto;

/**
 * Implementação do papel {@link TransactionFinder} responsável por buscar
 * transações.
 * 
 * @author eduardo
 *
 */
@Service
class TransactionFinderService implements TransactionFinder {

	private final TransactionDataAdapter transactionDataAdapter;

	TransactionFinderService(TransactionDataAdapter transactionDataAdapter) {
		this.transactionDataAdapter = transactionDataAdapter;
	}

	/**
	 * Busca uma entidade {@link TransactionDto}
	 * 
	 * @param transactionId id da transação
	 * @return uma entidade do tipo {@link TransactionDto}
	 * @throws NotFoundEntityException caso a entidade com o id especificado não
	 *                                 seja encontrada.
	 */
	@Transactional(readOnly = true)
	@Override
	public TransactionDto findById(UUID transactionId) {
		return transactionDataAdapter.findById(transactionId);
	}

}
