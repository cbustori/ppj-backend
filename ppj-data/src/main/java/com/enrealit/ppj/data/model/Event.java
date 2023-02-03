package com.enrealit.ppj.data.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.shared.enums.EventType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "events")
public abstract class Event extends EntityBase {

	@DBRef
	private User user;
	private String title;
	private String description;
	private EventType type;
	private List<String> tags;
	private List<CloudinaryPicture> pictures;

}
