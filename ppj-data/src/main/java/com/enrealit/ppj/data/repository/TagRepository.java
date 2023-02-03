package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

	Tag findByLabel(String label);

}
