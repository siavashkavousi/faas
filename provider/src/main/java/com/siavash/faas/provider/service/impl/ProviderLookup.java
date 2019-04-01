package com.siavash.faas.provider.service.impl;

import com.siavash.faas.provider.model.Provider;
import com.siavash.faas.provider.service.ProviderService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProviderLookup {

	private Map<Provider, ProviderService> servicesMap = new HashMap<>();

	public ProviderLookup(List<ProviderService> services) {
		services.forEach(service -> servicesMap.put(service.provider(), service));
	}

	public ProviderService providerService(Provider provider) {
		return servicesMap.get(provider);
	}

}
