package com.transactionmanager.account.error.handler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.transactionmanager.account.error.exception.DataIntegrityViolationException;
import com.transactionmanager.account.error.exception.NotFoundEntityException;

/**
 * Classe que captura todas as exceptions que foram lançadas dentro de uma
 * request e não foram tratadas.
 * 
 * @author eduardo
 *
 */
@ControllerAdvice
public class ExcecptionsHandler {

	/**
	 * Esse método não deve ser chamado pelo cliente. É o framework que o invoca.
	 * 
	 * @param notFoundEntityException exception do tipo
	 *                                {@link NotFoundEntityException}
	 * @return uma instancia de {@link ResponseEntity} com status code
	 *         {@link HttpStatus.NOT_FOUND}
	 */
	@ExceptionHandler(NotFoundEntityException.class)
	ResponseEntity<ResponseError> handlerNotFoundEntityException(NotFoundEntityException notFoundEntityException) {
		final ResponseError responseError = new ResponseError(notFoundEntityException.getMessage(),
				Collections.emptyList());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
	}

	/**
	 * Esse método não deve ser chamado pelo cliente. É o framework que o invoca.
	 * 
	 * @param dataIntegrityViolationException exception do tipo
	 *                                        {@link DataIntegrityViolationException}
	 * @return uma instancia de {@link ResponseEntity} com o status code
	 *         {@link HttpStatus.BAD_REQUEST}
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	ResponseEntity<ResponseError> handlerDataIntegrityViolationException(
			DataIntegrityViolationException dataIntegrityViolationException) {

		final ResponseError responseError = new ResponseError(dataIntegrityViolationException.getMessage(),
				Collections.emptyList());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ResponseError> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {

		final List<String> errorDetails = getInformationErroAsStringList(methodArgumentNotValidException);

		return ResponseEntity.badRequest().body(new ResponseError("Error when validate data.", errorDetails));
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ResponseError> handlerException(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResponseError("Internal server error.", Collections.emptyList()));
	}

	private List<String> getInformationErroAsStringList(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		return methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage).collect(Collectors.toList());
	}

}
