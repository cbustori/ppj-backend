package com.enrealit.ppj.service.exception;

public class TypeAccountNotFoundException extends RuntimeException {

	public TypeAccountNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public TypeAccountNotFoundException(String msg) {
		super(msg);
	}

}
