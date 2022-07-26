package com.virtusa.tariffmanagerrest.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.tariffmanagerrest.exception.ExpenseNotFoundException;
import com.virtusa.tariffmanagerrest.exception.FileNotUploadedException;
import com.virtusa.tariffmanagerrest.exception.InvalidFileTypeException;
import com.virtusa.tariffmanagerrest.exception.UserNotFoundException;
import com.virtusa.tariffmanagerrest.model.ExpenseModel;
import com.virtusa.tariffmanagerrest.model.UserModel;
import com.virtusa.tariffmanagerrest.security.JwtTokenHelper;
import com.virtusa.tariffmanagerrest.service.ExpenseService;
import com.virtusa.tariffmanagerrest.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "employee")
public class EmployeeController {

	@Autowired
	private UserService userService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Value("${uploadDir}")
	private String uploadFolder;

	static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@GetMapping
	public ResponseEntity<List<ExpenseModel>> getAllExpenses(HttpServletRequest request) {
		String email = this.jwtTokenHelper.getEmailFromJwt(request);
		log.info(email);
		if (email != null) {
			List<ExpenseModel> expenses = expenseService.getAllExpensesUser(email);
			return ResponseEntity.ok(expenses);
		} else
			throw new ExpenseNotFoundException("No expenses found");
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

	@GetMapping("/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") int id, HttpServletResponse response, Optional<ExpenseModel> expenseModel)
			throws IOException {

		expenseModel = expenseService.getExpenseById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(expenseModel.get().getBillImage());
		response.getOutputStream().close();
	}

	@PostMapping("/addexpense")
	public ResponseEntity<ExpenseModel> upload(@RequestParam("billImage") MultipartFile file,
			@RequestParam("remark") String remark, @RequestParam("billNumber") int billNumber,
			@RequestParam("billCost") int billCost, @RequestParam("datedOn") String datedOn,
			HttpServletRequest httpServletRequest) {

		ExpenseModel expense = new ExpenseModel();

		try {

			String uploadDirectory = httpServletRequest.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: {}", uploadDirectory);

			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());

			if (fileName == null || fileName.contains("..")) {
				throw new InvalidFileTypeException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				throw new FileNotUploadedException("File not saved");
			}

			byte[] imageData = file.getBytes();
			expense.setBillImage(imageData);
			expense.setBillCost(billCost);
			expense.setStatus("Pending");
			expense.setRemark(remark);
			expense.setBillNumber(billNumber);
			expense.setDatedOn(new SimpleDateFormat("yyyy-MM-dd").parse(datedOn));

			String email = this.jwtTokenHelper.getEmailFromJwt(httpServletRequest);

			log.info("Expense Controller " + userService.getUser(email));
			expense.setUser(userService.getUser(email));

			expenseService.addExpense(expense);

			return new ResponseEntity<>(expense, HttpStatus.CREATED);

		} catch (Exception e) {
			throw new FileNotUploadedException("There was problem while creating the Expense");
		}
	}
}
