package com.enrealit.ppj.shared.dto;

import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.enums.ManagedPlaceRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagedPlaceDto {

	private PlaceDto place;
	private ManagedPlaceRole role;

}
