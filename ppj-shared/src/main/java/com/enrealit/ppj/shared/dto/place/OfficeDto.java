package com.enrealit.ppj.shared.dto.place;

import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class OfficeDto extends PlaceDto {

	private String dummy;

	public OfficeDto() {
		_class = PlaceType.OFFICE._class();
		type = PlaceType.OFFICE;
	}

}
