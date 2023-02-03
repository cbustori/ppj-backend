package com.enrealit.ppj.shared.dto.place;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RestaurantDto extends PlaceDto {

	public RestaurantDto() {
		_class = PlaceType.RESTAURANT._class();
		type = PlaceType.RESTAURANT;
	}

}
