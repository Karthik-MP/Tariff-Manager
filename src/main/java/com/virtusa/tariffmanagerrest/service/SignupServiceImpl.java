package com.virtusa.tariffmanagerrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.repository.UserRepository;

@Service
public class SignupServiceImpl implements SignupService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public UserModel save(UserModel user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	public boolean userExists(String email) {
		return repository.findById(email).isPresent();
	}

}
