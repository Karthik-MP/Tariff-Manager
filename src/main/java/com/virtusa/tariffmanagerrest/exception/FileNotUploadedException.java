package com.virtusa.tariffmanagerrest.exception;

public class FileNotUploadedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotUploadedException() {
	}
	
	public FileNotUploadedException(String message) {
		super(message);
	}
}
