package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.enrealit.ppj.data.model.AccountSettings;
import com.enrealit.ppj.shared.enums.ProfileType;

@Repository
public interface AccountSettingsRepository extends MongoRepository<AccountSettings, String> {

	AccountSettings findByType(ProfileType type);

}
