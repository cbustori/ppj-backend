package com.enrealit.ppj.apiserver.resolver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.apiserver.exception.AccountNotAuthorizedException;
import com.enrealit.ppj.apiserver.exception.EventNotCreatedException;
import com.enrealit.ppj.apiserver.exception.PlaceNotCreatedException;
import com.enrealit.ppj.apiserver.exception.PlaceNotFoundException;
import com.enrealit.ppj.apiserver.exception.UserNotAllowedException;
import com.enrealit.ppj.apiserver.exception.UserNotFoundException;
import com.enrealit.ppj.apiserver.input.EventInput;
import com.enrealit.ppj.apiserver.input.PlaceInput;
import com.enrealit.ppj.apiserver.input.SchedulerItemInput;
import com.enrealit.ppj.apiserver.input.UserInput;
import com.enrealit.ppj.apiserver.security.SecurityHelper;
import com.enrealit.ppj.service.EventService;
import com.enrealit.ppj.service.PictureService;
import com.enrealit.ppj.service.PlaceService;
import com.enrealit.ppj.service.ProfileService;
import com.enrealit.ppj.service.SchedulerService;
import com.enrealit.ppj.service.UserService;
import com.enrealit.ppj.service.exception.AlreadyContributorException;
import com.enrealit.ppj.service.exception.InvalidEventTypeException;
import com.enrealit.ppj.service.exception.InvalidProfileException;
import com.enrealit.ppj.service.factory.ProfileFactory;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.SchedulerDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;
import com.enrealit.ppj.shared.enums.ProfileType;
import com.enrealit.ppj.shared.enums.UserStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class MutationResolver implements GraphQLMutationResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(MutationResolver.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private ProfileFactory profileFactory;

	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole())")
	public PlaceDto createPlace(PlaceInput input, List<FileUploadDto> files) throws JsonProcessingException {
		LOGGER.info("calling createPlace({})", input);
		String userId = SecurityHelper.getCurrentUserId();
		if (StringUtils.isBlank(userId)) {
			throw new UserNotFoundException(null, userId);
		}
		PlaceDto place = input.converToDto();
		ProfileType type = SecurityHelper.getCurrentProfile();
		ProfileService profile = profileFactory.getService(type);
		try {
			place = profile.createPlace(userId, place, files);
		} catch (InvalidProfileException e) {
			LOGGER.error("Fin en erreur: L'établissement n'a pas été créé: {}", input.getBusinessName());
			throw new AccountNotAuthorizedException(e.getMessage(), type);
		} catch (com.enrealit.ppj.service.exception.UserNotFoundException e) {
			LOGGER.error("Fin en erreur: Utilisateur inconnu: {}", userId);
			throw new UserNotFoundException(null, userId);
		}
		if (place != null) {
			userService.addManagedPlace(userId, place, ManagedPlaceRole.MANAGER);
			LOGGER.info("Returning value: {}", objectMapper.writeValueAsString(place));
			return place;
		}
		LOGGER.error("Fin en erreur: L'établissement n'a pas été créé: {}", input.getBusinessName());
		throw new PlaceNotCreatedException(input.getBusinessName());
	}

	@PreAuthorize("hasRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole())")
	public UserDto addContributor(String placeId, String contribEmail) throws JsonProcessingException {
		LOGGER.info("calling addContributor({}, {})", placeId, contribEmail);
		String userId = SecurityHelper.getCurrentUserId();
		if (StringUtils.isBlank(userId)) {
			throw new UserNotFoundException(null, userId);
		}
		ProfileType type = SecurityHelper.getCurrentProfile();
		ProfileService profile = profileFactory.getService(type);
		try {
			UserDto contrib = profile.addContributor(userId, placeId, contribEmail);
			if (contrib == null) {
				LOGGER.info("Une invitation a été envoyée à {}", contribEmail);
			}
			return contrib;
		} catch (InvalidProfileException e) {
			LOGGER.error("Fin en erreur: Le contributeur n'a pas été ajouté: {}", contribEmail);
			throw new AccountNotAuthorizedException(e.getMessage(), type);
		} catch (com.enrealit.ppj.service.exception.UserNotFoundException e) {
			LOGGER.error("Fin en erreur: Utilisateur inconnu: {}", userId);
			throw new UserNotFoundException(null, userId);
		} catch (com.enrealit.ppj.service.exception.PlaceNotFoundException e) {
			LOGGER.error("Fin en erreur: Le contributeur n'a pas été ajouté: {}", contribEmail);
			throw new PlaceNotFoundException(placeId);
		} catch (AlreadyContributorException e) {
			LOGGER.error("Fin en erreur: Ce contributeur existe déjà: {}", contribEmail);
			throw new AccountNotAuthorizedException(e.getMessage(), type);
		}
	}

	@PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(), T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public EventDto createEvent(EventInput input, List<FileUploadDto> files) throws JsonProcessingException {
		LOGGER.info("calling method: createEvent({}, {})", objectMapper.writeValueAsString(input),
				objectMapper.writeValueAsString(files));
		String userId = SecurityHelper.getCurrentUserId();
		if (StringUtils.isBlank(userId)) {
			throw new UserNotFoundException(null, userId);
		}
		EventDto event = input.convertToDto();
		event.setUser(userService.findById(userId));
		ProfileType type = SecurityHelper.getCurrentProfile();
		ProfileService profile = profileFactory.getService(type);
		try {
			event = profile.createEvent(userId, event, files);
		} catch (InvalidProfileException e) {
			LOGGER.error("Fin en erreur: L'évènement n'a pas été créé: {}", input.getTitle());
			throw new AccountNotAuthorizedException(e.getMessage(), type);
		} catch (InvalidEventTypeException e) {
			LOGGER.error("Fin en erreur: L'évènement n'a pas été créé: {}", input.getTitle());
			throw new EventNotCreatedException(input.getTitle());
		}
		if (event == null) {
			LOGGER.error("Fin en erreur: L'évènement n'a pas été créé: {}", input.getTitle());
			throw new EventNotCreatedException(input.getTitle());
		}
		LOGGER.info("Returning value: {}", objectMapper.writeValueAsString(event));
		return event;
	}

	@PreAuthorize("isAuthenticated()")
	public UserDto likePlace(String placeId) {
		LOGGER.info("calling method likePlace({})", placeId);
		String userId = SecurityHelper.getCurrentUserId();
		if (StringUtils.isBlank(userId)) {
			throw new UserNotFoundException(null, userId);
		}
		UserDto user = userService.findById(userId);
		if (user == null) {
			LOGGER.error("Fin en erreur: user {} introuvable", userId);
			throw new UserNotFoundException(null, userId);
		}
		PlaceDto place = placeService.findById(placeId);
		if (place == null) {
			LOGGER.error("Fin en erreur: place {} introuvable", placeId);
			throw new PlaceNotFoundException(placeId);
		}
		user.getFavoritePlaces().removeIf(p -> p.getId().equals(placeId));
		user.getFavoritePlaces().add(place);
		user = userService.save(user);
		LOGGER.info("returning value: {}", user);
		return user;
	}

	@PreAuthorize("isAuthenticated()")
	public UserDto setUserFirstConnection() {
		String id = SecurityHelper.getCurrentUserId();
		UserDto user = userService.findById(id);
		if (user == null || user.getUserStatus() == null
				|| !user.getUserStatus().equals(UserStatus.USER_FIRST_CONNECTION)) {
			throw new UserNotAllowedException("Statut de l'utilisateur invalide.");
		}
		user.setUserStatus(UserStatus.USER_CONFIRMED);
		userService.save(user);
		return user;
	}

	public UserDto updateProfile(UserInput userInput, FileUploadDto avatar) throws JsonProcessingException {
		LOGGER.info("calling method updateProfile({})", objectMapper.writeValueAsString(userInput));
		String id = SecurityHelper.getCurrentUserId();
		UserDto user = userService.findById(id);
		if (user != null) {
			user.setEmail(userInput.getEmail());
			user.setName(userInput.getLastName());
			user.setFirstName(userInput.getFirstName());
			user.setPhoneNumber(userInput.getPhoneNumber());
			return userService.updateProfile(user, userInput.getPassword(), avatar);
		}
		LOGGER.error("Fin en erreur: user {} introuvable", id);
		throw new UserNotFoundException(userInput.getEmail(), null);
	}

	@PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(), T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public Boolean deleteEvent(String eventId) {
		LOGGER.info("calling method deleteEvent({})", eventId);
		Boolean deleted = eventService.deleteEvent(eventId);
		LOGGER.info("returning value: {}", deleted);
		return deleted;
	}

	@PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(), T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public Boolean deletePicture(String publicId) {
		LOGGER.info("calling method deletePicture({})", publicId);
		Boolean deleted = pictureService.deletePicture(publicId);
		LOGGER.info("returning value: {}", deleted);
		return deleted;
	}

	@PreAuthorize("hasAnyRole(T(com.enrealit.ppj.shared.enums.UserType).MANAGER.getRole(), T(com.enrealit.ppj.shared.enums.UserType).CONTRIBUTOR.getRole())")
	public List<SchedulerDto> schedule(List<SchedulerItemInput> inputs) {
		LOGGER.info("calling method schedule({})", inputs);
		List<SchedulerDto> items = new ArrayList<>();
		inputs.stream().forEach(i -> {
			EventDto e = eventService.findById(i.getEvent());
			if (e == null) {
				LOGGER.warn("Evènement introuvable: {}", i.getEvent());
				return;
			}
			PlaceDto p = placeService.findById(i.getPlace());
			if (p == null) {
				LOGGER.warn("Place introuvable: {}", i.getPlace());
				return;
			}
			SchedulerDto s = SchedulerDto.builder().id(i.getId()).availableOn(i.getAvailableOn()).event(e).place(p)
					.build();
			items.add(s);
		});

		List<SchedulerDto> scheduler = schedulerService.schedule(items);
		LOGGER.info("returning values: {}", scheduler);
		return scheduler;
	}

}
