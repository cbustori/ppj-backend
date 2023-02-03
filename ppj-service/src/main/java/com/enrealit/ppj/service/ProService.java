package com.enrealit.ppj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.service.exception.AccountSettingsNotFoundException;
import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.shared.dto.AccountSettingsDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;
import com.enrealit.ppj.shared.enums.ProfileType;

@Component
public class ProService extends ProfileAbstractService {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private AccountSettingsService settingsService;

	@Override
	public EventDto createEvent(String userId, DishDto dish, List<FileUploadDto> files) throws InvalidProfileException {
		AccountSettingsDto settings = settingsService.getAccount(ProfileType.PRO);
		if (settings == null) {
			throw new AccountSettingsNotFoundException("Paramètres du profil introuvable", ProfileType.PRO);
		}
		UserDto user = userService.findById(userId);
		List<EventDto> list = eventService.findByUser(user);
		if (dish.getId() == null && list != null && list.size() >= settings.getMaxEvents()) {
			throw new InvalidProfileException(
					"Vous avez atteint le maximum d'évènements autorisés pour votre abonnement.");
		}
		return eventService.createEvent(dish, files);
	}

	@Override
	public EventDto createEvent(String userId, ConcertDto concert, List<FileUploadDto> files)
			throws InvalidProfileException {
		throw new InvalidProfileException("Votre abonnement en cours ne vous permet pas d'accèder à cette ressource");
	}

	@Override
	public EventDto createEvent(String userId, HappyHourDto hh, List<FileUploadDto> files)
			throws InvalidProfileException {
		throw new InvalidProfileException("Votre abonnement en cours ne vous permet pas d'accèder à cette ressource");
	}

	@Override
	public UserDto addContributor(String userId, String placeId, String contribEmail) throws InvalidProfileException {
		throw new InvalidProfileException("Votre abonnement en cours ne vous permet pas d'accèder à cette ressource");
	}

	@Override
	public PlaceDto createPlace(String userId, PlaceDto place, List<FileUploadDto> files)
			throws InvalidProfileException {
		AccountSettingsDto settings = settingsService.getAccount(ProfileType.PRO);
		if (settings == null) {
			throw new AccountSettingsNotFoundException("Paramètres du profil introuvable", ProfileType.PRO);
		}
		UserDto user = userService.findById(userId);
		if (place.getId() == null && user.getManagedPlaces() != null && user.getManagedPlaces().stream()
				.filter(mp -> ManagedPlaceRole.MANAGER == mp.getRole()).count() >= settings.getMaxEtabs()) {
			throw new InvalidProfileException(
					"Vous avez atteint le maximum d'établissements autorisés pour votre abonnement.");
		}
		place = placeService.createPlace(place, files);
		user = userService.addManagedPlace(userId, place, ManagedPlaceRole.MANAGER);
		return place;
	}

}
