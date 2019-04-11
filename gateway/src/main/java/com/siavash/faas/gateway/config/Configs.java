package com.siavash.faas.gateway.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Configs {

	private final Environment env;

	public Configs(final Environment env) {
		this.env = env;
	}

	public String getProviderBaseUrl() {
		return env.getRequiredProperty("faas.provider.base.url");
	}

	public Integer getProviderScaleMin() {
		return env.getRequiredProperty("faas.provider.scale.min", Integer.class);
	}

}
