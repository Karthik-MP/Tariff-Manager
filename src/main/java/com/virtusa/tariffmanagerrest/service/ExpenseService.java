package com.virtusa.tariffmanagerrest.service;

import java.util.List;
import java.util.Optional;

import com.virtusa.tariffmanagerrest.model.ExpenseModel;

public interface ExpenseService {

	List<ExpenseModel> getAllExpenses();

	List<ExpenseModel> getAllExpensesUser(String email);

	ExpenseModel getExpense(int expenseId);

	Optional<ExpenseModel> getExpenseById(int expenseId);

	ExpenseModel addExpense(ExpenseModel expense);

	ExpenseModel approveExpense(int expenseId);

	ExpenseModel rejectExpense(int expenseId);

}
