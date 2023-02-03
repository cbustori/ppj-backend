package com.enrealit.ppj.service.config;

import java.util.Locale;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enrealit.ppj.data.model.Concert;
import com.enrealit.ppj.data.model.Dish;
import com.enrealit.ppj.data.model.Event;
import com.enrealit.ppj.data.model.HappyHour;
import com.enrealit.ppj.data.model.Hotel;
import com.enrealit.ppj.data.model.Office;
import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.data.model.Pub;
import com.enrealit.ppj.data.model.Restaurant;
import com.enrealit.ppj.service.exception.InvalidEventTypeException;
import com.enrealit.ppj.service.exception.InvalidPlaceTypeException;
import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.event.ConcertDto;
import com.enrealit.ppj.shared.dto.event.DishDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.event.HappyHourDto;
import com.enrealit.ppj.shared.dto.place.HotelDto;
import com.enrealit.ppj.shared.dto.place.OfficeDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.place.PubDto;
import com.enrealit.ppj.shared.dto.place.RestaurantDto;
import com.github.javafaker.Faker;
import com.google.maps.GeoApiContext;

@Configuration
public class PpjServiceConfiguration {

	@Value("${com.enrealit.ppj.gmaps.api_key}")
	private String googleMapApiKey;

	@Value("${com.enrealit.ppj.cloudinary.cloud_name}")
	private String cloudinaryCloudName;

	@Value("${com.enrealit.ppj.cloudinary.api_key}")
	private String cloudinaryApiKey;

	@Value("${com.enrealit.ppj.cloudinary.api_secret}")
	private String cloudinaryApiSecret;

	private ModelMapper mapper;

