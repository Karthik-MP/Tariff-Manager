package com.virtusa.tariffmanagerrest.service;

import java.util.List;

import com.virtusa.tariffmanagerrest.model.UserModel;

public interface UserService {
	
	public UserModel deleteUser(String email);
	
	public List<UserModel> getAllUsers();
	
	public UserModel getUser(String email);
	
	public UserModel updateUser(UserModel user, String email);
}
