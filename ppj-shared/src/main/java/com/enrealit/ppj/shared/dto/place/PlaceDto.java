package com.enrealit.ppj.shared.dto.place;

import java.util.ArrayList;
import java.util.List;

import com.enrealit.ppj.shared.dto.AddressDto;
import com.enrealit.ppj.shared.dto.BaseDto;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.enums.PlaceState;
import com.enrealit.ppj.shared.enums.PlaceStatus;
import com.enrealit.ppj.shared.enums.PlaceType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class PlaceDto extends BaseDto {

	private String googlePlaceId;
	private String name;
	private AddressDto address;
	private String phoneNumber;
	protected PlaceType type;
	private PlaceState state;
	private PlaceStatus status;
	private List<String> tags;
	private List<CloudinaryPictureDto> pictures;
	private String website;

	public List<CloudinaryPictureDto> getPictures() {
		if (pictures == null) {
			pictures = new ArrayList<CloudinaryPictureDto>();
		}
		return pictures;
	}
}
