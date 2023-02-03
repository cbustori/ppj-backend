package com.enrealit.ppj.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.Restaurant;
import com.enrealit.ppj.data.repository.RestaurantRepository;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ModelMapper mapper;

	public RestaurantDto findById(String id) {
		return convertToDto(restaurantRepository.findById(id).get());
	}

	public List<RestaurantDto> getAllRestaurants() {
		return restaurantRepository.findAll().stream().map(restaurant -> convertToDto(restaurant))
				.collect(Collectors.toList());
	}

	public RestaurantDto save(RestaurantDto r) {
		return convertToDto(restaurantRepository.save(convertToEntity(r)));
	}

	private RestaurantDto convertToDto(Restaurant r) {
		return mapper.map(r, RestaurantDto.class);
	}

	private Restaurant convertToEntity(RestaurantDto r) {
		return mapper.map(r, Restaurant.class);
	}

}
