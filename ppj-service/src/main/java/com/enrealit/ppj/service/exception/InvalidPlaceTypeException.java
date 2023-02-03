package com.enrealit.ppj.service.exception;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPlaceTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9218958351806381502L;

	private PlaceType type;

	public InvalidPlaceTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InvalidPlaceTypeException(String msg, PlaceType type) {
		super(msg);
		this.type = type;
	}

}
