package com.enrealit.ppj.shared.dto.event;

import java.util.List;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EventFiltersDto {

	private Double minPrice;
	private Double maxPrice;
	private PlaceType placeType;
	private Double distance;
	private List<String> tags;

}
