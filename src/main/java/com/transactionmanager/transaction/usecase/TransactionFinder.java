package com.transactionmanager.transaction.usecase;

import java.util.UUID;

import com.transactionmanager.transaction.entity.TransactionDto;

/**
 * Inteface que define o papel responsável por buscar transações.
 * 
 * @author eduardo
 *
 */
public interface TransactionFinder {

	/**
	 * Busca uma transação associada ao id informado.
	 * 
	 * @param transactionId id da transação a ser recuperada.
	 * @return transação associada ao id informado.
	 * 
	 */
	TransactionDto findById(UUID transactionId);

}
