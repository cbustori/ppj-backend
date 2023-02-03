package com.enrealit.ppj.data.model;

import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.enrealit.ppj.shared.enums.EventType;
import com.enrealit.ppj.shared.enums.PlanningView;
import com.enrealit.ppj.shared.enums.ProfileType;
import com.enrealit.ppj.shared.enums.SharingPlatform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "account_settings")
@TypeAlias("account_settings")
public class AccountSettings {

	private ProfileType type;
	private List<EventType> typeEvents;
	private Integer maxEtabs;
	private Integer maxEvents;
	private PlanningView planningView;
	private Integer maxContributors;
	private Double eventPromotingPrice;
	private Double notificationPrice;
	private List<SharingPlatform> sharingPlatforms;
	private Boolean advancedPlanificationAccess;
	private Boolean advancedCalendarAccess;

}
