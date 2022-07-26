package com.virtusa.tariffmanagerrest.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {

	
	  Class<?>[] groups() default{};
	  
	  String message() default "User with this email already exists";
	  
	  Class<? extends Payload>[] payload() default {};
	 
}
