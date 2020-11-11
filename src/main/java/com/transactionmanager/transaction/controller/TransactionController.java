package com.transactionmanager.transaction.controller;

import java.util.UUID;
import java.util.function.Function;

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

import com.transactionmanager.account.error.handler.ResponseError;
import com.transactionmanager.commons.controller.Resource;
import com.transactionmanager.transaction.entity.TransactionDto;
import com.transactionmanager.transaction.usecase.TransactionCreator;
import com.transactionmanager.transaction.usecase.TransactionFinder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transactions")
@ResponseBody
class TransactionController {

	private final TransactionFinder transactionFinder;
	private final TransactionCreator transactionCreator;
	private static final Function<TransactionRequest, TransactionDto> MAPPER = createMapper();

	TransactionController(TransactionFinder transactionFinder, TransactionCreator transactionCreator) {
		this.transactionFinder = transactionFinder;
		this.transactionCreator = transactionCreator;
	}

	@PostMapping
	// @formatter:off
	@ApiOperation(	value = "Cria uma nova transação", 
	  				notes = "Este endpoint é utilizado para criar uma nova transação."
	)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "CREATED.", response = String.class),
			@ApiResponse(code = 404, message = "NOT_FOUND.", response = ResponseError.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST.", response = ResponseError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ResponseError.class) 
	})
	// @formatter:on
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resource> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
		final UUID transactionId = transactionCreator.createNewTransaction(MAPPER.apply(transactionRequest));
		return ResponseEntity.status(HttpStatus.CREATED).body(new Resource("/transactions/", transactionId));
	}

	@ApiIgnore
	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDto> findById(@PathVariable("transactionId") UUID transactionId) {
		return ResponseEntity.ok(transactionFinder.findById(transactionId));
	}
	
	private static Function<TransactionRequest, TransactionDto> createMapper() {
		return tr -> {
			final TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAccountId(tr.getAccountId());
			transactionDto.setAmmount(tr.getAmmount());
			transactionDto.setOperationType(tr.getOperationType());
			return transactionDto;
		};
	}

}
