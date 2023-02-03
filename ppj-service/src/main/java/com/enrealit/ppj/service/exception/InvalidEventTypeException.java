package com.enrealit.ppj.service.exception;

import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidEventTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3158370954717097093L;
	private EventType type;

	public InvalidEventTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidEventTypeException(String msg, EventType type) {
		super(msg);
		this.type = type;
	}

}
