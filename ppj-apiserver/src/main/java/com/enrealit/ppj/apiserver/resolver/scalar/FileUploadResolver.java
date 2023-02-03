package com.enrealit.ppj.apiserver.resolver.scalar;

import java.io.IOException;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.FileUploadDto;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class FileUploadResolver extends GraphQLScalarType {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadResolver.class);

	public FileUploadResolver() {
		super("FileUpload", "A file part in a multipart request", new Coercing<FileUploadDto, Void>() {

			@Override
			public Void serialize(Object dataFetcherResult) {
				throw new CoercingSerializeException("Upload is an input-only type");
			}

			@Override
			public FileUploadDto parseValue(Object input) {
				if (input instanceof Part) {
					Part part = (Part) input;
					try {
						String contentType = part.getContentType();
						byte[] content = IOUtils.toByteArray(part.getInputStream());
						part.delete();
						return new FileUploadDto(contentType, content);

					} catch (IOException e) {
						throw new CoercingParseValueException("Couldn't read content of the uploaded file");
					}
				} else if (null == input) {
					return null;
				} else {
					throw new CoercingParseValueException(
							"Expected type " + Part.class.getName() + " but was " + input.getClass().getName());
				}
			}

			@Override
			public FileUploadDto parseLiteral(Object input) {
				throw new CoercingParseLiteralException("Must use variables to specify Upload values");
			}
		});
	}

}
