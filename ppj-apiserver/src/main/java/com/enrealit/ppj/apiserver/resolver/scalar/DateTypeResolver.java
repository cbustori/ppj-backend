package com.enrealit.ppj.apiserver.resolver.scalar;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class DateTypeResolver extends GraphQLScalarType {

	private static final String DEFAULT_NAME = "Date";

	public DateTypeResolver() {
		this(DEFAULT_NAME);
	}

	public DateTypeResolver(final String name) {
		super(name, "Date type", new Coercing<LocalDate, String>() {
			private LocalDate convertImpl(Object input) {
				if (input instanceof String) {
					return DateTimeHelper.parseDate((String) input);
				} else if (input instanceof LocalDate) {
					return (LocalDate) input;
				}
				return null;
			}

			@Override
			public String serialize(Object input) {
				if (input instanceof Date) {
					return DateTimeHelper.toISOString((Date) input);
				} else {
					LocalDate result = convertImpl(input);
					if (result == null) {
						throw new CoercingSerializeException("Invalid value '" + input + "' for Date");
					}
					return DateTimeHelper.toISOString(result);
				}
			}

			@Override
			public LocalDate parseValue(Object input) {
				LocalDate result = convertImpl(input);
				if (result == null) {
					throw new CoercingParseValueException("Invalid value '" + input + "' for Date");
				}
				return result;
			}

			@Override
			public LocalDate parseLiteral(Object input) {
				if (!(input instanceof StringValue))
					return null;
				String value = ((StringValue) input).getValue();
				LocalDate result = convertImpl(value);
				return result;
			}
		});
	}

}
