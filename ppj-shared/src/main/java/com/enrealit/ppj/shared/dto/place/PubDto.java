package com.enrealit.ppj.shared.dto.place;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PubDto extends PlaceDto {

	private Boolean kitchen;
	private String nationality;

	public PubDto() {
		_class = PlaceType.PUB._class();
		type = PlaceType.PUB;
	}

}
