package com.siavash.faas.provider.config;

import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Configs {

	private final Environment env;

	public Configs(final Environment env) {
		this.env = env;
	}

	@Nullable
	public String getServiceNamePrefix() {
		return env.getProperty("provider.service.name.prefix");
	}

	public String getNetworkName() {
		return env.getRequiredProperty("provider.network.name");
	}

	public Integer getScaleMin() {
		return env.getRequiredProperty("provider.scale.min", Integer.class);
	}

	public Integer getScaleMax() {
		return env.getRequiredProperty("provider.scale.max", Integer.class);
	}

}
