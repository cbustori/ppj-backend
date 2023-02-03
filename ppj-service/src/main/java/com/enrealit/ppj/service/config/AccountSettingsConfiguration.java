package com.enrealit.ppj.service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.enrealit.ppj.data.model.AccountSettings;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "root")
@Data
public class AccountSettingsConfiguration {

	private Map<String, AccountSettings> accounts = new HashMap<>();

}
