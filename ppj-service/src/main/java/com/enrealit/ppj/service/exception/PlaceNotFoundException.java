package com.enrealit.ppj.service.exception;

public class PlaceNotFoundException extends Exception {

	private final String placeId;

	public PlaceNotFoundException(String msg, String placeId) {
		super(msg);
		this.placeId = placeId;
	}

	public String getPlaceId() {
		return placeId;
	}

}
