package com.siavash.faas.gateway.service.impl;

import com.siavash.faas.gateway.service.FunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FunctionServiceImpl implements FunctionService {

	private final WebClient.Builder webClientBuilder;

	public FunctionServiceImpl(final WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	@Override
	public Mono<ResponseEntity<String>> invoke(String funcName, String request) {
		WebClient.RequestBodyUriSpec requestSpec = webClientBuilder
				.baseUrl(constructUrl(funcName))
				.build()
				.post();

		if (!StringUtils.isEmpty(request)) {
			requestSpec.syncBody(request);
		}

		return requestSpec.exchange().flatMap(response -> response.toEntity(String.class));
	}

	private String constructUrl(String funcName) {
		return new StringBuilder("http://").append(funcName).append(":8090").toString();
	}

}
