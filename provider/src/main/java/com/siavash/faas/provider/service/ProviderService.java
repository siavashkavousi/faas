package com.siavash.faas.provider.service;

import com.siavash.faas.provider.model.DeployRequest;
import com.siavash.faas.provider.model.Provider;
import com.siavash.faas.provider.model.RemoveRequest;

public interface ProviderService {

	String deploy(DeployRequest deploy);

	void remove(RemoveRequest remove);

	Provider provider();

}
