package com.enrealit.ppj.apiserver.resolver;

import java.util.List;

import com.enrealit.ppj.apiserver.input.EventInput;
import com.enrealit.ppj.apiserver.input.PlaceInput;
import com.enrealit.ppj.apiserver.input.SchedulerItemInput;
import com.enrealit.ppj.apiserver.input.UserInput;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.SchedulerDto;
import com.enrealit.ppj.shared.dto.event.EventDto;
import com.enrealit.ppj.shared.dto.place.PlaceDto;
import com.enrealit.ppj.shared.dto.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PpjGraphQLMutation {

	UserDto addContributor(String placeId, String contribEmail) throws JsonProcessingException;

	PlaceDto createPlace(PlaceInput place, List<FileUploadDto> pictures) throws JsonProcessingException;

	EventDto createEvent(EventInput event, List<FileUploadDto> pictures) throws JsonProcessingException;

	UserDto likePlace(String placeId) throws JsonProcessingException;

	UserDto updateProfile(UserInput user, FileUploadDto avatarPicture) throws JsonProcessingException;

	UserDto setUserFirstConnection() throws JsonProcessingException;

	Boolean deleteEvent(String eventId) throws JsonProcessingException;

	Boolean deletePicture(String publicId) throws JsonProcessingException;

	List<SchedulerDto> schedule(List<SchedulerItemInput> scheduleItems) throws JsonProcessingException;

}
