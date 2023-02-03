package com.enrealit.ppj.data.model;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

	private String street;
	private String zipCode;
	private String city;
	private String country;
	@GeoSpatialIndexed(name = "idx_location_2dsphere", type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	private String icon;

}
