package com.enrealit.ppj.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.Scheduler;

@Repository
public interface SchedulerRepository extends MongoRepository<Scheduler, String> {
	
	List<Scheduler> findByAvailableOnAndPlace(LocalDate date, Place place);
	
	List<Scheduler> findByAvailableOnAndPlaceIn(LocalDate date, List<Place> places);
	
	List<Scheduler> findByAvailableOnBetweenAndPlaceIn(LocalDate start, LocalDate end, List<Place> places);
	
	List<Scheduler> findByPlaceInAndAvailableOn(List<Place> places, LocalDate date, Pageable pageable);
	
	List<Scheduler> findByPlaceInAndAvailableOnGreaterThanEqual(List<Place> places, LocalDate dateFrom, Pageable pageable);

}
