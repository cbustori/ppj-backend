package com.enrealit.ppj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;

@Component
public class PremiumService extends ProfileAbstractService {

	@Override
	public EventDto createEvent(String userId, DishDto dish, List<FileUploadDto> files) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDto createEvent(String userId, ConcertDto concert, List<FileUploadDto> files)
			throws InvalidProfileException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDto createEvent(String userId, HappyHourDto hh, List<FileUploadDto> files)
			throws InvalidProfileException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto addContributor(String userId, String placeId, String contribEmail) throws InvalidProfileException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceDto createPlace(String userId, PlaceDto place, List<FileUploadDto> files)
			throws InvalidProfileException {
		// TODO Auto-generated method stub
		return null;
	}

}
