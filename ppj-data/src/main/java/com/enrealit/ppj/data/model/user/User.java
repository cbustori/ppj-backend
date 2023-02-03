package com.enrealit.ppj.data.model.user;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.enrealit.ppj.data.model.Address;
import com.enrealit.ppj.data.model.CloudinaryPicture;
import com.enrealit.ppj.data.model.EntityBase;
import com.enrealit.ppj.data.model.Event;
import com.enrealit.ppj.data.model.ManagedPlace;
import com.enrealit.ppj.data.model.Place;
import com.enrealit.ppj.shared.enums.SocialType;
import com.enrealit.ppj.shared.enums.UserStatus;
import com.enrealit.ppj.shared.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
@TypeAlias("user")
public class User extends EntityBase {

	private String name;
	private String firstName;
	private String email;
	private String password;
	private String phoneNumber;
	private Address address;
	private UserType type;
	private Date birthday;
	private SocialType socialType;
	private UserStatus userStatus;
	private Profile profile;
	private CloudinaryPicture backgroundPicture;
	private CloudinaryPicture profilePicture;
	private List<ManagedPlace> managedPlaces;
	@DBRef
	private List<Place> favoritePlaces;
	private List<Event> favoriteEvents;

}
