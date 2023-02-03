package com.enrealit.ppj.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrealit.ppj.data.model.AccountSettings;
import com.enrealit.ppj.data.repository.AccountSettingsRepository;
import com.enrealit.ppj.service.config.AccountSettingsConfiguration;
import com.enrealit.ppj.shared.dto.AccountSettingsDto;
import com.enrealit.ppj.shared.enums.ProfileType;

@Service
public class AccountSettingsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountSettingsService.class);

	@Autowired
	private AccountSettingsConfiguration settingsConfig;

	@Autowired
	private AccountSettingsRepository accountSettingsRepo;

	@Autowired
	private ModelMapper mapper;

	@PostConstruct
	public void saveAccountSettings() {
		LOGGER.info("Importation des paramètres des profils PRO, PRO+ et PREMIUM");
		accountSettingsRepo.deleteAll();
		Map<String, AccountSettings> profiles = settingsConfig.getAccounts();
		if (profiles != null && !profiles.isEmpty()) {
			profiles.forEach((k, v) -> {
				accountSettingsRepo.save(v);
			});
		}
	}

	public List<AccountSettingsDto> getAccounts() {
		List<AccountSettings> profiles = accountSettingsRepo.findAll();
		Type listType = new TypeToken<List<AccountSettingsDto>>() {
		}.getType();
		return mapper.map(profiles, listType);
	}

	public AccountSettingsDto getAccount(ProfileType type) {
		AccountSettings profile = accountSettingsRepo.findByType(type);
		return mapper.map(profile, AccountSettingsDto.class);
	}

}
