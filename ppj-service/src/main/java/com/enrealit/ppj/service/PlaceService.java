package com.enrealit.ppj.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.data.repository.PlaceRepository;
import com.enrealit.ppj.data.repository.UserRepository;
import com.enrealit.ppj.service.exception.CloudinaryPictureUploadException;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.enums.PlaceType;

@Service
public class PlaceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaceService.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private ModelMapper mapper;

	public PlaceDto findById(String id) {
		Optional<Place> place = placeRepository.findById(id);
		if (place.isPresent()) {
			return convertToDto(place.get());
		}
		return null;
	}

	public List<PlaceDto> findByIds(List<String> ids) {
		return placeRepository.findByIdIn(ids).stream().map(place -> convertToDto(place)).collect(Collectors.toList());
	}

	public List<PlaceDto> findAll() {
		return placeRepository.findAll().stream().map(place -> convertToDto(place)).collect(Collectors.toList());
	}

	public PlaceDto createPlace(PlaceDto p, List<FileUploadDto> files) {
		if (files != null && !files.isEmpty()) {
			String folder = "places/" + p.getName();
			try {
				List<CloudinaryPictureDto> pics = pictureService.uploadPictures(folder, files);
				List<CloudinaryPictureDto> pictures = p.getPictures();
				pictures.addAll(pics);
				p.setPictures(pictures);
			} catch (CloudinaryPictureUploadException e) {
				LOGGER.error("Impossible d'uploader les images de l'établissement", e);
			}
		}
		return save(p);
	}

	public PlaceDto save(PlaceDto place) {
		return convertToDto(placeRepository.save(convertToEntity(place)));
	}

	public List<PlaceDto> getPlacesAroundMe(GeoJsonPointDto position, Float distanceInKms, PlaceType type) {
		Point p = new Point(position.getX(), position.getY());
		Distance d = new Distance(distanceInKms != null ? distanceInKms : 0, Metrics.KILOMETERS);
		if (type != null) {
			return placeRepository.findByAddressLocationNearAndType(p, d, type).stream()
					.map(place -> convertToDto(place)).collect(Collectors.toList());
		} else {
			return placeRepository.findByAddressLocationNear(p, d).stream().map(place -> convertToDto(place))
					.collect(Collectors.toList());
		}
	}

	public List<PlaceDto> getFavoritePlaces(String userId) {
		Optional<User> u = userRepository.findById(userId);
		if (u.isPresent()) {
			return u.get().getFavoritePlaces().stream().map(place -> convertToDto(place)).collect(Collectors.toList());
		}
		return null;
	}

	public List<PlaceDto> getPlacesByManager(String userId) {
		Optional<User> u = userRepository.findById(userId);
		if (u.isPresent()) {
			return u.get().getManagedPlaces().stream().map(place -> convertToDto(place.getPlace()))
					.collect(Collectors.toList());
		}
		return null;
	}

	private PlaceDto convertToDto(Place place) {
		return mapper.map(place, PlaceDto.class);
	}

	private Place convertToEntity(PlaceDto place) {
		Place p = mapper.map(place, Place.class);
		return p;
	}

}
