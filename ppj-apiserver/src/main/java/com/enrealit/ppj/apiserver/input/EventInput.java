package com.enrealit.ppj.apiserver.input;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.enums.EventType;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
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
public class EventInput implements GraphQLInputType {

	private String eventId;
	private EventType type;
	private String title;
	private String description;
	private List<String> tags;
	private List<CloudinaryPictureInput> pictures;
	private Double price;

	public EventDto convertToDto() {
		EventDto event = null;
		if (EventType.DISH_OF_THE_DAY == type) {
			event = new DishDto();
			((DishDto) event).setPrice(price);
		} else if (EventType.HAPPY_HOUR == type) {
			event = new HappyHourDto();
		}
		event.setId(getEventId());
		event.setTitle(getTitle());
		event.setDescription(getDescription());
		event.setTags(getTags());
		if (getPictures() != null && !getPictures().isEmpty()) {
			event.setPictures(getPictures().stream().map(e -> {
				return e.convertToDto();
			}).collect(Collectors.toList()));
		}
		return event;
	}

	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		return null;
	}
}
