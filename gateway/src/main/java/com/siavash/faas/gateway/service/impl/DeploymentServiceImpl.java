package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.client.ProviderClient;
import com.siavash.faas.gateway.model.ScaleMode;
import com.siavash.faas.gateway.model.ScaleRequest;
import com.siavash.faas.gateway.service.DeploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
	public Mono<ResponseEntity> scaleUp(String funcName) {
		return client.scale(new ScaleRequest(funcName, ScaleMode.UP));
	}

	@Override
	public Mono<ResponseEntity> scaleSpecific(String funcName, long specific) {
		return client.inspect(funcName).doOnSuccess(inspect -> {
			if (inspect.getReplicas() > 1) {
				client.scale(new ScaleRequest(funcName, ScaleMode.SPECIFIC, specific));
			} else {
				logger.debug("function: {} is scaled down already", funcName);
			}
		}).flatMap(t -> Mono.just(new ResponseEntity(HttpStatus.NO_CONTENT)));
	}

}
