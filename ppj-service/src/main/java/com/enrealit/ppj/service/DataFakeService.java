package com.enrealit.ppj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.shared.dto.AddressDto;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.ManagedPlaceDto;
import com.enrealit.ppj.shared.dto.TagDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.EventType;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;
import com.enrealit.ppj.shared.enums.PlaceType;
import com.enrealit.ppj.shared.enums.UserType;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;

@Service
@Profile("local")
public class DataFakeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataFakeService.class);

	@Value("${com.enrealit.ppj.service.fakes}")
	private Boolean fakes;

	@Value("${com.enrealit.ppj.service.position.lat}")
	private Double latitude;

	@Value("${com.enrealit.ppj.service.position.lng}")
	private Double longitude;

	@Value("${com.enrealit.ppj.service.position.distance}")
	private Integer distance;

	@Value("${com.enrealit.ppj.service.fakes.places}")
	private Integer nbFakePlaces;

	@Value("${com.enrealit.ppj.service.fakes.events}")
	private Integer nbFakeEvents;

	@Value("${com.enrealit.ppj.service.fakes.tags}")
	private Integer nbFakeTags;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Faker faker;

	private final String[] groups = { "food-type" };

	@PostConstruct
	public void generateFakeDatas() {
		if (fakes == null || !fakes) {
			LOGGER.info("GENERATION DE DONNEES ALEATOIRES DESACTIVEE");
			return;
		}
		UserDto admin = generateAdministrator();
		LOGGER.info("GENERATION DE {} TAGS ALEATOIRES", nbFakeTags);
		generateFakeTags();
		LOGGER.info("INSERTION DE {} RESTAURANTS AUTOUR DE LA POSITION ({},{}) ET DANS UN RAYON DE {} KMS",
				nbFakePlaces, latitude, longitude, (distance / 1000));
		TagDto tag = tagService.findByLabel(groups[0]);
		for (int i = 0; i < nbFakePlaces; i++) {
			PlaceDto r = generateRandomRestaurant();
			// r.setTags(tag.getTags());
			// PlaceDto pub = generateRandomPub();
			try {
				r = placeService.save(r);
				admin.getManagedPlaces().add(new ManagedPlaceDto(r, ManagedPlaceRole.MANAGER));
				userService.save(admin);
				generateRandomWeeklyEvents(r, tag);

				// pub = placeService.save(pub);
				// generateRandomWeeklyEvents(pub);
			} catch (Exception e) {
				LOGGER.error("INSERTION DE LA PLACE IMPOSSIBLE", e);
			}
		}
		LOGGER.info("GENERATION DES {} PLACES TERMINEE. FIN NORMALE", nbFakePlaces);
	}

	private UserDto generateAdministrator() {
		String email = "admin@enrealit.fr";
		UserDto admin = userService.findByEmail(email);
		if (admin != null) {
			return admin;
		}
		Address adr = faker.address();

		UserDto u = UserDto.builder().email(email).password(passwordEncoder.encode("ppj")).firstName("SUPER")
				.name("ADMIN").type(UserType.ADMIN).phoneNumber(faker.phoneNumber().phoneNumber())
				.address(AddressDto.builder().street(adr.streetAddress()).city(adr.city()).country(adr.country())
						.zipCode(adr.zipCode()).build())
				.build();
		// m.setProfilePicture(faker.avatar().image());
		// m.setBackgroundPicture(faker.company().logo());

		return userService.save(u);
	}

	private void generateFakeTags() {
		if (nbFakeTags == 0) {
			return;
		}
		for (String group : groups) {
			List<String> tags = new ArrayList<>();
			for (int i = 0; i < nbFakeTags; i++) {
				String tag = faker.hipster().word();
				tags.add(tag);
			}
			TagDto g = tagService.findByLabel(group);
			if (g == null) {
				g = TagDto.builder().label(group).tags(tags).build();
			}
			tagService.save(g);
		}
	}

	/**
	 * Génération de fakes évènements sur une période de n jours à partir de la date
	 * actuelle
	 * 
	 * @param p l'endroit où seront rattachés les évènements à créer
	 */
	private void generateRandomWeeklyEvents(PlaceDto p, TagDto groupTag) {
		for (int i = 0; i < nbFakeEvents; i++) {
			DishDto d = generateRandomDish(p);
			// d.setTags(groupTag.getTags());
			try {
				eventService.save(d);
				LOGGER.info("INSERTION DE L'EVENEMENT EFFECTUE: {}", d.getId());
			} catch (Exception e) {
				LOGGER.error("INSERTION DE L'EVENEMENT IMPOSSIBLE", e);
			}
		}
	}

//	private UserDto generateRandomManager() {
//		UserDto m = new UserDto();
//		m.setEmail(faker.internet().emailAddress());
//		m.setPassword(passwordEncoder.encode("secret"));
//		m.setFirstName(faker.artist().name());
//		m.setName(faker.superhero().name());
//		m.setType(UserType.MANAGER);
//		m.setPhoneNumber(faker.phoneNumber().phoneNumber());
//		Address adr = faker.address();
//		m.setAddress(AddressDto.builder().street(adr.streetAddress()).city(adr.city()).country(adr.country())
//				.zipCode(adr.zipCode()).build());
//		m.setProfilePicture(faker.avatar().image());
//		m.setBackgroundPicture(faker.internet().image());
//
//		return userService.save(m);
//
//	}

	private RestaurantDto generateRandomRestaurant() {
		String name = faker.company().name();
		String address = faker.address().streetAddress();
		String zipCode = faker.address().zipCode();
		String city = faker.address().city();
		String country = faker.address().country();

		GeoJsonPointDto randomPos = randomLocation();

		RestaurantDto r = new RestaurantDto();
		r.setType(PlaceType.RESTAURANT);
		r.setName(name);
		r.setAddress(AddressDto.builder().street(address).zipCode(zipCode).city(city).country(country)
				.location(randomPos).build());
		// r.setFoodType(faker.food().spice());
		// r.setPictures(Arrays.asList(faker.company().logo()));

		LOGGER.debug("INSERTION DE {} A LA POSITION: ({},{}) REUSSIE", name, randomPos.getX(), randomPos.getY());

		return r;
	}

	private DishDto generateRandomDish(PlaceDto p) {
		String description = faker.food().ingredient();
		DishDto d = new DishDto();
		d.setDescription(description);
		d.setPrice(faker.number().randomDouble(2, 8, 40));
		d.setType(EventType.DISH_OF_THE_DAY);

		return d;
	}

	/**
	 * Génère une coordonnée géographique random à partir d'une latitude/longitude
	 * et d'un périmètre défini
	 * 
	 * @return un point géospatial spécifique à MongoDB
	 */
	private GeoJsonPointDto randomLocation() {
		Random random = new Random();

		// Convert radius from meters to degrees
		double radiusInDegrees = distance / 111000f;

		double u = random.nextDouble();
		double v = random.nextDouble();
		double w = radiusInDegrees * Math.sqrt(u);
		double t = 2 * Math.PI * v;
		double x = w * Math.cos(t);
		double y = w * Math.sin(t);

		// Adjust the x-coordinate for the shrinking of the east-west distances
		double newX = x / Math.cos(Math.toRadians(latitude));

		double lat = newX + latitude;
		double lng = y + longitude;
		return new GeoJsonPointDto(lat, lng);
	}

}
