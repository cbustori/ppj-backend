package com.enrealit.ppj.shared.dto;

import java.util.List;

import com.enrealit.ppj.shared.enums.EventType;
import com.enrealit.ppj.shared.enums.PlanningView;
import com.enrealit.ppj.shared.enums.ProfileType;
import com.enrealit.ppj.shared.enums.SharingPlatform;

import lombok.Data;

@Data
public class AccountSettingsDto {

	private ProfileType type;
	private List<EventType> typeEvents;
	private Integer maxEtabs;
	private PlanningView planningView;
	private Integer maxContributors;
	private Integer maxEvents;
	private Double eventPromotingPrice;
	private Double notificationPrice;
	private List<SharingPlatform> sharingPlatforms;
	private Boolean advancedPlanificationAccess;
	private Boolean advancedCalendarAccess;

}
