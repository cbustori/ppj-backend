package com.enrealit.ppj.shared.dto.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enrealit.ppj.shared.dto.AddressDto;
import com.enrealit.ppj.shared.dto.BaseDto;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.ManagedPlaceDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.enums.SocialType;
import com.enrealit.ppj.shared.enums.UserStatus;
import com.enrealit.ppj.shared.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDto extends BaseDto {

	private String name;
	private String firstName;
	private String email;
	private String password;
	private AddressDto address;
	private String phoneNumber;
	private UserType type;
	private SocialType socialType;
	private UserStatus userStatus;
	private ProfileDto profile;
	private Date birthday;
	private CloudinaryPictureDto backgroundPicture;
	private CloudinaryPictureDto profilePicture;
	private List<ManagedPlaceDto> managedPlaces;
	private List<PlaceDto> favoritePlaces;
	private List<EventDto> favoriteEvents;

	public List<ManagedPlaceDto> getManagedPlaces() {
		if (managedPlaces == null) {
			managedPlaces = new ArrayList<>();
		}
		return managedPlaces;
	}

	public List<PlaceDto> getFavoritePlaces() {
		if (favoritePlaces == null) {
			favoritePlaces = new ArrayList<>();
		}
		return favoritePlaces;
	}

}
