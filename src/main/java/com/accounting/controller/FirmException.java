package com.accounting.controller;

public class FirmException extends Exception{

	public FirmException() {
		super();
	}

	public FirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FirmException(String message, Throwable cause) {
		super(message, cause);
	}

	public FirmException(String message) {
		super(message);
	}

	public FirmException(Throwable cause) {
		super(cause);
	}

}
