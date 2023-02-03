package com.enrealit.ppj.apiserver.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enrealit.ppj.shared.enums.TypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class PlaceNotFoundException extends RuntimeException implements GraphQLError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271517818537040909L;
	private String placeId;

	public PlaceNotFoundException(String placeId) {
		this.placeId = placeId;
	}

	@Override
	public String getMessage() {
		return "L'établissement n'a pas été trouvé.";
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.ValidationError;
	}

	@Override
	public Map<String, Object> getExtensions() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("errorCode", TypeException.PLACE_NOT_FOUND.toString());
		map.put("errorMessage", this.getMessage());
		return map;
	}

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

}
