package com.enrealit.ppj.shared.dto.event;

import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DishDto extends EventDto {

	private Double price;

	public DishDto() {
		_class = EventType.DISH_OF_THE_DAY._class();
		type = EventType.DISH_OF_THE_DAY;
	}

}
