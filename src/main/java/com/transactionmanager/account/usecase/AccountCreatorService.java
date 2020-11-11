package com.transactionmanager.account.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanager.account.entity.AccountDto;

/**
 * Implementação de um criador de contas {@link AccountCreator}
 * 
 * @author eduardo
 *
 */
@Service
class AccountCreatorService implements AccountCreator {

	private final AccountDataAdapter accountDataAdapter;

	public AccountCreatorService(AccountDataAdapter accountDataAdapter) {
		this.accountDataAdapter = accountDataAdapter;
	}

	/**
	 * Gera um uuid seta no accountDto, salva o accountDto no banco e retorna a mesma 
	 * instância
	 * 
	 * @param accountDto um accountDto para ser persistido no banco de dados.
	 * @return id da conta criada.
	 */
	@Transactional
	@Override
	public UUID createNewAccount(final AccountDto accountDto) {
		final UUID accountId = UUID.randomUUID();
		accountDto.setAccountId(accountId);
		accountDataAdapter.createAccount(accountDto);
		return accountId;
	}

}
