package com.enrealit.ppj.service;

import java.util.List;

import com.enrealit.ppj.service.exception.InvalidEventTypeException;
import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;

public abstract class ProfileAbstractService implements ProfileService {

	@Override
	public EventDto createEvent(String userId, EventDto event, List<FileUploadDto> files)
			throws InvalidProfileException, InvalidEventTypeException {
		if (event instanceof DishDto) {
			return createEvent(userId, (DishDto) event, files);
		} else if (event instanceof HappyHourDto) {
			return createEvent(userId, (HappyHourDto) event, files);
		} else if (event instanceof ConcertDto) {
			return createEvent(userId, (ConcertDto) event, files);
		}
		throw new InvalidEventTypeException("Le type d'évènement n'existe pas", event.getType());
	}

}
