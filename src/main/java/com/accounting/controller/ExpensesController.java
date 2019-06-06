package com.accounting.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import com.accounting.model.EntityManagerProvider;
import com.accounting.model.firm.Expense;
import com.accounting.model.firm.Firm;

public class ExpensesController {

	private static ExpensesController controller;
	private EntityManagerProvider provider;

	private ExpensesController() {
		provider = EntityManagerProvider.getInstance();
	}

	public static ExpensesController getInstance() {
		if (controller == null) {
			controller = new ExpensesController();
		}
		return controller;
	}

	public void createExpense(String expenseType, String description,  int price,
			 String currencyType, String firm,  Date date) throws ExpenseException {
		
		validateExpenseParameters(expenseType, description, firm, currencyType,
				date);
		EntityManager mng = provider.getEntityManager();
		
		try {
			mng.getTransaction().begin();
			Expense exp= new Expense();
			Firm firmDB = mng.find(Firm.class, firm);
			if(firmDB == null) {
				throw new ExpenseException("Няма такава фирма!!");
			}
			exp.setDateOfExpense(date);
			exp.setCurrency(currencyType);
			exp.setFirm(firmDB);
			exp.setDescription(description);
			exp.setPrice(price);
			exp.setType(expenseType);
			
			mng.persist(exp);
			mng.getTransaction().commit();
		} catch (RollbackException ex) {
			throw new ExpenseException("Грешка при създаване!");
		} finally {
			if(mng.getTransaction().isActive()) {
				mng.getTransaction().rollback();
			}
		}
	}

	private void validateExpenseParameters(String expenseType, String description, String firm, String currencyType,
			Date date) throws ExpenseException {
		if(expenseType == null || expenseType.equals("")) {
			throw new ExpenseException("Полето вид на разхода е задължително!!!");
		}
		if(description == null || description.equals("")) {
			throw new ExpenseException("Полето описание е задължително!!!");
		}
		if(firm == null || firm.equals("")) {
			throw new ExpenseException("Полето фирма е задължително!!!");
		}
		if(currencyType == null || currencyType.equals("")) {
			throw new ExpenseException("Полето вид валута е задължително!!!");
		}
		if(date == null) {
			throw new ExpenseException("Полето дата е задължително!!!");
		}
	}
	
	public List<Expense> getExpensesBetweenDates(Date from, Date to, String firmName) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		List<Expense> expenses = mng.createNamedQuery(Expense.QUERY_FIND_EXPENSES_BETWEEN_DATES)
				.setParameter("startDate",from).setParameter("endDate",to).setParameter("name", firmName).getResultList();
		mng.getTransaction().commit();
		return expenses;
	}
	
	public void deleteExpense(int id) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		Expense exp = mng.find(Expense.class, id);
		mng.remove(exp);
		mng.getTransaction().commit();
	}
}
