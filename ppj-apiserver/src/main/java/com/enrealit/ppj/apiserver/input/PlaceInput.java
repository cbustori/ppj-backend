package com.enrealit.ppj.apiserver.input;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;
import com.enrealit.ppj.shared.enums.PlaceStatus;
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
public class PlaceInput implements GraphQLInputType {

	private String placeId;
	private PlaceType type;
	private String googlePlaceId;
	private String businessName;
	private PlaceStatus status;
	private AddressInput address;
	private String phoneNumber;
	private List<String> tags;
	private List<CloudinaryPictureInput> pictures;
	private String website;

	public PlaceDto converToDto() {
		PlaceDto place = null;
		if (type == PlaceType.RESTAURANT) {
			place = new RestaurantDto();
		}
		place.setId(getPlaceId());
		place.setGooglePlaceId(getGooglePlaceId());
		place.setStatus(getStatus());
		// place.setState(PlaceState.OPEN);
		place.setName(getBusinessName());
		place.setAddress(getAddress().convertToDto());
		place.setPhoneNumber(getPhoneNumber());
		place.setWebsite(getWebsite());
		place.setTags(getTags());
		if (place.getPictures() != null && !place.getPictures().isEmpty()) {
			place.setPictures(getPictures().stream().map(p -> {
				return p.convertToDto();
			}).collect(Collectors.toList()));
		}

		return place;
	}

	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
