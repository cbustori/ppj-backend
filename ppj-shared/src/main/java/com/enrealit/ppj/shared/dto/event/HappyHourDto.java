package com.enrealit.ppj.shared.dto.event;

import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HappyHourDto extends EventDto {

	private Integer startingTime;
	private Integer endingTime;

	public HappyHourDto() {
		_class = EventType.HAPPY_HOUR._class();
		type = EventType.HAPPY_HOUR;
	}

}
