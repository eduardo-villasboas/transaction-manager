package com.transactionmanager.account.error.exception;

/**
 * classe de Exception desenvovilda para representar uma exceção 
 * relacionada as regras de integridade de dados. 
 *  
 * @author eduardo
 *
 */
public class DataIntegrityViolationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6667481208031625597L;

	public DataIntegrityViolationException(String message) {
		super(message);
	}
	
}
