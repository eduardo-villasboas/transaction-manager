package com.transactionmanager.account.error.handler;

import java.util.List;

/**
 * Classe modela uma resposta de erro do backend para o cliente.
 * 
 * @author eduardo
 *
 */
public class ResponseError {

	private final String errorMessage;
	private final List<String> errorDetails;

	ResponseError(final String errorMessage, List<String> errorDetails) {
		this.errorMessage = errorMessage;
		errorDetails.sort((s1, s2) -> s1.compareTo(s2));
		this.errorDetails = List.copyOf(errorDetails);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<String> getErrorDetails() {
		return errorDetails;
	}

}
