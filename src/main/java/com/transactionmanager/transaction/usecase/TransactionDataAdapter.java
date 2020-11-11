package com.transactionmanager.transaction.usecase;

import java.util.UUID;

import com.transactionmanager.account.error.exception.NotFoundEntityException;
import com.transactionmanager.transaction.entity.TransactionDto;

/**
 * Inteface que define o papel responsável acessar os dados de transações. Esta
 * interface também é responsável por inverter a depedência do acesso a dados.
 * De modo que as regras de negócio não dependam da organização e relacionamento
 * de tabelas.
 * 
 * @author eduardo
 *
 */
public interface TransactionDataAdapter {

	
	/**
	 * Cria uma nova transação
	 * 
	 * @param transactionDto dados da transação a ser criada.
	 * 
	 */
	void createTransaction(TransactionDto transactionDto);

	
	/**
	 * Busca uma transação utilizando o id.
	 * 
	 * @param transactionId id da transacao que vai ser recuperada.
	 * @return a transação associada ao parâmetro transactionId
	 * @throws NotFoundEntityException caso a entidate não seja encontrada.
	 */
	/* TODO: Lançar exception e criar teste.
	 */
	TransactionDto findById(UUID transactionId);

}
