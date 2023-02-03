package com.enrealit.ppj.apiserver.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enrealit.ppj.shared.enums.ProfileType;
import com.enrealit.ppj.shared.enums.TypeException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AccountNotAuthorizedException extends RuntimeException implements GraphQLError {

	private final ProfileType type;
	private final String message;

	public AccountNotAuthorizedException(String msg, ProfileType type) {
		this.message = msg;
		this.type = type;
	}

	@Override
	public String getMessage() {
		return "Votre abonnement en cours (" + type.libelle() + ") ne vous permet pas d'accèder à cette ressource: "
				+ message;
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