	@Bean
	public Faker faker() {
		return new Faker(new Locale("fr_FR"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public GeoApiContext geoApiContext() {
		return new GeoApiContext.Builder().apiKey(googleMapApiKey).build();
	}

	@Bean
	public Cloudinary getCloudinary() {
		return new Cloudinary(ObjectUtils.asMap("cloud_name", cloudinaryCloudName, "api_key", cloudinaryApiKey,
				"api_secret", cloudinaryApiSecret));
	}

	@Bean
	public ModelMapper modelMapper() {
		mapper = new ModelMapper();

		// mapping Place child classes: Place -> PlaceDto
		mapper.createTypeMap(Restaurant.class, PlaceDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), RestaurantDto.class));
		mapper.createTypeMap(Hotel.class, PlaceDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), HotelDto.class));
		mapper.createTypeMap(Pub.class, PlaceDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), PubDto.class));
		mapper.createTypeMap(Office.class, PlaceDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), OfficeDto.class));

		// mapping Place child classes: PlaceDto -> Place
		mapper.createTypeMap(RestaurantDto.class, Place.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Restaurant.class));
		mapper.createTypeMap(HotelDto.class, Place.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Hotel.class));
		mapper.createTypeMap(PubDto.class, Place.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Pub.class));
		mapper.createTypeMap(OfficeDto.class, Place.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Office.class));

		// mapping Event child classes: Event -> EventDto
		mapper.createTypeMap(Dish.class, EventDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), DishDto.class))
				.setPropertyConverter(placeConverter());
		mapper.createTypeMap(HappyHour.class, EventDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), HappyHourDto.class))
				.setPropertyConverter(placeConverter());
		mapper.createTypeMap(Concert.class, EventDto.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), ConcertDto.class))
				.setPropertyConverter(placeConverter());

		// mapping Event child classes: EventDto -> Event
		mapper.createTypeMap(DishDto.class, Event.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Dish.class))
				.setPropertyConverter(placeDtoConverter());
		mapper.createTypeMap(HappyHourDto.class, Event.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), HappyHour.class))
				.setPropertyConverter(placeDtoConverter());
		mapper.createTypeMap(ConcertDto.class, Event.class)
				.setConverter(mappingContext -> mapper.map(mappingContext.getSource(), Concert.class))
				.setPropertyConverter(placeDtoConverter());

		mapper.addConverter(placeConverter());
		mapper.addConverter(placeDtoConverter());
		mapper.addConverter(eventConverter());
		mapper.addConverter(eventDtoConverter());
		mapper.addConverter(geoPointConverter());
		mapper.addConverter(geoPointDtoConverter());

		return mapper;
	}

	private Converter<GeoJsonPointDto, GeoJsonPoint> geoPointConverter() {
		return new Converter<GeoJsonPointDto, GeoJsonPoint>() {
			@Override
			public GeoJsonPoint convert(MappingContext<GeoJsonPointDto, GeoJsonPoint> context) {
				GeoJsonPointDto p = context.getSource();
				return p == null ? null : new GeoJsonPoint(p.getX(), p.getY());
			}
		};
	}

	private Converter<GeoJsonPoint, GeoJsonPointDto> geoPointDtoConverter() {
		return new Converter<GeoJsonPoint, GeoJsonPointDto>() {
			@Override
			public GeoJsonPointDto convert(MappingContext<GeoJsonPoint, GeoJsonPointDto> context) {
				GeoJsonPoint p = context.getSource();
				return p == null ? null : new GeoJsonPointDto(p.getX(), p.getY());
			}
		};
	}

	private Converter<Place, PlaceDto> placeConverter() {
		return new Converter<Place, PlaceDto>() {
			@Override
			public PlaceDto convert(MappingContext<Place, PlaceDto> context) {
				return fromPlace(context.getSource());
			}
		};
	}

	private Converter<PlaceDto, Place> placeDtoConverter() {
		return new Converter<PlaceDto, Place>() {
			@Override
			public Place convert(MappingContext<PlaceDto, Place> context) {
				return fromPlace(context.getSource());
			}
		};
	}

	private PlaceDto fromPlace(Place p) {
		switch (p.getType()) {
		case RESTAURANT:
			return mapper.map(p, RestaurantDto.class);
		case HOTEL:
			return mapper.map(p, HotelDto.class);
		case OFFICE:
			return mapper.map(p, OfficeDto.class);
		case PUB:
			return mapper.map(p, PubDto.class);
		}
		throw new InvalidPlaceTypeException("L'emplacement ne correspond à aucun type connu", p.getType());
	}

	private Place fromPlace(PlaceDto p) {
		switch (p.getType()) {
		case RESTAURANT:
			return mapper.map(p, Restaurant.class);
		case HOTEL:
			return mapper.map(p, Hotel.class);
		case OFFICE:
			return mapper.map(p, Office.class);
		case PUB:
			return mapper.map(p, Pub.class);
		}
		throw new InvalidPlaceTypeException("L'emplacement ne correspond à aucun type connu", p.getType());
	}

	private Converter<Event, EventDto> eventConverter() {
		return new Converter<Event, EventDto>() {
			@Override
			public EventDto convert(MappingContext<Event, EventDto> context) {
				Event e = context.getSource();
				try {
					return fromEvent(e);
				} catch (InvalidEventTypeException e1) {
					return null;
				}
			}
		};
	}

	private Converter<EventDto, Event> eventDtoConverter() {
		return new Converter<EventDto, Event>() {
			@Override
			public Event convert(MappingContext<EventDto, Event> context) {
				try {
					return fromEvent(context.getSource());
				} catch (InvalidEventTypeException e) {
					return null;
				}
			}
		};
	}

	private EventDto fromEvent(Event e) throws InvalidEventTypeException {
		switch (e.getType()) {
		case DISH_OF_THE_DAY:
			return mapper.map(e, DishDto.class);
		case HAPPY_HOUR:
			return mapper.map(e, HappyHourDto.class);
		case CONCERT:
			return mapper.map(e, ConcertDto.class);
		case OTHER:
			break;
		}
		throw new InvalidEventTypeException("L'évènement ne correspond à aucun type connu", e.getType());
	}

	private Event fromEvent(EventDto e) throws InvalidEventTypeException {
		switch (e.getType()) {
		case DISH_OF_THE_DAY:
			return mapper.map(e, Dish.class);
		case HAPPY_HOUR:
			return mapper.map(e, HappyHour.class);
		case CONCERT:
			return mapper.map(e, Concert.class);
		case OTHER:
			break;
		}
		throw new InvalidEventTypeException("L'évènement ne correspond à aucun type connu", e.getType());
	}
}
