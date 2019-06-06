package com.accounting.model.firm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Expense
 *
 */
@Entity
@Table (name = "EXPENSES")
@NamedQuery(name = Expense.QUERY_FIND_EXPENSES_BETWEEN_DATES, query = "SELECT exp FROM Expense exp where exp.firm.firmName = :name AND exp.dateOfExpense BETWEEN :startDate AND :endDate")
@NamedQuery(name = Expense.QUERY_FIND_EXPENSE_FOR_MONTH, query = "SELECT FUNCTION('month', exp.dateOfExpense), sum(exp.price) FROM Expense exp where exp.firm.firmName = :name AND exp.type = :type AND FUNCTION('year', exp.dateOfExpense) = :year GROUP by FUNCTION('month', exp.dateOfExpense)")
@NamedQuery(name = Expense.QUERY_FIND_EXPENSE_FOR_YEARS, query = "SELECT FUNCTION('year', exp.dateOfExpense), sum(exp.price) FROM Expense exp where exp.firm.firmName = :name AND exp.type = :type AND exp.dateOfExpense BETWEEN :startDate AND :endDate GROUP by FUNCTION('year', exp.dateOfExpense)")
@Cacheable(false)
public class Expense implements Serializable {

	public static final String QUERY_FIND_EXPENSE_FOR_MONTH = "Expense.findExpenseForMonths";
	public static final String QUERY_FIND_EXPENSE_FOR_YEARS = "Expense.findExpenseForYears";
	public static final String QUERY_FIND_EXPENSES_BETWEEN_DATES = "Expense.findExpensesBetweenDates";
	public static final String QUERY_FIND_EXPENSES_BETWEEN_DATES_PRECISE = "Expense.findExpensesBetweenDatesPrecise";
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="TYPE")
	@NotNull
	private String type;
	
	@Column(name="DESCRIPTION")
	@NotNull
	private String description;
	
	@Column(name="CURRENCY")
	@NotNull
	private String currency;
	
	@Column(name="PRICE")
	@NotNull
	private int price;
	
	@Column(name="DATE")
	@NotNull
	private Date dateOfExpense;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "FIRM_ID")
	@NotNull
	private Firm firm;
	
	public Expense() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(Date dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
   
}
