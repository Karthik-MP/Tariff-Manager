package com.virtusa.tariffmanagerrest.exception;

public class ExpenseNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ExpenseNotFoundException() {
    }

    public ExpenseNotFoundException(String message) {
        super(message);
    }

}
