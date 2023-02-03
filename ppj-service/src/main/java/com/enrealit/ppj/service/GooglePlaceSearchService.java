package com.enrealit.ppj.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.shared.dto.GeoJsonPointDto;
import com.enrealit.ppj.shared.dto.gmaps.GooglePlaceDto;
import com.enrealit.ppj.shared.dto.gmaps.PlaceDetailsDto;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.PlaceAutocompleteType;
import com.google.maps.model.PlaceDetails;

@Service
public class GooglePlaceSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GooglePlaceSearchService.class);

	@Autowired
	private GeoApiContext context;

	public List<GooglePlaceDto> searchPlaces(String input) {
		PlaceAutocompleteRequest req = PlacesApi.placeAutocomplete(context, input, null);
		req = req.components(ComponentFilter.country("fr"));
		req = req.types(PlaceAutocompleteType.ESTABLISHMENT);
		try {
			AutocompletePrediction[] resp = req.await();
			return convertToDto(resp);
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error("", e);
		}
		return null;
	}

	public PlaceDetailsDto getPlaceDetails(String placeId) {
		PlaceDetailsRequest req = PlacesApi.placeDetails(context, placeId);
		req = req.fields(PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
				PlaceDetailsRequest.FieldMask.ADDRESS_COMPONENT, PlaceDetailsRequest.FieldMask.GEOMETRY,
				PlaceDetailsRequest.FieldMask.ICON, PlaceDetailsRequest.FieldMask.PLACE_ID,
				PlaceDetailsRequest.FieldMask.NAME, PlaceDetailsRequest.FieldMask.PRICE_LEVEL,
				PlaceDetailsRequest.FieldMask.OPENING_HOURS, PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER,
				PlaceDetailsRequest.FieldMask.WEBSITE, PlaceDetailsRequest.FieldMask.VICINITY);
		PlaceDetailsDto dto = new PlaceDetailsDto();
		try {
			PlaceDetails details = req.await();
			dto.setPlaceId(details.placeId);
			dto.setName(details.name);
			dto.setVicinity(details.vicinity);
			dto.setPostalCode(getComponent(AddressComponentType.POSTAL_CODE, details.addressComponents));
			dto.setCity(getComponent(AddressComponentType.LOCALITY, details.addressComponents));
			dto.setCountry(getComponent(AddressComponentType.COUNTRY, details.addressComponents));
			dto.setPhoneNumber(details.formattedPhoneNumber);
			dto.setPriceLevel(details.priceLevel != null ? details.priceLevel.ordinal() : -1);
			dto.setFormattedAddress(details.formattedAddress);
			dto.setWebsite(details.website != null ? details.website.toString() : null);
			dto.setIcon(details.icon != null ? details.icon.toString() : null);
			if (details.geometry != null && details.geometry.location != null) {
				dto.setGeometry(new GeoJsonPointDto(details.geometry.location.lat, details.geometry.location.lng));
			}
			// dto.setOpeningHours(details.openingHours);
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error("", e);
		}
		return dto;
	}

	private String getComponent(AddressComponentType type, AddressComponent[] components) {
		for (AddressComponent c : components) {
			for (AddressComponentType t : c.types) {
				if (t == type) {
					return c.longName;
				}
			}
		}
		return null;
	}

	private List<GooglePlaceDto> convertToDto(AutocompletePrediction[] results) {
		List<GooglePlaceDto> list = new ArrayList<>();
		for (AutocompletePrediction r : results) {
			GooglePlaceDto place = new GooglePlaceDto();
			place.setPlaceId(r.placeId);
			place.setName(r.description);
			list.add(place);
		}
		return list;
	}
}
