package com.siavash.faas.watchdog.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Configs {

	private final Environment env;

	public Configs(final Environment env) {
		this.env = env;
	}

	public String getFunctionCommand() {
		try {
			return env.getRequiredProperty("fprocess");
		} catch (IllegalStateException ex) {
			throw new RuntimeException("please provide a command to run (Hint: fill fprocess env variable with a convenient command)");
		}
	}

}
