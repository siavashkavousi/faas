package com.siavash.faas.gateway.client.impl;

import com.siavash.faas.gateway.client.ProviderClient;
import com.siavash.faas.gateway.config.Configs;
import com.siavash.faas.gateway.model.InspectResponse;
import com.siavash.faas.gateway.model.ScaleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProviderClientImpl implements ProviderClient {

	private final WebClient.Builder webClientBuilder;
	private final Configs configs;

	public ProviderClientImpl(WebClient.Builder webClientBuilder, Configs configs) {
		this.webClientBuilder = webClientBuilder;
		this.configs = configs;
	}

	@Override
	public Mono<InspectResponse> inspect(String funcName) {
		return webClientBuilder
				.baseUrl(configs.getProviderBaseUrl())
				.build()
				.get()
				.uri(new StringBuilder("/").append(funcName).toString())
				.exchange().flatMap(response -> response.bodyToMono(InspectResponse.class));
	}

	@Override
	public Mono<ResponseEntity> scale(ScaleRequest request) {
		return webClientBuilder
				.baseUrl(configs.getProviderBaseUrl())
				.build()
				.patch()
				.uri("/scale")
				.body(BodyInserters.fromObject(request))
				.exchange().flatMap(response -> response.toEntity(ResponseEntity.class));
	}

}
