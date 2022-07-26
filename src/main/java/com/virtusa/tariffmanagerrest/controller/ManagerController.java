package com.virtusa.tariffmanagerrest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.tariffmanagerrest.exception.UserNotFoundException;
import com.virtusa.tariffmanagerrest.model.ExpenseModel;
import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.security.JwtTokenHelper;
import com.virtusa.tariffmanagerrest.service.ExpenseService;
import com.virtusa.tariffmanagerrest.service.UserService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	static final Logger log = LoggerFactory.getLogger(ManagerController.class);

	@GetMapping
	public ResponseEntity<List<ExpenseModel>> getAllExpenses() {
		List<ExpenseModel> expenses = expenseService.getAllExpenses();
		return ResponseEntity.ok(expenses);
	}

	@GetMapping("/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") int id, HttpServletResponse response, Optional<ExpenseModel> expenseModel)
			throws IOException {

		expenseModel = expenseService.getExpenseById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(expenseModel.get().getBillImage());
		response.getOutputStream().close();
	}

	@GetMapping("/profile")
	public ResponseEntity<UserModel> getProfile(HttpServletRequest request) {
		String email = this.jwtTokenHelper.getEmailFromJwt(request);
		UserModel user = userService.getUser(email);
		if (user != null)
			return ResponseEntity.ok(user);
		else
			throw new UserNotFoundException("User with " + email + " email not found");
	}

	@PostMapping("/profile/update")
	public ResponseEntity<UserModel> updateProfile(@RequestBody UserModel user, HttpServletRequest request) {
		String email = this.jwtTokenHelper.getEmailFromJwt(request);
		UserModel updatedUser = userService.updateUser(user, email);
		if (updatedUser != null)
			return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
		else
			throw new UserNotFoundException("User with " + email + " email not found");
	}

	@PutMapping("/approve/{expenseId}")
	public ResponseEntity<ExpenseModel> updateExpenseApprove(@PathVariable int expenseId) {
		ExpenseModel expense = expenseService.approveExpense(expenseId);
		return ResponseEntity.ok(expense);
	}

	@PutMapping("/reject/{expenseId}")
	public ResponseEntity<ExpenseModel> updateExpenseReject(@PathVariable int expenseId) {
		ExpenseModel expense = expenseService.rejectExpense(expenseId);
		return ResponseEntity.ok(expense);
	}

}
