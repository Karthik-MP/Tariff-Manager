package com.virtusa.tariffmanagerrest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtusa.tariffmanagerrest.model.ExpenseModel;
import com.virtusa.tariffmanagerrest.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Override
	public List<ExpenseModel> getAllExpenses() {
		return expenseRepository.findAll();
	}
	
	@Override
	public List<ExpenseModel> getAllExpensesUser(String email) {
		return expenseRepository.findAllByUserEmail(email);
	}

	@Override
	public ExpenseModel approveExpense(int expenseId) {
		expenseRepository.updateExpenseApprove(expenseId);
		return getExpense(expenseId);
	}

	@Override
	public ExpenseModel rejectExpense(int expenseId) {
		expenseRepository.updateExpenseReject(expenseId);
		return getExpense(expenseId);
	}

	@Override
	public ExpenseModel getExpense(int expenseId) {
		return expenseRepository.findByExpenseId(expenseId);
	}

	@Override
	public Optional<ExpenseModel> getExpenseById(int expenseId) {
		return expenseRepository.findById(expenseId);
	}

	@Override
	public ExpenseModel addExpense(ExpenseModel expense) {
		return expenseRepository.save(expense);
	}

}
