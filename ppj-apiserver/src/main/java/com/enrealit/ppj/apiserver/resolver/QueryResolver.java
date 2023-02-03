package com.enrealit.ppj.apiserver.resolver;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.apiserver.exception.EventNotFoundException;
import com.enrealit.ppj.apiserver.exception.PlaceNotFoundException;
import com.enrealit.ppj.apiserver.exception.TagNotFoundException;
import com.enrealit.ppj.apiserver.exception.UserNotFoundException;
import com.enrealit.ppj.apiserver.input.EventFiltersInput;
import com.enrealit.ppj.apiserver.security.SecurityHelper;
import com.enrealit.ppj.service.AccountSettingsService;
import com.enrealit.ppj.service.EventService;
import com.enrealit.ppj.service.GooglePlaceSearchService;
import com.enrealit.ppj.service.PlaceService;
import com.enrealit.ppj.service.SchedulerService;
import com.enrealit.ppj.service.TagService;
import com.enrealit.ppj.service.UserService;
import com.enrealit.ppj.shared.dto.AccountSettingsDto;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.SchedulerDto;
import com.enrealit.ppj.shared.dto.TagDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.gmaps.GooglePlaceDto;
import com.enrealit.ppj.shared.dto.gmaps.PlaceDetailsDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.EventDateFilter;
import com.enrealit.ppj.shared.enums.PlanningView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class QueryResolver implements GraphQLQueryResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryResolver.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private GooglePlaceSearchService placeSearchService;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private AccountSettingsService profileService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ObjectMapper objectMapper;

	public List<EventDto> getEvents() throws JsonProcessingException {
		LOGGER.info("calling getEvents()");
		String userId = SecurityHelper.getCurrentUserId();
		if (StringUtils.isBlank(userId)) {
			LOGGER.error("User {} not found", userId);
			throw new UserNotFoundException(null, userId);
		}
		UserDto user = userService.findById(userId);
		if (user == null) {
			LOGGER.error("User {} not found", userId);
			throw new UserNotFoundException(null, userId);
		}
		List<EventDto> events = eventService.findByUser(user);
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(events));
		return events;
	}

	public List<SchedulerDto> getEventsAroundMe(List<Double> position, EventDateFilter eventDateFilter, Integer start,
			Integer limit, EventFiltersInput filters) throws JsonProcessingException {
		LOGGER.info("calling getEventsAroundMe({}, {}, {}, {}, {})", position, eventDateFilter, start, limit, filters);
		GeoJsonPointDto point = new GeoJsonPointDto(position.get(0), position.get(1));
		List<SchedulerDto> events = schedulerService.getEventsAroundMe(point, eventDateFilter, start, limit,
				filters != null ? filters.convertToDto() : null);
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(events));
		return events;
	}

	@PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(), T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public List<SchedulerDto> getSchedule(LocalDate availableOn, PlanningView view) throws JsonProcessingException {
		String userId = SecurityHelper.getCurrentUserId();
		LOGGER.info("calling getEventsByUserAndDate({}, {})", userId, availableOn);
		List<SchedulerDto> schedule = schedulerService.getSchedulerByDate(userId, availableOn, view);
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(schedule));
		return schedule;
	}

	@PreAuthorize("isAuthenticated()")
	public UserDto getMe() throws JsonProcessingException {
		String id = SecurityHelper.getCurrentUserId();
		LOGGER.info("calling getMe({})", id);
		UserDto user = userService.findById(id);
		if (user == null) {
			LOGGER.error("User {} not found", id);
			throw new UserNotFoundException(null, id);
		}
		LOGGER.info("returning value: {}", objectMapper.writeValueAsString(user));
		return user;
	}

	public SchedulerDto getEvent(String id) throws JsonProcessingException {
		LOGGER.info("calling getEvent({})", id);
		SchedulerDto event = schedulerService.findById(id);
		if (event == null) {
			LOGGER.error("event {} not found", id);
			throw new EventNotFoundException(id);
		}
		LOGGER.info("returning value: {}", objectMapper.writeValueAsString(event));
		return event;
	}

	public PlaceDto getPlace(String id) throws JsonProcessingException {
		LOGGER.info("calling getPlace({})", id);
		PlaceDto place = placeService.findById(id);
		if (place == null) {
			LOGGER.error("Place {} not found", id);
			throw new PlaceNotFoundException(id);
		}
		LOGGER.info("returning value: {}", objectMapper.writeValueAsString(place));
		return place;
	}

	// @PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(),
	// T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public List<GooglePlaceDto> searchPlaces(String search) throws JsonProcessingException {
		LOGGER.info("calling searchPlaces({})", search);
		List<GooglePlaceDto> results = placeSearchService.searchPlaces(search);
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(results));
		return results;
	}

	// @PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(),
	// T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public PlaceDetailsDto getPlaceDetails(String placeId) throws JsonProcessingException {
		LOGGER.info("calling getPlaceDetails({})", placeId);
		PlaceDetailsDto result = placeSearchService.getPlaceDetails(placeId);
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(result));
		return result;
	}

	public List<AccountSettingsDto> getAccounts() throws JsonProcessingException {
		LOGGER.info("calling getAccounts()");
		List<AccountSettingsDto> result = profileService.getAccounts();
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(result));
		return result;
	}

	public List<String> getTags(String label) throws JsonProcessingException {
		LOGGER.info("calling getTags({})", label);
		TagDto tag = tagService.findByLabel(label);
		if (tag == null) {
			throw new TagNotFoundException(label);
		}
		LOGGER.info("returning values: {}", objectMapper.writeValueAsString(tag));
		return tag.getTags();
	}

}
