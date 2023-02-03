package com.enrealit.ppj.apiserver.input;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.event.DishDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishInput extends EventInput {

	private Double price;

	@Override
	public DishDto convertToDto() {
		DishDto event = new DishDto();
		event.setPrice(price);
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

}
