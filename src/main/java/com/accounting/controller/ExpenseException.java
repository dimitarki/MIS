package com.accounting.controller;

public class ExpenseException extends Exception {

	public ExpenseException() {
	}

	public ExpenseException(String message) {
		super(message);
	}

	public ExpenseException(Throwable cause) {
		super(cause);
	}

	public ExpenseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpenseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
