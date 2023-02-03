package com.enrealit.ppj.service.exception;

public class AlreadyContributorException extends Exception {

	private final String userId;
	private final String placeId;

	public AlreadyContributorException(String msg, String userId, String placeId) {
		super(msg);
		this.userId = userId;
		this.placeId = placeId;
	}

	public String getUserId() {
		return userId;
	}

	public String getPlaceId() {
		return placeId;
	}

}
