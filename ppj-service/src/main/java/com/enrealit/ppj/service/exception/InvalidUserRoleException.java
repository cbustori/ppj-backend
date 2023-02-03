package com.enrealit.ppj.service.exception;

import com.enrealit.ppj.shared.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidUserRoleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3158370954717097093L;
	private UserType role;

	public InvalidUserRoleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidUserRoleException(String msg, UserType role) {
		super(msg);
		this.role = role;
	}

}
