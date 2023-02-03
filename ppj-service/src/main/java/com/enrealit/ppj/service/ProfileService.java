package com.enrealit.ppj.service;

import java.util.List;

import com.enrealit.ppj.service.exception.AccountSettingsNotFoundException;
import com.enrealit.ppj.service.exception.AlreadyContributorException;
import com.enrealit.ppj.service.exception.InvalidEventTypeException;
import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.service.exception.PlaceNotFoundException;
import com.enrealit.ppj.service.exception.UserNotFoundException;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;

public interface ProfileService {

	EventDto createEvent(String userId, EventDto dish, List<FileUploadDto> files)
			throws InvalidProfileException, InvalidEventTypeException;

	EventDto createEvent(String userId, DishDto dish, List<FileUploadDto> files)
			throws InvalidProfileException, AccountSettingsNotFoundException;

	EventDto createEvent(String userId, ConcertDto concert, List<FileUploadDto> files) throws InvalidProfileException;

	EventDto createEvent(String userId, HappyHourDto hh, List<FileUploadDto> files) throws InvalidProfileException;

	UserDto addContributor(String userId, String placeId, String contribEmail)
			throws InvalidProfileException, UserNotFoundException, PlaceNotFoundException, AlreadyContributorException;

	PlaceDto createPlace(String userId, PlaceDto place, List<FileUploadDto> files)
			throws InvalidProfileException, UserNotFoundException;

}
