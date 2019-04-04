package com.siavash.faas.provider.service;

import com.siavash.faas.provider.model.*;

public interface ProviderService {

	String deploy(DeployRequest deploy);

	void remove(RemoveRequest remove);

	void scale(ScaleRequest scale);

	InspectResponse inspect(String name);

	Provider provider();

}
