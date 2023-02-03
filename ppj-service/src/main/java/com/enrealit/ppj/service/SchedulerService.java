package com.enrealit.ppj.service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.Scheduler;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.data.repository.PlaceRepository;
import com.enrealit.ppj.data.repository.SchedulerRepository;
import com.enrealit.ppj.data.repository.UserRepository;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.SchedulerDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventFiltersDto;
import com.enrealit.ppj.shared.enums.EventDateFilter;
import com.enrealit.ppj.shared.enums.PlanningView;

@Service
public class SchedulerService {

	private static final double MAX_DISTANCE = 1.5d;

	@Autowired
	private SchedulerRepository schedulerRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PlaceRepository placeRepo;

	@Autowired
	private ModelMapper mapper;

	public List<SchedulerDto> getSchedulerByDate(String userId, LocalDate date, PlanningView view) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return null;
		}
		List<Place> mp = user.get().getManagedPlaces().stream().map(p -> p.getPlace()).collect(Collectors.toList());
		if (mp == null || mp.isEmpty()) {
			return null;
		}
		List<Scheduler> schedulers = new ArrayList<>();
		LocalDate start = null;
		LocalDate end = null;
		switch (view) {
		case DAY:
			schedulers = schedulerRepo.findByAvailableOnAndPlaceIn(date, mp);
			break;
		case WEEK:
			start = date.with(WeekFields.of(Locale.FRANCE).getFirstDayOfWeek());
			end = start.plusDays(6);
			schedulers = schedulerRepo.findByAvailableOnBetweenAndPlaceIn(start, end, mp);
			break;
		case MONTH:
			start = date.withDayOfMonth(1);
			end = date.withDayOfMonth(date.lengthOfMonth());
			schedulers = schedulerRepo.findByAvailableOnBetweenAndPlaceIn(start, end, mp);
			break;
		}
		return schedulers.stream().map(s -> mapper.map(s, SchedulerDto.class)).collect(Collectors.toList());
	}

	public List<SchedulerDto> schedule(List<SchedulerDto> scheduler) {
		List<SchedulerDto> list = new ArrayList<>();
		scheduler.forEach(s -> {
			list.add(mapper.map(schedulerRepo.save(mapper.map(s, Scheduler.class)), SchedulerDto.class));
		});
		return list;
	}

	public SchedulerDto findById(String id) {
		Optional<Scheduler> schedule = schedulerRepo.findById(id);
		return schedule.isPresent() ? mapper.map(schedule.get(), SchedulerDto.class) : null;
	}

	public List<SchedulerDto> getEventsAroundMe(GeoJsonPointDto position, EventDateFilter eventDateFilter,
			Integer start, Integer limit, EventFiltersDto filters) {
		Point p = new Point(position.getX(), position.getY());
		Distance d = new Distance(
				filters != null && filters.getDistance() != null ? filters.getDistance() : MAX_DISTANCE,
				Metrics.KILOMETERS);
		Pageable page = PageRequest.of(start, limit);

		List<Place> places = placeRepo.findByAddressLocationNear(p, d);
		if (places == null || places.isEmpty()) {
			return new ArrayList<>();
		}
		List<SchedulerDto> events = null;
		LocalDate availableOn = LocalDate.now();
		switch (eventDateFilter) {
		case TODAY:
			events = schedulerRepo.findByPlaceInAndAvailableOn(places, availableOn, page).stream()
					.map(e -> mapper.map(e, SchedulerDto.class)).collect(Collectors.toList());
			break;
		case TOMORROW:
			events = schedulerRepo.findByPlaceInAndAvailableOn(places, availableOn.plusDays(1l), page).stream()
					.map(e -> mapper.map(e, SchedulerDto.class)).collect(Collectors.toList());
			break;
		case SOON:
			events = schedulerRepo.findByPlaceInAndAvailableOnGreaterThanEqual(places, availableOn.plusDays(2), page)
					.stream().map(e -> mapper.map(e, SchedulerDto.class)).collect(Collectors.toList());
			break;
		default:
			return new ArrayList<>();
		}
		if (filters != null) {
			events = filterEvents(events, filters);
		}

		return events;
	}

	private List<SchedulerDto> filterEvents(List<SchedulerDto> events, EventFiltersDto filters) {
		return events.stream().filter((e) -> ((!(e.getEvent() instanceof DishDto) || (e.getEvent() instanceof DishDto
				&& (filters.getMinPrice() == null || ((DishDto) e.getEvent()).getPrice() >= filters.getMinPrice())
				&& (filters.getMaxPrice() == null || ((DishDto) e.getEvent()).getPrice() <= filters.getMaxPrice())))
				&& (filters.getTags() == null || filters.getTags().isEmpty()
						|| e.getEvent().getTags().stream().anyMatch(filters.getTags()::contains))))
				.collect(Collectors.toList());
	}

}
