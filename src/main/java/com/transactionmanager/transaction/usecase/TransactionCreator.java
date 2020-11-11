package com.transactionmanager.transaction.usecase;

import java.util.UUID;

import com.transactionmanager.transaction.entity.TransactionDto;

/**
 * Inteface que define o papel responsável por criar novas transações.
 * 
 * @author eduardo
 *
 */
public interface TransactionCreator {

	/**
	 * Cria uma nova transação. 
	 * @param transactionDto dados da nova transação.
	 * @return o id da nova transação.
	 */
	UUID createNewTransaction(TransactionDto transactionDto);

}
