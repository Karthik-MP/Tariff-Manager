package com.virtusa.tariffmanagerrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.tariffmanagerrest.exception.UserNotFoundException;
import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserModel>> allUsers() {
		List<UserModel> users = service.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserModel> getUser(@PathVariable String id) {
		UserModel user = service.getUser(id);
		if (user != null)
			return ResponseEntity.ok(user);
		else
			throw new UserNotFoundException();
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		UserModel user = service.deleteUser(id);
		if (user != null)
			return new ResponseEntity<>(user.getEmail(), HttpStatus.GONE);
		else
			throw new UserNotFoundException("User with " + id + " is not found.");
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<UserModel> updateUser(@PathVariable("id") String id, @RequestBody UserModel user) {
		UserModel updatedUser = service.updateUser(user, id);
		if (updatedUser != null)
			return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
		else
			throw new UserNotFoundException("User with " + id + " is not found.");
	}	
}
