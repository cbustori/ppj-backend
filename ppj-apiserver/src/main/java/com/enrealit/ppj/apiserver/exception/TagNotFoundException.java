package com.enrealit.ppj.apiserver.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enrealit.ppj.shared.enums.TypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class TagNotFoundException extends RuntimeException implements GraphQLError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271517818537040909L;
	private String label;

	public TagNotFoundException(String label) {
		this.label = label;
	}

	@Override
	public String getMessage() {
		return "Cette catégorie de tag n'existe pas.";
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
		map.put("errorCode", TypeException.TAG_NOT_FOUND.toString());
		map.put("errorMessage", this.getMessage());
		return map;
	}

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

}
