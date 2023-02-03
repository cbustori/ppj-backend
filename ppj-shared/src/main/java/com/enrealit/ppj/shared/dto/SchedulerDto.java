package com.enrealit.ppj.shared.dto;

import java.time.LocalDate;

import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SchedulerDto extends BaseDto {

	private LocalDate availableOn;
	private EventDto event;
	private PlaceDto place;

}
