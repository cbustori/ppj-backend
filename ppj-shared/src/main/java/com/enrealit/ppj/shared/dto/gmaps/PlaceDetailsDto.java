package com.enrealit.ppj.shared.dto.gmaps;

import com.enrealit.ppj.shared.dto.GeoJsonPointDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceDetailsDto {

	private String placeId;
	private String name;
	private String icon;
	private String postalCode;
	private String city;
	private String country;
	private String vicinity;
	private String formattedAddress;
	private GeoJsonPointDto geometry;
	private String phoneNumber;
	private Integer priceLevel;
	private String openingHours;
	private String website;

}
