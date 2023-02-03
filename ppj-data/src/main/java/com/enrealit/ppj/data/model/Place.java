package com.enrealit.ppj.data.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import com.enrealit.ppj.data.model.user.User;
import com.enrealit.ppj.shared.enums.PlaceState;
import com.enrealit.ppj.shared.enums.PlaceStatus;
import com.enrealit.ppj.shared.enums.PlaceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "places")
public class Place extends EntityBase {

	private String googlePlaceId;
	private String name;
	private Address address;
	private String phoneNumber;
	private PlaceType type;
	private PlaceState state;
	private PlaceStatus status;
	private List<User> subscribers;
	private List<String> tags;
	private List<CloudinaryPicture> pictures;
	private String website;

}
