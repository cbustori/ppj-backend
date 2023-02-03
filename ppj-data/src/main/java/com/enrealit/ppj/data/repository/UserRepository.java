package com.enrealit.ppj.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;
import com.enrealit.ppj.shared.enums.SocialType;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);

	List<User> findByManagedPlacesPlaceInAndManagedPlacesRole(List<Place> places, ManagedPlaceRole role);

	User findByEmailAndSocialType(String email, SocialType socialType);

	Boolean existsByEmail(String email);

}
