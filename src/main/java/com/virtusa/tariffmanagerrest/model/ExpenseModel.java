package com.virtusa.tariffmanagerrest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "expense")
public class ExpenseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int expenseId;

	@Column(unique = true)
	private int billNumber;
	private int billCost;

	@DateTimeFormat(pattern = "yyyy-dd-MM")
	private Date datedOn;

	private String status;
	private String remark;

	@Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
	private byte[] billImage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private UserModel user;

	public ExpenseModel() {
	}

	public ExpenseModel(int billNumber, int billCost, Date datedOn, String status, String remark) {
		super();
		this.billNumber = billNumber;
		this.billCost = billCost;
		this.datedOn = datedOn;
		this.status = status;
		this.remark = remark;
	}

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public int getBillCost() {
		return billCost;
	}

	public void setBillCost(int billCost) {
		this.billCost = billCost;
	}

	public Date getDatedOn() {
		return datedOn;
	}

	public void setDatedOn(Date datedOn) {
		this.datedOn = datedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ExpenseModel [expenseId=" + expenseId + ", billNumber=" + billNumber + ", billCost=" + billCost
				+ ", datedOn=" + datedOn + ", status=" + status + ", remark=" + remark + "]";
	}

	public byte[] getBillImage() {
		return billImage;
	}

	public void setBillImage(byte[] billImage) {
		this.billImage = billImage;
	}

}
