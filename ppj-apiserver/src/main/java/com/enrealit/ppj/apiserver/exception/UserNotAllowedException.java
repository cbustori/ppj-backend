package com.enrealit.ppj.apiserver.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;

import com.enrealit.ppj.shared.enums.TypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class UserNotAllowedException extends AccessDeniedException implements GraphQLError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6685811724108705578L;

	public UserNotAllowedException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		return "Accès refusé";
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
		map.put("errorCode", TypeException.NOT_AUTHORIZED.toString());
		map.put("errorMessage", this.getMessage());
		return map;
	}

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

}
