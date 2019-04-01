package com.siavash.faas.provider.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProviderConfigs {

	private final Environment env;

	public ProviderConfigs(final Environment env) {
		this.env = env;
	}

	public String getNetworkName() {
		return env.getRequiredProperty("provider.network.name");
	}

}
