package com.enrealit.ppj.apiserver.resolver.scalar;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.GeoJsonPointDto;

import graphql.language.ArrayValue;
import graphql.language.FloatValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.GraphQLScalarType;

@Component
public class GeoJsonPointTypeResolver extends GraphQLScalarType {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoJsonPointTypeResolver.class);

	public GeoJsonPointTypeResolver() {
		super("GeoJsonPoint", "Json point for MongoDB", new Coercing<GeoJsonPointDto, List<Double>>() {

			@Override
			public List<Double> serialize(Object input) {
				GeoJsonPointDto point = (GeoJsonPointDto) input;
				List<Double> coordinates = new ArrayList<>();
				coordinates.add(point.getX());
				coordinates.add(point.getY());
				return coordinates;
			}

			@Override
			public GeoJsonPointDto parseValue(Object input) {
				return parseLiteral(input);
			}

			@Override
			public GeoJsonPointDto parseLiteral(Object input) {
				List<Value> array = ((ArrayValue) input).getValues();
				GeoJsonPointDto geoPoint = null;
				if (!array.isEmpty()) {
					try {
						Double lat = ((FloatValue) array.get(0)).getValue().doubleValue();
						Double lng = ((FloatValue) array.get(1)).getValue().doubleValue();
						geoPoint = new GeoJsonPointDto(lat, lng);
					} catch (Exception e) {
						LOGGER.error("Coordonnées géographiques incorrectes {}", input);
						throw new CoercingParseLiteralException(
								"Unable to get coordinates from following value: '" + input + "'.");
					}
				}
				return geoPoint;
			}

		});
	}

}
