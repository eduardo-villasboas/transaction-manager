package com.transactionmanager.account.error.exception;

/**
 * classe de Exception desenvovilda para representar uma exceção 
 * relacionada a recursos não encontrados. 
 *  
 * @author eduardo
 *
 */
public class NotFoundEntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403495346417272664L;

	public NotFoundEntityException(String message) {
		super(message);
	}

}
