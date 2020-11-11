package com.transactionmanager.account.database;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.error.exception.NotFoundEntityException;
import com.transactionmanager.account.usecase.AccountDataAdapter;

/**
 * classe de implementação do papel {@link AccountDataAdapter} 
 * que tem a responstabilidade de acessar dados de contas.
 * @author eduardo
 *
 */
@Service
class AccountDataAdapterService implements AccountDataAdapter {

	private final AccountRepository accountRepository;

	public AccountDataAdapterService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * Busca uma entidade {@link Accounty} e mapeia para uma entidade {@link AccountDto}
	 * 
	 * @param accountId id da conta
	 * @return uma entidade do tipo {@link AccountDto}
	 * @throws NotFoundEntityException caso a entidade com o id especificado não seja encontrada.
	 *  
	 */
	@Override
	public AccountDto findById(UUID accountId) {
		final Optional<Account> optinalAccount = accountRepository.findById(accountId);
		final AccountDto accountDto = new AccountDto();
		final Account accounty = optinalAccount.orElseThrow(
				() -> new NotFoundEntityException(String.format("Error. Account not found for id %s", accountId)));
		accountDto.setAccountId(accounty.getAccountId());
		accountDto.setDocumentNumber(accounty.getDocumentNumber());
		return accountDto;
	}

	/**
	 * Cria e salva uma nova conta na base de dados.
	 * 
	 * @param accountDto dados da conta que será persistida.
	 */
	@Override
	public void createAccount(AccountDto accountDto) {
		Account account = new Account(accountDto);
		accountRepository.save(account);
	}

	/**
	 * 
	 * Verifica se uma conta existe na base de dados utilizando o seu id.
	 * 
	 * @param accountId id da conta
	 * @return true se a conta existe e false caso ela não exista.
	 * 
	 */
	@Override
	public boolean exists(UUID accountId) {
		return accountRepository.existsById(accountId);
	}

}
