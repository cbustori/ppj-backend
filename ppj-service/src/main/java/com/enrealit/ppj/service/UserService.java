package com.enrealit.ppj.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.ManagedPlace;
import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.data.repository.PlaceRepository;
import com.enrealit.ppj.data.repository.UserRepository;
import com.enrealit.ppj.service.exception.AlreadyContributorException;
import com.enrealit.ppj.service.exception.CloudinaryPictureUploadException;
import com.enrealit.ppj.service.exception.PlaceNotFoundException;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.ManagedPlaceDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	public UserDto findById(String id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? convertToDto(user.get()) : null;
	}

	public UserDto findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		return user != null ? convertToDto(user) : null;
	}

	public UserDto addManagedPlace(String userId, PlaceDto place, ManagedPlaceRole role) {
		UserDto user = findById(userId);
		if (user != null) {
			user.getManagedPlaces().removeIf(p -> p.getPlace().getId().equals(place.getId()));
			user.getManagedPlaces().add(new ManagedPlaceDto(place, role));
			return save(user);
		}
		return null;
	}

	public UserDto updateProfile(UserDto user, String password, FileUploadDto avatar) {
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(passwordEncoder.encode(password));
		}
		if (avatar != null) {
			try {
				CloudinaryPictureDto pic = pictureService.uploadProfilePicture(user, avatar);
				if (pic != null) {
					user.setProfilePicture(pic);
				}
			} catch (CloudinaryPictureUploadException e) {
				LOGGER.error("Impossible d'uploader l'image du profil", e);
			}
		}
		return save(user);
	}

	public UserDto addContributor(String placeId, String contribEmail)
			throws AlreadyContributorException, PlaceNotFoundException {
		Optional<Place> p = placeRepository.findById(placeId);
		if (!p.isPresent()) {
			throw new PlaceNotFoundException("Place introuvable", placeId);
		}
		Optional<User> contrib = userRepository.findByEmail(contribEmail);
		if (!contrib.isPresent()) {
			sendRequestToContributor(contribEmail);
			LOGGER.info("Invitation envoyée par email");
			return null;
		}
		User contributor = contrib.get();
		ManagedPlace mp = new ManagedPlace(p.get(), ManagedPlaceRole.CONTRIBUTOR);
		if (contributor.getManagedPlaces() != null && contributor.getManagedPlaces().stream()
				.anyMatch(m -> m.getPlace().getId().equals(placeId) && m.getRole() == ManagedPlaceRole.CONTRIBUTOR)) {
			throw new AlreadyContributorException("Cette personne est déjà contributeur de votre établissement",
					contributor.getId(), placeId);
		}
		if (contributor.getManagedPlaces() == null) {
			contributor.setManagedPlaces(new ArrayList<>());
		}
		contributor.getManagedPlaces().add(mp);
		return convertToDto(userRepository.save(contributor));
	}

	private void sendRequestToContributor(String contribEmail) {
		// TODO
	}

	public List<UserDto> getContributors(UserDto user) {
		List<UserDto> contributors = new ArrayList<>();
		if (user != null) {
			User u = mapper.map(user, User.class);
			List<User> entities = userRepository
					.findByManagedPlacesPlaceInAndManagedPlacesRole(
							u.getManagedPlaces().stream().filter(mp -> ManagedPlaceRole.MANAGER == mp.getRole())
									.map(mp -> mp.getPlace()).collect(Collectors.toList()),
							ManagedPlaceRole.CONTRIBUTOR);
			Type listType = new TypeToken<List<UserDto>>() {
			}.getType();
			contributors = mapper.map(entities, listType);
		}
		return contributors;
	}

	public UserDto save(UserDto user) {
		return convertToDto(userRepository.save(convertToEntity(user)));
	}

	private UserDto convertToDto(User u) {
		return mapper.map(u, UserDto.class);
	}

	private User convertToEntity(UserDto u) {
		return mapper.map(u, User.class);
	}

}
