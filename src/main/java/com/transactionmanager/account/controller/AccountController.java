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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transactionmanager.account.entity.AccountDto;
import com.transactionmanager.account.error.handler.ResponseError;
import com.transactionmanager.account.usecase.AccountCreator;
import com.transactionmanager.account.usecase.AccountFinder;
import com.transactionmanager.commons.controller.Resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	// @formatter:off
	@ApiOperation(	value = "Retorna uma conta associada ao id informado", 
	  				notes = "Este endpoint é utilizado para buscar uma conta única associada a um id único"
	)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK.", response = AccountDto.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST.", response = ResponseError.class),
			@ApiResponse(code = 404, message = "NOT_FOUND.", response = ResponseError.class),
			@ApiResponse(code = 500, message = "Internal Server Error.", response = ResponseError.class) 
	})
	// @formatter:on
	public ResponseEntity<AccountDto> findById(
			@PathVariable("accountId") @ApiParam(value = "id da conta") UUID accountId) {
		return ResponseEntity.ok(accountFinder.findById(accountId));
	}

	@PostMapping
	// @formatter:off
	@ApiOperation(	value = "Cria uma nova conta", 
	  				notes = "Este endpoint é utilizado para criar uma nova conta utilizando os parametros infomados."
	)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "CREATED.", response = Resource.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST.", response = ResponseError.class),
			@ApiResponse(code = 404, message = "NOT_FOUND.", response = ResponseError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseError.class) 
	})
	// @formatter:on
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resource> createAccount(@RequestBody @Valid final AccountRequest accountRequest) {
		final UUID accountId = accountCreator.createNewAccount(new AccountDto(accountRequest.getDocumentNumber()));
		return ResponseEntity.status(HttpStatus.CREATED).body(new Resource("/accounts/", accountId));
	}

}
