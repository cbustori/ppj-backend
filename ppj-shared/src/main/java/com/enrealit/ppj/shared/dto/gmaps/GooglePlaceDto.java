package com.enrealit.ppj.shared.dto.gmaps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GooglePlaceDto {

	private String placeId;
	private String name;

}
