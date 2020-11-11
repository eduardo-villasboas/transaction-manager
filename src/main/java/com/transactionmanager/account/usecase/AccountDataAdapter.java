package com.transactionmanager.account.usecase;

import java.util.UUID;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.error.exception.NotFoundEntityException;

/**
 * Inteface que define o papel responsável acessar os dados de transações. Esta
 * interface também é responsável por inverter a depedência do acesso a dados .
 * De modo que as regras de negócio não dependam da organização e relacionamento
 * de tabelas.
 * 
 * @author eduardo
 *
 */
public interface AccountDataAdapter {

	/**
	 * Busca uma conta utilizando o id.
	 * 
	 * @param accountId id da conta que vai ser recuperada.
	 * @return a conta associada ao parâmetro accountId
	 * @throws NotFoundEntityException caso a entidate não seja encontrada.
	 */
	AccountDto findById(UUID accountId);

	/**
	 * Cria uma nova conta
	 * 
	 * @param accountDto dados da conta a ser criada.
	 * 
	 */
	void createAccount(AccountDto accountDto);

	/**
	 * Verifica se uma conta existe na base de dados.
	 * 
	 * @param accountId id da conta a ser buscada.
	 * @return true se a conta existe e false caso ela não exista.
	 */
	boolean exists(UUID accountId);

}
