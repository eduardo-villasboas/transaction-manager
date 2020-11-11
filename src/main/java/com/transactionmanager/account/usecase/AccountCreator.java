package com.transactionmanager.account.usecase;

import java.util.UUID;

import com.transactionmanager.account.entity.AccountDto;

/**
 * Inteface que define o papel de criador de contas.
 * 
 * @author eduardo
 *
 */
public interface AccountCreator {

	/**
	 * Cria uma nova conta.
	 * 
	 * @param accountDto dados da conta
	 * @return o id da nova conta.
	 */
	UUID createNewAccount(AccountDto accountDto);

}
