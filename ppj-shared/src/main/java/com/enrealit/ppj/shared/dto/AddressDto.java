package com.enrealit.ppj.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

	private String street;
	private String zipCode;
	private String city;
	private String country;
	private GeoJsonPointDto location;
	private String icon;

}
