package com.enrealit.ppj.apiserver.input;

import java.util.List;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.AddressDto;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInput implements GraphQLInputType {

	private String street;
	private String zipCode;
	private String city;
	private String country;
	private List<Double> location;
	private String icon;

	public AddressDto convertToDto() {
		GeoJsonPointDto point = location == null ? null : new GeoJsonPointDto(location.get(0), location.get(1));
		return AddressDto.builder().street(street).zipCode(zipCode).city(city).country(country).location(point)
				.icon(icon).build();
	}

	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
