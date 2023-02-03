package com.enrealit.ppj.service.exception;

import com.enrealit.ppj.shared.enums.ProfileType;

public class AccountSettingsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ProfileType type;

	public AccountSettingsNotFoundException(String msg, ProfileType type) {
		super(msg);
		this.type = type;
	}

	public ProfileType getType() {
		return type;
	}

}
