package com.transactionmanager.account.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.usecase.AccountCreator;
import com.transactionmanager.account.usecase.AccountFinder;

@RestController
@RequestMapping("/accounts")
@ResponseBody
class AccountController {

	private final AccountFinder accountFinder;
	private final AccountCreator accountCreator;

	AccountController(AccountFinder accountFinder, AccountCreator accountCreator) {
		this.accountFinder = accountFinder;
		this.accountCreator = accountCreator;
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDto> findById(@PathVariable("accountId") UUID accountId) {
		return ResponseEntity.ok(accountFinder.findById(accountId));
	}

	@PostMapping
	public ResponseEntity<String> createAccount(@RequestBody @Valid final AccountDto accountDto) {
		final UUID accountId = accountCreator.createNewAccount(accountDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(String.format("/accounts/%s", accountId));
	}

}
