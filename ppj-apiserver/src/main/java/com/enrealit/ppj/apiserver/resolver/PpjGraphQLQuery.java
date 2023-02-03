package com.enrealit.ppj.apiserver.resolver;

import java.time.LocalDate;
import java.util.List;

import com.enrealit.ppj.apiserver.input.EventFiltersInput;
import com.enrealit.ppj.shared.dto.AccountSettingsDto;
import com.enrealit.ppj.shared.dto.SchedulerDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.gmaps.GooglePlaceDto;
import com.enrealit.ppj.shared.dto.gmaps.PlaceDetailsDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.EventDateFilter;
import com.enrealit.ppj.shared.enums.PlanningView;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PpjGraphQLQuery {

	List<SchedulerDto> getEventsAroundMe(List<Double> position, EventDateFilter eventDateFilter, Integer start,
			Integer limit, EventFiltersInput filters) throws JsonProcessingException;

	List<SchedulerDto> getSchedule(LocalDate availableOn, PlanningView view) throws JsonProcessingException;

	List<EventDto> getEvents() throws JsonProcessingException;

	SchedulerDto getEvent(String id) throws JsonProcessingException;

	UserDto getMe() throws JsonProcessingException;

	PlaceDto getPlace(String id) throws JsonProcessingException;

	List<GooglePlaceDto> searchPlaces(String search) throws JsonProcessingException;

	PlaceDetailsDto getPlaceDetails(String placeId) throws JsonProcessingException;

	List<AccountSettingsDto> getAccounts() throws JsonProcessingException;

	List<String> getTags(String label) throws JsonProcessingException;

}
