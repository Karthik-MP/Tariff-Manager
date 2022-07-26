package com.virtusa.tariffmanagerrest.exception;

public class WrongPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WrongPasswordException() {
		super();
	}

	public WrongPasswordException(String message) {
		super(message);
	}

}
