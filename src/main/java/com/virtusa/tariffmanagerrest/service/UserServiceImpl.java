package com.virtusa.tariffmanagerrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	public UserModel deleteUser(String email) {
		UserModel user = getUser(email);
		if (user != null)
			repository.delete(user);

		return user;
	}

	public List<UserModel> getAllUsers() {
		return repository.findAll();
	}

	public UserModel getUser(String email) {
		return repository.findByEmail(email);
	}

	public UserModel updateUser(UserModel user, String email) {
		UserModel find = getUser(email);
		if (find != null) {
			user.setEmail(find.getEmail());
			user.setPassword(find.getPassword());
			user.setName(user.getName());
			user.setMobileNumber(user.getMobileNumber());
			user.setRole(find.getRole());
			repository.save(user);
		}
		return user;
	}
}
