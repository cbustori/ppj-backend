package com.enrealit.ppj.apiserver.input;

import java.util.List;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.event.EventFiltersDto;
import com.enrealit.ppj.shared.enums.PlaceType;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFiltersInput implements GraphQLInputType {

	private Double minPrice;
	private Double maxPrice;
	private PlaceType placeType;
	private Double distanceInKms;
	private List<String> tags;

	public EventFiltersDto convertToDto() {
		return EventFiltersDto.builder().minPrice(minPrice).maxPrice(maxPrice).placeType(placeType)
				.distance(distanceInKms).tags(tags).build();
	}

	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
