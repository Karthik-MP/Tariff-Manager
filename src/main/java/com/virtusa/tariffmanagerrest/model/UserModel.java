package com.virtusa.tariffmanagerrest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String email;
	private String password;
	private String name;
	private String mobileNumber;
	private String role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<ExpenseModel> expenses;

	public UserModel() {
		super();
	} 
	
	public UserModel(String password, String name, String mobileNumber, String role) {
		super();
		this.password = password;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.role = role;
	}

	public UserModel(String email, String password, String name, String mobileNumber, String role) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public List<ExpenseModel> getExpenses() {
		return expenses;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setExpenses(List<ExpenseModel> expenses) {
		this.expenses = expenses;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserModel [email=" + email + ", password=" + password + ", name=" + name + ", mobileNumber="
				+ mobileNumber + ", role=" + role + "]";
	}

}
