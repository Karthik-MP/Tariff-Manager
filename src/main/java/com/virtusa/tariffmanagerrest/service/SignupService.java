package com.virtusa.tariffmanagerrest.service;

import com.virtusa.tariffmanagerrest.model.UserModel;

public interface SignupService {

	public UserModel save(UserModel user);
	
	public boolean userExists(String email);
}
