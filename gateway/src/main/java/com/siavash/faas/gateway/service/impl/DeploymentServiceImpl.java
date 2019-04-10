package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.client.ProviderClient;
import com.siavash.faas.gateway.model.ScaleMode;
import com.siavash.faas.gateway.model.ScaleRequest;
import com.siavash.faas.gateway.service.DeploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DeploymentServiceImpl implements DeploymentService {

	private final ProviderClient client;

	public DeploymentServiceImpl(final ProviderClient client) {
		this.client = client;
	}

	@Override
	public Mono<ResponseEntity> scale(String funcName) {
		return client.scale(new ScaleRequest(funcName, ScaleMode.UP));
	}

}
