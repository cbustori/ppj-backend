package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Pub;

@Repository
public interface PubRepository extends MongoRepository<Pub, String> {

}
