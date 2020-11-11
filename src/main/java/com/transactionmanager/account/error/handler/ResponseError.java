package com.transactionmanager.account.error.handler;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe modela uma resposta de erro do backend para o cliente.
 * 
 * @author eduardo
 *
 */
@ApiModel(description = "Resposta de erro.")
public class ResponseError {

	@ApiModelProperty(value = "Descrição geral do erro.", example = "Error when validate data.")
	private final String errorMessage;

	@ApiModelProperty(value = "Lista com as descrições detalhadas dos errors ou uma lista "
			+ "vazia no caso de não existir erros detalhados", example = "[id cannot be null, document_number cannot be null]")
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
