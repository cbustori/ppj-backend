package com.enrealit.ppj.apiserver.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;

@Component
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomGraphQLErrorHandler.class);

	@Override
	public List<GraphQLError> processErrors(List<GraphQLError> errors) {
		return errors.stream().map(this::getNested).collect(Collectors.toList());
	}

	private GraphQLError getNested(GraphQLError error) {
		if (error instanceof ExceptionWhileDataFetching) {
			ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
			LOGGER.error("Impossible d'accéder à la ressource demandée", exceptionError.getException());
			if (exceptionError.getException() instanceof GraphQLError) {
				return (GraphQLError) exceptionError.getException();
			} else if (exceptionError.getException() instanceof AccessDeniedException) {
				return new UserNotAllowedException("Accès refusé");
			}
		}
		return error;
	}

}
