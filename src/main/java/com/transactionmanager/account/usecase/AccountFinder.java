package com.transactionmanager.account.usecase;

import java.util.UUID;

import com.transactionmanager.account.entity.AccountDto;

/**
 * Inteface que define o papel responsável por buscar contas.
 * 
 * @author eduardo
 *
 */
public interface AccountFinder {

	/**
	 * Busca uma conta associada ao id informado.
	 * 
	 * @param accountId id da conta a ser recuperada.
	 * @return conta associada ao id.
	 * 
	 */
	AccountDto findById(UUID accountId);

}
