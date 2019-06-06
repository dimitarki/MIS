package com.accounting.controller;

public class PersonException extends Exception {

	public PersonException() {
		super();
	}

	public PersonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersonException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonException(String message) {
		super(message);
	}

	public PersonException(Throwable cause) {
		super(cause);
	}

}
