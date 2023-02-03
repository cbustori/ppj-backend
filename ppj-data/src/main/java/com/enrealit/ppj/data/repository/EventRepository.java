package com.enrealit.ppj.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Event;
import com.enrealit.ppj.data.model.user.User;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	List<Event> findByUser(User user);

//	List<Event> findByAvailableOn(LocalDate date);
//
//	List<Event> findByPlaceAndAvailableOnBetween(Place place, LocalDate from, LocalDate to);
//
//	List<Event> findByPlaceInAndAvailableOn(List<Place> places, LocalDate date, Pageable pageable);
//
//	List<Event> findByPlaceInAndAvailableOnGreaterThanEqual(List<Place> places, LocalDate dateFrom, Pageable pageable);

}
