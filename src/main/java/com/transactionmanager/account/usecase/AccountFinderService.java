package com.transactionmanager.account.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.error.exception.NotFoundEntityException;

/**
 * Implementação do papel de buscador de contas.
 * 
 * @author eduardo
 *
 */
@Service
class AccountFinderService implements AccountFinder {

	private final AccountDataAdapter accountDataAdapter;

	AccountFinderService(AccountDataAdapter accountDataAdapter) {
		this.accountDataAdapter = accountDataAdapter;
	}

	/**
	 * Busca uma entidade {@link AccountDto}
	 * 
	 * @param accountId id da conta
	 * @return uma entidade do tipo {@link AccountDto}
	 * @throws NotFoundEntityException caso a entidade com o id especificado não seja encontrada.
	 * 
	 */
	@Transactional(readOnly = true)
	@Override
	public AccountDto findById(UUID accountId) {
		return accountDataAdapter.findById(accountId);
	}

}
