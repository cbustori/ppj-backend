package com.enrealit.ppj.shared.dto.place;

import java.util.Date;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HotelDto extends PlaceDto {

	private Date openingTime;
	private Integer nbStars;

	public HotelDto() {
		_class = PlaceType.HOTEL._class();
		type = PlaceType.HOTEL;
	}

}
