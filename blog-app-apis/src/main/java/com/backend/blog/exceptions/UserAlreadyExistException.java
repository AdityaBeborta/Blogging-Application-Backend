package com.backend.blog.exceptions;

public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String msg) {
		super(msg);
	}
}
