package com.jaqp7363.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig {
	@Value("${jaqp7363.api.key}")
	private String apiKey;
	
	public String getApiKey() {
		return apiKey;
	}
}
