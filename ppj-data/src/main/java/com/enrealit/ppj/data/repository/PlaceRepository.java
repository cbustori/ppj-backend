package com.enrealit.ppj.data.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.shared.enums.PlaceType;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

	List<Place> findByIdIn(List<String> ids);

	List<Place> findByAddressLocationNear(Point p, Distance d);

	List<Place> findByAddressLocationNearAndType(Point p, Distance d, PlaceType type);

}
