package com.enrealit.ppj.shared.dto.event;

import java.util.ArrayList;
import java.util.List;

import com.enrealit.ppj.shared.dto.BaseDto;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class EventDto extends BaseDto {

	private String title;
	private String description;
	protected EventType type;
	private List<String> tags;
	private List<CloudinaryPictureDto> pictures;
	private UserDto user;

	public List<String> getTags() {
		if (tags == null) {
			tags = new ArrayList<String>();
		}
		return tags;
	}

	public List<CloudinaryPictureDto> getPictures() {
		if (pictures == null) {
			pictures = new ArrayList<CloudinaryPictureDto>();
		}
		return pictures;
	}

}
