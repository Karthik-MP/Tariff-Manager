package com.virtusa.tariffmanagerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.tariffmanagerrest.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

	UserModel findByEmail(String email);

	UserModel findByEmailAndPassword(String email, String password);
	
	int deleteByEmail(String email);

}
