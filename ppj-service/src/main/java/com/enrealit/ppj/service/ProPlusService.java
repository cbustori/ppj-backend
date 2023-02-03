package com.enrealit.ppj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.service.exception.AccountSettingsNotFoundException;
import com.enrealit.ppj.service.exception.AlreadyContributorException;
import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.service.exception.PlaceNotFoundException;
import com.enrealit.ppj.service.exception.UserNotFoundException;
import com.enrealit.ppj.shared.dto.AccountSettingsDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.ManagedPlaceDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;
import com.enrealit.ppj.shared.enums.ProfileType;

@Component
public class ProPlusService extends ProfileAbstractService {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private AccountSettingsService settingsService;

	@Override
	public EventDto createEvent(String userId, DishDto dish, List<FileUploadDto> files) {
		return eventService.createEvent(dish, files);
	}

	@Override
	public EventDto createEvent(String userId, ConcertDto concert, List<FileUploadDto> files)
			throws InvalidProfileException {
		return eventService.createEvent(concert, files);
	}

	@Override
	public EventDto createEvent(String userId, HappyHourDto hh, List<FileUploadDto> files)
			throws InvalidProfileException {
		return eventService.createEvent(hh, files);
	}

	@Override
	public UserDto addContributor(String userId, String placeId, String contribEmail)
			throws InvalidProfileException, UserNotFoundException, PlaceNotFoundException, AlreadyContributorException {
		AccountSettingsDto settings = settingsService.getAccount(ProfileType.PRO_PLUS);
		if (settings == null) {
			throw new AccountSettingsNotFoundException("Paramètres du profil introuvable", ProfileType.PRO_PLUS);
		}
		UserDto user = userService.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("Utilisateur inconnu.");
		}
		if (user.getManagedPlaces() == null || user.getManagedPlaces().isEmpty()) {
			throw new PlaceNotFoundException("Aucun établissement trouvé.", null);
		}
		Optional<ManagedPlaceDto> managedPlace = user.getManagedPlaces().stream()
				.filter(mp -> mp.getPlace().getId().equals(placeId)).findFirst();
		if (!managedPlace.isPresent() || ManagedPlaceRole.MANAGER != managedPlace.get().getRole()) {
			throw new InvalidProfileException("Vous n'êtes pas le responsable de cet établissement!");
		}
		List<UserDto> contributors = userService.getContributors(user);
		if (contributors != null && contributors.size() >= settings.getMaxContributors()) {
			throw new InvalidProfileException(
					"Vous avez atteint le maximum de contributeurs autorisés pour votre abonnement.");
		}
		return userService.addContributor(placeId, contribEmail);
	}

	@Override
	public PlaceDto createPlace(String userId, PlaceDto place, List<FileUploadDto> files)
			throws InvalidProfileException, UserNotFoundException {
		AccountSettingsDto settings = settingsService.getAccount(ProfileType.PRO_PLUS);
		if (settings == null) {
			throw new AccountSettingsNotFoundException("Paramètres du profil introuvable", ProfileType.PRO_PLUS);
		}
		UserDto user = userService.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("Utilisateur inconnu.");
		}
		if (place.getId() == null && user.getManagedPlaces().stream()
				.filter(mp -> mp.getRole() == ManagedPlaceRole.MANAGER).count() >= settings.getMaxEtabs()) {
			throw new InvalidProfileException(
					"Vous avez atteint le maximum d'établissements autorisés pour votre abonnement.");
		}
		place = placeService.createPlace(place, files);
		userService.addManagedPlace(userId, place, ManagedPlaceRole.MANAGER);
		return place;
	}

}
