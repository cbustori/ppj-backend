package com.enrealit.ppj.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enrealit.ppj.service.PremiumService;
import com.enrealit.ppj.service.ProPlusService;
import com.enrealit.ppj.service.ProService;
import com.enrealit.ppj.service.ProfileService;
import com.enrealit.ppj.service.exception.TypeAccountNotFoundException;
import com.enrealit.ppj.shared.enums.ProfileType;

@Component
public class ProfileFactory {

	@Autowired
	private ProService proService;

	@Autowired
	private ProPlusService proPlusService;

	@Autowired
	private PremiumService premiumService;

	public ProfileService getService(ProfileType type) {
		switch (type) {
		case PRO:
			return proService;
		case PRO_PLUS:
			return proPlusService;
		case PREMIUM:
			return premiumService;
		}
		throw new TypeAccountNotFoundException("Type de compte non défini.");
	}

}
