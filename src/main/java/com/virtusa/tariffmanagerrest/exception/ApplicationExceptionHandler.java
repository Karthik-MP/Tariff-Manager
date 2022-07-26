package com.virtusa.tariffmanagerrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
		return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
		return new ResponseEntity<>(userAlreadyExistsException.getMessage(), HttpStatus.FOUND);
	}
	
	@ExceptionHandler(ExpenseNotFoundException.class)
	public ResponseEntity<String> handleExpenseNotFoundException(ExpenseNotFoundException expenseNotFoundException) {
		return new ResponseEntity<>(expenseNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileNotUploadedException.class)
	public ResponseEntity<String> handleFileNotUploadedException(FileNotUploadedException fileNotUploadedException) {
		return new ResponseEntity<>(fileNotUploadedException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidFileTypeException.class)
	public ResponseEntity<String> handleInvalidFileTypeException(InvalidFileTypeException invalidFileTypeException) {
		return new ResponseEntity<>(invalidFileTypeException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(JwtTokenNotFoundException.class)
	public ResponseEntity<String> handleJwtTokenNotFoundException(JwtTokenNotFoundException jwtTokenNotFoundException) {
		return new ResponseEntity<>(jwtTokenNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(WrongPasswordException.class)
	public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException wrongPasswordException) {
		return new ResponseEntity<>(wrongPasswordException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}
