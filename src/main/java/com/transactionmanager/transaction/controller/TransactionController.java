package com.transactionmanager.transaction.controller;

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

import com.transactionmanager.transaction.entity.TransactionDto;
import com.transactionmanager.transaction.usecase.TransactionCreator;
import com.transactionmanager.transaction.usecase.TransactionFinder;

@RestController
@RequestMapping("/transactions")
@ResponseBody
class TransactionController {

	private final TransactionFinder transactionFinder;
	private final TransactionCreator transactionCreator;

	TransactionController(TransactionFinder transactionFinder, TransactionCreator transactionCreator) {
		this.transactionFinder = transactionFinder;
		this.transactionCreator = transactionCreator;
	}

	@PostMapping
	public ResponseEntity<String> createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
		final UUID transactionId = transactionCreator.createNewTransaction(transactionDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(String.format("/transactions/%s", transactionId));
	}

	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionDto> findById(@PathVariable("transactionId") UUID transactionId) {
		return ResponseEntity.ok(transactionFinder.findById(transactionId));
	}
}
