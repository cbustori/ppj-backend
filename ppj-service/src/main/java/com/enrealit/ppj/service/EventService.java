package com.enrealit.ppj.service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.Event;
import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.data.repository.EventRepository;
import com.enrealit.ppj.data.repository.PlaceRepository;
import com.enrealit.ppj.service.exception.CloudinaryPictureUploadException;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.EventFiltersDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.EventDateFilter;

@Service
public class EventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
	private static final double MAX_DISTANCE = 1.5d;

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private PlaceRepository placeRepo;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private ModelMapper mapper;

	public EventDto findById(String id) {
		Optional<Event> event = eventRepo.findById(id);
		return event.isPresent() ? convertToDto(event.get()) : null;
	}

	public List<EventDto> findByUser(UserDto user) {
		List<Event> events = eventRepo.findByUser(mapper.map(user, User.class));
		Type listType = new TypeToken<List<EventDto>>() {
		}.getType();
		return mapper.map(events, listType);
	}

	public EventDto createEvent(EventDto event, List<FileUploadDto> files) {
		if (files != null && !files.isEmpty()) {
			String folder = "events/" + event.getType().libelle() + "/" + event.getTitle();
			try {
				List<CloudinaryPictureDto> pics = pictureService.uploadPictures(folder, files);
				List<CloudinaryPictureDto> pictures = event.getPictures();
				if (pictures == null) {
					pictures = new ArrayList<>();
				}
				pictures.addAll(pics);
				event.setPictures(pictures);
			} catch (CloudinaryPictureUploadException ex) {
				LOGGER.error("Impossible d'uploader les images du plat", ex);
				return null;
			}
		}
		return save(event);
	}

	public List<EventDto> getEventsAroundMe(GeoJsonPointDto position, EventDateFilter eventDateFilter, Integer start,
			Integer limit, EventFiltersDto filters) {
		Point p = new Point(position.getX(), position.getY());
		Distance d = new Distance(
				filters != null && filters.getDistance() != null ? filters.getDistance() : MAX_DISTANCE,
				Metrics.KILOMETERS);
		Pageable page = PageRequest.of(start, limit);

		List<Place> places = placeRepo.findByAddressLocationNear(p, d);
		if (places == null || places.isEmpty()) {
			return new ArrayList<>();
		}
		List<EventDto> events = null;
		LocalDate availableOn = LocalDate.now();
		switch (eventDateFilter) {
		case TODAY:
//			events = eventRepo.findByPlaceInAndAvailableOn(places, availableOn, page).stream().map(e -> convertToDto(e))
//					.collect(Collectors.toList());
			break;
		case TOMORROW:
//			events = eventRepo.findByPlaceInAndAvailableOn(places, availableOn.plusDays(1), page).stream()
//					.map(e -> convertToDto(e)).collect(Collectors.toList());
			break;
		case SOON:
//			events = eventRepo.findByPlaceInAndAvailableOnGreaterThanEqual(places, availableOn.plusDays(2), page)
//					.stream().map(e -> convertToDto(e)).collect(Collectors.toList());
			break;
		default:
			return new ArrayList<>();
		}
		if (filters != null) {
			events = filterEvents(events, filters);
		}

		return events;
	}

	private List<EventDto> filterEvents(List<EventDto> events, EventFiltersDto filters) {
		return events.stream()
				.filter((e) -> ((!(e instanceof DishDto) || (e instanceof DishDto
						&& (filters.getMinPrice() == null || ((DishDto) e).getPrice() >= filters.getMinPrice())
						&& (filters.getMaxPrice() == null || ((DishDto) e).getPrice() <= filters.getMaxPrice())))
						&& (filters.getTags() == null || filters.getTags().isEmpty()
								|| e.getTags().stream().anyMatch(filters.getTags()::contains))))
				.collect(Collectors.toList());
	}

	public Boolean deleteEvent(String eventId) {
		eventRepo.deleteById(eventId);
		return true;
	}

	public EventDto save(EventDto event) {
		Event e = convertToEntity(event);
		return convertToDto(eventRepo.save(e));
	}

	private EventDto convertToDto(Event event) {
		return mapper.map(event, EventDto.class);
	}

	private Event convertToEntity(EventDto event) {
		return mapper.map(event, Event.class);
	}

}
