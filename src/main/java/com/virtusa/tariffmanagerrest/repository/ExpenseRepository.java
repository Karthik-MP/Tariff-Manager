package com.virtusa.tariffmanagerrest.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.virtusa.tariffmanagerrest.model.ExpenseModel;
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, Integer>{
	
	List<ExpenseModel> findAll();
	List<ExpenseModel> findAllByUserEmail(String email);
	ExpenseModel findByExpenseId(int expenseId);
	Optional<ExpenseModel> findById(int expenseId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE expense SET status = 'Approved' WHERE expense_id = ?1",nativeQuery=true )
    void updateExpenseApprove(Integer expenseId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE expense SET status = 'Rejected' WHERE expense_id = ?1",nativeQuery=true )
	void updateExpenseReject(Integer expenseId);
}
