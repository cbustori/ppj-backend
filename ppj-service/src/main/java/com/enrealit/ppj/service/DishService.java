package com.enrealit.ppj.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.Dish;
import com.enrealit.ppj.data.repository.DishRepository;
import com.enrealit.ppj.shared.dto.event.DishDto;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private ModelMapper mapper;

	public DishDto findById(String id) {
		return convertToDto(dishRepository.findById(id).get());
	}

	public DishDto save(DishDto d) {
		return convertToDto(dishRepository.save(convertToEntity(d)));
	}

	public List<DishDto> findAll() {
		return dishRepository.findAll().stream().map(dish -> convertToDto(dish)).collect(Collectors.toList());
	}

	private DishDto convertToDto(Dish dish) {
		return mapper.map(dish, DishDto.class);
	}

	private Dish convertToEntity(DishDto dish) {
		return mapper.map(dish, Dish.class);
	}

}
