package com.virtusa.tariffmanagerrest.exception;

public class JwtTokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenNotFoundException() {
	}

	public JwtTokenNotFoundException(String message) {
		super(message);
	}

}
