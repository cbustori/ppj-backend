package com.enrealit.ppj.shared.dto.event;

import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ConcertDto extends EventDto {

	private String bandName;

	public ConcertDto() {
		_class = EventType.CONCERT._class();
		type = EventType.CONCERT;
	}

}
