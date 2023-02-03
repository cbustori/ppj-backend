package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Dish;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

}
