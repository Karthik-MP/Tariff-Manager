package com.virtusa.tariffmanagerrest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.virtusa.tariffmanagerrest.service.SignupService;

@Component
public class InvalidValidator implements ConstraintValidator<Invalid, String> {

	@Autowired
	private SignupService service;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

		return email.equals("admin") || service.userExists(email);
	}
}
