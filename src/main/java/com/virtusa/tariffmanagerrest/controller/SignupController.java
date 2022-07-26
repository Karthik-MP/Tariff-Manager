package com.virtusa.tariffmanagerrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtusa.tariffmanagerrest.exception.UserAlreadyExistsException;
import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.service.SignupService;

@Controller
@RequestMapping("/public")
public class SignupController {

	@Autowired
	private SignupService service;
	
	@PostMapping("/register")
	public ResponseEntity<UserModel> saveUser(@RequestBody UserModel user) {

		if (service.userExists(user.getEmail())) {
			throw new UserAlreadyExistsException("User with "+user.getEmail()+" is already present.");
		}

		UserModel newUser = service.save(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

}
