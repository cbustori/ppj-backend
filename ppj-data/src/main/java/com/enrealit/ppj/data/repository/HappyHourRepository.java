package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.HappyHour;

@Repository
public interface HappyHourRepository extends MongoRepository<HappyHour, String> {

	// HappyHour findByStartingTime(Date start);

}
