package com.enrealit.ppj.service.exception;

public class InvalidProfileException extends Exception {

	public InvalidProfileException(String msg) {
		super(msg);
	}

	public InvalidProfileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
