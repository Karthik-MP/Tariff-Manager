package com.virtusa.tariffmanagerrest.exception;

public class InvalidFileTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFileTypeException() {
    }

    public InvalidFileTypeException(String message) {
        super(message);
    }

}
